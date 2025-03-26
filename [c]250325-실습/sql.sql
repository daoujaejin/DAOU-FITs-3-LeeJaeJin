CREATE TABLE transactions (
    customer_name VARCHAR2(50) NOT NULL,
    stock_name VARCHAR2(50) NOT NULL,
    trade_type VARCHAR2(20) NOT NULL,
    quantity NUMBER(10) NOT NULL,
    price NUMBER(15, 2) NOT NULL
);
