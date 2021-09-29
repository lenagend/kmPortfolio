INSERT INTO users (username, password) values ('user1', 'password1');
INSERT INTO users (username, password) values ('user2', 'password2');

INSERT INTO authorities (username, authority) values ('user1', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) values ('user2', 'ROLE_USER');

commit;