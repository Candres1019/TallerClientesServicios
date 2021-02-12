package co.edu.escuelaing.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que realiza la conexion con la base de datos.
 */
public class DataBaseConnectionJDBC {

    private static final String url = "jdbc:postgresql://ec2-34-198-31-223.compute-1.amazonaws.com:5432/d84nik8hj33vv0?ssl=true&sslmode=require";
    private static final String driver = "org.postgresql.Driver";
    private static final String user = "ywxwiezdzzyvcz";
    private static final String pwd = "86163d007692b9ed1146bacdb5e7dd35b3636745d1c259ffac7283c83a24c42b";
    private static Connection connection;

    /**
     * Constructor de la clase DataBaseConnectionJDBC.
     */
    public DataBaseConnectionJDBC(){
        try {
            /*Class.forName(driver);*/
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection(url, user, pwd);
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna la conexion a la base de datos.
     * @return - conexion.
     */
    public static Connection getConnection() {
        return connection;
    }

}