CREATE OR REPLACE TRIGGER auditar_borrado_obra
BEFORE DELETE ON OBRA
FOR EACH ROW
BEGIN
    INSERT INTO c##root.obra_audit (id_obra, titulo, anyo, borrado_en)
    VALUES(:OLD.id, :OLD.titulo, :OLD.anyo, SYSTIMESTAMP);
END;
/