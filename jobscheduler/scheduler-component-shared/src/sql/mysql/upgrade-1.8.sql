-- Upgrade for Quartz 1.6 to 1.8

-- New index.
CREATE INDEX QRTZ_JOB_LISTENERS_NAME_GROUP ON QRTZ_JOB_LISTENERS (JOB_NAME , JOB_GROUP);

-- New index
CREATE INDEX QRTZ_JOB_LISTENERS_NAME_GROUP ON QRTZ_JOB_LISTENERS (JOB_NAME , JOB_GROUP);

-- New index
CREATE INDEX QRTZ_TRIGGERS_NAME_GROUP ON QRTZ_TRIGGERS (TRIGGER_NAME , TRIGGER_GROUP);

-- New index
CREATE INDEX QRTZ_SIMPLE_TRIGGERS_NAME_GROUP ON QRTZ_SIMPLE_TRIGGERS (TRIGGER_NAME , TRIGGER_GROUP);

-- New index
CREATE INDEX QRTZ_CRON_TRIGGERS_NAME_GROUP ON QRTZ_CRON_TRIGGERS (TRIGGER_NAME , TRIGGER_GROUP);

-- New index
CREATE INDEX QRTZ_BLOB_TRIGGERS_NAME_GROUP ON QRTZ_BLOB_TRIGGERS (TRIGGER_NAME , TRIGGER_GROUP);

-- Drop column
ALTER TABLE QRTZ_SCHEDULER_STATE DROP COLUMN RECOVERER;

-- Increase size
ALTER TABLE QRTZ_SIMPLE_TRIGGERS MODIFY TIMES_TRIGGERED BIGINT(10) NOT NULL;

-- Increase size
ALTER TABLE QRTZ_CRON_TRIGGERS MODIFY CRON_EXPRESSION VARCHAR(120) NOT NULL;