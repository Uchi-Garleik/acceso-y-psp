/*
p_id_edicionAux := TRIM(p_id_edicion);
return -5;
SELECT MAX(char_length) INTO p_maxLength_idEdicion FROM USER_TAB_COLUMNS WHERE TABLE_NAME = 'EDICION' AND COLUMN_NAME = 'ID';

*/

set SERVEROUTPUT on;
DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.alta_obra('Revenge of the Jedi', 2005));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.borrado_obra(12345));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.alta_autor('John', 'Doe', TO_DATE('01/01/1990', 'DD/MM/YYYY')));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.borrado_autor(' '));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.vincular('2', '3C8X4'));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.desvincular('2', '3C8X4'));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.alta_edicion('QCAAR', '123124154', 2005));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.borrado_edicion('2'));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.alta_ejemplar('0'));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.borrado_ejemplar('4','2'));
END;
/

DECLARE
BEGIN
DBMS_OUTPUT.PUT_LINE(BiblioTK.baja_ejemplar('qweqweqweqwewqeqweqweqweqweqwe','2'));
END;
/