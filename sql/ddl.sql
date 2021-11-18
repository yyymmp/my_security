CREATE TABLE users ( username VARCHAR ( 50 ) NOT NULL PRIMARY KEY, PASSWORD VARCHAR ( 500 ) NOT NULL, enabled boolean NOT NULL );
CREATE TABLE authorities (
                             username VARCHAR ( 50 ) NOT NULL,
                             authority VARCHAR ( 50 ) NOT NULL,
                             CONSTRAINT fk_authorities_users FOREIGN KEY ( username ) REFERENCES users ( username ));
CREATE UNIQUE INDEX ix_auth_username ON authorities ( username, authority )