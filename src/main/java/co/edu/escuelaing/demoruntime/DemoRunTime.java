package co.edu.escuelaing.demoruntime;

import java.io.IOException;

import static co.edu.escuelaing.picosparkweb.PicoSpark.*;

public class DemoRunTime {

    public static void main(String[] args) throws IOException {
        port(getPort());
        /*get("/hello", "Hello world!");*/
        get("/hello", (req, resp) -> "Hello world! from lamba");
        startServer();
    }

    private static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 3478; //returns default port if heroku-port isn't set
    }
}
