DROP TABLE bankCustomers;

CREATE TABLE bankCustomers(
    USER_ID number(10) primary key,
    first_name varchar2(255) not null,
    last_name varchar2(255) not null,
    user_name varchar2(255) unique not null,
    user_pass varchar(255) not null
);

CREATE SEQUENCE USER_ID_SEQ
    start with 1000
    increment by 1;
    
CREATE OR REPLACE TRIGGER addCustomerIdTrig
BEFORE INSERT ON bankCustomers
FOR EACH ROW
BEGIN
    IF: new.USER_ID IS NULL THEN
    SELECT USER_ID_SEQ.nextval INTO: new.USER_ID from dual;
    END IF;
END;
/

--will need to update if I want to add in whether the user is an admin or not
CREATE OR REPLACE PROCEDURE addCustomer (first_name IN varchar2, last_name IN varchar2, user_name IN varchar2, user_pass IN varchar2, USER_ID OUT number)
IS
BEGIN
    INSERT INTO bankcustomers (first_name, last_name, user_name, user_pass)
    VALUES (first_name, last_name, user_name, user_pass);
    USER_ID := USER_ID_SEQ.currval;
    commit;
END;
/

SELECT * from bankCustomers;

--create an index using the primary key so that the table is sorted
DROP TABLE bankAccounts;
DROP SEQUENCE BANK_ACCOUNT_ID_SEQ;

CREATE TABLE bankAccounts(
    BANK_ACCOUNT_ID number(10) primary key,
    acc_type varchar2(255) not null,
    balance number(10, 2) not null,
    USER_ID number(10),
    CONSTRAINT USER_ID FOREIGN KEY (USER_ID) REFERENCES bankCustomers(USER_ID)
);

CREATE SEQUENCE BANK_ACCOUNT_ID_SEQ
    start with 1
    increment by 1;
    
CREATE OR REPLACE TRIGGER addBankIdTrig
BEFORE INSERT ON bankAccounts
FOR EACH ROW
BEGIN
    IF :new.BANK_ACCOUNT_ID IS NULL THEN
    SELECT BANK_ACCOUNT_ID_SEQ.nextval INTO :new.BANK_ACCOUNT_ID from dual;
    END IF;
END;
/

CREATE OR REPLACE PROCEDURE addAccount (acc_type IN varchar2, balance IN number, USER_ID IN number, BANK_ACCOUNT_ID OUT number)
IS
BEGIN
    INSERT INTO bankAccounts (acc_type, balance, USER_ID)
    VALUES (acc_type, balance, USER_ID);
    BANK_ACCOUNT_ID := BANK_ACCOUNT_ID_SEQ.currval;
    commit;
END;
/

INSERT INTO bankAccounts VALUES(0, 'checking', 5.55, 1);
INSERT INTO bankAccounts VALUES(2, 'savings', 100.11, 1);
INSERT INTO bankAccounts VALUES(1, 'savings', 10.00, 2);

SELECT * from bankAccounts;

CREATE OR REPLACE PROCEDURE deposit (BANK_ACCOUNT_ID_IN IN number, amount IN number, newbalance OUT number)
IS
BEGIN
    UPDATE bankaccounts
    SET bankaccounts.balance = (bankaccounts.balance + amount)
    WHERE bankaccounts.BANK_ACCOUNT_ID = BANK_ACCOUNT_ID_IN;
    
    SELECT bankaccounts.balance INTO newbalance
    FROM bankAccounts
    WHERE bankaccounts.BANK_ACCOUNT_ID = bank_account_id_in;
    commit;
END;
/

CREATE OR REPLACE PROCEDURE withdraw (BANK_ACCOUNT_ID_IN IN number, amount IN number, newbalance OUT number)
IS
BEGIN
    UPDATE bankaccounts
    SET bankaccounts.balance = (bankaccounts.balance - amount)
    WHERE bankaccounts.BANK_ACCOUNT_ID = BANK_ACCOUNT_ID_IN;
    
    SELECT bankaccounts.balance INTO newbalance
    FROM bankaccounts
    WHERE bankaccounts.BANK_ACCOUNT_ID = bank_account_id_in;
    commit;
END;
/

Select * from bankAccounts;

--SELECT bankAccounts.balance 
--    FROM bankaccounts
--    JOIN bankCustomers ON bankcustomers.user_id = bankaccounts.user_id
--    GROUP BY bankaccounts.user_id
--    HAVING bankaccounts.uesr_id = 1;

