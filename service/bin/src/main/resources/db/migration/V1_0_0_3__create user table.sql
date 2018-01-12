CREATE TABLE if not exists sc_user (
	id SERIAL PRIMARY KEY NOT NULL,
	created timestamp NOT NULL,
	name varchar(50) DEFAULT NULL,
    email varchar(50) DEFAULT NULL,
    mobile varchar(15) NOT NULL,
    created_by varchar(50) NOT NULL,
    user_type varchar(20) NOT NULL,
    hierarchy varchar(20) NOT NULL,
    password varchar(100) NOT NULL,
    active boolean NOT NULL,
    address_id int REFERENCES sc_address
);

CREATE TABLE if not exists sc_user_role(
	id SERIAL PRIMARY KEY NOT NULL,
	role varchar(50) NOT NULL,
	pc_user int REFERENCES sc_user
);

CREATE TABLE if not exists sc_admin_user(
	id SERIAL PRIMARY KEY NOT NULL,
	admin_id int NOT NULL,
	user_id int NOT NULL
);

CREATE TABLE if not exists sc_school_user(
	id SERIAL PRIMARY KEY NOT NULL,
	school_id int NOT NULL,
	user_id int NOT NULL
);

CREATE TABLE if not exists sc_institution_user(
	id SERIAL PRIMARY KEY NOT NULL,
	institution_id int NOT NULL,
	user_id int NOT NULL
);