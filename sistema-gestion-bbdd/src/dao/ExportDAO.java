package dao;

import connection.MotorConexion;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExportDAO {
    MotorConexion motorConexion;
    private final String $FINDALL = "SELECT * FROM ";

    public ExportDAO(){
        motorConexion = MotorConexion.getMotorConexion();
    }

    public int exportTableToXML(String tableName){
        try {
            ResultSet resultSet;
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new PrintWriter(new FileOutputStream(tableName + ".xml")));


            motorConexion.connect();
            resultSet = motorConexion.executeQuery($FINDALL);
            try {
                while(resultSet.next()){

                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (XMLStreamException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        motorConexion.close();
        return 0;
    }

}
