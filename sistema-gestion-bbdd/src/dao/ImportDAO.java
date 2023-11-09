package dao;

import connection.MotorConexion;

public class ImportDAO {

    MotorConexion motorConexion;

    public ImportDAO(){
        motorConexion = MotorConexion.getMotorConexion();
    }

    public void importTableToSQL(){
        motorConexion.connect();
    }

}
