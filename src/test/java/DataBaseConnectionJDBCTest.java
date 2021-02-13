import co.edu.escuelaing.persistence.DataBaseConnectionJDBC;
import co.edu.escuelaing.persistence.DataBaseJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataBaseConnectionJDBCTest {

    @Test
    public void canMakeConnection(){
        Boolean connectionState = false;
        DataBaseConnectionJDBC dataBaseConnectionJDBC;
        for (int i=0; i<3; i++){
            dataBaseConnectionJDBC = new DataBaseConnectionJDBC();
            Connection connection = DataBaseConnectionJDBC.getConnection();
            connectionState = connection != null;
            if (connectionState){
                break;
            }
        }
        assertTrue(connectionState);
    }

    /*@Test
    public void canInsertData() throws SQLException {
        DataBaseJDBC dataBaseJDBC = new DataBaseJDBC();
        dataBaseJDBC.insertDatos("4 5 6 7 8", "30");
    }*/

    @Test
    public void canSelectData() throws SQLException {
        DataBaseJDBC dataBaseJDBC = new DataBaseJDBC();
        ArrayList<String> result = dataBaseJDBC.consultarDatos();
        System.out.println(result);
        if(result == null || result.isEmpty()){
            fail("Datos mal consultados!");
        }
    }

}
