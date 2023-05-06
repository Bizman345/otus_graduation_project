create table product
(
    id  bigint not null primary key,
    subject_name varchar(400),
    brand_name varchar(400)
);

create table price
(
    id   bigserial not null primary key,
    value numeric(16,2),
    product_id bigint references product(id),
    date timestamp,
    sent boolean default false
);

create table users
(
    id   bigserial not null primary key,
    chat_id bigint,
    name varchar(100)
);

create table product_clients
(
    id   bigserial not null primary key,
    user_id bigint references users(id),
    product_id bigint references product(id)
);