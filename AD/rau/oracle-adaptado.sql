--
-- PROCEDIMIENTOS
--

-- 1. Borrar todos los registros un repartidor contratado
-- ilegalmente por si tenemos que enfrentarnos a una inspección
-- de Hacienda precipitadamente.
CREATE OR REPLACE PROCEDURE
    p_hacienda(v_usuario IN NUMBER)
IS
    v_control_horario NUMBER;
    v_control_geolocalizacion NUMBER;
    v_control_registro NUMBER;
BEGIN
    v_control_horario := f_borrar_registro(horario, 1);
    v_control_geolocalizacion := f_borrar_registro(geolocalizacion, 1);
    v_control_registro := f_borrar_registro(orden_trabajo, 1);

    IF (v_control_horario <> 0) THEN
        DBMS_OUTPUT.PUT_LINE('No existen o no se han podido eliminar los registros de la tabla "horario".')
    ELSE IF (v_control_geolocalizacion <> 0) THEN
        DBMS_OUTPUT.PUT_LINE('No existen o no se han podido eliminar los registros de la tabla "geolocalizacion".')
    ELSE IF (v_control_registro <> 0) THEN
         DBMS_OUTPUT.PUT_LINE('No existen o no se han podido eliminar los registros de la tabla "registro".');
    END IF;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se han encontrado registros.');
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Se ha producido un error.');
END;
/

-- 2. Crear una plantilla de horario y asignarlo a un repartidor
-- ahorrandole tiempo al gerente del establecimiento.
CREATE OR REPLACE PROCEDURE
    p_asignar_horario(v_repartidor IN NUMBER)
IS
    v_plantilla_horario NUMBER;
BEGIN
    v_plantilla_horario := f_crear_horario('19:00PM', '01:00AM');

    INSERT INTO horario (id_repartidor, id_horario) VALUES (v_repartidor, v_plantilla_horario);
EXCEPTION
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Error al buscar el empleado.');
        RETURN NULL;
END;

----------------------------------------------------------------
-- FUNCIONES
--

-- 1. Borrar los registros de una tabla de un usuario en concreto.
CREATE OR REPLACE FUNCTION
    f_borrar_registro(v_tabla IN VARCHAR2, v_repartidor IN NUMBER) RETURN NUMBER
AS
    v_sql VARCHAR2(255);
    v_registros NUMBER;
BEGIN
    v_sql := 'DELETE FROM ' || v_tabla || ' WHERE  id_repartidor = ' || v_repartidor || ';';
    EXECUTE v_sql;
    v_registros := SQL$ROWCOUNT;
    DBMS_OUTPUT.PUT_LINE('Se han eliminado ' || v_registros || ' registros de la tabla "' || v_tabla || '".');

    RETURN v_registros;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se ha encontrado ningún registro relacionado.');
        RETURN 0;
    WHEN TOO_MANY_ROWS THEN
        DBMS_OUTPUT.PUT_LINE('Se han encontrado varias plantillas.');
        RETURN NULL;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('Se ha producido un error.');
        RETURN 0;
END;
/

-- 2. Crear una plantilla de horario genérica.
CREATE OR REPLACE FUNCTION
    f_crear_horario(v_entrada IN VARCHAR, v_salida IN VARCHAR) RETURN NUMBER
AS
    v_horario VARCHAR;
    v_plantilla_id NUMBER;
BEGIN
    v_horario := v_entrada || '-' || v_salida;
    INSERT INTO plantilla_horario VALUES (v_horario, v_horario, v_horario, v_horario, v_horario, null, null);

    SELECT id INTO v_control FROM plantilla_horario WHERE lunes = v_horario AND sabado = NULL AND domingo = NULL;

    IF (v_plantilla_id > 0 ) THEN
        RETURN v_plantilla_id;
    ELSE
        RETURN 0;
EXCEPTION
    WHEN NO_DATA_FOUND THEN
        DBMS_OUTPUT.PUT_LINE('No se ha encontrado la plantilla horaria.');
        RETURN NULL;
    WHEN OTHERS THEN
        DBMS_OUTPUT.PUT_LINE('No se ha podido crear la plantilla de horario.');
        RETURN 0;
END;
/

----------------------------------------------------------------
--
-- TABLAS TRADUCIDAS A ORACLE
--
CREATE TABLE CLIENTE (
    id NUMBER,
    dni VARCHAR2(9),
    nombre VARCHAR2(50) NOT NULL,
    email VARCHAR2(50),
    usuario VARCHAR2(50) NOT NULL,
    clave VARCHAR2(255) NOT NULL,
    CONSTRAINT PK_CLIENTE_ID PRIMARY KEY (id),
    CONSTRAINT UK_CLIENTE_DNI UNIQUE (dni),
    CONSTRAINT UK_CLIENTE_USUARIO UNIQUE (usuario)
);

CREATE TABLE VENTA (
    id NUMBER,
    id_cliente NUMBER,
    envio VARCHAR2(200),
    facturacion VARCHAR2(200) NOT NULL,
    fecha VARCHAR2(200),
    metodo VARCHAR2(200) NOT NULL,
    num_tarjeta VARCHAR2(12),
    gastos_envio VARCHAR2(100),
    CONSTRAINT PK_VENTA_ID PRIMARY KEY (id),
    CONSTRAINT FK_VENTA_ID_CLIENTE FOREIGN KEY (id_cliente) REFERENCES CLIENTE (id),
    CONSTRAINT CK_VENTA_METODO CHECK (metodo IN ('efecitvo', 'online', 'online-premium', 'Otro'))
);

CREATE TABLE REPARTIDOR (
    id NUMBER,
    dni VARCHAR2(9),
    nombre VARCHAR2(50) NOT NULL,
    email VARCHAR2(50),
    CONSTRAINT PK_REPARTIDOR_ID PRIMARY KEY (id),
    CONSTRAINT UK_REPARTIDOR_DNI UNIQUE (dni)
);

CREATE TABLE ORDEN_TRABAJO (
    id NUMBER,
    id_repartidor NUMBER,
    id_venta NUMBER,
    fecha_salida VARCHAR(20),
    fecha_entrega VARCHAR(20),
    CONSTRAINT PK_ORDEN_TRABAJO_ID PRIMARY KEY (id),
    CONSTRAINT FK_ORDEN_TRABAJO_ID_REPARTIDOR FOREIGN KEY (id_repartidor) REFERENCES REPARTIDOR (id),
    CONSTRAINT FK_ORDEN_TRABAJO_ID_VENTA FOREIGN KEY (id_venta) REFERENCES VENTA (id)
);

CREATE TABLE GEOLOCALIZACION (
    id NUMBER,
    id_repartidor NUMBER,
    cord_lat VARCHAR(50) NOT NULL,
    cord_lng VARCHAR(50) NOT NULL,
    fecha VARCHAR(20) NOT NULL,
    CONSTRAINT PK_GEOLOCALIZACION_ID PRIMARY KEY (id),
    CONSTRAINT FK_GEOLOCALIZACION_ID_REPARTIDOR FOREIGN KEY (id_repartidor) REFERENCES REPARTIDOR (id)
);

CREATE TABLE PLANTILLA_HORARIO (
    id NUMBER,
    lunes VARCHAR(50),
    martes VARCHAR(50),
    miercoles VARCHAR(50),
    jueves VARCHAR(50),
    viernes VARCHAR(50),
    sabado VARCHAR(50),
    domingo VARCHAR(50),
    CONSTRAINT PK_PLANTILLA_HORARIO_ID PRIMARY KEY (id)
);

CREATE TABLE HORARIO (
    id NUMBER,
    id_repartidor NUMBER,
    id_horario NUMBER,
    CONSTRAINT PK_HORARIO_ID PRIMARY KEY (id),
    CONSTRAINT FK_HORARIO_ID_REPARTIDOR FOREIGN KEY (id_repartidor) REFERENCES REPARTIDOR (id),
    CONSTRAINT FK_HORARIO_ID_HORARIO FOREIGN KEY (id_horario) REFERENCES PLANTILLA_HORARIO (id)
);

--
-- DATOS
--
INSERT INTO CLIENTE VALUES (1, '12345678A', 'Juan Pérez', 'juanperez@gmail.com', 'juanperez', '123456');
INSERT INTO CLIENTE VALUES (2, '98765432B', 'María González', 'mariagonzalez@gmail.com', 'mariagonzalez', '654321');

INSERT INTO VENTA VALUES (1, 1, 'C/ Amargura de examen 81', 'Juan Peréz', '11/12/2023 08:49:08', 'online-premium', 'ES98564365984', '0.00');

INSERT INTO REPARTIDOR VALUES (1, '85423657A', 'Feliciano Poco', 'fpoco@pedidos.com');

INSERT INTO ORDEN_TRABAJO VALUES (1, 1, 1, '11/12/2023 08:59:08', '11/12/2023 10:45:54');

INSERT INTO GEOLOCALIZACION VALUES (1, 1, '100.74.558.65', '54.856.854.36', '11/12/2022 09:00:00');
INSERT INTO GEOLOCALIZACION VALUES (2, 1, '100.74.100.65', '54.856.700.36', '12/12/2022 09:30:00');

INSERT INTO PLANTILLA_HORARIO VALUES (1, '19:00PM-01:00AM', '19:00PM-01:00AM', '19:00PM-01:00AM', '19:00PM-01:00AM', '19:00PM-01:00AM', null, null);

INSERT INTO HORARIO VALUES (1, 1, 1);