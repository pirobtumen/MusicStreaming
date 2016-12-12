package Client;

import javazoom.jlgui.basicplayer.BasicPlayer;

import java.io.File;

/**
 *
 * - Joaquín Claverías
 * - Alberto Sola
 */
public class Reproductor {

    private BasicPlayer player;

    //------------------------------------------------------------------------------------------------------------------

    public Reproductor(){
        this.player= new BasicPlayer();
    }

    public void Play() throws Exception{
        this.player.play();
    }

    //------------------------------------------------------------------------------------------------------------------

    public void OpenMp3() throws Exception{
        //this.player.open(new File("/Users/joaquinrodriguezclaverias/Desktop/MusicStreamer/songs/download.mp3"));
        this.player.open(new File("songs/download.mp3"));
    }

    //------------------------------------------------------------------------------------------------------------------

    public void Pause() throws Exception{
        this.player.pause();
    }

    //------------------------------------------------------------------------------------------------------------------

    public void Resume() throws Exception{
        this.player.resume();
    }

    //------------------------------------------------------------------------------------------------------------------

    public void Stop() throws Exception{
        this.player.stop();
    }
}
