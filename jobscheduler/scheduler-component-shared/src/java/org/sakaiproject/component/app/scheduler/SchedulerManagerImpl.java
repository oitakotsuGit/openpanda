/**********************************************************************************
 * $URL$
 * $Id$
 **********************************************************************************
 *
 * Copyright (c) 2003, 2004, 2005, 2006, 2007, 2008 The Sakai Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.opensource.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **********************************************************************************/

package org.sakaiproject.component.app.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.sakaiproject.api.app.scheduler.ConfigurableJobProperty;
import org.sakaiproject.api.app.scheduler.ConfigurableJobPropertyValidationException;
import org.sakaiproject.api.app.scheduler.ConfigurableJobPropertyValidator;
import org.sakaiproject.api.app.scheduler.JobBeanWrapper;
import org.sakaiproject.api.app.scheduler.SchedulerManager;
import org.sakaiproject.component.cover.ComponentManager;
import org.sakaiproject.component.api.ServerConfigurationService;
import org.sakaiproject.component.app.scheduler.jobs.SpringConfigurableJobBeanWrapper;
import org.sakaiproject.component.app.scheduler.jobs.SpringInitialJobSchedule;
import org.sakaiproject.component.app.scheduler.jobs.SpringJobBeanWrapper;
import org.sakaiproject.db.api.SqlService;

public class SchedulerManagerImpl implements SchedulerManager
{

  private static final Log LOG = LogFactory.getLog(SchedulerManagerImpl.class);
  public final static String
        SCHEDULER_LOADJOBS      = "scheduler.loadjobs";
  private DataSource dataSource;
  private String serverId;
  private Set<String> qrtzJobs;
  private Map<String, String> qrtzQualifiedJobs = new TreeMap<String, String>(); // map for SelectItems
  /** The properties file from the classpath */
  private String qrtzPropFile;
  /** The properties file from sakai.home */
  private String qrtzPropFileSakai;
  private Properties qrtzProperties;
  private TriggerListener globalTriggerListener;
  private Boolean autoDdl;
  private boolean startScheduler = true;
  private Map<String, JobBeanWrapper> beanJobs = new Hashtable<String, JobBeanWrapper>();

  private static final String JOB_INTERFACE = "org.quartz.Job";
  private static final String STATEFULJOB_INTERFACE = "org.quartz.StatefulJob";
  

  // Service dependencies
  private ServerConfigurationService serverConfigurationService;
  private SchedulerFactory schedFactory;
  private Scheduler scheduler;
  private SqlService sqlService;


  private LinkedList<TriggerListener>
      globalTriggerListeners = new LinkedList<TriggerListener>();
  private LinkedList<JobListener>
      globalJobListeners = new LinkedList<JobListener>();

  private LinkedList<SpringInitialJobSchedule>
      initialJobSchedule = null;

public void init()
  {
    try
    {
      qrtzProperties = initQuartzConfiguration();

      qrtzProperties.setProperty("org.quartz.scheduler.instanceId", serverId);

      // note: becuase job classes are jarred , it is impossible to iterate
      // through a directory by calling listFiles on a file object.
      // Therefore, we need the class list list from spring.

      // find quartz jobs from specified 'qrtzJobs' and verify they
      // that these jobs implement the Job interface
      Iterator<String> qrtzJobsIterator = qrtzJobs.iterator();
      while (qrtzJobsIterator.hasNext())
      {
        String className = (String) qrtzJobsIterator.next();
        Class cl = null;
        try
        {
          cl = Class.forName(className);
        }
        catch (ClassNotFoundException e)
        {
          LOG.warn("Could not locate class: " + className + " on classpath");
        }
        if (cl != null)
        {
          // check that each class implements the Job interface           
          if (doesImplementJobInterface(cl))
          {
            qrtzQualifiedJobs.put(cl.getName(), cl.getName());
          }
          else
          {
            LOG.warn("Class: " + className
                + " does not implement quartz Job interface");
          }
        }
      }
   // run ddl
      if (autoDdl.booleanValue()){
        try
        {
           sqlService.ddl(this.getClass().getClassLoader(), "quartz");
        }
        catch (Throwable t)
        {
          LOG.warn(this + ".init(): ", t);
        }
      }

      boolean isInitialStartup = isInitialStartup(sqlService);
      if (isInitialStartup && autoDdl.booleanValue())
      {
    	  LOG.info("Performing initial population of the Quartz tables.");
    	  sqlService.ddl(this.getClass().getClassLoader(), "init_locks");
      }
      /*
         Determine whether or not to load the jobs defined in the initialJobSchedules list. These jobs will be loaded
         under the following conditions:
            1) the server configuration property "scheduler.loadjobs" is "true"
            2) "scheduler.loadjobs" is "init" and this is the first startup for the scheduler (eg. this is a new Sakai instance)
         "scheduler.loadjobs" is set to "init" by default
       */
      String
          loadJobs = serverConfigurationService.getString(SCHEDULER_LOADJOBS, "init").trim();

      List<SpringInitialJobSchedule>
          initSchedules = getInitialJobSchedules();
      
      boolean
          loadInitSchedules = (initSchedules != null) && (initSchedules.size() > 0) &&
                                (("init".equalsIgnoreCase(loadJobs) && isInitialStartup) ||
                                 "true".equalsIgnoreCase(loadJobs));

      if (loadInitSchedules)
          LOG.debug ("Preconfigured jobs will be loaded");
      else
          LOG.debug ("Preconfigured jobs will not be loaded");
      
      


      // start scheduler and load jobs
      schedFactory = new StdSchedulerFactory(qrtzProperties);
      scheduler = schedFactory.getScheduler();

      // loop through persisted jobs removing both the job and associated
      // triggers for jobs where the associated job class is not found
      String[] arrJobs = scheduler.getJobNames(Scheduler.DEFAULT_GROUP);
      for (int i = 0; i < arrJobs.length; i++)
      {
        try
        {
          JobDetail detail = scheduler.getJobDetail(arrJobs[i], Scheduler.DEFAULT_GROUP);
          String bean = detail.getJobDataMap().getString(JobBeanWrapper.SPRING_BEAN_NAME);
          Job job = (Job) ComponentManager.get(bean);
          if (job == null) {
              LOG.warn("scheduler cannot load class for persistent job:" + arrJobs[i]);
        	  scheduler.deleteJob(arrJobs[i], Scheduler.DEFAULT_GROUP);
              LOG.warn("deleted persistent job:" + arrJobs[i]);
          }
        }
        catch (SchedulerException e)
        {
          LOG.warn("scheduler cannot load class for persistent job:" + arrJobs[i]);
          scheduler.deleteJob(arrJobs[i], Scheduler.DEFAULT_GROUP);
          LOG.warn("deleted persistent job:" + arrJobs[i]);
        }
      }

      for (TriggerListener tListener : globalTriggerListeners)
      {
          scheduler.addGlobalTriggerListener(tListener);
      }

      for (JobListener jListener : globalJobListeners)
      {
          scheduler.addGlobalJobListener(jListener);
      }

      if (loadInitSchedules)
      {
          LOG.debug ("Loading preconfigured jobs");
          loadInitialSchedules();
      }

      //scheduler.addGlobalTriggerListener(globalTriggerListener);
      if (isStartScheduler()) {
          scheduler.start();
      } else {
          LOG.info("Scheduler Not Started, startScheduler=false");
      }
    }
    catch (Exception e)
    {
      LOG.error("Failed to start scheduler.", e);
      throw new IllegalStateException("Scheduler cannot start!", e);
    }

    

  }

  /**
   * This loads the configurations for quartz.
   * It loads the defaults from the classpath and then loads override values from
   * sakai.home.
   * @return The quartz properties.
   * @throws IOException When we can't load the default values.
   */
  private Properties initQuartzConfiguration() throws IOException
  {
    InputStream propertiesInputStream = null;
    Properties properties = new Properties();
    // load the default quartz properties file
    // if this fails we want to propogate the error to stop startup.
    try
    {
      propertiesInputStream = this.getClass().getResourceAsStream(qrtzPropFile);
      properties.load(propertiesInputStream);
    }
    finally
    {
      if (propertiesInputStream != null)
      {
        try
        {
          propertiesInputStream.close();
        }
        catch (IOException e)
        {
          LOG.debug("Failed to close stream.", e);
        }
      }
    }
  
    // load the configuration out of sakai home
    // any failures here shouldn't result in startup failing.
    File file = new File(serverConfigurationService.getSakaiHomePath(), qrtzPropFileSakai);
    if (file.exists() && file.isFile()) {
      try
      {
        propertiesInputStream = new FileInputStream(file);
        properties.load(propertiesInputStream);
        LOG.info("Loaded extra configuration from: "+ file.getAbsolutePath());
      }
      catch (IOException e)
      {
        LOG.warn("Failed to load file: "+ file, e);
      }
      finally
      {
        if (propertiesInputStream != null)
        {
          try
          {
            propertiesInputStream.close();
          }
          catch (IOException e)
          {
            LOG.debug("Failed to close stream.", e);
          }
        }
      }
    }
    return properties;
  }

  private boolean doesImplementJobInterface(Class cl)
  {
    Class[] classArr = cl.getInterfaces();
    for (int i = 0; i < classArr.length; i++)
    {
    	if (classArr[i].getName().equals(JOB_INTERFACE) || 
    			classArr[i].getName().equals(STATEFULJOB_INTERFACE))
      {
        return true;
      }
    }
    return false;
  }

    /**
     * Runs an SQL select statement to determine if the Quartz lock rows exist in the database. If the rows do not exist
     * this method assumes this is the first time the scheduler has been started. The select statement will be defined
     * in the {vendor}/checkTables.sql file within the shared library deployed by this project. The statement should be
     * of the form "SELECT COUNT(*) from QUARTZ_LOCKS;". If the count is zero it is assumed this is a new install. 
     * If the count is non-zero it is assumed the QUARTZ_LOCKS table has been initialized and this is not a new install.
     *
     * @param sqlService
     * @return
     */
  private boolean isInitialStartup(SqlService sqlService)
  {
      String
          checkTablesScript = sqlService.getVendor() + "/checkTables.sql";
      ClassLoader
          loader = this.getClass().getClassLoader();
      String
          chkStmt = null;
      InputStream
          in = null;
      BufferedReader
          r = null;

      try
      {

          // find the resource from the loader
          in = loader.getResourceAsStream(checkTablesScript);

          r = new BufferedReader(new InputStreamReader(in));

          chkStmt = r.readLine();
      }
      catch (Exception e)
      {
          LOG.error("Could not read the file " + checkTablesScript + " to determine if this is a new installation. Preconfigured jobs will only be loaded if the server property scheduler.loadjobs is \"true\"", e);
          return false;
      }
      finally
      {
          try
          {
              r.close();
          }
          catch (Exception e){}
          try
          {
              in.close();
          }
          catch (Exception e){}
      }

      List<String> l = sqlService.dbRead(chkStmt);
      if (l != null && l.size() > 0) 
      {
    	  return (l.get(0).equalsIgnoreCase("0"));
      }
      else 
      {
    	  return false;
      }
  }

    /**
     * Loads jobs and schedules triggers for preconfigured jobs.
     */
  private void loadInitialSchedules()
  {
      for (SpringInitialJobSchedule sched : getInitialJobSchedules())
      {
          SpringJobBeanWrapper
              wrapper = sched.getJobBeanWrapper();

          LOG.debug ("Loading schedule for preconfigured job \"" + wrapper.getJobType() + "\"");

          JobDetail
              jd = new JobDetail (sched.getJobName(), Scheduler.DEFAULT_GROUP, wrapper.getJobClass(), false, true, true);
          JobDataMap
              map = jd.getJobDataMap();

          map.put(JobBeanWrapper.SPRING_BEAN_NAME, wrapper.getBeanId());
          map.put(JobBeanWrapper.JOB_TYPE, wrapper.getJobType());

          if (SpringConfigurableJobBeanWrapper.class.isAssignableFrom(wrapper.getClass()))
          {
              SpringConfigurableJobBeanWrapper
                  confJob = (SpringConfigurableJobBeanWrapper) wrapper;
              ConfigurableJobPropertyValidator
                  validator = confJob.getConfigurableJobPropertyValidator();
              Map<String, String>
                  conf = sched.getConfiguration();
              boolean
                  fail = false;

              for (ConfigurableJobProperty cProp : confJob.getConfigurableJobProperties())
              {
                  String
                      key = cProp.getLabelResourceKey(),
                      val = conf.get(key);

                  LOG.debug ("job property '" + key + "' is set to '" + val + "'");

                  if (val == null && cProp.isRequired())
                  {
                      val = cProp.getDefaultValue();

                      if (val == null)
                      {
                          LOG.error ("job property '" + key + "' is required but has no value; job '" + sched.getJobName() + "' of type '" + wrapper.getJobClass() + "' will not be configured");

                          fail = true;
                          break;
                      }

                      LOG.debug ("job property '" + key + "' set to default value '" + val + "'");
                  }

                  if (val != null)
                  {

                      try
                      {
                          validator.assertValid(key, val);
                      }
                      catch (ConfigurableJobPropertyValidationException cjpve)
                      {
                          LOG.error ("job property '" + key + "' was set to an invalid value '" + val + "'; job '" + sched.getJobName() + "' of type '" + wrapper.getJobClass() + "' will not be configured");

                          fail = true;
                          break;
                      }

                      map.put (key, val);
                  }

              }
              if (fail) continue;
          }

          try
          {
              scheduler.addJob(jd, false);
          }
          catch (SchedulerException e)
          {
              LOG.error ("Failed to schedule job '" + sched.getJobName() + "' of type '" + wrapper.getJobClass() + "'");
              continue;
          }

          Trigger trigger = null;
          try
          {
              trigger = new CronTrigger(sched.getTriggerName(), Scheduler.DEFAULT_GROUP,
                                                jd.getName(), Scheduler.DEFAULT_GROUP,
                                                sched.getCronExpression());
          }
          catch (ParseException e)
          {
              LOG.error ("Error parsing cron exception. Failed to schedule job '" + sched.getJobName() + "' of type '" + wrapper.getJobClass() + "'");
          }

          try
          {
              scheduler.scheduleJob(trigger);
          }
          catch (SchedulerException e)
          {
              LOG.error ("Trigger could not be scheduled. Failed to schedule job '" + sched.getJobName() + "' of type '" + wrapper.getJobClass() + "'");
          }

      }
  }


  /**
   * @see org.sakaiproject.api.app.scheduler.SchedulerManager#destroy()
   */
  public void destroy()
  {
    try{
      if (!scheduler.isShutdown()){
        scheduler.shutdown();
      }
    }
    catch (Throwable t){
      LOG.error("An error occurred while stopping the scheduler", t);
    }
  }


  public List<SpringInitialJobSchedule> getInitialJobSchedules()
  {
      return initialJobSchedule;
  }

  public void setInitialJobSchedules(List<SpringInitialJobSchedule> jobSchedule)
  {
      if(jobSchedule == null || jobSchedule.size() < 1)
        return;
      
      this.initialJobSchedule = new LinkedList<SpringInitialJobSchedule> ();

      initialJobSchedule.addAll(jobSchedule);
  }
  /**
   * @deprecated use {@link #setGlobalTriggerListeners(Set<TriggerListener>)}
   * @return Returns the globalTriggerListener.
   */
  public TriggerListener getGlobalTriggerListener()
  {
    return globalTriggerListener;
  }

  /**
   * @deprecated use {@link #getGlobalTriggerListeners()}
   * @param globalTriggerListener The globalTriggerListener to set.
   */
  public void setGlobalTriggerListener(TriggerListener globalTriggerListener)
  {
    this.globalTriggerListener = globalTriggerListener;

      if (globalTriggerListeners != null)
      {
          globalTriggerListeners.addFirst(globalTriggerListener);
      }
  }

  public void setGlobalTriggerListeners (final List<TriggerListener> listeners)
  {
      globalTriggerListeners.clear();

      if (globalTriggerListener != null)
      {
          globalTriggerListeners.add(globalTriggerListener);
      }

      if (listeners != null)
      {
          globalTriggerListeners.addAll(listeners);
      }
  }

  public List<TriggerListener> getGlobalTriggerListeners()
  {
      return Collections.unmodifiableList(globalTriggerListeners);
  }

  public void setGlobalJobListeners (final List<JobListener> listeners)
  {
      globalJobListeners.clear();

      if (listeners != null)
      {
          globalJobListeners.addAll(listeners);
      }
  }

  public List<JobListener> getGlobalJobListeners()
  {
      return Collections.unmodifiableList(globalJobListeners);
  }

  /**
   * @return Returns the serverId.
   */
  public String getServerId()
  {
    return serverId;
  }

  /**
   * @param serverId The serverId to set.
   */
  public void setServerId(String serverId)
  {
    this.serverId = serverId;
  }

  /**
   * @return Returns the dataSource.
   */
  public DataSource getDataSource()
  {
    return dataSource;
  }

  /**
   * @param dataSource The dataSource to set.
   */
  public void setDataSource(DataSource dataSource)
  {
    this.dataSource = dataSource;
  }

  public void setSqlService(SqlService sqlService)
  {
    this.sqlService = sqlService;
  }

  /**
   * @return Returns the qrtzQualifiedJobs.
   */
  public Map<String, String> getQrtzQualifiedJobs()
  {
    return qrtzQualifiedJobs;
  }

  /**
   * @param qrtzQualifiedJobs The qrtzQualifiedJobs to set.
   */
  public void setQrtzQualifiedJobs(Map<String, String> qrtzQualifiedJobs)
  {
    this.qrtzQualifiedJobs = qrtzQualifiedJobs;
  }

  /**
   * @return Returns the qrtzJobs.
   */
  public Set<String> getQrtzJobs()
  {
    return qrtzJobs;
  }

  /**
   * @param qrtzJobs The qrtzJobs to set.
   */
  public void setQrtzJobs(Set<String> qrtzJobs)
  {
    this.qrtzJobs = qrtzJobs;
  }

  /**
   * @return Returns the qrtzPropFile.
   */
  public String getQrtzPropFile()
  {
    return qrtzPropFile;
  }

  /**
   * @param qrtzPropFile The qrtzPropFile to set.
   */
  public void setQrtzPropFile(String qrtzPropFile)
  {
    this.qrtzPropFile = qrtzPropFile;
  }

  public void setQrtzPropFileSakai(String qrtzPropFileSakai)
  {
    this.qrtzPropFileSakai = qrtzPropFileSakai;
  }

  /**
   * @return Returns the scheduler.
   */
  public Scheduler getScheduler()
  {
    return scheduler;
  }

  /**
   * @param scheduler The sched to set.
   */
  public void setScheduler(Scheduler scheduler)
  {
    this.scheduler = scheduler;
  }

  /**
   * @param serverConfigurationService The ServerConfigurationService to get our configuation from.
   */
  public void setServerConfigurationService(ServerConfigurationService serverConfigurationService)
  {
    this.serverConfigurationService = serverConfigurationService;
  }

  /**
   * @see org.sakaiproject.api.app.scheduler.SchedulerManager#setAutoDdl(java.lang.Boolean)
   */
  public void setAutoDdl(Boolean b)
  {
    autoDdl = b;
  }

   public Map<String, JobBeanWrapper> getBeanJobs() {
      return beanJobs;
   }

   public void registerBeanJob(String jobName, JobBeanWrapper job) {
      getBeanJobs().put(jobName, job);
   }

   public JobBeanWrapper getJobBeanWrapper(String beanWrapperId) {
      return (JobBeanWrapper) getBeanJobs().get(beanWrapperId);
   }

   public boolean isStartScheduler() {
       return startScheduler;
   }

   public void setStartScheduler(boolean startScheduler) {
       this.startScheduler = startScheduler;
   }
}
