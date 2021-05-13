create table observed_devices(
    id bigint primary key,
    name varchar(128) not null,
    password varchar(128) not null,
    unique (name)
);

create table observed_data(
    id bigint primary key,
    observed_id bigint not null,
    foreign key (observed_id) references observed_devices(id),

    time datetime default current_timestamp,
    processor_temperature tinyint,
    processor_load tinyint,
    ram_load tinyint
);

create table users(
    id bigint primary key,
    name varchar(128) not null,
    password varchar(128) not null,
    unique (name)
);

create table groups(
    id bigint primary key,
    name varchar(128) not null,
    entrance_password varchar(128) not null,
    unique (name)
);

create table users_groups(
    id bigint primary key,
    user_id bigint not null,
    group_id bigint not null,
    foreign key (user_id) references users(id),
    foreign key (group_id) references groups(id)
);

create table devices_groups(
    id bigint primary key,
    device_id bigint not null,
    group_id bigint not null,
    foreign key (device_id) references observed_devices(id),
    foreign key (group_id) references groups(id)
);