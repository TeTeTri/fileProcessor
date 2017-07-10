/*
 * This file is generated by jOOQ.
*/
package com.assignment.fileProcessor.repository.tables;


import com.assignment.fileProcessor.repository.Keys;
import com.assignment.fileProcessor.repository.Medical;
import com.assignment.fileProcessor.repository.tables.records.DepartmentRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


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
public class Department extends TableImpl<DepartmentRecord> {

    private static final long serialVersionUID = 1545740136;

    /**
     * The reference instance of <code>medical.department</code>
     */
    public static final Department DEPARTMENT = new Department();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DepartmentRecord> getRecordType() {
        return DepartmentRecord.class;
    }

    /**
     * The column <code>medical.department.department_id</code>.
     */
    public final TableField<DepartmentRecord, Integer> DEPARTMENT_ID = createField("department_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('medical.department_id_seq')", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>medical.department.name</code>.
     */
    public final TableField<DepartmentRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * Create a <code>medical.department</code> table reference
     */
    public Department() {
        this("department", null);
    }

    /**
     * Create an aliased <code>medical.department</code> table reference
     */
    public Department(String alias) {
        this(alias, DEPARTMENT);
    }

    private Department(String alias, Table<DepartmentRecord> aliased) {
        this(alias, aliased, null);
    }

    private Department(String alias, Table<DepartmentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Medical.MEDICAL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<DepartmentRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DEPARTMENT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DepartmentRecord> getPrimaryKey() {
        return Keys.DEPARTMENT_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DepartmentRecord>> getKeys() {
        return Arrays.<UniqueKey<DepartmentRecord>>asList(Keys.DEPARTMENT_PKEY, Keys.DEPARTMENT_NAME_UNQ);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Department as(String alias) {
        return new Department(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Department rename(String name) {
        return new Department(name, null);
    }
}
