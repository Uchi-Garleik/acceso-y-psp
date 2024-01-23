/*
Queremos analizar una cadena que cogeremos por teclado
y que contendrá los dos apellidos de una persona
y querremos almacenar únicamente el primer apellido en una segunda variable.
Intentaremos realizar el ejercicio analizando carácter a carácter la cadena de entrada utilizando un WHILE
*/
set serveroutput on;
DECLARE
    -- v_apellidos TABLA.APELLIDOS%TYPE
    v_apellidos VARCHAR2(50);
    v_primerApellido v_apellidos%TYPE;
BEGIN
    v_apellidos := '&apellidos';
    v_primerApellido := SUBSTR(v_apellidos, 1, INSTR(v_apellidos,' ',1,1));
    DBMS_OUTPUT.PUT_LINE('Primer apellido: ' || v_primerApellido);
END;
/

/*
Queremos reprogramar el ejemplo anterior. Para ello lo que queremos utilizar es la función "SUBSTR"
de modo que nos devuelva UNICAMENTE un carácter de modo que
vayamos analizando la cadena de entrada carácter a carácter como en el caso anterior.
Intentaremos realizar el ejercicio con un LOOP.
*/

DECLARE
    -- v_apellidos TABLA.APELLIDOS%TYPE
    v_apellidos VARCHAR2(50);
    v_primerApellido v_apellidos%TYPE;
    v_primerApellidoLength NUMBER;
    v_count NUMBER;
BEGIN
    v_count := 1;
    v_apellidos := '&apellidos';
    v_primerApellidoLength := LENGTH(SUBSTR(v_apellidos,1,INSTR(v_apellidos,' ',1, 1)));
    WHILE v_count < v_primerApellidoLength LOOP
        v_primerApellido := v_primerApellido || SUBSTR(v_apellidos, v_count, 1);
        v_count := v_count + 1;
    END LOOP;
    DBMS_OUTPUT.PUT_LINE('Primer apellido: ' || v_primerApellido);
END;
/