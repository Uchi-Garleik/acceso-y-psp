-- EJERCICIO 6: 
    -- DESARROLLA UNA FUNCION QUE DEVUELVA EL NUMERO DE AÑOS COMPLETOS QUE HAY ENTRE DOS FECHAS QUE SE PASAN COMO PARAMETROS

CREATE OR REPLACE FUNCTION FullYearsBetweenDates (DATE1 DATE, DATE2 DATE) RETURN NUMBER
IS
    YEAR1 NUMBER;
    YEAR2 NUMBER;
    YEARSBETWEEN NUMBER;
BEGIN
    YEAR1 := EXTRACT(YEAR FROM DATE1);
    YEAR2 := EXTRACT(YEAR FROM DATE2);
    YEARSBETWEEN := ABS(YEAR1 - YEAR2);
    IF MONTHS_BETWEEN(DATE1, DATE2) < 12 THEN
        YEARSBETWEEN := YEARSBETWEEN - 1;
    END IF;

    RETURN ABS(YEARSBETWEEN);
END;
/

-- EJERCICIO 7: 
    -- ESCRIBE UNA FUNCIÓN QUE, HACIENDO USO DE LA FUNCIÓN ANTERIOR, DEVUELVA LOS TRIENIOS QUE HAY ENTRE DOS FECHAS (UN TRIENIO SON TRES AÑOS)
CREATE OR REPLACE FUNCTION TRIENNIUMSBETWEENDATES (DATE1 DATE, DATE2 DATE) RETURN NUMBER
IS
    YEARSBETWEENDATES NUMBER;
    TRIENIUMS NUMBER;
BEGIN
    SELECT FULLYEARSBETWEENDATES (DATE1, DATE2) INTO YEARSBETWEENDATES FROM DUAL;
    TRIENIUMS := ROUND((YEARSBETWEENDATES / 3),0);
    RETURN TRIENIUMS;
END;
/

--EJERCICIO 8: 
    -- CODIFICA UN PROCEDIMIENTO QUE RECIBA UNA LISTA DE HASTA CINCO NÚMEROS Y VISUALICE SU SUMA
CREATE OR REPLACE PROCEDURE SUMNUMBERS(NUM1 NUMBER DEFAULT 0, NUM2 NUMBER DEFAULT 0, NUM3 NUMBER DEFAULT 0, NUM4 NUMBER DEFAULT 0, NUM5 NUMBER DEFAULT 0)
IS
    SUMOFNUMBERS NUMBER;
BEGIN
    SUMOFNUMBERS := (NUM1 + NUM2 + NUM3 + NUM4 + NUM5);
    DBMS_OUTPUT.PUT_LINE('SUM OF NUMBERS: ' || SUMOFNUMBERS);
END;
/

-- EJERCICIO 9:
    -- ESCRIBE UNA FUNCIÓN QUE DEVUELVA SOLAMENTE CARACTERES ALFABÉTICOS
    -- SUSTITUYENDO CUALQUIER OTRO CARÁCTER POR BLANCOS
    -- A PARTIR DE UNA CADENA QUE SE PASARÁ EN LA LLAMADA
CREATE OR REPLACE FUNCTION WEIRDASSFUNCTION(TEXTTOMODIFY VARCHAR) RETURN VARCHAR
IS
    MODIFIEDTEXT VARCHAR;
BEGIN
    FOR I IN 1..LENGTH(TEXTTOMODIFY)
    LOOP
        IF SUBSTR(TEXTTOMODIFY, I, 1)
    END LOOP;
END;
/
