package dao;

import connection.MotorConexion;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportDAO {
    MotorConexion motorConexion;
    private final String $FINDALL = "SELECT * FROM ";

    public ExportDAO(){
        motorConexion = MotorConexion.getMotorConexion();
    }

    public int exportTableToXML(String tableName){
        ResultSet resultSet;
        XMLOutputFactory xmlOutputFactory;
        XMLStreamWriter xmlStreamWriter;


        motorConexion.connect();
        resultSet = motorConexion.executeQuery($FINDALL + tableName);
        try {
            while(resultSet.next()){

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        motorConexion.close();
        return 0;
    }

}
