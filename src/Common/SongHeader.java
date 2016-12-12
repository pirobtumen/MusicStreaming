package Common;

/**
 *
 * - Alberto Sola
 */

public class SongHeader {
    /*
        Contain the song metadata.
     */
    private String name         = "None";
    private String author       = "None";

    private int id              = 0;

    // ------------------------------------------------------------------------

    public SongHeader(){}

    // ------------------------------------------------------------------------

    public SongHeader(String data){
        parseString(data);
    }

    // ------------------------------------------------------------------------

    public int getId(){
        return id;
    }

    // ------------------------------------------------------------------------

    public String getAuthor() {
        return author;
    }

    // ------------------------------------------------------------------------

    public String getName() {
        return name;
    }

    // ------------------------------------------------------------------------

    public void setName(String name) {
        this.name = name;
    }

    // ------------------------------------------------------------------------

    public void setAuthor(String author) {
        this.author = author;
    }

    // ------------------------------------------------------------------------

    public void setId(int id) {
        this.id = id;
    }

    // ------------------------------------------------------------------------

    public void parseString(String data){
        // TODO
        // Decodificar la información en formato: id: <>, name: <>, author: <> hasta encontrar un fin de cadena.
        // Almacenar la información en un SongHeader.

        String aux;

        // Añadimos el valor del ID
       // this.setId( Integer.parseInt( data.substring(data.indexOf(':')+1, data.indexOf(',')-1) ) );

        // Recortamos la parte inicial de la cadena. Nos queda: name: <>, author: <>
       // aux= data.substring(data.indexOf(',')+1);

        // Añadimos el valor del name
      //  this.setName( aux.substring( aux.indexOf(':')+1, aux.indexOf(',')-1) );

        // Recortamos de nuevo la subcadena. Nos queda: author: <>
      //  aux= aux.substring(aux.indexOf(',')+1);

        // Añadimos el valor del author
     //   this.setAuthor( aux.substring( aux.indexOf(':')+1, aux.indexOf(',')-1) );
    }

    // ------------------------------------------------------------------------

    @Override
    public String toString(){
        String data = new String();

        data += "\tTitle: ";
        data += name;
        data += ',';
        data += "\tArtist: ";
        data += author;
        data += ',';
        data += "\tid: ";
        data += Integer.toString(id);
        data += ".\n";

        return data;
    }
}
