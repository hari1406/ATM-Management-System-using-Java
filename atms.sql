create database atms;
use atms;
CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(10, 2) DEFAULT 0
);
describe accounts;
INSERT INTO accounts (balance) VALUES (10000.00);
show tables;
drop table accounts;
select * from accounts;
ALTER TABLE accounts
ADD COLUMN account_holder_name VARCHAR(100),
ADD COLUMN password VARCHAR(50);
INSERT INTO accounts (account_holder_name, password, balance) VALUES ('John Doe', '123456', 10000.00);
INSERT INTO accounts (account_holder_name, password, balance) VALUES ('Jane Smith', 'password', 100000.00);
INSERT INTO accounts (account_holder_name, password, balance) VALUES ('Alice Johnson', 'secure123', 100000.00);
INSERT INTO accounts (account_holder_name, password, balance) VALUES ('Bob Brown', 'mysecretpass', 100000.00);
