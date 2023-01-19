--liquibase formatted sql

--changeset rishat:create-multiple-tables splitStatements:true endDelimiter:;

create table activation_code
(
    activation_code_id int8 not null,
    create_datetime    timestamp,
    external_code_id   int8,
    subscription_id    int8,
    primary key (activation_code_id)
);
create table subscription
(
    id              bigserial not null,
    create_datetime timestamp,
    name            varchar(255),
    phone_number    varchar(255),
    tariff_id       int8,
    primary key (id)
);
create table tariff
(
    tariff_id       bigserial not null,
    create_datetime timestamp,
    name            varchar(255),
    primary key (tariff_id)
);

alter table activation_code add constraint FK6wh8i21swmvp3sq440fk8suk7
    foreign key (subscription_id) references subscription;
alter table subscription add constraint FK9kfe2e1s74cg2i49cdoe4we48
    foreign key (tariff_id) references tariff;

INSERT INTO TARIFF (name, create_datetime)
values ('winter', now());
INSERT INTO TARIFF (name, create_datetime)
values ('summer', now());





