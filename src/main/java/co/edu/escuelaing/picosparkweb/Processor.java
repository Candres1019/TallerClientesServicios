package co.edu.escuelaing.picosparkweb;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;

public interface Processor {

    /*
     * Método para manejar la cadena si la llave que esta buscando la url existe
     * Retorna un encabezado valido de http y el resultado.
     */
    String handle(String path, HttpRequest req, HttpResponse resp);


}
