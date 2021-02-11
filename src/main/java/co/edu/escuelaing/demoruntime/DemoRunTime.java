package co.edu.escuelaing.demoruntime;

import java.io.IOException;
import static co.edu.escuelaing.picosparkweb.PicoSpark.*;

/**
 *
 */
public class DemoRunTime {

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        port(getPort());
        get("/hello", (req, resp) -> "Hello world! from lamba");
        get("/htmlShow", (req, resp) -> "index.html");
        get("/js/app.js", (req, resp) -> "app.js");
        get("/imagenShow", (req, resp) -> "teslaLogo.png");
        get("/css/style.css", (req, resp) -> "style.css");
        startServer();
    }

    /**
     *
     * @return
     */
    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 3478;
    }
}
