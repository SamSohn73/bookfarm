--
-- DWIT Class 3 Second java project for Small Shopping mall
--
DROP DATABASE IF EXISTS bookfarm;
CREATE DATABASE bookfarm;

USE bookfarm;


DROP TABLE IF EXISTS customer;
CREATE TABLE customer (
	idx				int				NOT NULL auto_increment primary key,
	username		varchar(64)		NOT NULL,
	password		varchar(64)		NOT NULL,
	firstname		varchar(64)		NOT NULL,
	lastname		varchar(64),
	postcode		varchar(64)		NOT NULL,
	address1		varchar(64)		NOT NULL,
	address2		varchar(64)		NOT NULL,
	
	phone1			varchar(64)		NOT NULL,
	phone2			varchar(64),
	phone3			varchar(64),
	email1			varchar(64)		NOT NULL UNIQUE,
	email2			varchar(64)		UNIQUE,
	
	gender			char(1)			NOT NULL,
	newsletter		char(1)			NOT NULL,
	birthday		date			NOT NULL,
	grade			int DEFAULT '0'	NOT NULL,
	last_login		date,
	login_cnt		int(5),
	account_created	date,
	
	on_line			char(1)			NOT NULL
) CHARACTER SET utf8 COLLATE utf8_unicode_ci; -- You should use utf8_unicode_ci for using emoji


DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
	idx				int				NOT NULL auto_increment primary key,
	user_name		varchar(64)		binary NOT NULL,
	user_pass		varchar(64)		NOT NULL
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;


DROP TABLE IF EXISTS category;
CREATE TABLE category (
	idx					int				NOT NULL auto_increment primary key,
	parent_idx			int DEFAULT '0'	NOT NULL,
--	categories_image	varchar(64),
	category_name		varchar(32)		NOT NULL
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;


DROP TABLE IF EXISTS basket;
CREATE TABLE basket (
	idx				int				NOT NULL auto_increment primary key,
	customer_idx	int DEFAULT '0'	NOT NULL,
	product_idx		int				NOT NULL,
	quantity		int				NOT NULL,
	options			varchar(255),
	final_price		decimal(15,4),
	date_added		date DEFAULT now()
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;


DROP TABLE IF EXISTS orders;
CREATE TABLE orders (
	idx						int				NOT NULL auto_increment primary key,
	customers_idx			int				NOT NULL,
	
	delivery_name			varchar(255)	NOT NULL,
	delivery_postcode		varchar(64)		NOT NULL,
	delivery_address1		varchar(64)		NOT NULL,
	delivery_address2		varchar(64)		NOT NULL,
	delivery_phone1			varchar(64)		NOT NULL,
	delivery_phone2			varchar(64),
	delivery_phone3			varchar(64),
	delivery_email1			varchar(64)		NOT NULL UNIQUE,
	delivery_email2			varchar(64)		UNIQUE,
	
	billing_name			varchar(255)	NOT NULL,
	billing_postcode		varchar(64)		NOT NULL,
	billing_address1		varchar(64)		NOT NULL,
	billing_address2		varchar(64)		NOT NULL,
	billing_phone1			varchar(64)		NOT NULL,
	billing_phone2			varchar(64),
	billing_phone3			varchar(64),
	billing_email1			varchar(64)		NOT NULL UNIQUE,
	billing_email2			varchar(64)		UNIQUE,  
	
	payment_method			varchar(255)	NOT NULL,
	final_price				decimal(15,4)	NOT NULL,
	
	last_modified			date,
	date_purchased			date,
	orders_status			int(5)			NOT NULL,
	orders_date_finished	date,
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;


DROP TABLE IF EXISTS orders_product;
CREATE TABLE orders_product (
	idx					int				NOT NULL auto_increment primary key,
	orders_idx			int				NOT NULL,
	products_idx		int				NOT NULL,
	products_quantity	int(2)			NOT NULL,
	final_price			decimal(15,4)	NOT NULL
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;


DROP TABLE IF EXISTS product;
CREATE TABLE product (
	idx					int				NOT NULL auto_increment primary key,
	category_idx		int				NOT NULL,
	product_quantity	int(4)			NOT NULL,
	product_name		varchar(12),
	product_image		varchar(64),
	product_price		decimal(15,4)	NOT NULL,
	product_desc		text
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;


DROP TABLE IF EXISTS review;
CREATE TABLE review (
	idx				int				NOT NULL auto_increment primary key,
	products_idx	int				NOT NULL,
	customers_idx	int,
	reviews_rating	int(1),
	review_title	varchar(255)	NOT NULL,
	review_text		text			NOT NULL,
	date_added		date DEFAULT now(),
	last_modified	date,
	reviews_read	int(5)			NOT NULL default '0'
) CHARACTER SET utf8 COLLATE utf8_unicode_ci;


create table postcode (
	idx				int				auto_increment primary key,
	zip				int				not null unique,
	si_do			varchar(20)		not null,
	si_gu_gun		varchar(20)		not null,
	eup_myun		varchar(20)		not null,
	li				varchar(20)		not null,
	road			varchar(80)		not null,
	longaddr		varchar(1000)	not null
);


--
-- data
--
insert into admin (user_name, user_pass) values ("admin", "12345");

insert into category (category_name) values ("국내도서");
insert into category (category_name) values ("해외도서");
insert into category (category_name) values ("eBook");
insert into category (category_name) values ("웹소설");
insert into category (category_name) values ("CD/LP");
insert into category (category_name) values ("DVD/Bluelay");
insert into category (category_name) values ("영화");
insert into category (category_name) values ("공연");
insert into category (category_name) values ("GIFT");
insert into category (category_name) values ("중고");
insert into category (parent_idx, category_name) values (1, "소설");
insert into category (parent_idx, category_name) values (1, "인문");
insert into category (parent_idx, category_name) values (1, "예술");
insert into category (parent_idx, category_name) values (1, "사회");
insert into category (parent_idx, category_name) values (1, "경제");
insert into category (parent_idx, category_name) values (1, "경영");
insert into category (parent_idx, category_name) values (1, "문학");
insert into category (parent_idx, category_name) values (1, "역사");
insert into category (parent_idx, category_name) values (1, "종교");
insert into category (parent_idx, category_name) values (1, "과학");
insert into category (parent_idx, category_name) values (1, "자기개발");
insert into category (parent_idx, category_name) values (1, "만화");
insert into category (parent_idx, category_name) values (1, "여행");
insert into category (parent_idx, category_name) values (1, "어린이");
insert into category (parent_idx, category_name) values (1, "청소년");
insert into category (parent_idx, category_name) values (1, "요리");
insert into category (parent_idx, category_name) values (1, "육아");
insert into category (parent_idx, category_name) values (1, "가정살림");
insert into category (parent_idx, category_name) values (1, "건강");
insert into category (parent_idx, category_name) values (1, "취미");
insert into category (parent_idx, category_name) values (1, "교재");
insert into category (parent_idx, category_name) values (1, "외국어");
insert into category (parent_idx, category_name) values (1, "IT");
insert into category (parent_idx, category_name) values (1, "수험서");
insert into category (parent_idx, category_name) values (1, "참고서");
insert into category (parent_idx, category_name) values (1, "요리");
insert into category (parent_idx, category_name) values (1, "소설");
insert into category (parent_idx, category_name) values (1, "인문");
insert into category (parent_idx, category_name) values (1, "예술");
insert into category (parent_idx, category_name) values (1, "사회");
insert into category (parent_idx, category_name) values (1, "경제");
insert into category (parent_idx, category_name) values (1, "경영");
insert into category (parent_idx, category_name) values (1, "문학");
insert into category (parent_idx, category_name) values (1, "역사");
insert into category (parent_idx, category_name) values (1, "종교");
insert into category (parent_idx, category_name) values (1, "과학");
insert into category (parent_idx, category_name) values (1, "자기개발");
insert into category (parent_idx, category_name) values (1, "만화");
insert into category (parent_idx, category_name) values (1, "여행");
insert into category (parent_idx, category_name) values (1, "어린이");
insert into category (parent_idx, category_name) values (1, "청소년");
insert into category (parent_idx, category_name) values (1, "요리");
insert into category (parent_idx, category_name) values (1, "육아");
insert into category (parent_idx, category_name) values (1, "가정살림");
insert into category (parent_idx, category_name) values (1, "건강");
insert into category (parent_idx, category_name) values (1, "취미");
insert into category (parent_idx, category_name) values (1, "교재");
insert into category (parent_idx, category_name) values (1, "외국어");
insert into category (parent_idx, category_name) values (1, "IT");
insert into category (parent_idx, category_name) values (1, "수험서");
insert into category (parent_idx, category_name) values (1, "참고서");
insert into category (parent_idx, category_name) values (1, "요리");

insert into category (parent_idx, category_name) values (2, "소설");
insert into category (parent_idx, category_name) values (2, "인문");
insert into category (parent_idx, category_name) values (2, "예술");
insert into category (parent_idx, category_name) values (2, "사회");
insert into category (parent_idx, category_name) values (2, "경제");
insert into category (parent_idx, category_name) values (2, "경영");
insert into category (parent_idx, category_name) values (2, "문학");
insert into category (parent_idx, category_name) values (2, "역사");
insert into category (parent_idx, category_name) values (2, "종교");
insert into category (parent_idx, category_name) values (2, "과학");
insert into category (parent_idx, category_name) values (2, "자기개발");
insert into category (parent_idx, category_name) values (2, "만화");
insert into category (parent_idx, category_name) values (2, "여행");
insert into category (parent_idx, category_name) values (2, "어린이");
insert into category (parent_idx, category_name) values (2, "청소년");
insert into category (parent_idx, category_name) values (2, "요리");
insert into category (parent_idx, category_name) values (2, "육아");
insert into category (parent_idx, category_name) values (2, "가정살림");
insert into category (parent_idx, category_name) values (2, "건강");
insert into category (parent_idx, category_name) values (2, "취미");
insert into category (parent_idx, category_name) values (2, "교재");
insert into category (parent_idx, category_name) values (2, "외국어");
insert into category (parent_idx, category_name) values (2, "IT");
insert into category (parent_idx, category_name) values (2, "수험서");
insert into category (parent_idx, category_name) values (2, "참고서");
insert into category (parent_idx, category_name) values (2, "요리");

insert into category (parent_idx, category_name) values (3, "소설");
insert into category (parent_idx, category_name) values (3, "인문");
insert into category (parent_idx, category_name) values (3, "예술");
insert into category (parent_idx, category_name) values (3, "사회");
insert into category (parent_idx, category_name) values (3, "경제");
insert into category (parent_idx, category_name) values (3, "경영");
insert into category (parent_idx, category_name) values (3, "문학");
insert into category (parent_idx, category_name) values (3, "역사");
insert into category (parent_idx, category_name) values (3, "종교");
insert into category (parent_idx, category_name) values (3, "과학");
insert into category (parent_idx, category_name) values (3, "자기개발");
insert into category (parent_idx, category_name) values (3, "만화");
insert into category (parent_idx, category_name) values (3, "여행");
insert into category (parent_idx, category_name) values (3, "어린이");
insert into category (parent_idx, category_name) values (3, "청소년");
insert into category (parent_idx, category_name) values (3, "요리");
insert into category (parent_idx, category_name) values (3, "육아");
insert into category (parent_idx, category_name) values (3, "가정살림");
insert into category (parent_idx, category_name) values (3, "건강");
insert into category (parent_idx, category_name) values (3, "취미");
insert into category (parent_idx, category_name) values (3, "교재");
insert into category (parent_idx, category_name) values (3, "외국어");
insert into category (parent_idx, category_name) values (3, "IT");
insert into category (parent_idx, category_name) values (3, "수험서");
insert into category (parent_idx, category_name) values (3, "참고서");
insert into category (parent_idx, category_name) values (3, "요리");

insert into category (parent_idx, category_name) values (4, "장르");
insert into category (parent_idx, category_name) values (4, "랭킹");
insert into category (parent_idx, category_name) values (4, "신작");
insert into category (parent_idx, category_name) values (4, "완결");

insert into category (parent_idx, category_name) values (5, "가요");
insert into category (parent_idx, category_name) values (5, "클래식");
insert into category (parent_idx, category_name) values (5, "POP");
insert into category (parent_idx, category_name) values (5, "ROCK");
insert into category (parent_idx, category_name) values (5, "JAZZ");
insert into category (parent_idx, category_name) values (5, "OST");
insert into category (parent_idx, category_name) values (5, "어린이");
insert into category (parent_idx, category_name) values (5, "뉴에이지");
insert into category (parent_idx, category_name) values (5, "종교");
insert into category (parent_idx, category_name) values (5, "명상");
insert into category (parent_idx, category_name) values (5, "국악");
insert into category (parent_idx, category_name) values (5, "일본");
insert into category (parent_idx, category_name) values (5, "세계");

insert into category (parent_idx, category_name) values (6, "액션");
insert into category (parent_idx, category_name) values (6, "SF");
insert into category (parent_idx, category_name) values (6, "드라마");
insert into category (parent_idx, category_name) values (6, "코미디");
insert into category (parent_idx, category_name) values (6, "고전");
insert into category (parent_idx, category_name) values (6, "다큐");
insert into category (parent_idx, category_name) values (6, "교육");
insert into category (parent_idx, category_name) values (6, "성인");
insert into category (parent_idx, category_name) values (6, "뮤직");
insert into category (parent_idx, category_name) values (6, "게임");

insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "불멸", "image/불멸.jpg", 15000, "blabla~");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "레모니스니켓", "image/레모니스니켓.jpg", 13000, "고양이 이야기~");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "아이디얼책", "image/아이디얼책.jpg", 17000, "책 분류 이야기");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "조용한그림책", "image/조용한그림책.jpg", 9000, "조용히 읽으렴");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "흰책", "image/흰책.jpg", 8000, "그냥 표지가 하애보였지만 내용은 정반대");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "brick bible", "image/brick bible.jpg", 9000, "종교 이야기");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "fairy tales", "image/fairy tales.jpg", 15000, "요정 이야기 판타지");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "how you lose her", "image/how you lose her.jpg", 7000, "그녀를 잃어버린 어느 남자의 이야기");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "pantone", "image/pantone.jpg", 15000, "판토네가 무엇인지 아는가? 모른다면 이책을 보거라");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "pictorial", "image/pictorial.jpg", 15000, "팩토리얼이 아니다! 픽토리얼 그것은 무엇을 의미하는가");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "pride", "image/pride.jpg", 15000, "너가 버려왔던 자존심 이야기");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "sartorialist", "image/sartorialist.jpg", 15000, "어느 한적한 오후에...");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "starwars", "image/starwars.jpg", 15000, "영화로도 개봉한 스타워즈의 리메이크판 그 세계관을 엿보고 싶다면...");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "theartbook", "image/theartbook.jpg", 15000, "예술의 세계로 당신을 인도해줍니다.");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "tollbooth", "image/tollbooth.jpg", 15000, "왠지 치과가 가고싶은 사람들을 위한 전혀 상관없는 이야기");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "vagina", "image/vagina.jpg", 15000, "교양있는 사람들을 위한 무언가");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "why we broke up", "image/why we broke up.jpg", 15000, "왜 우리는 살아가야 하는가?");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "witch", "image/witch.jpg", 15000, "실존했던 마녀이야기를 우리의 시각으로 풀어낸...");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "더미", "image/불멸.jpg", 15000, "blabla~");
insert into product (category_idx, product_quantity, product_name, product_image, product_price, product_desc) values (1, 100, "더미2", "image/불멸.jpg", 15000, "blabla~");



--select * from category;
--delete from category where idx > 0;