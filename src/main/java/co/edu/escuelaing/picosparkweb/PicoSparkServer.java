package co.edu.escuelaing.picosparkweb;

import co.edu.escuelaing.httpserver.HttpServer;

import javax.swing.*;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 *
 */
public class PicoSparkServer implements Processor {

    private static PicoSparkServer _instance = new PicoSparkServer();
    private int port;
    private int httPort=36000;
    Map<String, BiFunction<HttpRequest, HttpResponse, String>> functions = new HashMap<>();
    HttpServer hserver = new HttpServer();
    private ArrayList<Double> datos;

    /**
     * Todo lo que llegue con el prefijo /Apps tomarlo para registrar en el servidor
     */
    private PicoSparkServer(){
        hserver.registerProccessor("/Apps", this);
    }

    /**
     *
     * @return
     */
    public static PicoSparkServer getInstance(){
        return  _instance;
    }

    /**
     * Registrar los parametros que esta mandando el cliente
     * @param route
     * @param bifunc
     */
    public void get(String route, BiFunction<HttpRequest, HttpResponse, String> bifunc){
        functions.put(route, bifunc);
    }

    /**
     * Servidor para inicializar
     * @throws IOException
     */
    public void startServer() throws IOException {
        hserver.startServer(httPort);
    }

    /**
     *
     * @param serverPort
     */
    public void port(int serverPort){
        this.httPort = serverPort;
    }

    /**
     *
     * @return
     */
    private String validErrorHtppHeader() {
        return "HTTP/1.1 400 Not Found\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>I Found and Error</h1>\n"
                + "</body>\n"
                +"<html>\n";
    }

    /**
     *
     * @return
     */
    private String validOkHtppHeader() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
    }

    /**
     *
     * @return
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
                + "</body>\n"
                +"<html>\n";
    }

    /**
     *
     * @param path
     * @param req
     * @param resp
     * @return
     */
    @Override
    public String handle(String path, HttpRequest req, HttpResponse resp) {
        System.out.println("------------------------------------------------------");
        System.out.println(path);
        System.out.println("------------------------------------------------------");
        if(functions.containsKey(path)){
            return validOkHtppHeader() + functions.get(path).apply(req, resp);
        }else if(path.contains("datos")){
            handleDataDatos(path);
            return validOkHtppHeaderDatos();
        }
        return validErrorHtppHeader() +" Error";
    }

    private void handleDataDatos(String datosString){
        System.out.println(datosString);
        String[] listDatos = datosString.split("");
        String daticosN = "";
        for(int i=0; i<listDatos.length; i++){
            System.out.println("ENTRO" + i);
            if(listDatos[i].equals("=")){
                System.out.println("ENTRO Igu");
                daticosN = datosString.substring(i, datosString.length());
                break;
            }
        }
        System.out.println(daticosN);
    }

}