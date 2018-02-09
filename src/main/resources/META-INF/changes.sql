create database joorjens_db DEFAULT character set utf8 DEFAULT COLLATE utf8_unicode_ci;

mysqldump -u user -p db_name > backup- file.sql
mysql -u root -p db_name < backup- file.sql

--write changes ...
ALTER TABLE store DROP FOREIGN KEY FK_STORE__AREA_areaCity;
ALTER TABLE store CHANGE COLUMN id_area_city id_area_zone BIGINT;
ALTER TABLE store ADD CONSTRAINT FK_STORE__AREA_areaZone FOREIGN KEY (id_area_zone) REFERENCES area (id);

ALTER TABLE distributor DROP COLUMN contractTypeDetail;
ALTER TABLE distributor DROP COLUMN contractTypeValue;
ALTER TABLE distributor DROP FOREIGN KEY FK_DISTRIBUTOR__OST__settlementType;
ALTER TABLE distributor DROP COLUMN id_settlement_type;

DROP TABLE product;

DROP TABLE distributor_product_price_history;
DROP TABLE distributor_product_price;
DROP TABLE distributor_product_discount;
DROP TABLE distributor_bundling_package_product;
DROP TABLE distributor_bundling_package;
DROP TABLE distributor_discount_package_product;
DROP TABLE distributor_discount_package;
DROP TABLE distributor_product;

DELETE FROM role__permission WHERE id_permission >= 3231 AND id_permission <= 3248;
DELETE FROM permission WHERE id >= 3231 AND id <= 3248;

ALTER TABLE vehicle DROP COLUMN color;
ALTER TABLE distributor DROP COLUMN activityType;
ALTER TABLE store DROP COLUMN activityType;
--UPDATE product_category_type set id = 1230 where id = 1210 and id_parent is NULL;

DROP TABLE valid_ip;
DELETE FROM order_status_type;