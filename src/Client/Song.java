package Client;

import Common.SongHeader;

import java.io.*;

/**
 *
 * - Joaquín Claverías
 */

public class Song {

    private     SongHeader  info;
    private     byte[]      data_song;
    public      int         option;

    //------------------------------------------------------------------------------------------------------------------

    public Song(byte[] arrayBytes){
        this.data_song= arrayBytes;
        saveFile(arrayBytes);
    }

    //------------------------------------------------------------------------------------------------------------------

    public boolean saveFile(byte[] arrayBytes){
        boolean saved= false;

        try {
            OutputStream out = new FileOutputStream("songs/download.mp3");
            out.write(arrayBytes);
            out.close();
            saved= true;
        } catch (Exception e){
            System.out.println("\n No se pudo guardar la descarga. \n");
        }
        return saved;
    }

    //------------------------------------------------------------------------------------------------------------------

}
