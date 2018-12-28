 DROP TABLE answers CASCADE;
 DROP TABLE answerstatus CASCADE;
 DROP TABLE bounties CASCADE;
 DROP TABLE bountystatus CASCADE;
 DROP TABLE roles CASCADE;
 DROP TABLE subjects CASCADE;
 DROP TABLE users CASCADE;
 DROP TABLE wallets CASCADE;
 DROP TABLE SubjectsToBounties;
 DROP TABLE AnswersToUsers;
 DROP TABLE BountiesToUsers;
 DROP TABLE Products CASCADE;
 DROP TABLE UserProducts;


CREATE TABLE Roles
(
    role_id 		NUMERIC     PRIMARY KEY,
    role 			VARCHAR(10) NOT NULL
);

CREATE TABLE Wallets
(
    wallet_id		SERIAL     PRIMARY KEY,
    balance			INTEGER    NOT NULL DEFAULT 0
);

CREATE TABLE Users
(
	user_id    	 	SERIAL        	PRIMARY KEY,
	username   		VARCHAR (50)  	NOT NULL UNIQUE,
	password   		VARCHAR (64)  	NOT NULL,
	email      		VARCHAR (150) 	NOT NULL UNIQUE,
	picture	   		VARCHAR (150),
	rating	   		NUMERIC			NOT NULL DEFAULT 0,
	wallet_id		INTEGER			NOT NULL REFERENCES Wallets (wallet_id),
	role_id			INTEGER 		NOT NULL REFERENCES Roles (role_id)
);

CREATE TABLE AnswerStatus
(
    answer_status_id 	NUMERIC     	PRIMARY KEY,
    answer_status  		VARCHAR(20) 	NOT NULL
);

CREATE TABLE Answers
(
    answer_id  		SERIAL 		    PRIMARY KEY,
	user_id         INTEGER         NOT NULL REFERENCES Users(user_id),
    description 	VARCHAR(511)	NOT NULL, 
    submitted   	TIMESTAMP 	    NOT NULL,
    votes			INTEGER 	    NOT NULL DEFAULT 0,
    status_id 		INTEGER 	    NOT NULL REFERENCES AnswerStatus (answer_status_id),
    bounty_id		INTEGER			NOT NULL 
);

CREATE TABLE BountyStatus
(
	bounty_status_id 	NUMERIC     	PRIMARY KEY,
	bounty_status    	VARCHAR(20) 	NOT NULL
);

CREATE TABLE Subjects
(
    subject_id		NUMERIC			PRIMARY KEY,
	subject   		VARCHAR(20)		NOT NULL
);

CREATE TABLE Bounties
(
	bounty_id  			SERIAL 		    PRIMARY KEY,
	title				VARCHAR(127)	NOT NULL,
	description 		VARCHAR(511)	NOT NULL, 
	submitted   		TIMESTAMP,
	amount 	    		INTEGER			NOT NULL DEFAULT 0,
	votes 				INTEGER 		NOT NULL DEFAULT 0,
	expiration	 	    TIMESTAMP		NOT NULL,
	status_id 			INTEGER 		NOT NULL REFERENCES BountyStatus (bounty_status_id),
	correct_answer_id 	INTEGER	    	REFERENCES 	Answers (answer_id),
	picture 			VARCHAR (150), 
	user_id 		 	INTEGER 		NOT NULL REFERENCES Users (user_id)
);

CREATE TABLE SubjectsToBounties
(    
    subjects_to_bounties_id	SERIAL 		PRIMARY KEY,
    bounty_id				INTEGER		NOT NULL REFERENCES Bounties (bounty_id),
    subject_id				INTEGER		NOT NULL REFERENCES Subjects (subject_id)
);


CREATE TABLE AnswersToUsers
(    
    vote_id				SERIAL 		PRIMARY KEY,
    user_id		    	INTEGER		NOT NULL REFERENCES Users (user_id),
    answer_id			INTEGER		NOT NULL REFERENCES Answers (answer_id)
);

CREATE TABLE BountiesToUsers
(    
    vote_id				SERIAL 		PRIMARY KEY,
    user_id		    	INTEGER		NOT NULL REFERENCES Users (user_id),
    bounty_id			INTEGER		NOT NULL REFERENCES Bounties (bounty_id)
);


CREATE TABLE Products 
(
	product_id			SERIAL	    	PRIMARY KEY,
	product_name 		VARCHAR (50)	NOT NULL,
	product_credit		INTEGER			NOT NULL,
	product_cost		INTEGER			NOT NULL
);

CREATE TABLE UserProducts
(
	user_product_id		SERIAL 		PRIMARY KEY,
	user_id				INTEGER		NOT NULL REFERENCES Users (user_id),
	product_id			INTEGER		NOT NULL REFERENCES Products (product_id),
	purchase_date		TIMESTAMP	
);


ALTER TABLE Answers ADD CONSTRAINT bounty_id_fk
	FOREIGN KEY (bounty_id) REFERENCES Bounties (bounty_id) ON DELETE NO ACTION ON UPDATE NO ACTION;


INSERT INTO roles (role_id, role) VALUES(1, 'user');
INSERT INTO roles (role_id, role) VALUES(2, 'admin');

INSERT INTO bountystatus (bounty_status_id , bounty_status) VALUES(1, 'posted');
INSERT INTO bountystatus (bounty_status_id , bounty_status) VALUES(2, 'answered');
INSERT INTO bountystatus (bounty_status_id , bounty_status) VALUES(3, 'expired');

INSERT INTO subjects (subject_id , subject) VALUES(1, 'Math');
INSERT INTO subjects (subject_id , subject) VALUES(2, 'Programming');
INSERT INTO subjects (subject_id , subject) VALUES(3, 'Art');
INSERT INTO subjects (subject_id , subject) VALUES(4, 'Biology');
INSERT INTO subjects (subject_id , subject) VALUES(5, 'Physics');
INSERT INTO subjects (subject_id , subject) VALUES(6, 'Chemistry');
INSERT INTO subjects (subject_id , subject) VALUES(7, 'Calculus');
INSERT INTO subjects (subject_id , subject) VALUES(8, 'Algebra');
INSERT INTO subjects (subject_id , subject) VALUES(9, 'Java');
INSERT INTO subjects (subject_id , subject) VALUES(10, 'Python');
INSERT INTO subjects (subject_id , subject) VALUES(11, 'C');
INSERT INTO subjects (subject_id , subject) VALUES(12, 'History');
INSERT INTO subjects (subject_id , subject) VALUES(13, 'Fashion');
INSERT INTO subjects (subject_id , subject) VALUES(14, 'Life Skills');
INSERT INTO subjects (subject_id , subject) VALUES(15, 'General Help');
INSERT INTO subjects (subject_id , subject) VALUES(16, 'Advice');
INSERT INTO subjects (subject_id , subject) VALUES(17, 'Other');

INSERT INTO answerstatus (answer_status_id,answer_status ) VALUES(1, 'submitted');
INSERT INTO answerstatus (answer_status_id,answer_status ) VALUES(2, 'reported');
INSERT INTO answerstatus (answer_status_id,answer_status ) VALUES(3, 'best');


INSERT INTO products (product_id, product_name, product_credit, product_cost) VALUES(1, 'Basic', 100, 1);
INSERT INTO products (product_id, product_name, product_credit, product_cost) VALUES(2, 'Special', 1100, 10);
INSERT INTO products (product_id, product_name, product_credit, product_cost) VALUES(3, 'Deluxe', 2300, 20);

SELECT * FROM SUBJECTS;
