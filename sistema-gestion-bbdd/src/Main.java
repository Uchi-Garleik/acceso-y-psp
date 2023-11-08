import connection.MotorConexion;
import dao.ExportDAO;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ExportDAO exportDAO = new ExportDAO();
        exportDAO.exportTableToXML("usuarios");


    }
}