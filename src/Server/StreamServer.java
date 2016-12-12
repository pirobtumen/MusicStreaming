/*
    Práctica 2 - Fundamenos de Redes

    Servidor de streaming.

    Autores:
     - Alberto Sola
     - Joaquín Rodríguez

    2016/2017 - 3ºA
 */

package Server;

import Common.ClientAction;
import Common.SongHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Server.StreamServer
 *
 * Manages and streams songs.
 *
 * Clients can connect in order to get:
 * - Playlist (TCP)
 * - Songs (UDP)
 *
 * Author: Alberto Sola
 */
public class StreamServer {
    /*
        Music Streaming Server.
     */

    // ------------------------------------------------------------------------

    private int port = 0;
    private Map<Integer,StreamSong> songList = new HashMap<>();

    ServerSocket server_socket = null;
    Socket client_socket = null;

    // ------------------------------------------------------------------------

    private void find_port() {
        /*
         Find a port where the server can start listening.
         */
        port = 9090;
    }

    // ------------------------------------------------------------------------

    private void start(){
        /*
         Initialize Server Socket.
         */

        try{
            server_socket = new ServerSocket(port);
        }
        catch (IOException e){
            System.err.println("Can't start the socket. Port: " + port);
            System.exit(-1);
        }
    }

    // ------------------------------------------------------------------------

    private void sendPlaylist(Socket socket){
        /*
            Send playlist.

             Song format: id:<song id>,name:<song_name>,author:<song_author>\n
         */
        OutputStream out = null;
        byte[] buffer;
        String playList = new String();
        String data = new String();

        // Get the playlist
        for(Map.Entry<Integer, StreamSong> entry : songList.entrySet())
            playList += entry.getValue().getHeader().toString();

        // Send
        try {
            out = socket.getOutputStream();

            data += Integer.toString(playList.length()) + 1;
            data = Integer.toString(data.length()+playList.length());
            data += ',';
            data += playList;

            buffer = data.getBytes();

            // Enviamos el array por el outputStream;
            out.write(buffer,0,buffer.length);
        } catch ( IOException e ){
            System.out.println("Can't send playlist.");
        }
    }

    // ------------------------------------------------------------------------

    private void sendSong(Integer id, Socket socket){
        /*
            Send a song given its ID over a client socket.

            Format: <song_size_bytes>,<file_bytes>
         */
        OutputStream out = null;
        StreamSong song = null;
        String bytes = new String();
        int size = 0;

        // TODO: throw exception
        if(id < 0)
            System.err.println("ID out of index: " + Integer.toString(id));
        else{
            try{
                out = socket.getOutputStream();

                System.out.println("ID: " + id);
                song = songList.get(id);

                size = song.getSongBytes().length;
                bytes += Integer.toString(song.getSongBytes().length) + ",";
                size += bytes.length();
                bytes = Integer.toString(size) + ',';

                System.out.println("SIZE: " + size);
                out.write(bytes.getBytes());
                out.write(song.getSongBytes());


            } catch (IOException e ){
                System.err.println(e.getMessage());
                System.err.println("Can't send song to the client.");
            }
        }
    }

    // ------------------------------------------------------------------------

    private ClientAction getAction(Socket socket){
        /*
            Gets the client action.

            - Action ID
            - Data
         */
        ClientAction action = new ClientAction();

        String request = null;
        byte [] datosRecibidos = new byte[1024];
        int bytesRecibidos=0;

        InputStream inputStream;

        try {
            inputStream = socket.getInputStream();
            bytesRecibidos = inputStream.read(datosRecibidos);

            if(bytesRecibidos > -1) {
                request = new String(datosRecibidos, 0, bytesRecibidos);

                action.setAction(request);
                System.out.println("Received: " + action.toString());
            }

        }
        catch (IOException e) {
            // TODO: Client action err
            System.out.println("Can't get client request.");
        }

        return action;
    }

    // ------------------------------------------------------------------------

    private void execute(ClientAction action, Socket socket){
        /*
            Execute the client action.
         */
        ClientAction.ActionType actionType = action.getAction();
        Integer songID;

        switch (actionType){
            case GET_PLAYLIST:
                sendPlaylist(socket);
                break;

            case GET_SONG:
                songID = Integer.parseInt(action.getData());
                sendSong(songID,socket);
                break;

            default:
                System.out.println("Nothing to do.");
        }

    }

    // ------------------------------------------------------------------------

    public StreamServer(){}

    // ------------------------------------------------------------------------

    public void addSong(SongHeader header, String path){

        // TODO: check if songs can be added
        StreamSong song = new StreamSong(header,path);

        songList.put(header.getId(),song);

        System.out.println("Added song: " + header.getAuthor() + " - " + header.getName());
    }

    // ------------------------------------------------------------------------

    public void listen(){
        /*
            Start the server.
         */
        ClientAction action;

        find_port();    // Find an open port
        start();        // Start the sockets


        // Start listening
        System.out.println("Listening on port " + port + ".");

        while(true){
            System.out.println("Wait for a client...");

            try {
                client_socket = server_socket.accept();
                System.out.println("New client.");

                action = getAction(client_socket);

                execute(action,client_socket);
            }
            catch (IOException e){
                System.err.println("Can't connect with the client...");
            }

        }
    }

    // ------------------------------------------------------------------------

    public static void main(String [] args){
        SongHeader header;
        String path;
        StreamServer server = new StreamServer();

        // --------------------------------------------------------------------

        // TODO: read songs in the folder
        // TODO: read song tags
        // TODO: generate IDs
        // Add songs

        header = new SongHeader();
        header.setAuthor("Metallica");
        header.setName("Bleeding me");
        header.setId(0);

        path = new String("songs/0.mp3");

        server.addSong(header,path);

        header = new SongHeader();
        header.setAuthor("Muse");
        header.setName("Starlight");
        header.setId(1);

        path = new String("songs/1.mp3");

        server.addSong(header,path);


        header = new SongHeader();
        header.setAuthor("Gareth Coker");
        header.setName("Ori and Blind Forest");
        header.setId(2);

        path = new String("songs/2.mp3");

        server.addSong(header,path);

        // --------------------------------------------------------------------

        server.listen();
    }

}
