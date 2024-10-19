package modelo;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    Connection con;

    public Connection getConnection() {
        String host = "localhost";
        String port = "3306";
        String bd = "labDSI";
        String user = "root";
        String password = "root";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + bd;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return con;
    }
}
