DECLARE
id5 CHAR(5);
BEGIN
id5:= dbms_random.string('X', 5); -- insertar una obra
INSERT INTO obra (id, titulo) VALUES (id5, 'Cinco semanas en globo');
END;
/