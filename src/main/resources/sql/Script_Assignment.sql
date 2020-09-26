-- CREATE TABLE
CREATE TABLE users (
    user_no      NUMBER NOT NULL,
    name        NVARCHAR2(50) NOT NULL,
    username    NVARCHAR2(20) NOT NULL,
    password     NVARCHAR2(255) NOT NULL,
    created_by    NVARCHAR2(20) NOT NULL,
    created_on   TIMESTAMP(3) NOT NULL,
    modified_by   NVARCHAR2(20) NOT NULL,
    modified_on   TIMESTAMP(3) NOT NULL,
    CONSTRAINT members_fk PRIMARY KEY ( user_no )
);

CREATE TABLE role (
    ROLE_NO      NUMBER NOT NULL,
    ROLE_NAME    NVARCHAR2(50) NOT NULL,
    DESCRIPTION    NVARCHAR2(20000),
    created_by    NVARCHAR2(20) NOT NULL,
    created_on   TIMESTAMP(3) NOT NULL,
    modified_by   NVARCHAR2(20) NOT NULL,
    modified_on   TIMESTAMP(3) NOT NULL,
    CONSTRAINT role_fk PRIMARY KEY ( ROLE_NO )
);

CREATE TABLE user_role(
    user_role_no      NUMBER NOT NULL,
    user_no      NUMBER NOT NULL,
    role_no      NUMBER NOT NULL,
    created_by    NVARCHAR2(20) NOT NULL,
    created_on   TIMESTAMP(3) NOT NULL,
    modified_by   NVARCHAR2(20) NOT NULL,
    modified_on   TIMESTAMP(3) NOT NULL,
    CONSTRAINT user_role_pk PRIMARY KEY ( user_role_no ),
    CONSTRAINT user_fk FOREIGN KEY (user_no) REFERENCES users(user_no),
    CONSTRAINT role_fk FOREIGN KEY (role_no) REFERENCES role(role_no)
);


CREATE TABLE DOCUMENT (
    DOCUMENT_NO      NUMBER NOT NULL,
    FILE_NAME    NVARCHAR2(255) NOT NULL,
    url    NVARCHAR2(255),
    created_by    NVARCHAR2(20) NOT NULL,
    created_on   TIMESTAMP(3) NOT NULL,
    modified_by   NVARCHAR2(20) NOT NULL,
    modified_on   TIMESTAMP(3) NOT NULL,
    CONSTRAINT document_fk PRIMARY KEY ( DOCUMENT_NO )
);

CREATE SEQUENCE  "SOIBAC1001"."DOCUMENT_NO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
CREATE SEQUENCE  "SOIBAC1001"."USERS_NO_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
CREATE SEQUENCE  "SOIBAC1001"."USER_DOCUMENT_NO_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
CREATE SEQUENCE  "SOIBAC1001"."ROLE_NO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;
CREATE SEQUENCE  "SOIBAC1001"."USER_ROLE_NO_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE  NOKEEP  NOSCALE  GLOBAL ;

-- INSERT DATA
-- USERS
INSERT INTO SOIBAC1001.USERS (USER_NO, USERNAME, PASSWORD, CREATED_ON, CREATED_BY, MODIFIED_BY, MODIFIED_ON, EMAIL, NAME) VALUES (24, 'thang', '$2a$10$qo1TxSd1FcuR8nL8k94XCe4TNyC/NYXWmZEzCnlNylxx48t6yxTTK', TO_TIMESTAMP('2020-08-02 19:43:02.441', 'YYYY-MM-DD HH24:MI:SS.FF3'), 'tien', 'tien', TO_TIMESTAMP('2020-08-02 19:43:02.441', 'YYYY-MM-DD HH24:MI:SS.FF3'), '123@gmail.com', 'thang');
INSERT INTO SOIBAC1001.USERS (USER_NO, USERNAME, PASSWORD, CREATED_ON, CREATED_BY, MODIFIED_BY, MODIFIED_ON, EMAIL, NAME) VALUES (21, 'bao', '$2a$10$4pZmnrmJ1Xmm6JNWH4uLlu9O7uUa.mzDggCKSIVawQj5nNFY9K106', TO_TIMESTAMP('2020-08-02 18:34:24.705', 'YYYY-MM-DD HH24:MI:SS.FF3'), null, null, TO_TIMESTAMP('2020-08-02 18:34:24.705', 'YYYY-MM-DD HH24:MI:SS.FF3'), 'abc@gmail.com', 'bao');

-- ROLES
INSERT INTO SOIBAC1001.ROLE (ROLE_NO, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, DESCRIPTION, ROLE_NAME) VALUES (1, 'bao', TO_TIMESTAMP('2020-07-30 07:27:13.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'bao', TO_TIMESTAMP('2020-07-30 07:27:13.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null, 'ROLE_ADMIN');
INSERT INTO SOIBAC1001.ROLE (ROLE_NO, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, DESCRIPTION, ROLE_NAME) VALUES (2, 'bao', TO_TIMESTAMP('2020-07-30 07:30:35.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'bao', TO_TIMESTAMP('2020-07-30 07:30:35.000000', 'YYYY-MM-DD HH24:MI:SS.FF6'), null, 'ROLE_USER');

-- USER ROLE
INSERT INTO SOIBAC1001.USER_ROLE (USER_ROLE_NO, USER_NO, ROLE_NO, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON) VALUES (6, 21, 2, 'bao', TO_TIMESTAMP('2020-08-03 02:41:36.000', 'YYYY-MM-DD HH24:MI:SS.FF3'), 'bao', TO_TIMESTAMP('2020-08-03 02:41:36.000', 'YYYY-MM-DD HH24:MI:SS.FF3'));
INSERT INTO SOIBAC1001.USER_ROLE (USER_ROLE_NO, USER_NO, ROLE_NO, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON) VALUES (7, 24, 2, 'bao', TO_TIMESTAMP('2020-08-03 02:41:07.000', 'YYYY-MM-DD HH24:MI:SS.FF3'), 'bao', TO_TIMESTAMP('2020-08-03 02:41:07.000', 'YYYY-MM-DD HH24:MI:SS.FF3'));

-- DOCUMENT
INSERT INTO SOIBAC1001.DOCUMENT (DOCUMENT_NO, CREATED_BY, CREATED_ON, MODIFIED_BY, MODIFIED_ON, FILE_NAME, URL) VALUES (1, 'tien', TO_TIMESTAMP('2020-08-02 21:34:45.962000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'tien', TO_TIMESTAMP('2020-08-02 21:34:45.962000', 'YYYY-MM-DD HH24:MI:SS.FF6'), 'ft_batch_xlsx_2019_VIE.xlsx', 'E:\upload\2020-08-02\ft_batch_xlsx_2019_VIE.xlsx');