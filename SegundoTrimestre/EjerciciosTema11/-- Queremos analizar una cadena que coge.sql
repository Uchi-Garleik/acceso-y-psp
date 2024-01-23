-- Queremos analizar una cadena que cogeremos por teclado
-- y que contendrá los dos apellidos de una persona y
-- querremos almacenar únicamente el primer apellido en una segunda variable.
-- Intentaremos realizar el ejercicio analizando carácter a carácter la cadena de entrada utilizando un WHILE
CREATE OR REPLACE FUNCTION ApellidosPersona (apellidos VARCHAR2) RETURN VARCHAR2 IS
    primerApellido VARCHAR2(60);
    contador NUMBER;
BEGIN
    primerApellido := '';
    contador := 1;
    WHILE contador <= LENGTHB(apellidos) AND SUBSTR(apellidos /*CHAR*/, contador /*POSITION*/, 1 /*SUBSTRING_LENGTH*/) <> ' ' LOOP
        primerApellido := primerApellido || SUBSTR(apellidos /*CHAR*/, contador /*POSITION*/, 1 /*SUBSTRING_LENGTH*/);
        contador := contador + 1;
    END LOOP;
    return primerApellido;
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(ApellidosPersona('Serna Rosales'));
END;
/

-- Queremos reprogramar el ejemplo anterior.
-- Para ello lo que queremos utilizar es la función "SUBSTR"
-- de modo que nos devuelva UNICAMENTE un carácter de modo que vayamos
-- analizando la cadena de entrada carácter a carácter como en el caso anterior.
-- Intentaremos realizar el ejercicio con un LOOP.
CREATE OR REPLACE FUNCTION ApellidosPersona2 (apellidos VARCHAR2) RETURN VARCHAR2 IS
BEGIN
    return SUBSTR(apellidos , 1, INSTR(apellidos, ' ', 1));
END;
/
DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(ApellidosPersona2('Serna Rosales'));
END;
/
-- Escribe un procedimiento que reciba dos numeros y visualice su SUMA
CREATE OR REPLACE PROCEDURE sumaNumeros (num1 NUMBER, num2 NUMBER) IS
    sumaNumeros NUMBER;
BEGIN
    sumaNumeros := num1 + num2;
    DBMS_OUTPUT.PUT_LINE(sumaNumeros);
END;
/

-- Codifica un procedimiento que reciba una cadena y la visualice al revés
CREATE OR REPLACE PROCEDURE ReverseString(texto VARCHAR2) IS
    revertedString VARCHAR2(40);
BEGIN
    FOR i IN REVERSE 1..LENGTH(texto) LOOP
        revertedString := revertedString || SUBSTR(texto, i, 1);
    END LOOP;
    DBMS_OUTPUT.PUT_LINE(revertedString);
END;
/
DECLARE
BEGIN
    ReverseString('hola');
END;
/

-- Reescribe el codigo de los dos ejercicios anteriores para convertirlos en funciones
-- que retornen los valores que mostraban los PROCEDIMIENTOS

-- Escribe una función que reciba una fecha y devuelva el año, en numero, correspondiente a esa fecha
CREATE OR REPLACE FUNCTION GetYear(Fecha DATE) RETURN NUMBER IS
BEGIN
    return EXTRACT(YEAR FROM Fecha);
END;
/

-- Escribe un bloque PLSQL que haga uso de la función anterior
DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE('Han pasado: ' || ABS(2000 - GetYear(TO_DATE('2024-01-01', 'YYYY/MM/DD'))) || ' años desde el 2000' );
END;
/

-- Desarrolla una función que devuelva el numero de años completos que hay entre dos fechas que se pasan como parametros
CREATE OR REPLACE FUNCTION RETURNFULLYEARS(FECHA1 DATE, FECHA2 DATE) RETURN NUMBER IS
    p_years NUMBER;
BEGIN
    p_years := ABS(EXTRACT(YEAR FROM FECHA1) - EXTRACT(YEAR FROM FECHA2));
    IF ( MONTHS_BETWEEN(FECHA1, FECHA2) < 12 ) THEN
        p_years := p_years - 1;
    END IF;
    RETURN p_years;
END;
/

-- Escribe una función, que, haciendo uso de la función anterior, devuelva los trienios que hay entre dos fechas
CREATE OR REPLACE FUNCTION TRIENIOS(FECHA1 DATE, FECHA2 DATE) RETURN NUMBER IS
BEGIN
    RETURN RETURNFULLYEARS(FECHA1, FECHA2) / 3;
END;
/

-- Codifica un procedimiento que reciba una lista de hasta 5 numeros y visualice su suma
CREATE OR REPLACE PROCEDURE SUMA5(NUM1 NUMBER DEFAULT 0, NUM2 NUMBER DEFAULT 0, NUM3 NUMBER DEFAULT 0, NUM4 NUMBER DEFAULT 0, NUM5 NUMBER DEFAULT 0) IS
BEGIN
    DBMS_OUTPUT.PUT_LINE(NUM1 + NUM2 + NUM3 + NUM4 + NUM5);
END;
/


-- Escribe una función que deuvelva solamente caracteres alfabeticos sustituyendo cualquier otro caracter
-- por blancos a partir de una cadena que se pasará en la llamada
CREATE OR REPLACE FUNCTION ALFAB(CADENA VARCHAR2) RETURN VARCHAR2 IS
    nuevoText VARCHAR2(30);
BEGIN
    FOR I IN 1..LENGTH(CADENA) LOOP
        IF REGEXP_LIKE(SUBSTR(CADENA, I, 1), '[[:alpha:]]') THEN
            nuevoText := nuevoText || SUBSTR(CADENA, I, 1);
            ELSE
                nuevoText := nuevoText || ' ';
        END IF;
    END LOOP;
    RETURN nuevoText;
END;
/

-- Codifica un procedimiento que permita borrar un empleado cuyo numero se pasara en la llamada
CREATE OR REPLACE PROCEDURE BORRAREMP (IDEMP NUMBER, TABLA VARCHAR2) IS
    p_SQL VARCHAR2(200);
BEGIN
    p_SQL := 'DELETE FROM ' || TABLA || ' WHERE ID = ' || IDEMP;
    EXECUTE IMMEDIATE p_SQL;
END;
/


-- Escribe un procedimiento que modifique la localidad de un departamento. El procedimiento recibira como parametro
-- El numero del departamento y la nueva localidad

CREATE OR REPLACE FUNCTION MODIFICARLOCALIDADDEP (IDDEP IN NUMBER, IDLOC IN NUMBER) RETURN NUMBER AS
    p_SQL VARCHAR2(200);
    p_Rows NUMBER;
BEGIN
    p_SQL := ' UPDATE DEPARTAMENTO DEP SET DEP.ID_LOC = :IDLOC WHERE DEP.ID_DEP = :IDDEP';
    EXECUTE IMMEDIATE p_SQL USING IDDEP, IDLOC;
        IF SQL%FOUND THEN
            DBMS_OUTPUT.PUT_LINE('Se han actualizado ' || SQL%ROWCOUNT || ' campos');
            ELSE
            DBMS_OUTPUT.PUT_LINE('NO DATA FOUND');
        END IF;
    RETURN SQL%ROWCOUNT;
    EXCEPTION
        WHEN TOO_MANY_ROWS THEN
            DBMS_OUTPUT.PUT_LINE('Se ha intentando actualizar más de un campo a la vez');
            return 1;
        WHEN OTHERS THEN
            DBMS_OUTPUT.PUT_LINE('Ha habido un problema');
            RETURN 12345;
END;
/

CREATE OR REPLACE FUNCTION GETDEPLOCINFO(RETRIEVEDROWS OUT NUMBER) RETURN NUMBER AS
p_NombreDep VARCHAR2(200);
p_NombreLoc VARCHAR2(200);
CURSOR c_DepLoc IS SELECT DEP.NOMBRE AS DEPNOMBRE, LOC.NOMBRE AS LOCNOMBRE FROM DEPARTAMENTO DEP INNER JOIN LOCALIDAD LOC ON LOC.ID_LOC = DEP.ID_LOC;
BEGIN
    OPEN c_DepLoc;
    FOR I IN (SELECT DEP.NOMBRE AS DEPNOMBRE, LOC.NOMBRE AS LOCNOMBRE FROM DEPARTAMENTO DEP INNER JOIN LOCALIDAD LOC ON LOC.ID_LOC = DEP.ID_LOC) LOOP
        FETCH c_DepLoc INTO p_NombreDep, p_NombreLoc;
        DBMS_OUTPUT.PUT_LINE('Nombre Dep: ' || p_NombreDep || ', Nombre Loc: ' || p_NombreLoc);
    END LOOP;
    RETURN SQL%ROWCOUNT;
    CLOSE c_DepLoc;
END;

DECLARE
COLS NUMBER;
BEGIN
DBMS_OUTPUT.PUT_LINE(GETDEPLOCINFO(COLS));
DBMS_OUTPUT.PUT_LINE('Columnas modificadas: ' || cols);
END;
/






DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(MODIFICARLOCALIDADDEP(2,2));
END;
/

CREATE TABLE DEPARTAMENTO(
    ID_DEP INT PRIMARY KEY,
    NOMBRE VARCHAR2(200),
    ID_LOC INT
);

ALTER TABLE DEPARTAMENTO ADD CONSTRAINT FK_DEPARTAMENTO_ID_LOC FOREIGN KEY (ID_LOC) REFERENCES LOCALIDAD(ID_LOC);

CREATE TABLE LOCALIDAD(
    ID_LOC INT PRIMARY KEY,
    NOMBRE VARCHAR2(100)
);

INSERT INTO LOCALIDAD(ID_LOC, NOMBRE) VALUES(1, 'LOC_1');
INSERT INTO DEPARTAMENTO(ID_DEP, NOMBRE, ID_LOC) VALUES(1,'DEP_1',1);

-- Construye un bloque PLSQL que escriba el texto 'hola'.

