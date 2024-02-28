-- KNOWN ISSUES:
/*
p_idAux := TRIM(p_id);
SELECT MAX(char_length) INTO p_maxLengthID FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'AUTOR' AND COLUMN_NAME = 'ID';
RAISE NO_DATA_FOUND: WHEN NO DATA FOUND AND THEN RETURN X NUMBER. INSTEAD OF IMMEDIATELY RETURNING A NUMBER.
COMPROBAR QUE TODAS LAS TABLAS EXISTEN
*/
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
    --     RAISE LONGITUD_CARACTERES_INCORRECTO;
    -- END IF;

    IF LENGTH(p_apellidos) > p_maxLengthApellidos OR LENGTH(p_nombre) > p_maxLengthNombre THEN
        RAISE LONGITUD_CARACTERES_INCORRECTO;
    END IF;

    INSERT INTO AUTOR (id, nombre, apellidos, nacimiento) VALUES (p_id, p_nombre, p_apellidos, p_nacimiento);

    RETURN p_id;

    EXCEPTION
        WHEN NUMERO_EN_VEZ_DE_CARACTERES THEN
            RETURN '-1';
        WHEN LONGITUD_CARACTERES_INCORRECTO THEN
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
            RAISE LONGITUD_CARACTERES_INCORRECTO;
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
            RAISE LONGITUD_CARACTERES_INCORRECTO;
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

    END;

END BiblioTK;
/