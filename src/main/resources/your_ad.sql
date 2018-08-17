create database your_ad character set utf8 collate utf8_general_ci;

use your_ad;

create table user(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  surname varchar(255) not null ,
  username varchar(255) not null unique ,
  password varchar(255) not null,
  role varchar(255) not null
)engine InnoDB character set utf8 collate utf8_general_ci;

create table category(
  id int not null auto_increment primary key ,
  name varchar(255) not null ,
  parent_id int,
  foreign key (parent_id) references category(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table ad(
  id int not null auto_increment primary key ,
  title varchar(255) not null ,
  description text not null ,
  created_date DATETIME not null ,
  price double not null ,
  imgUrl varchar(255) not null ,
  category_id int not null ,
  user_id int not null ,
  foreign key (category_id) references category(id) on delete cascade ,
  foreign key (user_id) references user(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;

create table image(
  id int not null auto_increment primary key ,
  url varchar(255) not null ,
  ad_id int not null ,
  foreign key (ad_id) references ad(id) on delete cascade
)engine InnoDB character set utf8 collate utf8_general_ci;


insert into category(name, parent_id) values
  ('Vehicles',null ),
  ('Electronics',null ),
  ('Clothing and fashion',null ),
  ('Real estate',null ),
  ('Cars',1),
  ('Motorcycles',1),
  ('Buses',1),
  ('Phones',2),
  ('Smartphones',8),
  ('Cell phones',8),
  ('Computers',2),
  ('Apartments',4),
  ('Houses',4),
  ('Man clothing',3),
  ('Women clothing',3),
  ('Childrens clothing',3);

insert into user(name, surname, username, password, role) values
('ADMIN','ADMIN','admin','admin','ADMIN');