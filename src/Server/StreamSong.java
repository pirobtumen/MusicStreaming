package Server;

import Common.SongHeader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 *
 * - Alberto Sola
 */

public class StreamSong {
    /*
        File in bytes (song) that is going to be sent.

        - Song metadata.
        - Song bytes.
     */

    private SongHeader header = null;
    private byte [] songBytes = null;

    // ------------------------------------------------------------------------

    public StreamSong(SongHeader header){
        this.header = header;
    }

    // ------------------------------------------------------------------------

    public StreamSong(SongHeader header, String path){
        this.header = header;
        loadSong(path);
    }


    // ------------------------------------------------------------------------

    public void loadSong(String pathStr){
        Path path = Paths.get(pathStr);
        try {
            songBytes = Files.readAllBytes(path);
        } catch (IOException e ){
            System.out.println("Can't load song: " + pathStr);
        }
    }

    // ------------------------------------------------------------------------

    public byte[] getSongBytes() {
        return songBytes;
    }

    // ------------------------------------------------------------------------

    public SongHeader getHeader() {
        return header;
    }
}
