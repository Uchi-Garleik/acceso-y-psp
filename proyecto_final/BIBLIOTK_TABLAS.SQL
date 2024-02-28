CREATE TABLE autor (
id CHAR(4),
nombre VARCHAR(30),
apellidos VARCHAR(60),
nacimiento DATE,
CONSTRAINT PK_autor PRIMARY KEY (id),
CONSTRAINT NN_nombre CHECK (nombre IS NOT NULL),
  CONSTRAINT NN_apellidos CHECK (apellidos IS NOT NULL)
);
CREATE TABLE autor (
id CHAR(4),
nombre VARCHAR(30),
apellidos VARCHAR(60),
nacimiento DATE,
CONSTRAINT PK_autor PRIMARY KEY (id),
CONSTRAINT NN_nombre CHECK (nombre IS NOT NULL),
  CONSTRAINT NN_apellidos CHECK (apellidos IS NOT NULL)
);
CREATE TABLE autor_obra (
id_autor CHAR(4),
id_obra CHAR(5),
CONSTRAINT PK_autor_obra PRIMARY KEY (id_autor, id_obra),
CONSTRAINT FK_autor_obra_id_autor FOREIGN KEY (id_autor) REFERENCES autor(id),
CONSTRAINT FK_autor_obra_id_obra FOREIGN KEY (id_obra) REFERENCES obra(id)
);
CREATE TABLE edicion (
id CHAR(6),
id_obra CHAR(5),
isbn VARCHAR(20),
anyo INTEGER,
CONSTRAINT PK_edicion PRIMARY KEY (id),
CONSTRAINT NN_id_obra CHECK (id_obra IS NOT NULL),
CONSTRAINT NN_isbn CHECK (isbn IS NOT NULL),
  CONSTRAINT FK_edicion FOREIGN KEY (id_obra) REFERENCES obra(id)
);
CREATE TABLE ejemplar (
id_edicion CHAR(6),
numero INTEGER,
alta DATE,
baja DATE,
CONSTRAINT PK_ejemplar PRIMARY KEY (id_edicion, numero),
CONSTRAINT FK_ejemplar FOREIGN KEY (id_edicion) REFERENCES edicion(id),
CONSTRAINT NN_alta CHECK (alta IS NOT NULL)
);

CREATE TABLE obra_audit (
    id NUMBER GENERATED ALWAYS AS IDENTITY START WITH 1 INCREMENT BY 1,
    id_obra CHAR(5) UNIQUE NOT NULL,
    titulo VARCHAR2(100),
    anyo NUMBER(38),
    borrado_en TIMESTAMP WITH TIME ZONE
);

ALTER TABLE OBRA_AUDIT ADD CONSTRAINT PK_OBRA_AUDIT_ID PRIMARY KEY (ID);
ALTER TABLE OBRA_AUDIT ADD CONSTRAINT CK_OBRA_AUDIT_ANYO CHECK (anyo BETWEEN 1 AND 2100);