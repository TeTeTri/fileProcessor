/*
 * This file is generated by jOOQ.
*/
package com.assignment.fileProcessor.repository.tables.records;


import com.assignment.fileProcessor.repository.tables.DocumentReport;

import java.sql.Timestamp;

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
        "jOOQ version:3.9.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DocumentReportRecord extends UpdatableRecordImpl<DocumentReportRecord> implements Record6<Integer, Timestamp, Integer, Timestamp, String, Boolean> {

    private static final long serialVersionUID = -369182676;

    /**
     * Setter for <code>medical.document_report.report_id</code>.
     */
    public void setReportId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>medical.document_report.report_id</code>.
     */
    public Integer getReportId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>medical.document_report.execution_time</code>.
     */
    public void setExecutionTime(Timestamp value) {
        set(1, value);
    }

    /**
     * Getter for <code>medical.document_report.execution_time</code>.
     */
    public Timestamp getExecutionTime() {
        return (Timestamp) get(1);
    }

    /**
     * Setter for <code>medical.document_report.doctor_id</code>.
     */
    public void setDoctorId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>medical.document_report.doctor_id</code>.
     */
    public Integer getDoctorId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>medical.document_report.process_execution_time</code>.
     */
    public void setProcessExecutionTime(Timestamp value) {
        set(3, value);
    }

    /**
     * Getter for <code>medical.document_report.process_execution_time</code>.
     */
    public Timestamp getProcessExecutionTime() {
        return (Timestamp) get(3);
    }

    /**
     * Setter for <code>medical.document_report.error</code>.
     */
    public void setError(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>medical.document_report.error</code>.
     */
    public String getError() {
        return (String) get(4);
    }

    /**
     * Setter for <code>medical.document_report.document_source</code>.
     */
    public void setDocumentSource(Boolean value) {
        set(5, value);
    }

    /**
     * Getter for <code>medical.document_report.document_source</code>.
     */
    public Boolean getDocumentSource() {
        return (Boolean) get(5);
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
    public Row6<Integer, Timestamp, Integer, Timestamp, String, Boolean> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row6<Integer, Timestamp, Integer, Timestamp, String, Boolean> valuesRow() {
        return (Row6) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return DocumentReport.DOCUMENT_REPORT.REPORT_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field2() {
        return DocumentReport.DOCUMENT_REPORT.EXECUTION_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field3() {
        return DocumentReport.DOCUMENT_REPORT.DOCTOR_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field4() {
        return DocumentReport.DOCUMENT_REPORT.PROCESS_EXECUTION_TIME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return DocumentReport.DOCUMENT_REPORT.ERROR;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Boolean> field6() {
        return DocumentReport.DOCUMENT_REPORT.DOCUMENT_SOURCE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getReportId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value2() {
        return getExecutionTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value3() {
        return getDoctorId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value4() {
        return getProcessExecutionTime();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getError();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean value6() {
        return getDocumentSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentReportRecord value1(Integer value) {
        setReportId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentReportRecord value2(Timestamp value) {
        setExecutionTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentReportRecord value3(Integer value) {
        setDoctorId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentReportRecord value4(Timestamp value) {
        setProcessExecutionTime(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentReportRecord value5(String value) {
        setError(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentReportRecord value6(Boolean value) {
        setDocumentSource(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentReportRecord values(Integer value1, Timestamp value2, Integer value3, Timestamp value4, String value5, Boolean value6) {
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
     * Create a detached DocumentReportRecord
     */
    public DocumentReportRecord() {
        super(DocumentReport.DOCUMENT_REPORT);
    }

    /**
     * Create a detached, initialised DocumentReportRecord
     */
    public DocumentReportRecord(Integer reportId, Timestamp executionTime, Integer doctorId, Timestamp processExecutionTime, String error, Boolean documentSource) {
        super(DocumentReport.DOCUMENT_REPORT);

        set(0, reportId);
        set(1, executionTime);
        set(2, doctorId);
        set(3, processExecutionTime);
        set(4, error);
        set(5, documentSource);
    }
}
