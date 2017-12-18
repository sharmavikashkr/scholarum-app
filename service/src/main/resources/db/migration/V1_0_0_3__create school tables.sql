CREATE TABLE if not exists pc_school (
	id SERIAL PRIMARY KEY NOT NULL,
	created timestamp NOT NULL,
	name varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    mobile varchar(15) NOT NULL,
    access_key varchar(50) NOT NULL,
    secret_key varchar(50) NOT NULL,
    active boolean NOT NULL,
	address_id int REFERENCES sc_address
);

CREATE TABLE if not exists pc_school_user(
	id SERIAL PRIMARY KEY NOT NULL,
	school_id int NOT NULL,
	user_id int NOT NULL
);