package UI;

import Client.Reproductor;
import Client.Song;
import Client.StreamClient;
import Common.ClientAction;

import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * - Joaquín Claverías
 * */

public class main {

    public main(){}

    public static void main(String[] args) throws Exception {
        String          host        = "localhost";
        int             port        = 9090;
        ClientAction    action      = new ClientAction();
        StreamClient    client      = new StreamClient();
        int             option      = -1;
        int             songs       = 3;  // Establecido a mano para probar
        int             select_song = -1;
        boolean         connected   = false;
        byte[]          data_list   = null;
        byte[]          data_song   = null;
        String          list        = "";
        Song            download    = null;

        do{

            // ¿Que queremos hacer, listar las canciones o descargar una en concreto?

            System.out.println("\n ¿Qué desea hacer? \n [0] Salir. \n [1] Descargar playlist. \n [2] Descargar canción. \n [3] Reproducir canción. \n");
            Scanner select_option = new Scanner(System.in);

            option = select_option.nextInt();

            // Nos conectamos al servidor

            connected = client.connect(host, port);

            if(!connected){
                System.err.println("Can't connect to the server.");
                System.exit(-1);
            }

            // Creamos la acción del cliente y ejecutamos la acción

            switch (option){

                case 1:
                    action.setAction(ClientAction.ActionType.GET_PLAYLIST);
                    data_list = client.execute(action);

                    list = "";

                    // Decodificar data a String para ver la lista.

                    for(int i = 0; i < data_list.length; i++) {
                        list += (char)data_list[i];
                        //if((char) data_list[i] == '.')
                        //    songs++;
                    }

                    System.out.println("\n La lista de Canciones disponible es: \n"+list);


                    break;

                case 2:
                    // TODO: preguntar índice de canción
                    download= new Song(data_song);

                    action.setAction(ClientAction.ActionType.GET_SONG);
                    // Para probar por defecto es la 0

                    System.out.println(" ¿Qué cancion de la lista quieres descargar? (Escribe su ID) (-1 para salir) \n"+list+"\n"+Integer.toString(songs));
                    select_song= -1;

                    do{
                        select_song = select_option.nextInt();
                        if(select_song > songs || select_song < 0)
                            System.out.println("El ID seleccionado no está en la lista. Escribe un ID válido. \n");


                    } while(select_song > songs || select_song < 0);

                    action.setData(Integer.toString(select_song));
                    data_song = client.execute(action);

                    download = new Song(data_song);

                    data_song= null;

                    break;


                case 3:
                    // Reproducir archivo. Adaptar este codigo.

                    if(download != null) {

                        Reproductor r = new Reproductor();

                        while(select_song!=-1) {
                            System.out.println("\n ¿Que hacemos?\n [0] Play \n [1] Stop \n [2] Pause \n [3] Resume \n [-1] Salir\n");
                            select_song= select_option.nextInt();

                            if (select_song == 0) {
                                r.OpenMp3();
                                r.Play();
                            } else if (select_song == 1)
                                r.Stop();
                            else if (select_song == 2)
                                r.Pause();
                            else if (select_song == 3)
                                r.Resume();
                        }
                    }
                    else
                        System.out.println("\n No se ha descargado ningún archivo.");

                    break;

                default:
                    System.out.println("Unrecognised option.");
            }

        }while(option != 0);

        // Nos desconectamos
        client.disconnect();
        System.out.println("Bye.");
    }
}
