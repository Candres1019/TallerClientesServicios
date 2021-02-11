package co.edu.escuelaing.httpserver;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * Clase aparte que maneja los recursos para ser mostrados.
 */
public class DocumentPicoSparkReader {

    private static final String imagePath = "src/main/resources/img/teslaLogo.png";
    private static final String htmlPath = "src/main/resources/hello.html";
    private static final String jsPath = "src/main/resources/js/app.js";
    private static final String cssPath = "src/main/resources/css/style.css";

    private static final String contentCss = "Content-Type: text/css";
    private static final String contentJs = "Content-Type: text/javascript";
    private static final String contentHtml = "Content-Type: text/html";

    /**
     *
     * @param clientSocket -
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
     *
     * @param clientSocket -
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
            /*PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream());*/
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeBytes("HTTP/1.1 200 OK");
            //printWriter.println("HTTP/1.1 200 OK");
            dataOutputStream.writeBytes(contenT);
            //printWriter.println(contenT);
            String linea;
            while ((linea=bufferedReader.readLine()) != null){
                System.out.println(linea);
                //printWriter.println(linea + "\r\n");
                //printWriter.println("\r\n");
                dataOutputStream.writeBytes(linea + "\r\n");
                dataOutputStream.writeBytes("\r\n");
            }
            bufferedReader.close();
            //printWriter.close();
            dataOutputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}