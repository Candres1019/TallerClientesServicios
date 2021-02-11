package co.edu.escuelaing.httpserver;

import co.edu.escuelaing.picosparkweb.Processor;

import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class HttpServer {

    private int port;
    Map<String, Processor> routesToProcesssors = new HashMap();

    /**
     *
     * @param httPort
     * @throws IOException
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
                    if (resp.contains(".png")){
                        DocumentPicoSparkReader.imageReader(clientSocket);
                    }else if (resp.contains(".html")){
                        DocumentPicoSparkReader.fileReader(clientSocket, "html");
                    }else if (resp.contains(".js")){
                        DocumentPicoSparkReader.fileReader(clientSocket, "js");
                    }
                    else if (resp.contains(".css")){
                        DocumentPicoSparkReader.fileReader(clientSocket, "css");
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
     *
     * @return
     */
    public int getPort(){
        return this.port=port;
    }

    /**
     *
     * @param port
     */
    public void setPort(int port){
        this.port=port;
    }

    /**
     *
     * @param path
     * @param proccessor
     */
    public void registerProccessor(String path, Processor proccessor) {
        routesToProcesssors.put(path,proccessor);
    }

    /**
     *
     * @return
     */
    public String validOkHtppResponse() {
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
