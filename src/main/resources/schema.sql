CREATE TABLE if not exists users
(
    username varchar(50) not null primary key,
    password varchar(50) not null,
    enabled char(1) default '1'
);

CREATE TABLE if not exists authorities
(
    username varchar(50) not null,
    authority varchar(50) not null,
    constraint fk_authorities_users
        foreign key(username) references users(username)
);

CREATE unique index ix_auth_username
    on authorities (username, authority);