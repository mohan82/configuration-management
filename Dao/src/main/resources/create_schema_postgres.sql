drop schema cm cascade;
create schema cm;

set search_path=cm;

CREATE TABLE  ENVIRONMENT
(
  ENVIRONMENT_PK SERIAL NOT NULL,
  NAME VARCHAR(200) NOT NULL,
  PRIMARY KEY (ENVIRONMENT_PK)
)TABLESPACE configurationmanagement;

CREATE  UNIQUE INDEX IDX_ENVIRONMENT_NAME ON ENVIRONMENT (NAME);

CREATE TABLE FILE
(
    FILE_PK SERIAL NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    ENVIRONMENT_FK INTEGER  REFERENCES ENVIRONMENT(ENVIRONMENT_PK) NOT NULL,
    PRIMARY KEY (FILE_PK)
) TABLESPACE configurationmanagement;

CREATE UNIQUE INDEX  IDX_FILE_NAME ON FILE(NAME);

CREATE TABLE PROPERTIES
(
    PROPERTIES_PK SERIAL NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    VALUE VARCHAR(200) NOT NULL,
    FILE_FK INTEGER  REFERENCES FILE(FILE_PK) NOT NULL,
    PRIMARY KEY (PROPERTIES_PK)
) TABLESPACE configurationmanagement;

CREATE UNIQUE INDEX  IDX_PROPERTIES_NAME ON PROPERTIES(NAME);

CREATE TABLE PROPERTIES_VERSION
(
    PROPERTIES_VERSION_PK SERIAL NOT NULL,
    NAME VARCHAR(200) NOT NULL,
    OLD_VALUE VARCHAR(200) NOT NULL,
    NEW_VALUE VARCHAR(200) NOT NULL,
    PROPERTIES_FK INTEGER  REFERENCES PROPERTIES (PROPERTIES_PK) NOT NULL,
    PARENT_VERSION INTEGER REFERENCES PROPERTIES_VERSION(PROPERTIES_VERSION_PK),
    PRIMARY KEY ( PROPERTIES_VERSION_PK)
) TABLESPACE configurationmanagement;

CREATE UNIQUE INDEX  IDX_PROPERTIES_OLD_VALUE ON PROPERTIES_VERSION(OLD_VALUE);
CREATE UNIQUE INDEX  IDX_PROPERTIES_NEW_VALUE ON PROPERTIES_VERSION(NEW_VALUE);