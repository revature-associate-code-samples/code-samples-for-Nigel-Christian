-- Drop table

-- DROP TABLE ers_reimbursement

CREATE TABLE ers.reimbursement_status (
	reimb_status_id NUMERIC NOT NULL,
	reimb_status VARCHAR (10),
	CONSTRAINT pk_reimb_status PRIMARY KEY (reimb_status_id)
);

CREATE TABLE ers.reimbursement_type (
	reimb_type_id NUMERIC NOT NULL,
	reimb_type VARCHAR (10),
	CONSTRAINT pk_reimb_type PRIMARY KEY (reimb_type_id)
);

CREATE TABLE ers.user_roles (
	ers_user_role_id NUMERIC NOT NULL,
	user_role VARCHAR (10),
	CONSTRAINT pk_ers_user_roles PRIMARY KEY (ers_user_role_id)
);

CREATE TABLE ers_users (
	ers_users_id NUMERIC NOT NULL,
	ers_username VARCHAR (50) NOT NULL,
	ers_password VARCHAR (50) NOT NULL,
	user_first_name VARCHAR (100) NOT NULL,
	user_last_name VARCHAR (100) NOT NULL,
	user_email VARCHAR (150) NOT NULL,
	user_role_id NUMERIC NOT NULL,
	CONSTRAINT pk_ers_users PRIMARY KEY (ers_users_id),
	CONSTRAINT unv1_ers_username UNIQUE (ers_username),
	CONSTRAINT unv1_ers_password UNIQUE (ers_password),
	CONSTRAINT fk_user_roles FOREIGN KEY (user_role_id) REFERENCES 	
	ers.user_roles(ers_user_role_id)
);

CREATE TABLE ers.reimbursement (
	reimb_id NUMERIC NOT NULL,
	reimb_amount NUMERIC NOT NULL,
	reimb_submitted TIMESTAMP NOT NULL,
	reimb_resolved TIMESTAMP,
	reimb_description VARCHAR (250) NOT NULL,
	reimb_reciept BOOLEAN, 
	reimb_author NUMERIC NOT NULL,
	reimb_resolver NUMERIC NOT NULL,
	reimb_status_id NUMERIC NOT NULL,
	reimb_type_id NUMERIC NOT NULL,
	CONSTRAINT pk_reimbursement PRIMARY KEY (reimb_id),
	CONSTRAINT fk_ers_users_auth FOREIGN KEY (reimb_author) REFERENCES 	
	ers.ers_users(ers_users_id),
	CONSTRAINT fk_ers_users_reslvr FOREIGN KEY (reimb_resolver) 
	REFERENCES ers.ers_users(ers_users_id),
	CONSTRAINT fk_ers_reimbursement_status FOREIGN KEY (reimb_status_id) 
	REFERENCES 	ers.reimbursement_status(reimb_status_id),
	CONSTRAINT fk_ers_reimbursement_type FOREIGN KEY (reimb_type_id) 
	REFERENCES 	ers.reimbursement_type(reimb_type_id)
);

--Initialize table for user roles 
INSERT INTO ers.user_roles
(ers_user_role_id, user_role)
VALUES(1, 'manager'),(2,'employee');

--Initialize table for reimbursement status
INSERT INTO ers.reimbursement_status
(reimb_status_id, reimb_status)
VALUES(1, 'pending'),(2,'approved'),(3,'denied');

--Initialize table for reimbursement type 
INSERT INTO ers.reimbursement_type
(reimb_type_id, reimb_type)
VALUES(1, 'lodging'),(2,'travel'),(3,'food'),(4,'other');

--Initialize user table with manager and test account
INSERT INTO ers.ers_users
(ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES(1, 'nchristian', 'e4df62dcf00f1cf79a1a175375bc3dc281fde694f7', 'nigel', 'christian', 'nchristian@gmail.com', 1);
/* example of plaintext password. Don't do this*/
INSERT INTO ers.ers_users
(ers_users_id, ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id)
VALUES(2, 'jdepp', '3993215d55e0595828ea9c70f526dd213540c7ff1d', 'jonny', 'depp', 'jdepp@gmail.com', 2);

INSERT INTO ers.reimbursement
(reimb_id, reimb_amount, reimb_submitted, reimb_resolved, reimb_description, reimb_reciept, reimb_author, reimb_resolver, reimb_status_id, reimb_type_id)
VALUES(1, 20, '2018-11-07', NULL, 'test reimbursement', false, 2, 1, 1, 1);

