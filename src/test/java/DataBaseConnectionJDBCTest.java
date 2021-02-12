import co.edu.escuelaing.persistence.DataBaseConnectionJDBC;
import co.edu.escuelaing.persistence.DataBaseJDBC;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataBaseConnectionJDBCTest {

    @Test
    public void canMakeConnection(){
        DataBaseConnectionJDBC dataBaseConnectionJDBC = new DataBaseConnectionJDBC();
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
