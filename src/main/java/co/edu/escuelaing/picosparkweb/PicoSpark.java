package co.edu.escuelaing.picosparkweb;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.BiFunction;

/**
 *
 */
public class PicoSpark {

    /**
     *
     * @param route
     * @param sup
     */
    public static void get(String route, BiFunction<HttpRequest, HttpResponse, String> sup){
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.get(route,sup);
    }

    /**
     * Método que actualiza el puerto para que el servidor corra
     * @param port
     */
    public static void port(int port){
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.port(port);
    }

    /**
     * Método que permite inicializarlo esplicitamente porque no hay hilos y dejar escuchar antes todos los get
     * @throws IOException
     */
    public static void startServer() throws IOException {
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.startServer();
    }

}
