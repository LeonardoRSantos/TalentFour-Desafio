CREATE TABLE Person(
 id INTEGER NOT NULL PRIMARY KEY,
 person_type INTEGER NOT NULL,
 document_number VARCHAR(20) NOT NULL,
 name VARCHAR(150) NOT NULL,
 birth_date DATE NOT NULL,
 created_at TIMESTAMP NOT NULL
);

CREATE TABLE Address(
 id INTEGER PRIMARY KEY NOT NULL,
 street VARCHAR(100) NOT NULL,
 city VARCHAR(100) NOT NULL,
 state VARCHAR(100) NOT NULL
);

CREATE TABLE Cliente(
 id INTEGER PRIMARY KEY NOT NULL,
 name VARCHAR(150) NOT NULL,
 email VARCHAR(100) NOT NULL,
 username VARCHAR(100) NOT NULL,
 address_id INTEGER,
 created_at TIMESTAMP NOT NULL,
 change_date TIMESTAMP NOT NULL
);

CREATE SEQUENCE cliente_seq START WITH 1 INCREMENT BY 1;

-- Sequences para Person
CREATE SEQUENCE person_seq START WITH 1 INCREMENT BY 1;

-- Sequences para Address
CREATE SEQUENCE address_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE user_system (
    id integer not null PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    status INTEGER,
    hash VARCHAR(255)
);

ALTER TABLE user_system ADD CONSTRAINT fk_user_person FOREIGN KEY (id) REFERENCES Person(id);

CREATE TABLE profile (
 id INTEGER NOT NULL PRIMARY KEY,
 name VARCHAR(50) NOT NULL,
 system_name VARCHAR(10) NOT NULL,
 created_at TIMESTAMP NOT NULL
);

CREATE TABLE user_profile (
 id INTEGER PRIMARY KEY,
 user_id INTEGER NOT NULL,
 profile_id INTEGER NOT NULL,
 created_at TIMESTAMP NOT NULL
);

-- Adicionando constraints com ALTER TABLE
ALTER TABLE Cliente ADD CONSTRAINT fk_address FOREIGN KEY (address_id) REFERENCES Address(id) ON DELETE CASCADE;

ALTER TABLE user_profile ADD CONSTRAINT fk_user_user_profile FOREIGN KEY (user_id) REFERENCES user_system(id);
ALTER TABLE user_profile ADD CONSTRAINT fk_profile_user_profile FOREIGN KEY (profile_id) REFERENCES profile(id);

-- Sequence para userProfile
CREATE SEQUENCE profile_seq START WITH 1 INCREMENT BY 1;

-- Sequence para user_profile
CREATE SEQUENCE user_profile_seq START WITH 1 INCREMENT BY 1;
