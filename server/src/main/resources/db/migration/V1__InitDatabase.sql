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