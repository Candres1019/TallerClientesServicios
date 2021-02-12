package co.edu.escuelaing.httpserver;

import co.edu.escuelaing.picosparkweb.Processor;
import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Servidor Http
 */
public class HttpServer {

    private int port;
    Map<String, Processor> routesToProcesssors = new HashMap();

    /**
     * Iniciador del servidor Http
     * @param httPort - puerto por donde debe correr el servicio
     * @throws IOException - IOException
     */
    public void startServer(int httPort) throws IOException {
        port = httPort;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(getPort());
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
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean isFirstLine = true;
            String path = "";

            while ((inputLine = in.readLine()) != null) {
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
                    String newPath = path.substring(key.length());
                    resp = routesToProcesssors.get(key).handle(newPath, null, null);
                    if (resp.contains("teslaLogo.png")){
                        DocumentPicoSparkReader.imageReader(clientSocket);
                    }else if (resp.contains("Onlyhello.html")){
                        DocumentPicoSparkReader.fileReader(clientSocket, "html");
                    }else if (resp.contains("Onlyapp.js")){
                        DocumentPicoSparkReader.fileReader(clientSocket, "js");
                    }
                    else if (resp.contains("Onlystyle.css")){
                        DocumentPicoSparkReader.fileReader(clientSocket, "css");
                    }else if(resp.contains("Onlydata.view")){
                        DocumentPicoSparkReader.viewReader(clientSocket);
                    }
                }
            }

            if(resp==null){
                outputLine = validOkHtppResponse();
            }else{
                outputLine = resp;
            }

            out.println(outputLine);
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    /**
     * Solicitar el puerto asignado al servidor.
     * @return - puerto del servidor.
     */
    public int getPort(){
        return this.port=port;
    }

    /**
     * Modificar el puerto del servidor.
     * @param port - Nuevo puerto para que el serividor corra.
     */
    public void setPort(int port){
        this.port=port;
    }

    /**
     * Metodo para registrar el nuevo processor.
     * @param path -
     * @param proccessor -
     */
    public void registerProccessor(String path, Processor proccessor) {
        routesToProcesssors.put(path,proccessor);
    }

    /**
     * Metodo para retornar un html de respuesta valido.
     * @return -
     */
    public String validOkHtppResponse() {
        return "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n"
                + "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<meta charset=\"UTF-8\">\n"
                + "<title>LAB03</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1> Mi Propio Mensaje</h1>\n"
                + "</body>\n"
                +"<html>\n";
    }

}
