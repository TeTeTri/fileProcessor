/*
 * This file is generated by jOOQ.
*/
package com.assignment.fileProcessor.repository.tables;


import com.assignment.fileProcessor.repository.Keys;
import com.assignment.fileProcessor.repository.Medical;
import com.assignment.fileProcessor.repository.tables.records.DiseaseRecord;

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
public class Disease extends TableImpl<DiseaseRecord> {

    private static final long serialVersionUID = 1193502508;

    /**
     * The reference instance of <code>medical.disease</code>
     */
    public static final Disease DISEASE = new Disease();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<DiseaseRecord> getRecordType() {
        return DiseaseRecord.class;
    }

    /**
     * The column <code>medical.disease.disease_id</code>.
     */
    public final TableField<DiseaseRecord, Integer> DISEASE_ID = createField("disease_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('medical.disease_id_seq')", org.jooq.impl.SQLDataType.INTEGER)), this, "");

    /**
     * The column <code>medical.disease.name</code>.
     */
    public final TableField<DiseaseRecord, String> NAME = createField("name", org.jooq.impl.SQLDataType.VARCHAR.length(50).nullable(false), this, "");

    /**
     * Create a <code>medical.disease</code> table reference
     */
    public Disease() {
        this("disease", null);
    }

    /**
     * Create an aliased <code>medical.disease</code> table reference
     */
    public Disease(String alias) {
        this(alias, DISEASE);
    }

    private Disease(String alias, Table<DiseaseRecord> aliased) {
        this(alias, aliased, null);
    }

    private Disease(String alias, Table<DiseaseRecord> aliased, Field<?>[] parameters) {
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
    public Identity<DiseaseRecord, Integer> getIdentity() {
        return Keys.IDENTITY_DISEASE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<DiseaseRecord> getPrimaryKey() {
        return Keys.DISEASE_PKEY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<DiseaseRecord>> getKeys() {
        return Arrays.<UniqueKey<DiseaseRecord>>asList(Keys.DISEASE_PKEY, Keys.DISEASE_NAME_UNQ);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Disease as(String alias) {
        return new Disease(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Disease rename(String name) {
        return new Disease(name, null);
    }
}
