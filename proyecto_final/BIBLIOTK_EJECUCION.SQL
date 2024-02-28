/*
SET SERVEROUTPUT ON;
DECLARE
BEGIN
  PRUEBA.PROC_PRUEBA();
END;
/
Hola Mundo !!!

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