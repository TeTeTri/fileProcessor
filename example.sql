-- EXAMPLE DATABASE

DROP SCHEMA IF EXISTS "medical" CASCADE;

CREATE SCHEMA "medical";

CREATE SEQUENCE "medical"."department_id_seq";
CREATE TABLE "medical"."department" (
    "department_id"     INT             DEFAULT nextval('medical.department_id_seq'),
    "name"              VARCHAR(50)     NOT NULL,
    
    CONSTRAINT "department_pkey"        PRIMARY KEY ("department_id"),
    CONSTRAINT "department_name_unq"    UNIQUE ("name")
);
ALTER SEQUENCE "medical"."department_id_seq" OWNED BY "medical"."department"."department_id";

CREATE TABLE "medical"."doctor" (
    "doctor_id"         INT             ,
    "department_id"     INT             NOT NULL,
    
    CONSTRAINT "doctor_pkey"                    PRIMARY KEY ("doctor_id"),
    CONSTRAINT "doctor_department_id_fkey"      FOREIGN KEY ("department_id")       REFERENCES "medical"."department"
);

CREATE TABLE "medical"."patient" (
    "patient_id"        INT             ,
    "first_name"        VARCHAR(50)     NOT NULL,
    "last_name"         VARCHAR(50)     NOT NULL,
    
    CONSTRAINT "patient_pkey"           PRIMARY KEY ("patient_id")
);

CREATE SEQUENCE "medical"."disease_id_seq";
CREATE TABLE "medical"."disease" (
    "disease_id"        INT             DEFAULT nextval('medical.disease_id_seq'),
    "name"              VARCHAR(50)     NOT NULL,
    
    CONSTRAINT "disease_pkey"           PRIMARY KEY ("disease_id"),
    CONSTRAINT "disease_name_unq"       UNIQUE ("name")
);
ALTER SEQUENCE "medical"."disease_id_seq" OWNED BY "medical"."disease"."disease_id";

CREATE SEQUENCE "medical"."record_id_seq";
CREATE TABLE "medical"."medical_record" (
    "record_id"         INT         DEFAULT nextval('medical.record_id_seq'),
    "doctor_id"         INT         NOT NULL,
    "patient_id"        INT         NOT NULL,
    "disease_id"        INT         NOT NULL,
    
    CONSTRAINT "medical_record_pkey"                PRIMARY KEY ("record_id"),
    CONSTRAINT "medical_record_doctor_id_fkey"      FOREIGN KEY ("doctor_id")       REFERENCES "medical"."doctor",
    CONSTRAINT "medical_record_patient_id_fkey"     FOREIGN KEY ("patient_id")      REFERENCES "medical"."patient",
    CONSTRAINT "medical_record_disease_id_fkey"     FOREIGN KEY ("disease_id")      REFERENCES "medical"."disease",
    CONSTRAINT "medical_record_unq"                 UNIQUE ("doctor_id", "patient_id", "disease_id")
);
ALTER SEQUENCE "medical"."record_id_seq" OWNED BY "medical"."medical_record"."record_id";

CREATE SEQUENCE "medical"."report_id_seq";
CREATE TABLE "medical"."document_report" (
    "report_id"                     INT             DEFAULT nextval('medical.report_id_seq'),
    "execution_time"                TIMESTAMP       NOT NULL,
    "doctor_id"                     INT             NOT NULL,
    "process_execution_time"        TIMESTAMP       NOT NULL,
    "error"                         VARCHAR(50)     ,
    "document_source"               BOOLEAN         NOT NULL,
    
    CONSTRAINT "document_report_pkey"               PRIMARY KEY ("report_id"),
    CONSTRAINT "document_report_doctor_id_fkey"     FOREIGN KEY ("doctor_id")       REFERENCES "medical"."doctor"
);
ALTER SEQUENCE "medical"."report_id_seq" OWNED BY "medical"."document_report"."report_id";

-- EXAMPLE DATA

INSERT INTO "medical"."department" VALUES("marand");

INSERT INTO "medical"."doctor" VALUES(100, 1);

INSERT INTO "medical"."patient" VALUES(1, "Bostjan", "Lah");
INSERT INTO "medical"."patient" VALUES(2, "Boris", "Marn");
INSERT INTO "medical"."patient" VALUES(3, "Anze", "Droljc");

INSERT INTO "medical"."disease" VALUES("nice_to_people");
INSERT INTO "medical"."disease" VALUES("long_legs");
INSERT INTO "medical"."disease" VALUES("used_to_have_dredds");
INSERT INTO "medical"."disease" VALUES("chocaholic");
INSERT INTO "medical"."disease" VALUES("great_haircut");

INSERT INTO "medical"."medical_record" VALUES(100, 1, 1);
INSERT INTO "medical"."medical_record" VALUES(100, 1, 2);
INSERT INTO "medical"."medical_record" VALUES(100, 2, 3);
INSERT INTO "medical"."medical_record" VALUES(100, 2, 1);
INSERT INTO "medical"."medical_record" VALUES(100, 3, 4);
INSERT INTO "medical"."medical_record" VALUES(100, 3, 5);
