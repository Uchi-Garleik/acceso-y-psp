package model;

import java.sql.*;

public class SqlMotor {
    private Connection conn;
    private Statement st;
    private ResultSet rs;
    private static final String
            CONTROLLER = "com.mysql.cj.jdbc.Driver",
            HOST = "localhost",
            DATABASE = "examen",
            USER = "root",
            PASSWORD = "",
            URL = "jdbc:mysql://" + HOST + "/" + DATABASE;

    public void connect() {
        try {
            Class.forName(CONTROLLER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: no se pudo cargar el controlador JDBC de MySQL.");
        }

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            st = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: no se pudo establecer la conexión con la base de datos.");
        }
    }

    public int execute(String sql) {
        int response = 0;

        try {
            response = st.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: no se pudo ejecutar la sentencia SQL.");
        }

        return response;
    }

    public ResultSet executeQuery(String sql) {
        try {
            rs = st.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: no se pudo ejecutar la sentencia SQL.");
        }

        return rs;
    }

    public void disconnect() {
        try {
            if (rs != null && st != null && conn != null) {
                rs.close();
                st.close();
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error: no se pudo finalizar la conexión con la base de datos.");
        }
    }
}
