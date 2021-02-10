package co.edu.escuelaing.httpserver;

import co.edu.escuelaing.picosparkweb.PicoSparkServer;
import co.edu.escuelaing.picosparkweb.Processor;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class HttpServer {

    private int port;

    //Todo lo que empiece con esa cadena String mandela a un proceso
    Map<String, Processor> routesToProcesssors = new HashMap();

    public void startServer(int httPort) throws IOException {
        port = httPort;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
            /*serverSocket = new ServerSocket(36000);*/
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(1);
        }

        boolean running = true;
        while(running){
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir en puerto "+ getPort()+" ... ");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean isFirstLine = true;
            String path = "";

            //Imprimir encabezados recibidos.
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                // Obtener path que se busca
                if(isFirstLine){
                    path = inputLine.split(" ")[1];
                    isFirstLine = false;
                }
                if (!in.ready()) {
                    break;
                }
            }

            String resp=null;
            for(String key: routesToProcesssors.keySet()){
                if(path.startsWith(key)){
                    System.out.println(path.substring(key.length()));
                    String newPath = path.substring(key.length());
                    resp = routesToProcesssors.get(key).handle(newPath, null, null);
                }
            }

            System.out.println("resp: "+resp);

            if(resp==null){
                /*System.out.println("nuloooooooooooooooooooooo");*/
                outputLine = validOkHtppResponse();
            }else{
                /*System.out.println("okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");*/
                outputLine = resp;
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /*public int getPort(){
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 3600; //returns default port if heroku-port isn't set
    }*/

    public int getPort(){
        return this.port=port;
    }

    public void setPort(int port){
        this.port=port;
    }

    public void registerProccessor(String path, Processor proccessor) {
        routesToProcesssors.put(path,proccessor);
    }

    public String validOkHtppResponse() {
        /*System.out.println("++++++++++++++++validOkHtppResponse");*/
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>Title of the document</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1> mi propio mensaje</h1>\n"
                + "</body>\n"
                +"<html>\n";
    }
}
