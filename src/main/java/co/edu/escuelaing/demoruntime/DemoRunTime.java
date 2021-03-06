package co.edu.escuelaing.demoruntime;

import java.io.IOException;
import static co.edu.escuelaing.picosparkweb.PicoSpark.*;

/**
 * Clase DemoRunTime, encargada de hacer las solicitudes get y de iniciar el servidor.
 */
public class DemoRunTime {

    /**
     * Metodo principal de la clase DemoRunTime.
     * @param args - args.
     * @throws IOException - IOException.
     */
    public static void main(String[] args) throws IOException {
        port(getPort());
        get("/hello", (req, resp) -> "Hello world! from lambda");
        get("/htmlShow", (req, resp) -> "Onlyhello.html");
        get("/js/app.js", (req, resp) -> "Onlyapp.js");
        get("/imagenShow", (req, resp) -> "OnlyteslaLogo.png");
        get("/css/style.css", (req, resp) -> "Onlystyle.css");
        get("/verDatos", (req, resp) -> "Onlydata.view");
        startServer();
    }

    /**
     * Retorna el puerto por el que deberia correr el servidor, creado para evitar errores en un ambiente de
     * despliegue no local
     * @return - puerto.
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 3478;
    }

}