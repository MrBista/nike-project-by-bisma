CREATE TABLE IF NOT EXISTS users(
id INT AUTO_INCREMENT PRIMARY KEY,
username varchar(255) UNIQUE NOT NULL,
email varchar(255) UNIQUE NOT NULL,
first_name varchar(255),
last_name varchar (255),
avatar char,
password varchar(255),
phone_number varchar(255),
birth_of_date date,
created_at timestamp,
updated_at timestamp
);

CREATE TABLE IF NOT EXISTS roles(
id INT AUTO_INCREMENT PRIMARY KEY,
name varchar(255)
);



CREATE TABLE IF NOT EXISTS user_roles(
id INT AUTO_INCREMENT PRIMARY KEY,
user_id INT,
role_id INT,
CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT fk_roles FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE IF NOT EXISTS address_users(
id int AUTO_INCREMENT PRIMARY KEY,
user_id int,
title varchar(255),
jalan varchar(255),
desa varchar(255),
kecamatan varchar(255),
kabupaten varchar(255),
provinsi varchar(255),
negara varchar(255),
code_pos varchar(255),
CONSTRAINT fk_user_address FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS categories(
id int AUTO_INCREMENT PRIMARY KEY,
name varchar(255),
description text,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp
);

CREATE TABLE IF NOT EXISTS sub_categories(
id int AUTO_INCREMENT PRIMARY KEY,
parent_id int,
name varchar(255),
description text,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT fk_categories FOREIGN KEY (parent_id) REFERENCES categories(id)
);

CREATE TABLE IF NOT EXISTS products(
id int AUTO_INCREMENT PRIMARY KEY,
name varchar(255),
description text,
summary varchar(255),
cover varchar (255),
category_id int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT fk_product_to_subcategory FOREIGN KEY (category_id) REFERENCES sub_categories(id)
);

CREATE TABLE IF NOT EXISTS wishlists(
id int AUTO_INCREMENT PRIMARY KEY,
product_id int,
user_id int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT fk_whislist_to_product FOREIGN key(product_id) REFERENCES products(id),
CONSTRAINT fk_whislist_to_users FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS products_attributes(
id int AUTO_INCREMENT PRIMARY KEY,
type_attribute varchar(255),
value varchar(255),
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp
);

CREATE TABLE IF NOT EXISTS products_details(
id int AUTO_INCREMENT PRIMARY KEY,
product_id int,
size_attribute_id int,
color_attribute_id int,
sku varchar(255),
price varchar(255),
quantity int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT fk_products_details_to_product FOREIGN KEY (product_id) REFERENCES products(id),
CONSTRAINT fk_product_detail_size_to_attribute FOREIGN KEY (size_attribute_id) REFERENCES products_attributes(id),
CONSTRAINT fk_product_detail_color_to_attribute FOREIGN KEY (color_attribute_id) REFERENCES products_attributes(id)
);

CREATE TABLE IF NOT EXISTS product_detail_images (
id int AUTO_INCREMENT PRIMARY KEY,
url varchar(255),
product_id int,
product_detail_id int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT fk_product_images FOREIGN KEY (product_id) REFERENCES products(id),
CONSTRAINT fk_product_detail_images FOREIGN KEY (product_detail_id) REFERENCES products_details(id)
);

CREATE TABLE IF NOT EXISTS cart (
id int AUTO_INCREMENT PRIMARY KEY,
user_id int,
total int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT cart_to_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS cart_item (
id int AUTO_INCREMENT PRIMARY KEY,
cart_id int,
product_id int,
product_detail_id int,
quantity int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT cart_item_to_cart FOREIGN KEY (cart_id) REFERENCES cart(id),
CONSTRAINT cart_item_to_product_detail FOREIGN KEY (product_detail_id) REFERENCES products_details(id),
CONSTRAINT cart_item_to_product FOREIGN KEY (product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS payments (
id int AUTO_INCREMENT PRIMARY KEY,
user_id int,
order_id int,
amount int ,
status int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT payment_to_users FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS order_details (
id int AUTO_INCREMENT PRIMARY KEY,
user_id int,
payment_id int,
total int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT order_details_to_user FOREIGN KEY (user_id) REFERENCES users(id),
CONSTRAINT order_details_to_payments FOREIGN KEY (payment_id) REFERENCES payments(id)
);

CREATE TABLE IF NOT EXISTS order_item (
id int AUTO_INCREMENT PRIMARY KEY,
order_id int,
product_id int,
product_detail_id int,
quantity int,
created_at timestamp DEFAULT current_timestamp,
updated_at timestamp,
CONSTRAINT order_item_to_order FOREIGN KEY (order_id) REFERENCES order_details(id),
CONSTRAINT order_item_to_product FOREIGN KEY (product_id) REFERENCES products(id),
CONSTRAINT order_item_to_product_details FOREIGN KEY (product_detail_id) REFERENCES products_details(id)
);

ALTER TABLE roles
ADD COLUMN created_at timestamp DEFAULT current_timestamp;

-- add cascade on delete and on update in product also sub product
-- cascade ketika categori di hapus maka hapus semua sub_categories yang ada tapi jangan dengan product
ALTER TABLE sub_categories
DROP FOREIGN KEY fk_categories;
ALTER TABLE sub_categories
ADD CONSTRAINT fk_categories FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE;
ALTER TABLE products
DROP FOREIGN KEY fk_product_to_subcategory;
ALTER TABLE products
ADD CONSTRAINT fk_product_to_subcategory FOREIGN KEY (category_id) REFERENCES sub_categories(id) ON DELETE SET NULL  ON UPDATE CASCADE ;

-- add cascade ketika user dihapus maka akan menghapus address_users dan juga dengan whislist dan juga punya user_roles
ALTER TABLE address_users
DROP FOREIGN KEY fk_user_address;
ALTER TABLE address_users
ADD CONSTRAINT fk_user_address FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE wishlists
DROP FOREIGN KEY fk_whislist_to_users;
ALTER TABLE wishlists
ADD CONSTRAINT fk_whislist_to_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE user_roles
DROP FOREIGN KEY fk_users;
ALTER TABLE user_roles
ADD CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE;


INSERT INTO roles(name)
VALUES
("SUPER_ADMIN"),
("ADMIN"),
("USER");

-- DELETE FROM users WHERE id = 1;
--
-- INSERT INTO categories  (name,description)
-- VALUES
-- ("shirt", "ini adalah kategori baju"),
-- ("pants", "ini kategori untuk pants"),
-- ("shoes", "ini kategori untuk sepatu");
--
-- INSERT INTO sub_categories (parent_id,name, description)
-- VALUES
-- (3, "Pria", "sepatu khusus pria"),
-- (3, "Wanita", "sepatu khusu wanita"),
-- (3, "trendy", "sepatu yang lagi trend");
--
-- INSERT INTO products (name, description, summary, cover, category_id)
-- VALUES
-- ("nike", "ini nike bos", "ini adalah summary nike", "gambar cover", 6);
--
--
-- DELETE FROM  categories WHERE id = 2;

-- DROP TABLE IF EXISTS user_roles,address_users , roles, users ;




