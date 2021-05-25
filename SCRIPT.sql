/*
	CREAR BASE DE DATOS CON EL NOMBRE: db_sorteo
	CREAR LOGIN ROL CON EL NOMBRE: sorteo
	ASIGNAR PASSWORD AL LOGIN ROL: sorteo
	
	

*/





/*---------------------CREAR LA TABLA DE USUARIOS------------------------*/
CREATE TABLE usuarios
(
  usuarioid serial NOT NULL,
  nrodocumento character varying(10),
  nombrecompleto character varying(100),
  clave character varying(255),
  celular character varying(20),
  estadi character varying(1),
  tipousuario character varying(1),
  CONSTRAINT usuarios_usuarioid_pk PRIMARY KEY (usuarioid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuarios
  OWNER TO postgres;


/*---------------------CREAR LA TABLA DE SORTEOS------------------------*/
CREATE TABLE sorteos
(
  sorteoid serial NOT NULL,
  descripcion character varying(200),
  fechasorteo timestamp with time zone,
  estado character varying(1),
  comentario character varying(255),
  totalventa integer,
  preciorifa integer,
  CONSTRAINT sorteos_sorteoid_ok PRIMARY KEY (sorteoid)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE sorteos
  OWNER TO postgres;


/*---------------------CREAR LA TABLA DE RIFAS------------------------*/
CREATE TABLE rifas
(
  rifaid serial NOT NULL,
  sorteoid integer,
  usuarioid integer,
  nrorifa integer,
  precio integer,
  fechacompra timestamp with time zone,
  CONSTRAINT rifas_rifaid_pk PRIMARY KEY (rifaid),
  CONSTRAINT rifas_fk1 FOREIGN KEY (sorteoid)
      REFERENCES sorteos (sorteoid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT rifas_fk2 FOREIGN KEY (usuarioid)
      REFERENCES usuarios (usuarioid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE rifas
  OWNER TO postgres;

/*---------------------CREAR LA TABLA DE PREMIOS------------------------*/
CREATE TABLE premios
(
  sorteoid integer,
  rifaid integer,
  puesto integer,
  descripcion character varying(200),
  premioid serial NOT NULL,
  CONSTRAINT premios_pk PRIMARY KEY (premioid),
  CONSTRAINT premio_fk1 FOREIGN KEY (rifaid)
      REFERENCES rifas (rifaid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT premio_fk2 FOREIGN KEY (sorteoid)
      REFERENCES sorteos (sorteoid) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE premios
  OWNER TO postgres;
























