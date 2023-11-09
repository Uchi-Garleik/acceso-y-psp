package dao;

import connection.MotorConexion;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Statement;

public class ImportDAO {

    MotorConexion motorConexion;

    public ImportDAO(){
        motorConexion = MotorConexion.getMotorConexion();
    }

    public void importTableToSQL(){
        try {
            FileReader fileReader = new FileReader("usuarios.xml");
            motorConexion.connect();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(fileReader);
            String elementoActual = null;
            String nombre = null;
            String correo_electronico = null;
            String contrasena = null;

            while(xmlStreamReader.hasNext()){
                int event = xmlStreamReader.next();
                switch(event){
                    case XMLStreamConstants.START_ELEMENT:
                        elementoActual = xmlStreamReader.getLocalName();
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        String texto = xmlStreamReader.getText().trim();
                        if (!texto.isEmpty()) {
                            switch (elementoActual) {
                                case "nombre":
                                    nombre = texto;
                                    break;
                                case "correo_electronico":
                                    correo_electronico = texto;
                                    break;
                                case "contrasena":
                                    contrasena = texto;
                                    break;
                            }
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        if ("usuario".equals(xmlStreamReader.getLocalName())) {
                            insertarUsuario(nombre, correo_electronico, contrasena);
                        }
                        break;

                }
            }

        } catch (FileNotFoundException | XMLStreamException e) {
            throw new RuntimeException(e);
        }
        

    }

    private void insertarUsuario(String nombre, String correo_electronico, String contrasena) {
        String sql = "INSERT INTO usuarios (nombre, correo_electronico, contrasena) VALUES" + "('"+ nombre + "', '" + correo_electronico + "', '" + contrasena + "');";
        System.out.println(sql);

        motorConexion.executeUpdate(sql);


    }

}
