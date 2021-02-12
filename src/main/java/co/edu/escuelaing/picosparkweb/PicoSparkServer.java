package co.edu.escuelaing.picosparkweb;

import co.edu.escuelaing.calculator.CalculadoraEstadistica;
import co.edu.escuelaing.httpserver.HttpServer;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * Clase PicoSparkServ,
 */
public class PicoSparkServer implements Processor {

    private static PicoSparkServer _instance = new PicoSparkServer();
    private int port;
    private int httPort=36000;
    Map<String, BiFunction<HttpRequest, HttpResponse, String>> functions = new HashMap<>();
    HttpServer hserver = new HttpServer();
    private String daticos;

    /**
     * Controlar el flujo solo por el path /Apps
     */
    private PicoSparkServer(){
        hserver.registerProccessor("/Apps", this);
    }

    /**
     * Pedir la instancia de la clase PicoSparkServer.
     * @return - Instancia de la clase PicoSparkServer.
     */
    public static PicoSparkServer getInstance(){
        return  _instance;
    }

    /**
     * Resgistro de parametros que son enviados.
     * @param route - path.
     * @param bifunc - funcion lambda.
     */
    public void get(String route, BiFunction<HttpRequest, HttpResponse, String> bifunc){
        functions.put(route, bifunc);
    }

    /**
     * Iniciar el servidor.
     * @throws IOException
     */
    public void startServer() throws IOException {
        hserver.startServer(httPort);
    }

    /**
     * Obtener el puerto por donde correr.
     * @param serverPort - puerto por donde correr.
     */
    public void port(int serverPort){
        this.httPort = serverPort;
    }

    /**
     * Para retornar un header valido de error.
     * @return - Cadena que contiene el html del error.
     */
    private String validErrorHtppHeader() {
        return "HTTP/1.1 400 Not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Error</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>I Found and Error</h1>\n"
                + "</body>\n"
                +"<html>\n";
    }

    /**
     * Para retornar un header valido de 200.
     * @return - Cadena que contiene el html del 200.
     */
    private String validOkHtppHeader() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
    }

    /**
     * Para retornar el header que contendra los datos.
     * @return - html de datos de aceptacion.
     */
    private String validOkHtppHeaderDatos() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Datos Ingresados</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>Datos Ingresados</h1>\n"
                + "<button onclick=\"window.location.href='/Apps/htmlShow';\">"
                + "Devolverse\n"
                + "<button/>\n"
                + "</body>\n"
                +"<html>\n";
    }

    /**
     * Metodo para manejar las peticiones.
     * @param path -link path.
     * @param req -
     * @param resp -
     * @return - Cadena html dependiendo del tipo.
     */
    @Override
    public String handle(String path, HttpRequest req, HttpResponse resp) {
        if(functions.containsKey(path)){
            return validOkHtppHeader() + functions.get(path).apply(req, resp);
        }else if(path.contains("datos")){
            handleDataDatos(path);
            return validOkHtppHeaderDatos();
        }
        return validErrorHtppHeader() +" Error";
    }

    /**
     * Clase para manejar la peticion cuando
     * @param datosString - path a convertir a datos utiles.
     */
    private void handleDataDatos(String datosString){
        System.out.println(datosString);
        String[] listDatos = datosString.split("");
        this.daticos = "";
        for(int i=0; i<listDatos.length; i++){
            if(listDatos[i].equals("=")){
                this.daticos = datosString.substring(i+1, datosString.length());
                break;
            }
        }
        CalculadoraEstadistica calculadoraEstadistica = new CalculadoraEstadistica();
        calculadoraEstadistica.stringToLinkedList(daticos);
        calculadoraEstadistica.guardarDatosEnDB();
    }

}