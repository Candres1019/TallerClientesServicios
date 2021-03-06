package co.edu.escuelaing.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase que maneja la insercion y consulta de la base de datos.
 */
public class DataBaseJDBC {

    private static Connection connection;
    private boolean connectionState = false;
    private final int connectionTry = 3;

    /**
     * Constructor del handler de base de datos.
     */
    public DataBaseJDBC(){
        for (int i=0; i<connectionTry; i++){
            DataBaseConnectionJDBC dataBaseConnectionJDBC = new DataBaseConnectionJDBC();
            connection = DataBaseConnectionJDBC.getConnection();
            connectionState = connection != null;
            if (connectionState){
                break;
            }
        }
    }

    /**
     * Metodo para insertar datos.
     * @param datos - Cadena de datos que fueron ingresadas por el usuario.
     * @param suma - suma de los datos dados.
     * @throws SQLException - Excepcion SQL
     */
    public void insertDatos (String datos, String suma) throws SQLException {
        if (connectionState) {
            PreparedStatement registrarDatos = null;
            String consultaRegistrarDatos = "INSERT INTO sumatoria(numerosingresados, suma) VALUES(?,?);";
            try {
                //Asignar Parametros
                registrarDatos = connection.prepareStatement(consultaRegistrarDatos);
                registrarDatos.setString(1, datos);
                registrarDatos.setString(2, suma);
                //Ejecutar
                registrarDatos.execute();
                //Guardar cambios
                connection.commit();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            connection.close();
        }
    }

    /**
     * Metodo para consultar la base de datos
     * @return - ArrayList de los datos que estan guardados, con formato html para insercion html directa.
     */
    public ArrayList<String> consultarDatos(){
        if(connectionState) {
            try {
                ArrayList<String> daticos = new ArrayList<String>();
                PreparedStatement consultarDatos = null;
                String consultaConsultarDatos = "SELECT numerosingresados, suma FROM sumatoria";

                consultarDatos = connection.prepareStatement(consultaConsultarDatos);
                ResultSet resultSet = consultarDatos.executeQuery();

                while (resultSet.next()) {
                    daticos.add("<tr>\n");
                    daticos.add("<th>" + resultSet.getString("numerosingresados") + "</th>\n");
                    daticos.add("<th>" + resultSet.getString("suma") + "</th>\n");
                    daticos.add("<tr>\n");
                }

                return daticos;
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
        }
        ArrayList<String> daticosTemp = new ArrayList<>();
        daticosTemp.add("<th>" + "Couldn't establish a connection after " + connectionTry + "attempts" +", Try Again" + "</th>\n");
        return daticosTemp;
    }

}