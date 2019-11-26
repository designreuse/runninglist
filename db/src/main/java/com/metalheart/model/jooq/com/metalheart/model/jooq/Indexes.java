/*
 * This file is generated by jOOQ.
 */
package com.metalheart.model.jooq;


import com.metalheart.model.jooq.tables.FlywaySchemaHistory;
import com.metalheart.model.jooq.tables.RunningListArchive;
import com.metalheart.model.jooq.tables.Task;
import com.metalheart.model.jooq.tables.WeekWorkLog;

import javax.annotation.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index FLYWAY_SCHEMA_HISTORY_PK = Indexes0.FLYWAY_SCHEMA_HISTORY_PK;
    public static final Index FLYWAY_SCHEMA_HISTORY_S_IDX = Indexes0.FLYWAY_SCHEMA_HISTORY_S_IDX;
    public static final Index RUNNING_LIST_ARCHIVE_PKEY = Indexes0.RUNNING_LIST_ARCHIVE_PKEY;
    public static final Index TASK_PKEY = Indexes0.TASK_PKEY;
    public static final Index TASK_TITLE_KEY = Indexes0.TASK_TITLE_KEY;
    public static final Index WEEK_WORK_LOG_PKEY = Indexes0.WEEK_WORK_LOG_PKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index FLYWAY_SCHEMA_HISTORY_PK = Internal.createIndex("flyway_schema_history_pk", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.INSTALLED_RANK }, true);
        public static Index FLYWAY_SCHEMA_HISTORY_S_IDX = Internal.createIndex("flyway_schema_history_s_idx", FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY, new OrderField[] { FlywaySchemaHistory.FLYWAY_SCHEMA_HISTORY.SUCCESS }, false);
        public static Index RUNNING_LIST_ARCHIVE_PKEY = Internal.createIndex("running_list_archive_pkey", RunningListArchive.RUNNING_LIST_ARCHIVE, new OrderField[] { RunningListArchive.RUNNING_LIST_ARCHIVE.YEAR, RunningListArchive.RUNNING_LIST_ARCHIVE.WEEK }, true);
        public static Index TASK_PKEY = Internal.createIndex("task_pkey", Task.TASK, new OrderField[] { Task.TASK.ID }, true);
        public static Index TASK_TITLE_KEY = Internal.createIndex("task_title_key", Task.TASK, new OrderField[] { Task.TASK.TITLE }, true);
        public static Index WEEK_WORK_LOG_PKEY = Internal.createIndex("week_work_log_pkey", WeekWorkLog.WEEK_WORK_LOG, new OrderField[] { WeekWorkLog.WEEK_WORK_LOG.TASK_ID, WeekWorkLog.WEEK_WORK_LOG.DAY_INDEX }, true);
    }
}