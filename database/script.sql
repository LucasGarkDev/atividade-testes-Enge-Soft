create database if not exists test_products;
use test_products;
create table if not exists product(
                        id int not null auto_increment primary key ,
                        name varchar(200) not null,
                        price double not null default 0,
                        quantity int not null default  1,
                        description text default null
);


/*
 ############  CRUD QUERYS

INSERT INTO product(name, price, quantity,description)
VALUES ('Caja de Panqueques',25.50,1,'');

INSERT INTO product(name, price, quantity,description)
VALUES ('Consome',25.50,1,'Bote de Xg');

INSERT INTO product(name, price, quantity,description)
VALUES ('Sopas',1.25,5,'Sopas de sobre variadas');

SELECT id, name, price, quantity, description FROM product;

INSERT INTO product(name, price, quantity, description) VALUES (?,?,?,?);

DELETE FROM product WHERE id = ?;

UPDATE product SET name = 'a',price=10,quantity=2, description='aa' WHERE id = 1;
*/