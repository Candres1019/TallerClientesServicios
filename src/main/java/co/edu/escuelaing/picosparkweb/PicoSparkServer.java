package co.edu.escuelaing.picosparkweb;

import co.edu.escuelaing.httpserver.HttpServer;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class PicoSparkServer implements Processor {

    private static PicoSparkServer _instance = new PicoSparkServer();
    private int port;
    private int httPort=36000;
    //Mapa que mapea las rutas con la función lambda
    Map<String, BiFunction<HttpRequest, HttpResponse, String>> functions = new HashMap<>();
    HttpServer hserver = new HttpServer();

    /*
     * Todo lo que llegue con el prefijo /Apps tomarlo para registrar en el servidor
     */
    private PicoSparkServer(){
        hserver.registerProccessor("/Apps", this);
    }


    public static PicoSparkServer getInstance(){
        return  _instance;
    }
    /*
     * Registrar los parametros que esta mandando el cliente
     */
    public void get(String route, BiFunction<HttpRequest, HttpResponse, String> bifunc){
        functions.put(route, bifunc);
    }

    /*
     * Servidor para inicializar
     */
    public void startServer() throws IOException {

        hserver.startServer(httPort);
    }

    public void port(int serverPort){
        this.httPort = serverPort;
    }

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

    private String validOkHtppHeader() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
    }

    @Override
    public String handle(String path, HttpRequest req, HttpResponse resp) {
        /*System.out.println("----------------------public String handle");
        System.out.println(path);
        System.out.println(functions.containsKey(path));*/
        if(functions.containsKey(path)){
            return validOkHtppHeader() + functions.get(path).apply(req, resp);
        }
        return validErrorHtppHeader() +" Error";
    }


}
