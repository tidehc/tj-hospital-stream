-- 遥测
CREATE SEQUENCE YCATS_CURRENT_SEQ AS BIGINT
    START WITH 1 INCREMENT BY 1 NO MAXVALUE CACHE 1000 CYCLE ORDER;



CREATE TABLE MAXIMO.YCATS_CURRENT
(
    YCATSID    BIGINT NOT NULL,
    IEDNAME    VARGRAPHIC(100 CODEUNITS16),
    COLTIME    TIMESTAMP,
    UA         DECIMAL(31, 2),
    UB         DECIMAL(31, 2),
    UC         DECIMAL(31, 2),
    IA         DECIMAL(31, 2),
    IB         DECIMAL(31, 2),
    IC         DECIMAL(31, 2),
    CREATETIME TIMESTAMP
);


CREATE SEQUENCE YCATS_LOG_SEQ AS BIGINT
    START WITH 1 INCREMENT BY 1 NO MAXVALUE CACHE 1000 CYCLE ORDER;

CREATE TABLE MAXIMO.YCATS_LOG
(
    YCATSID    BIGINT NOT NULL,
    IEDNAME    VARGRAPHIC(100 CODEUNITS16),
    COLTIME    TIMESTAMP,
    UA         DECIMAL(31, 2),
    UB         DECIMAL(31, 2),
    UC         DECIMAL(31, 2),
    IA         DECIMAL(31, 2),
    IB         DECIMAL(31, 2),
    IC         DECIMAL(31, 2),
    CREATETIME TIMESTAMP
);


-- 遥信.sql

CREATE SEQUENCE YXATS_CURRENT_SEQ AS BIGINT
    START WITH 1 INCREMENT BY 1 NO MAXVALUE CACHE 1000 CYCLE ORDER;



CREATE TABLE MAXIMO.YXATS_CURRENT
(
    YXATSID        BIGINT NOT NULL,
    IEDNAME        VARGRAPHIC(100 CODEUNITS16),
    COLTIME        TIMESTAMP,
    VARIABLENAME   VARGRAPHIC(100 CODEUNITS16),
    VAL            INTEGER,
    CREATETIME     TIMESTAMP
);


CREATE SEQUENCE YXATS_LOG_SEQ AS BIGINT
    START WITH 1 INCREMENT BY 1 NO MAXVALUE CACHE 1000 CYCLE ORDER;

CREATE TABLE MAXIMO.YXATS_LOG
(
    YXATSID      BIGINT NOT NULL,
    IEDNAME      VARGRAPHIC(100 CODEUNITS16),
    COLTIME      TIMESTAMP,
    VARIABLENAME VARGRAPHIC(100 CODEUNITS16),
    VAL          INTEGER,
    CREATETIME   TIMESTAMP
);