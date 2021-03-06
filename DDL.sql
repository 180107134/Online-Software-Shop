--CREATE TABLES
--TABLE MEMBER
----------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE MEMBER (
       member_id NUMBER(20) PRIMARY KEY,
       username  VARCHAR(50) NOT NULL,
       password VARCHAR(20) NOT NULL,
       email VARCHAR(50) NOT NULL,
       fullname VARCHAR(50) NOT NULL,
       address VARCHAR(150) NOT NULL,
       phone VARCHAR(20) NOT NULL,
       gender VARCHAR(1),
       birthdate DATE
);
----------------------------------------------------------------------------------------------------------------------------------------
--TABLE PRODUCT
----------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE PRODUCT (
       product_id NUMBER(20) PRIMARY KEY,
       productname  VARCHAR(100) NOT NULL,
       producttype VARCHAR(100) NOT NULL,
       description VARCHAR(255) NOT NULL,
       stock NUMBER(20) NOT NULL,
       price NUMBER(20) NOT NULL,
       imagesource VARCHAR(255) NOT NULL
);
----------------------------------------------------------------------------------------------------------------------------------------
--TABLE PRODUCTTYPE
CREATE TABLE ProductType (
       TypeID NUMBER(10) PRIMARY KEY,
       description VARCHAR(200) NOT NULL
);  
----------------------------------------------------------------------------------------------------------------------------------------
--TABLE RATINGTYPE
CREATE TABLE RatingType (
       valueid NUMBER(10) PRIMARY KEY,
       description VARCHAR(50) NOT NULL
);
----------------------------------------------------------------------------------------------------------------------------------------
--TABLE RATING
CREATE TABLE RATING (
       rating_id NUMBER(20) PRIMARY KEY,
       member_id NUMBER(20),
       product_id NUMBER(20),
       value NUMBER(20),
);
----------------------------------------------------------------------------------------------------------------------------------------
--TABLE COMMENTS
CREATE TABLE COMMENTS (
       comment_id NUMBER(20) PRIMARY KEY,
       member_id NUMBER(20),
       product_id NUMBER(20),
       message VARCHAR(255),
);

----------------------------------------------------------------------------------------------------------------------------------------
--TABLE TRANSACTIONS
CREATE TABLE TRANSACTIONS (
       transaction_id NUMBER(20) PRIMARY KEY,
       member_id NUMBER(20) NOT NULL,
       product_id NUMBER(20) NOT NULL,
       quantity NUMBER(20) NOT NULL,
       approvalstatus VARCHAR(10),
       transactiondate DATE
);
----------------------------------------------------------------------------------------------------------------------------------------
--TABLE CART
CREATE TABLE CART (
       cart_id NUMBER(20) PRIMARY KEY,
       member_id NUMBER(20) NOT NULL,
       product_id NUMBER(20) NOT NULL,
       quantity NUMBER(20),
       cartdate DATE
);
----------------------------------------------------------------------------------------------------------------------------------------

--CREATE RELATIONSHIPS
----------------------------------------------------------------------------------------------------------------------------------------
--RATING (member_id) -> MEMBER (member_id)
ALTER TABLE RATING
ADD CONSTRAINT FK_RATING_MEMBER FOREIGN KEY (member_id) REFERENCES MEMBER (member_id);
----------------------------------------------------------------------------------------------------------------------------------------
--RATING (product_id) -> PRODUCT (product_id)
ALTER TABLE RATING
ADD CONSTRAINT FK_RATING_PRODUCT FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id);
----------------------------------------------------------------------------------------------------------------------------------------
--RATING (value) -> RATINGTYPE (valueid)
ALTER TABLE RATING
ADD CONSTRAINT FK_RATING_RATINGTYPE FOREIGN KEY (value) REFERENCES RATINGTYPE (valueid);
----------------------------------------------------------------------------------------------------------------------------------------
--COMMENTS (member_id) -> MEMBER (member_id)
ALTER TABLE COMMENTS
ADD CONSTRAINT FK_COMMENTS_MEMBER FOREIGN KEY (member_id) REFERENCES MEMBER (member_id);
----------------------------------------------------------------------------------------------------------------------------------------
--COMMENTS (product_id) -> PRODUCT (product_id)
ALTER TABLE COMMENTS
ADD CONSTRAINT FK_COMMENTS_PRODUCT FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id);
----------------------------------------------------------------------------------------------------------------------------------------
--TRANSACTIONS (member_id) -> MEMBER (member_id)
ALTER TABLE TRANSACTIONS
ADD CONSTRAINT FK_TRANSACTIONS_MEMBER FOREIGN KEY (member_id) REFERENCES MEMBER (member_id);
----------------------------------------------------------------------------------------------------------------------------------------
--TRANSACTIONS (product_id) -> PRODUCT (product_id)
ALTER TABLE TRANSACTIONS
ADD CONSTRAINT FK_TRANSACTIONS_PRODUCT FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id);
----------------------------------------------------------------------------------------------------------------------------------------
--CART (member_id) -> MEMBER (member_id)
ALTER TABLE CART
ADD CONSTRAINT FK_CART_MEMBER FOREIGN KEY (member_id) REFERENCES MEMBER (member_id);
----------------------------------------------------------------------------------------------------------------------------------------
-- CART (product_id) -> PRODUCT (product_id)
ALTER TABLE CART
ADD CONSTRAINT FK_CART_PRODUCT FOREIGN KEY (product_id) REFERENCES PRODUCT (product_id);
----------------------------------------------------------------------------------------------------------------------------------------
-- PRODUCT (producttype) -> PRODUCTTYPE (typeID)
ALTER TABLE PRODUCT
ADD CONSTRAINT FK_PRODUCT_PRODUCTTYPE FOREIGN KEY (producttype) REFERENCES PRODUCTTYPE (TypeID);
----------------------------------------------------------------------------------------------------------------------------------------
