CREATE DATABASE fis2demo
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'en_US.utf8'
       LC_CTYPE = 'en_US.utf8'
       CONNECTION LIMIT = -1;


CREATE TABLE employee
(
  id serial NOT NULL,
  address character varying(255),
  name character varying(255),
  CONSTRAINT employee_pkey PRIMARY KEY (id )
);

CREATE TABLE phone
(
  id serial NOT NULL,
  employee_id integer,
  phone character varying(255),
  type character varying(255),
  CONSTRAINT phone_pkey PRIMARY KEY (id ),
  CONSTRAINT fk_hbvecruwjtpc6u53ujlmjpvtw FOREIGN KEY (employee_id)
      REFERENCES employee (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
