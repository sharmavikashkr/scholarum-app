CREATE TABLE if not exists sc_address (
	id SERIAL PRIMARY KEY NOT NULL,
    address_line1 varchar(255) DEFAULT NULL,
    address_line2 varchar(255) DEFAULT NULL,
    city varchar(30) DEFAULT NULL,
    state varchar(50) DEFAULT NULL,
    country varchar(50) DEFAULT NULL,
    pincode varchar(10) DEFAULT NULL
);

CREATE TABLE if not exists sc_hierarchy (
	id SERIAL PRIMARY KEY NOT NULL,
    name varchar(20) NOT NULL
);

CREATE TABLE if not exists sc_role (
	id SERIAL PRIMARY KEY NOT NULL,
    name varchar(30) NOT NULL,
    hierarchy_id int REFERENCES sc_hierarchy
);

CREATE TABLE if not exists sc_module (
	id SERIAL PRIMARY KEY NOT NULL,
    name varchar(30) NOT NULL
);

CREATE TABLE if not exists sc_role_module (
	id SERIAL PRIMARY KEY NOT NULL,
    role_id int REFERENCES sc_role,
    module_id int REFERENCES sc_module
);

CREATE TABLE if not exists sc_activity (
	id SERIAL PRIMARY KEY NOT NULL,
    name varchar(30) NOT NULL,
    module_id int REFERENCES sc_module
);

CREATE TABLE if not exists sc_role_permission (
	id SERIAL PRIMARY KEY NOT NULL,
    object_id int NOT NULL,
    _add boolean NOT NULL,
    _view boolean NOT NULL,
    _edit boolean NOT NULL,
    _delete boolean NOT NULL,
    activity_id int REFERENCES sc_activity,
    role_module_id int REFERENCES sc_role_module
);