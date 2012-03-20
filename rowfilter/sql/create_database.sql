drop table contains cascade
drop table products cascade
drop table invoice cascade
create table products (pid integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY, label varchar(255) UNIQUE, purchasePrice double, retailprice double, supplier varchar(255), inSale boolean)
create table invoice (iid integer GENERATED BY DEFAULT AS IDENTITY (START WITH 1) PRIMARY KEY, Date date, total double, waiter varchar(255), time time)
/*to store the sum with the invoice is important because it may not change even if the product price changes after closin the invoice*/
create table contains (iid integer FOREIGN KEY REFERENCES invoice(iid), pid integer FOREIGN KEY REFERENCES products(pid), quantity integer not null, price double not null)