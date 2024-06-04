CREATE TABLE product_data (
	id UUID DEFAULT (gen_random_uuid()) NOT NULL,
	title varchar NOT NULL,
	price INTEGER NOT NULL,
	CONSTRAINT product_data_pk PRIMARY KEY (id)
);

INSERT INTO product_data (title, price) VALUES
	 ('item1','100'),
	 ('item2','150'),
	 ('item3','200')