--liquibase formatted sql

--changeset Yeustsihneyeu:create-ticket-table
create table ticket (
    id          uuid not null,
    name        varchar(64) not null,
    action_id   varchar(255) not null,
    cost        int not null,

    primary key(id)
);