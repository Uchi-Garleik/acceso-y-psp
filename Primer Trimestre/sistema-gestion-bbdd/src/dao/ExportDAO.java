package dao;

import connection.MotorConexion;
import methods.Management;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ExportDAO {
    MotorConexion motorConexion;
    private String $FINDALL = "SELECT * FROM ";

    public ExportDAO(){
        motorConexion = MotorConexion.getMotorConexion();
    }

    public void exportTableToXML(String tableName){
        try {
            ResultSet resultSet;
            motorConexion.connect();
            resultSet = motorConexion.executeQuery($FINDALL + tableName);

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int countColumn = resultSetMetaData.getColumnCount();

            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(new PrintWriter(new FileOutputStream(tableName + ".xml")));

            try {
                xmlStreamWriter.writeStartDocument();
                xmlStreamWriter.writeStartElement(tableName);

                while(resultSet.next()){
                    xmlStreamWriter.writeStartElement(Management.SingularToPlural(tableName));
                    for (int i = 0; i < countColumn; i++) {
                        xmlStreamWriter.writeStartElement(resultSetMetaData.getColumnName(i+1));

                        // Ideally you would check here if the column is FK or not.
                        // Problem is it depends on the way the fields are written and the PK field could be written as
                        // id_XXXXX meaning this method would need to be adapted.
                        // This code ignores FK checking due to the issues it can cause due to the column names
                        // and it also asumes that the PK_ID field is written as simply "id".
                        if (resultSetMetaData.getColumnName(i+1).equals("id")){
                            xmlStreamWriter.writeAttribute("PK","true");
                        }

                        xmlStreamWriter.writeCharacters(resultSet.getString(i+1));
                        xmlStreamWriter.writeEndElement();
                    }
                    xmlStreamWriter.writeEndElement();
                }

                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndDocument();
                // Unlike other IO methods, XMLStreamWriter needs to be closed. Otherwise, it wont write anything
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
    }

}
