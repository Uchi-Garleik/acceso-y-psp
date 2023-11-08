package dao;

import connection.MotorConexion;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
            motorConexion.connect();
            resultSet = motorConexion.executeQuery($FINDALL + tableName);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int countColumn = resultSetMetaData.getColumnCount();

            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new PrintWriter(new FileWriter("oasjdoafsjo.xml")));

            try {
                xmlStreamWriter.writeStartDocument();
                xmlStreamWriter.writeStartElement(tableName);

                while(resultSet.next()){
                    xmlStreamWriter.writeStartElement(tableName.substring(0,tableName.length()-1));
                    for (int i = 0; i < countColumn; i++) {
                        xmlStreamWriter.writeStartElement(resultSetMetaData.getColumnName(i+1));
                        xmlStreamWriter.writeCharacters(resultSet.getString(i+1));
//                        if (resultSetMetaData.getColumnName(i+1).equals("id")){
//                            xmlStreamWriter.writeAttribute("pk", "true");
//                        }
                        xmlStreamWriter.writeEndElement();
                    }
                    xmlStreamWriter.writeEndElement();
                }

                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndDocument();
                xmlStreamWriter.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (XMLStreamException | FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        motorConexion.close();
        return 0;
    }

}
