-- create user table
-- create password table (hashed, salted, any other techniques added later to secure it

DROP DATABASE IF EXISTS chat_db;

CREATE DATABASE chat_db;

USE chat_db;

CREATE TABLE roles(
	role_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	role_name varchar(20) NOT NULL
);

CREATE TABLE users(
	user_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	user_name varchar(25) NOT NULL,
	pword varchar(100) NOT NULL,
	role_id int,
	CONSTRAINT fk_users_roles_role_id FOREIGN KEY(role_id) REFERENCES roles(role_id)
);

/* These are active (semi-instant) chat rooms which have no original post which use javascript to load new messages 
from the server on an interval timer. Another difference in the web application will be that chat_rooms will likely
be auto-deleted after a period of time, either inactivity in the room or an elapsed time (ie. 7 days) 
to keep the database from filling up
*/
CREATE table chat_rooms(
	chat_room_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	chat_room_name varchar(50) NOT NULL,
	private_room boolean,
	user_id int,	
	CONSTRAINT fk_chat_rooms_users_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

/* These are statically loaded discussion threads which have an original text post and need the page to be refreshed to view new posts */
CREATE table discussion_rooms(
	chat_room_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	chat_room_name varchar(50) NOT NULL,
	private_room boolean,
	original_post_text varchar(300),
	user_id int,	
	CONSTRAINT fk_discussion_rooms_users_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

/* Indicates which users (id'd by their user_id) can access which chat_rooms(chat_room_id) */
CREATE table chat_room_auth_users(
	chat_room_auth_user_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	chat_room_id int,
	CONSTRAINT chat_room_id_auth_users_fk FOREIGN KEY(chat_room_id) REFERENCES chat_rooms(chat_room_id),
	user_id int,
	CONSTRAINT fk_chat_room_auth_users_users_user_id FOREIGN KEY(user_id) REFERENCES users(user_id)
);

/* messages coincide with chat_rooms */
CREATE table messages(
	message_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	chat_room_id int NOT NULL,
	CONSTRAINT fk_messages_chat_rooms_chat_room_id FOREIGN KEY(chat_room_id) REFERENCES chat_rooms(chat_room_id),
	user_id int,
	CONSTRAINT fk_messages_users_user_id FOREIGN KEY(user_id) REFERENCES users(user_id),
	message varchar(300) NOT NULL
);

/* posts coincide with discussion_rooms */
CREATE table posts(
	message_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	chat_room_id int NOT NULL,
	CONSTRAINT fk_posts_chat_rooms_chat_room_id FOREIGN KEY(chat_room_id) REFERENCES chat_rooms(chat_room_id),
	user_id int,
	CONSTRAINT fk_posts_users_user_id FOREIGN KEY(user_id) REFERENCES users(user_id),
	message varchar(300) NOT NULL
);
/*
-- logging table(s)
CREATE table log_type(
	log_type_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	log_type varchar(25)
);

CREATE table log(
	log_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
	CONSTRAINT log_type_id_fk FOREIGN KEY(log_type_id) REFERENCES log_type(log_type_id),
	description varchar(50)
);
*/

GRANT ALL PRIVILEGES ON chat_db.* TO 'chatUser'@'localhost';

/* GRANT SELECT, INSERT, DELETE ON messages TO 'chatUser'@'localhost'; */
/* GRANT SELECT, INSERT, DELETE ON users TO 'chatUser'@'localhost'; */
/* GRANT SELECT, INSERT, DELETE ON chat_rooms TO 'chatUser'@'localhost'; */
/* GRANT SELECT, INSERT, DELETE ON chat_room_auth_users TO 'chatUser'@'localhost'; */
/* GRANT SELECT, INSERT, DELETE ON roles TO 'chatUser'@'localhost'; */
/* GRANT SELECT, INSERT, DELETE ON log TO 'chatUser'@'localhost'; */

INSERT INTO roles (role_name) VALUES("ADMIN");
INSERT INTO roles (role_name) VALUES("USER");

INSERT INTO users (user_name, pword, role_id) VALUES ('admin', '$2b$12$eTv7J/271nfScExbZ91gHeWwW/Q7LNG.InRUuQhXPfm1.yNSYVI2C', 1);
INSERT INTO users (user_name, pword, role_id) VALUES ('dan', '$2b$12$eTv7J/271nfScExbZ91gHeWwW/Q7LNG.InRUuQhXPfm1.yNSYVI2C', 2);

/* --logging is still under construction and is subject to change
INSERT INTO log_type(log_type) VALUES('user');
INSERT INTO log_type(log_type) VALUES('chat room');
INSERT INTO log_type(log_type) VALUES('chat message');
/*
--INSERT INTO chat_room_type(chat_room_type) VALUES('public');
--INSERT INTO chat_room_type(chat_room_type) VALUES('private');
*/
--update users set user_id=1, user_name='dan', pword='{bcrypt}$2b$12$eTv7J/271nfScExbZ91gHeWwW/Q7LNG.InRUuQhXPfm1.yNSYVI2C', authorities='USER' WHERE user_id=1;
