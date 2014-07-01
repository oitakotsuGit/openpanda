/**********************************************************************************
 * $URL: https://source.sakaiproject.org/svn/search/tags/sakai-10.0/search-impl/impl/src/java/org/sakaiproject/search/index/impl/StandardAnalyzerFactory.java $
 * $Id: StandardAnalyzerFactory.java 105078 2012-02-24 23:00:38Z ottenhoff@longsight.com $
 ***********************************************************************************
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

package org.sakaiproject.search.index.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;
import org.sakaiproject.search.index.AnalyzerFactory;


/**
 * @author ieb
 */
public class StandardAnalyzerFactory implements AnalyzerFactory
{

	private static final Log log = LogFactory.getLog(SnowballAnalyzerFactory.class);

	private static String[] stopWords = null;
	static
	{
		try
		{
			ArrayList<String> al = new ArrayList<String>();
			BufferedReader br = new BufferedReader(
					new InputStreamReader(
							SnowballAnalyzerFactory.class
									.getResourceAsStream("/org/sakaiproject/search/component/bundle/stopwords.txt")));
			for (String line = br.readLine(); line != null; line = br.readLine())
			{
				al.add(line.trim());
			}
			br.close();
			stopWords = al.toArray(new String[0]);
		}
		catch (Exception ex)
		{
			log.error("Failed to load Stop words into Analyzer", ex);
		}
	}

	public Analyzer newAnalyzer()
	{
		List<String> words = Arrays.asList(stopWords);
		Set<String> wordSet = new HashSet<String>(words);
		return new StandardAnalyzer(Version.LUCENE_29, wordSet);
	}

}
