-- liquibase formatted sql

-- changeset skindrat:1
create table notification_task(
    id serial primary key,
    chat bigint not null,
    message VARCHAR(255) not null,
    send_date_time TIMESTAMP not null
)

