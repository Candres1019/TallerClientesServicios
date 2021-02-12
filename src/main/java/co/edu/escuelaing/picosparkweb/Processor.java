package co.edu.escuelaing.picosparkweb;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase Processor.
 */
public interface Processor {

    /**
     * Método para manejar las peticiones.
     * @param path - link path.
     * @param req -
     * @param resp -
     * @return - Cadena html dependiendo del tipo.
     */
    String handle(String path, HttpRequest req, HttpResponse resp);

}
