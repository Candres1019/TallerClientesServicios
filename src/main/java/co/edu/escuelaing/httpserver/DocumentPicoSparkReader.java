package co.edu.escuelaing.httpserver;

import co.edu.escuelaing.persistence.DataBaseJDBC;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Clase aparte que maneja los recursos para ser mostrados.
 */
public class DocumentPicoSparkReader {

    private static final String imagePath = "src/main/resources/img/teslaLogo.png";
    private static final String htmlPath = "src/main/resources/hello.html";
    private static final String jsPath = "src/main/resources/js/app.js";
    private static final String cssPath = "src/main/resources/css/style.css";
    private static final String viewPath = "src/main/resources/view.html";

    private static final String contentCss = "Content-Type: text/css";
    private static final String contentJs = "Content-Type: text/javascript";
    private static final String contentHtml = "Content-Type: text/html";

    /**
     * Metodo para leer archivos de tipo imagen.
     * @param clientSocket - clientSocket.
     */
    public static void imageReader(Socket clientSocket){
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(imagePath));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
            dataOutputStream.writeBytes( "HTTP/1.1 200 \r\n");
            dataOutputStream.writeBytes("Content-Type: image/jpeg \r\n");
            dataOutputStream.writeBytes("\r\n");
            dataOutputStream.write(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo para leer archivos js, css y html
     * @param clientSocket - clientSocket.
     */
    public static void fileReader(Socket clientSocket, String tipo){
        try {
            String temp = "";
            String contenT = "";

            switch (tipo){
                case "html":
                    temp = htmlPath;
                    contenT = contentHtml;
                    break;
                case "js":
                    temp = jsPath;
                    contenT = contentJs;
                    break;
                case "css":
                    temp = cssPath;
                    contenT = contentCss;
                    break;
            }
            File archivo = new File(temp);
            FileReader reader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(reader);
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeBytes("HTTP/1.1 200 OK");
            dataOutputStream.writeBytes(contenT);
            String linea;
            while ((linea=bufferedReader.readLine()) != null){
                dataOutputStream.writeBytes(linea + "\r\n");
                dataOutputStream.writeBytes("\r\n");
            }
            bufferedReader.close();
            dataOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Metodo para leer el archivo que contiene el visualizador de los datos.
     * @param clientSocket - clientSocket.
     */
    public static void viewReader(Socket clientSocket){
        try {
            DataBaseJDBC dataBaseJDBC = new DataBaseJDBC();
            File archivo = new File(viewPath);
            FileReader reader = new FileReader(archivo);
            BufferedReader bufferedReader = new BufferedReader(reader);
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeBytes("HTTP/1.1 200 OK");
            dataOutputStream.writeBytes(contentHtml);
            String linea;
            while ((linea=bufferedReader.readLine()) != null){
                if(linea.contains("</table>")){
                    ArrayList<String> dataTableMid = dataBaseJDBC.consultarDatos();
                    for (String string : dataTableMid){
                        dataOutputStream.writeBytes(string);
                    }
                }
                dataOutputStream.writeBytes(linea + "\r\n");
                dataOutputStream.writeBytes("\r\n");
            }
            bufferedReader.close();
            dataOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}