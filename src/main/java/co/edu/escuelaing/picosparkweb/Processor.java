package co.edu.escuelaing.picosparkweb;


import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public interface Processor {

    /**
     * Método para manejar la cadena si la llave que esta buscando la url existe
     * Retorna un encabezado valido de http y el resultado.
     * @param path
     * @param req
     * @param resp
     * @return
     */
    String handle(String path, HttpRequest req, HttpResponse resp);

}
