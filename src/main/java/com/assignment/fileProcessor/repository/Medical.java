/*
 * This file is generated by jOOQ.
*/
package com.assignment.fileProcessor.repository;


import com.assignment.fileProcessor.repository.tables.Department;
import com.assignment.fileProcessor.repository.tables.Disease;
import com.assignment.fileProcessor.repository.tables.Doctor;
import com.assignment.fileProcessor.repository.tables.DocumentReport;
import com.assignment.fileProcessor.repository.tables.MedicalRecord;
import com.assignment.fileProcessor.repository.tables.Patient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


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
public class Medical extends SchemaImpl {

    private static final long serialVersionUID = 261998691;

    /**
     * The reference instance of <code>medical</code>
     */
    public static final Medical MEDICAL = new Medical();

    /**
     * The table <code>medical.department</code>.
     */
    public final Department DEPARTMENT = com.assignment.fileProcessor.repository.tables.Department.DEPARTMENT;

    /**
     * The table <code>medical.disease</code>.
     */
    public final Disease DISEASE = com.assignment.fileProcessor.repository.tables.Disease.DISEASE;

    /**
     * The table <code>medical.doctor</code>.
     */
    public final Doctor DOCTOR = com.assignment.fileProcessor.repository.tables.Doctor.DOCTOR;

    /**
     * The table <code>medical.document_report</code>.
     */
    public final DocumentReport DOCUMENT_REPORT = com.assignment.fileProcessor.repository.tables.DocumentReport.DOCUMENT_REPORT;

    /**
     * The table <code>medical.medical_record</code>.
     */
    public final MedicalRecord MEDICAL_RECORD = com.assignment.fileProcessor.repository.tables.MedicalRecord.MEDICAL_RECORD;

    /**
     * The table <code>medical.patient</code>.
     */
    public final Patient PATIENT = com.assignment.fileProcessor.repository.tables.Patient.PATIENT;

    /**
     * No further instances allowed
     */
    private Medical() {
        super("medical", null);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
            Sequences.DEPARTMENT_ID_SEQ,
            Sequences.DISEASE_ID_SEQ,
            Sequences.RECORD_ID_SEQ,
            Sequences.REPORT_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
            Department.DEPARTMENT,
            Disease.DISEASE,
            Doctor.DOCTOR,
            DocumentReport.DOCUMENT_REPORT,
            MedicalRecord.MEDICAL_RECORD,
            Patient.PATIENT);
    }
}