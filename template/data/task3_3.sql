-- TODO Task 3
drop database if exists kwikee;

create database kwikee;

use kwikee;

create table orders(
    order_id varchar(56) not null,
    date date not null,
    name varchar(128) not null,
    address varchar(256) not null,
    priority boolean not null,
    comments text,

    constraint pk_order_Id primary key(order_id)
);

create table lineItems(
    lineItem_id int auto_increment,
    order_id varchar(56) not null,
    prod_id varchar(128) not null,
    quantity int not null,
    name varchar(128) not null,
    price float not null,

    constraint pk_lineItem_id primary key (lineItem_id),
    constraint fk_order_Id foreign key(order_id)
        references orders(order_id)
);

select "Grant privileges to fred";
grant all privileges on kwikee.* to 'fred'@'%';
flush privileges;
