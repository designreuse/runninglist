/*
 * This file is generated by jOOQ.
 */
package com.metalheart.model.jooq.tables.records;


import com.metalheart.model.jooq.tables.Task;

import java.time.OffsetDateTime;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TaskRecord extends UpdatableRecordImpl<TaskRecord> implements Record6<Integer, String, String, Integer, OffsetDateTime, OffsetDateTime> {

    private static final long serialVersionUID = 2020125070;

    /**
     * Setter for <code>public.task.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.task.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.task.title</code>.
     */
    public void setTitle(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.task.title</code>.
     */
    public String getTitle() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.task.description</code>.
     */
    public void setDescription(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.task.description</code>.
     */
    public String getDescription() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.task.priority</code>.
     */
    public void setPriority(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.task.priority</code>.
     */
    public Integer getPriority() {
        return (Integer) get(3);
    }

    /**
     * Setter for <code>public.task.created_at</code>.
     */
    public void setCreatedAt(OffsetDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.task.created_at</code>.
     */
    public OffsetDateTime getCreatedAt() {
        return (OffsetDateTime) get(4);
    }

    /**
     * Setter for <code>public.task.modified_at</code>.
     */
    public void setModifiedAt(OffsetDateTime value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.task.modified_at</code>.
     */
    public OffsetDateTime getModifiedAt() {
        return (OffsetDateTime) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, Integer, OffsetDateTime, OffsetDateTime> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, String, String, Integer, OffsetDateTime, OffsetDateTime> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Task.TASK.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Task.TASK.TITLE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Task.TASK.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return Task.TASK.PRIORITY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field5() {
        return Task.TASK.CREATED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<OffsetDateTime> field6() {
        return Task.TASK.MODIFIED_AT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component5() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime component6() {
        return getModifiedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getPriority();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value5() {
        return getCreatedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OffsetDateTime value6() {
        return getModifiedAt();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskRecord value2(String value) {
        setTitle(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskRecord value3(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskRecord value4(Integer value) {
        setPriority(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskRecord value5(OffsetDateTime value) {
        setCreatedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskRecord value6(OffsetDateTime value) {
        setModifiedAt(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TaskRecord values(Integer value1, String value2, String value3, Integer value4, OffsetDateTime value5, OffsetDateTime value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached TaskRecord
     */
    public TaskRecord() {
        super(Task.TASK);
    }

    /**
     * Create a detached, initialised TaskRecord
     */
    public TaskRecord(Integer id, String title, String description, Integer priority, OffsetDateTime createdAt, OffsetDateTime modifiedAt) {
        super(Task.TASK);

        set(0, id);
        set(1, title);
        set(2, description);
        set(3, priority);
        set(4, createdAt);
        set(5, modifiedAt);
    }
}