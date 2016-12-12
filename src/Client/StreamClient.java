package Client;

import Common.ClientAction;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *  * Client.StreamClient
 *
 * Clients show music and Downloads
 *
 * Clients can connect in order to get:
 * - Playlist (TCP)
 * - Songs (TCP)
 *
 * Author: Alberto Sola
 */
public class StreamClient {

    private String host="localhost";
    private int port=9090;

    private Socket socketService= null;
    private ClientAction action;

    private byte []buferFinal;

    public StreamClient(){}

    //------------------------------------------------------------------------------------------------------------------

    private byte[] getData(){
        /*
            The server send the data: size,data.

            This function gets the data sent from the server and it returns 'data'.

         */
        final int PACKAGE_SIZE = 1024;

        byte[] data = null;
        byte[] bufferPackage = new byte[PACKAGE_SIZE];
        int totalBytes;
        int readBytes;
        int basePos;
        int sizeTotal;
        int sizeData;
        int j = 0;
        InputStream inputStream;

        try{

            // Read data sent from the server
            inputStream = this.socketService.getInputStream();

            // Get package total size
            totalBytes = inputStream.read(bufferPackage);
            String tam= new String(bufferPackage);
            sizeTotal = Integer.parseInt(tam.substring(0, tam.indexOf(',')));

            // Reserve data
            sizeData = sizeTotal - ( Integer.toString(sizeTotal).length() + 1);
            data = new byte[sizeData];

            System.out.println("El tamaño del paquete es: " + sizeTotal);
            System.out.println("El tamaño de la cancion es: " + sizeData);

            // Copy the first part
            basePos = tam.indexOf(',') + 1;

            // Cogemos los bytes que han llegado con el primer paquete.
            for(int i= basePos; i<bufferPackage.length && j < sizeData; i++) {
                data[j] = bufferPackage[i];
                j++;
            }

            // Copy all the data
            while(totalBytes < sizeTotal) {
                readBytes   = inputStream.read(bufferPackage);
                totalBytes += readBytes;

                // Añadimos buferRecepción a un buffer general, para tener todos los datos.
                for(int i=0; i<readBytes && j<sizeData; i++) {
                    data[j] = bufferPackage[i];
                    j++;
                }

            }

        } catch (IOException e){
            System.err.println("Can't get data.");
        }

        return data;
    }

    //------------------------------------------------------------------------------------------------------------------

    public boolean connect(String host, int port){
        /*
            Connect to the server 'host' and 'port'.
         */
        boolean canConnect = false;

        try{
            this.socketService= new Socket(this.host, this.port);
            canConnect = true;
        } catch (UnknownHostException e) {
            System.err.println("Error: equipo desconocido");
        } catch (IOException e) {
            System.err.println("Error: no se pudo establecer la conexión");
        }

        return canConnect;
    }

    //------------------------------------------------------------------------------------------------------------------

    public void disconnect(){
        /*
            Close the socket.
         */
        try {
            if (socketService != null)
                socketService.close();
        }catch (IOException e){
            System.err.println("Can't disconnect.");
        }
    }

    //------------------------------------------------------------------------------------------------------------------

    public byte[] execute(ClientAction action){
        /*
            Execute an action in order to retrieve data from the server.
         */
        byte[] data = null;
        byte []buferEnvioList;
        OutputStream outputStream = null;

        try {
            // Send the action
            outputStream = this.socketService.getOutputStream();

            buferEnvioList = action.getAll().getBytes();
            outputStream.write(buferEnvioList, 0, buferEnvioList.length);
            outputStream.flush();

            // Get the data
            data = getData();

        } catch (IOException e){
            System.err.println("Can't get server data.");
        }

        return data;
    }

}
