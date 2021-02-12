package co.edu.escuelaing.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnectionJDBC {

    private static final String url = "jdbc:postgresql://ec2-34-198-31-223.compute-1.amazonaws.com:5432/d84nik8hj33vv0?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";
    private static final String driver = "org.postgresql.Driver";
    private static final String user = "ywxwiezdzzyvcz";
    private static final String pwd = "86163d007692b9ed1146bacdb5e7dd35b3636745d1c259ffac7283c83a24c42b";
    private static Connection connection;

    /**
     *
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
     *
     * @return
     */
    public static Connection getConnection() {
        return connection;
    }

}