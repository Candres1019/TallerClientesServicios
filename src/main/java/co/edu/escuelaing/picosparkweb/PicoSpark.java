package co.edu.escuelaing.picosparkweb;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.function.BiFunction;

/**
 * Clase PicoSpark,
 */
public class PicoSpark {

    /**
     * Obtener la respuesta adecuada del servidor.
     * @param route - path
     * @param sup - BiFunction
     */
    public static void get(String route, BiFunction<HttpRequest, HttpResponse, String> sup){
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.get(route,sup);
    }

    /**
     * Actualiza el puerto del servidor.
     * @param port - puerto del servidor.
     */
    public static void port(int port){
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.port(port);
    }

    /**
     * Inicializacion del servidor.
     * @throws IOException -
     */
    public static void startServer() throws IOException {
        PicoSparkServer pserver = PicoSparkServer.getInstance();
        pserver.startServer();
    }

}
