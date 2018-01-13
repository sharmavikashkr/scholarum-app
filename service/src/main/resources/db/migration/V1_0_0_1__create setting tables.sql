CREATE TABLE if not exists sc_address (
	id SERIAL PRIMARY KEY NOT NULL,
    address_line1 varchar(255) DEFAULT NULL,
    address_line2 varchar(255) DEFAULT NULL,
    city varchar(30) DEFAULT NULL,
    state varchar(50) DEFAULT NULL,
    country varchar(50) DEFAULT NULL,
    pincode varchar(10) DEFAULT NULL
);