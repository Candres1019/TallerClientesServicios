package co.edu.escuelaing.picosparkweb;


// fachada de comandos para publicar sus servicios como clase Spark

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import java.io.IOException;
import java.util.function.BiFunction;

public class PicoSpark {

    public static void get(String route, BiFunction<HttpRequest, HttpResponse, String> sup){
        // obtiene la unica instancia de PicoSparkServer
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        // Registra ruta con un cuerpo
        pserver.get(route,sup);
    }
    /*
     * Método que actualiza el puerto para que el servidor corra
     */
    public static void port(int port){
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.port(port);
    }

    /*
     * Método que permite inicializarlo esplicitamente porque no hay hilos y dejar escuchar antes todos los get
     */
    public static void startServer() throws IOException {
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.startServer();
    }


}
