CREATE OR REPLACE PACKAGE BODY BiblioTK AS
FUNCTION alta_obra (p_titulo VARCHAR, p_anyo INTEGER DEFAULT NULL) RETURN VARCHAR IS
    p_id VARCHAR(5);
    p_count INTEGER;
    BEGIN
        p_id := DBMS_RANDOM.STRING('X', 5); -- GENERAR ID ALEATORIA PARA OBRA
        SELECT COUNT(*) INTO p_count FROM OBRA WHERE id = p_id;
        WHILE (p_count) > 0 LOOP
            p_id := DBMS_RANDOM.STRING('X', 5);
        END LOOP;
        INSERT INTO OBRA ( id, titulo, anyo) VALUES ( p_id, p_titulo, p_anyo); -- INSERTAMOS OBRA
        RETURN p_id;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN '-1';
END;

FUNCTION borrado_obra (p_id VARCHAR) RETURN INTEGER IS
    p_count INTEGER;
    BEGIN
        SELECT COUNT(*) INTO p_count FROM OBRA WHERE id = p_id;
        IF p_count = 0 THEN
            RETURN 0;
        ELSE
            DELETE FROM OBRA WHERE id = p_id;
            RETURN 1;
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN -1;
END;

FUNCTION alta_autor (p_nombre VARCHAR, p_apellidos VARCHAR, p_nacimiento DATE DEFAULT NULL) RETURN VARCHAR IS
    p_id CHAR(4);
    p_countAux NUMBER(1);
    p_maxLengthNombre NUMBER(3);
    p_maxLengthApellidos NUMBER(3);
    BEGIN

    --SELECT COUNT(*) INTO p_id FROM AUTOR ORDER BY id DESC FETCH FIRST 1 ROWS ONLY;
    SELECT COUNT(*) INTO p_countAux FROM AUTOR;
    SELECT MAX(char_length) INTO p_maxLengthApellidos FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'AUTOR' AND COLUMN_NAME = 'APELLIDOS';
    SELECT MAX(char_length) INTO p_maxLengthNombre FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'AUTOR' AND COLUMN_NAME = 'NOMBRE';
    IF p_countAux = 0 THEN
        p_id := '0';
    END IF;

    IF p_countAux > 0 THEN
        SELECT id INTO p_id FROM AUTOR ORDER BY id DESC FETCH FIRST 1 ROWS ONLY;
        p_id := TO_CHAR(TO_NUMBER(p_id) + 1);
    END IF;

    -- CHECK NACIMIENTO GREATER THAN TODAY'S DATE
    IF p_nacimiento > SYSDATE THEN
        RAISE NUMERO_EN_VEZ_DE_CARACTERES;
    END IF;

    -- CHECK NACIMIENTO SMALLER THAN MINIMUM ALLOWED DATE
    IF p_nacimiento < TO_DATE('01/01/0001', 'DD/MM/YYYY') THEN
        RAISE NUMERO_EN_VEZ_DE_CARACTERES;
    END IF;

    -- CHECK IF LENGTH OF NOMBRE OR APELLIDOS IS GREATER THAN 0 AND NOT WHITE SPACES
    -- DEBIDO A QUE LA TABLA PERMITE VALORES NULOS, NO SE IMPLEMENTA ESTE CHECKEO
    -- IF LENGTH(TRIM(p_apellidos)) <= 0 OR LENGTH(TRIM(p_nombre)) <= 0 THEN
    --     RAISE VALUE_OUT_OF_RANGE;
    -- END IF;

    IF LENGTH(p_apellidos) > p_maxLengthApellidos OR LENGTH(p_nombre) > p_maxLengthNombre THEN
        RAISE VALUE_OUT_OF_RANGE;
    END IF;

    INSERT INTO AUTOR (id, nombre, apellidos, nacimiento) VALUES (p_id, p_nombre, p_apellidos, p_nacimiento);

    RETURN p_id;

    EXCEPTION
        WHEN NUMERO_EN_VEZ_DE_CARACTERES THEN
            RETURN '-1';
        WHEN VALUE_OUT_OF_RANGE THEN
            RETURN '-2';
        WHEN NO_DATA_FOUND THEN
            RETURN '-5';
        WHEN OTHERS THEN
            RETURN '-3';
END;

FUNCTION borrado_autor (p_id VARCHAR) RETURN INTEGER IS
    p_countAux NUMBER(3);
    p_maxLengthID NUMBER(6);
    p_idAux AUTOR.id%TYPE;
    BEGIN
        SELECT MAX(char_length) INTO p_maxLengthID FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'AUTOR' AND COLUMN_NAME = 'ID';
        p_idAux := TRIM(p_id);

        IF LENGTH(p_idAux) > p_maxLengthID THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM AUTOR WHERE id = p_idAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        DELETE FROM AUTOR WHERE id = p_idAux;
        RETURN 1;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN OTHERS THEN
            RETURN -1;
END;

FUNCTION vincular (p_id_autor VARCHAR, p_id_obra VARCHAR) RETURN INTEGER IS
    p_id_autorAux AUTOR.id%TYPE;
    p_id_obraAux OBRA.id%TYPE;
    p_maxLength_idAutor NUMBER(6);
    p_maxLength_idObra NUMBER(6);
    p_countAux NUMBER(3);
    BEGIN
        p_id_autorAux := TRIM(p_id_autor);
        p_id_obraAux := TRIM(p_id_obra);
        SELECT MAX(char_length) INTO p_maxLength_idAutor FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'AUTOR' AND COLUMN_NAME = 'ID';
        SELECT MAX(char_length) INTO p_maxLength_idObra FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'OBRA' AND COLUMN_NAME = 'ID';

        IF LENGTH(p_id_obraAux) > p_maxLength_idObra OR LENGTH(p_id_autorAux) > p_maxLength_idAutor THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM AUTOR WHERE id = p_id_autorAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM OBRA WHERE id = p_id_obraAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        INSERT INTO AUTOR_OBRA (id_autor, id_obra) VALUES (p_id_autorAux, p_id_obraAux);
        RETURN 1;

    EXCEPTION
        WHEN OTHERS THEN
            RETURN -1;

END;

FUNCTION desvincular (p_id_autor VARCHAR, p_id_obra VARCHAR) RETURN INTEGER IS
    p_id_autorAux AUTOR.id%TYPE;
    p_id_obraAux OBRA.id%TYPE;
    p_countAux NUMBER(3);
    p_maxLength_idAutor NUMBER(6);
    p_maxLength_idObra NUMBER(6);
    BEGIN
        p_id_autorAux := TRIM(p_id_autor);
        p_id_obraAux := TRIM(p_id_obra);
        SELECT MAX(char_length) INTO p_maxLength_idAutor FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'AUTOR' AND COLUMN_NAME = 'ID';
        SELECT MAX(char_length) INTO p_maxLength_idObra FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'OBRA' AND COLUMN_NAME = 'ID';
        SELECT COUNT(*) INTO p_countAux FROM AUTOR_OBRA WHERE id_autor = p_id_autorAux AND id_obra = p_id_obraAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;
        DELETE FROM AUTOR_OBRA WHERE id_autor = p_id_autorAux AND id_obra = p_id_obraAux;
        RETURN 1;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN OTHERS THEN
            RETURN -1;
END;

FUNCTION alta_edicion(p_id_obra VARCHAR, p_isbn VARCHAR, p_anyo INTEGER DEFAULT NULL) RETURN VARCHAR IS
    p_id_obraAux OBRA.id%TYPE;
    p_id_edicion EDICION.ID%TYPE;
    p_anyoAux OBRA.anyo%TYPE;
    p_countAux NUMBER(3);
    p_maxLength_idObra NUMBER(6);
    p_maxLength_ISBN NUMBER(6);
    BEGIN
        p_id_obraAux := TRIM(p_id_obra);
        SELECT MAX(char_length) INTO p_maxLength_idObra FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'OBRA' AND COLUMN_NAME = 'ID';
        SELECT MAX(char_length) INTO p_maxLength_ISBN FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'EDICION' AND COLUMN_NAME = 'ISBN';
        SELECT ANYO INTO p_anyoAux FROM OBRA WHERE id = p_id_obraAux;

        IF P_ANYO < p_anyoAux OR P_ANYO > (EXTRACT(YEAR FROM SYSDATE)+3) THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;

        IF LENGTH(p_id_obraAux) > p_maxLength_idObra OR LENGTH(p_isbn) > p_maxLength_ISBN THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;

        IF P_ANYO < 0 OR P_ANYO > 9999 THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;

        --> 0 = DOESN'T EXIST (INCORRECT P_ID_OBRA), 1 = EXISTS
        -- COMPROBAR QUE EXISTE UNA OBRA CON LA ID_OBRA
        SELECT COUNT(*) INTO p_countAux FROM OBRA WHERE id = p_id_obraAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        --> 0 = DOESN'T EXIST, 1 = EXISTS
        SELECT COUNT(*) INTO p_countAux FROM EDICION WHERE ISBN = p_isbn;
        IF p_countAux <> 0 THEN
            RAISE DATA_EXISTS;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM EDICION;
        IF p_countAux = 0 THEN
            p_id_edicion := '0';
            ELSE
            SELECT id INTO p_id_edicion FROM EDICION ORDER BY id DESC FETCH FIRST 1 ROWS ONLY;
            p_id_edicion := TO_CHAR(TO_NUMBER(p_id_edicion) + 1);
        END IF;

        INSERT INTO EDICION (ID, ID_OBRA, ISBN, anyo) VALUES (p_id_edicion, p_id_obraAux, p_isbn, p_anyo);
        RETURN 1;

    EXCEPTION
        WHEN OTHERS THEN
            RETURN -1;

END;

FUNCTION borrado_edicion(p_id VARCHAR) RETURN INTEGER IS
    p_idAux EDICION.ID%TYPE;
    p_countAux NUMBER(3);
    BEGIN
        p_idAux := TRIM(p_id);
        SELECT COUNT(*) INTO p_countAux FROM EDICION WHERE id = p_idAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        DELETE FROM EDICION WHERE id = p_idAux;
        RETURN 1;
    EXCEPTION
        WHEN OTHERS THEN
            RETURN -1;
    END;

FUNCTION alta_ejemplar(p_id_edicion VARCHAR) RETURN INTEGER IS
    /*
    * ID_EDICION NOT NULL CHAR(6)
    * NUMERO NUMBER NOT NULL(38). NUMERO = N. N = EjemplaresMismaEdicion + 1;
    * ALTA DATE 17. SYSDATE
    * BAJA DATE
    */
    p_id_edicionAux EDICION.ID%TYPE;
    p_countAux NUMBER(3);
    p_maxLength_idEdicion NUMBER(6);
    p_Numero EJEMPLAR.NUMERO%TYPE;
    BEGIN
        p_id_edicionAux := TRIM(p_id_edicion);
        -- CHECK FOR LENGTH OF ID
        SELECT MAX(char_length) INTO p_maxLength_idEdicion FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'EDICION' AND COLUMN_NAME = 'ID';
        IF LENGTH(p_id_edicionAux) > p_maxLength_idEdicion THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM EDICION WHERE ID = p_id_edicionAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND ALTA IS NOT NULL AND (BAJA IS NULL OR BAJA > SYSDATE);
        IF p_countAux = 1 THEN
            RAISE DATA_EXISTS;
        END IF;

        -- CHECK FOR AMOUNT OF EJEMPLARES OF THE SAME EDITION
        SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE id_edicion = p_id_edicionAux;
        IF p_countAux = 0 THEN
            p_Numero := 1;
            ELSE
            --SELECT MAX(NUMERO) INTO p_Numero FROM EJEMPLAR WHERE id_edicion = p_id_edicionAux;
            p_Numero := p_countAux + 1;
        END IF;

        -- CHECK IF EJEMPLAR IS AVAILABLE AND NOT ALREADY GIVEN / OR HAS BEEN GIVEN BACK
        SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux;
        IF p_countAux = 0 THEN
            INSERT INTO EJEMPLAR (ID_EDICION, NUMERO, ALTA, BAJA) VALUES (p_id_edicionAux, p_Numero, SYSDATE, NULL);
            RETURN 1;
        END IF;

        IF p_countAux > 0 THEN
            SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND (BAJA < SYSDATE OR ALTA IS NULL);
            IF p_countAux = 1 THEN
                INSERT INTO EJEMPLAR (ID_EDICION, NUMERO, ALTA, BAJA) VALUES (p_id_edicionAux, p_Numero, SYSDATE, NULL);
                RETURN 1;
            END IF;
        END IF;


    EXCEPTION
        WHEN OTHERS THEN
            RETURN -1;
    END;

FUNCTION borrado_ejemplar (p_id_edicion VARCHAR, p_numero INTEGER) RETURN INTEGER IS
    p_countAux NUMBER(3);
    p_maxLength_idEdicion NUMBER(6);
    p_id_edicionAux EDICION.ID%TYPE;
    p_numeroAux EJEMPLAR.NUMERO%TYPE;
    p_numeroMaximo EJEMPLAR.NUMERO%TYPE;
    BEGIN
        p_id_edicionAux := TRIM(p_id_edicion);
        p_numeroAux := TRIM(p_numero);
        SELECT MAX(char_length) INTO p_maxLength_idEdicion FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'EDICION' AND COLUMN_NAME = 'ID';
        IF LENGTH(p_id_edicionAux) > p_maxLength_idEdicion THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_numeroAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;
        -- Sólo se podrá borrar un ejemplar si es el último de su serie, no tiene fecha de baja y además no han pasado más de 30 días desde la fecha de alta.
        --SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_numero AND (BAJA IS NULL OR BAJA > SYSDATE) AND (SYSDATE - ALTA) < 30;
        SELECT NUMERO INTO p_numeroMaximo FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND BAJA IS NULL AND ABS(SYSDATE - ALTA) < 30 ORDER BY NUMERO DESC FETCH FIRST 1 ROWS ONLY;

        IF SQL%ROWCOUNT = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        IF p_numero = p_numeroMaximo THEN
            DELETE FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_numero;
            RETURN 1;
        ELSE
            RAISE FAILURE_TO_MEET_REQUIREMENTS;
        END IF;
    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN FAILURE_TO_MEET_REQUIREMENTS THEN
            RETURN -1;
        WHEN OTHERS THEN
            RETURN -2;
    END;

FUNCTION baja_ejemplar (p_id_edicion VARCHAR, p_numero INTEGER) RETURN INTEGER IS
    p_countAux NUMBER(3);
    p_maxLength_idEdicion NUMBER(6);
    p_id_edicionAux EDICION.ID%TYPE;

    BEGIN
        SELECT MAX(char_length) INTO p_maxLength_idEdicion FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'EDICION' AND COLUMN_NAME = 'ID';
        IF LENGTH(TRIM(p_id_edicion)) > p_maxLength_idEdicion THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;
        p_id_edicionAux := TRIM(p_id_edicion);
        SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_numero;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_numero AND BAJA IS NULL;
        IF p_countAux = 1 THEN
            UPDATE EJEMPLAR SET BAJA = SYSDATE WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_numero;
            RETURN 1;
        ELSE
            RAISE FAILURE_TO_MEET_REQUIREMENTS;
        END IF;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 0;
        WHEN FAILURE_TO_MEET_REQUIREMENTS THEN
            RETURN -1;
        WHEN VALUE_OUT_OF_RANGE THEN
            RETURN -3;
        WHEN OTHERS THEN
            RETURN -2;

END;



FUNCTION apertura_prestamo (p_id_edicion VARCHAR, p_numero VARCHAR, p_id_socio VARCHAR) RETURN INTEGER IS
    p_maxLength_idEdicion NUMBER(6);
    p_maxLength_numero NUMBER(6);
    p_maxLength_idSocio NUMBER(6);
    p_id_edicionAux EJEMPLAR.ID_EDICION%TYPE;
    p_id_numeroAux EJEMPLAR.NUMERO%TYPE;
    p_id_socioAux Socio.ID%TYPE;
    p_countAux NUMBER(3);
    BEGIN
        p_id_numeroAux := TRIM(p_numero);
        p_id_socioAux := TRIM(p_id_socio);
        SELECT MAX(char_length) INTO p_maxLength_idEdicion FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'EJEMPLAR' AND COLUMN_NAME = 'ID_EDICION';
        IF LENGTH(TRIM(p_id_edicion)) > p_maxLength_idEdicion THEN
            RAISE VALUE_OUT_OF_RANGE;
        END IF;
        p_id_edicionAux := TRIM(p_id_edicion);
        SELECT COUNT(*) INTO p_countAux FROM SOCIO WHERE ID = p_id_socioAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;

        -- SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_id_numeroAux;
        -- IF p_countAux = 0 THEN
        --     RAISE NO_DATA_FOUND;
        -- END IF;

        SELECT COUNT(*) INTO p_countAux FROM EJEMPLAR WHERE (ID_EDICION = p_id_edicionAux AND NUMERO = p_id_numeroAux AND ALTA IS NULL) OR (ID_EDICION = p_id_edicionAux AND NUMERO = p_id_numeroAux AND BAJA < SYSDATE);
        IF p_countAux = 0 THEN
            RAISE EJEMPLAR_PRESTADO;
        END IF;


        SELECT COUNT(*) INTO p_countAux FROM PRESTAMO WHERE ID_EDICION = p_id_edicionAux AND NUMERO_EJEMPLAR = p_id_numeroAux AND ESTADO_PRESTAMO IS NULL;
        IF p_countAux = 0 THEN
            UPDATE EJEMPLAR SET BAJA = NULL WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_id_numeroAux;
            UPDATE EJEMPLAR SET ALTA = SYSDATE WHERE ID_EDICION = p_id_edicionAux AND NUMERO = p_id_numeroAux;
            INSERT INTO PRESTAMO (ID_EDICION, NUMERO_EJEMPLAR, ID_SOCIO, FECHA_DEVOLUCION, ESTADO_PRESTAMO) VALUES (p_id_edicionAux, p_id_numeroAux, p_id_socioAux, CURRENT_TIMESTAMP + 30, 'prestado');
            RETURN 0;
        ELSE
            RAISE FAILURE_TO_MEET_REQUIREMENTS;
        END IF;

    EXCEPTION
        WHEN NO_DATA_FOUND THEN
            RETURN 1;
        WHEN FAILURE_TO_MEET_REQUIREMENTS THEN
            RETURN 2;
        WHEN VALUE_OUT_OF_RANGE THEN
            RETURN 3;
        WHEN OTHERS THEN
            RETURN 444;
END;

FUNCTION cierre_prestamo (ID_PRESTAMOAux NUMBER) RETURN INTEGER IS
    p_countAux NUMBER(3);
    p_id_edicion PRESTAMO.ID_EDICION%TYPE;
    p_numero PRESTAMO.NUMERO_EJEMPLAR%TYPE;
    CURSOR c_Prestamo IS SELECT ID_EDICION, NUMERO_EJEMPLAR FROM PRESTAMO WHERE PRESTAMO.ID_PRESTAMO = ID_PRESTAMOAux;
    BEGIN
        SELECT COUNT(*) INTO p_countAux FROM PRESTAMO WHERE PRESTAMO.ID_PRESTAMO = ID_PRESTAMOAux;
        IF p_countAux = 0 THEN
            RAISE NO_DATA_FOUND;
        END IF;
        SELECT COUNT(*) INTO p_countAux FROM PRESTAMO WHERE PRESTAMO.ID_PRESTAMO = ID_PRESTAMOAux AND ESTADO_PRESTAMO = 'pendiente';
        open c_Prestamo;
        FETCH c_Prestamo INTO p_id_edicion, p_numero;
        close c_Prestamo;

        UPDATE PRESTAMO SET FECHA_DEVOLUCION_REAL = CURRENT_TIMESTAMP WHERE PRESTAMO.ID_PRESTAMO = ID_PRESTAMOAux;
        UPDATE PRESTAMO SET ESTADO_PRESTAMO = 'devuelto' WHERE PRESTAMO.ID_PRESTAMO = ID_PRESTAMOAux;
        UPDATE EJEMPLAR SET BAJA = SYSDATE WHERE ID_EDICION = p_id_edicion AND NUMERO = p_numero;

        RETURN 0;

END;



END BiblioTK;
/