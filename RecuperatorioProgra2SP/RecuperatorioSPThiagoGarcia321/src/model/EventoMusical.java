package model;

import java.io.Serializable;
import java.time.LocalDate;
import service.CSVSerializable;


public class EventoMusical extends Evento implements CSVSerializable, Serializable, Comparable<EventoMusical> {
    private static final long serialVersionUID = 1L;   
    private String artista;
    private GeneroMusical genero;

    
    public EventoMusical(int id, String nombre, LocalDate fecha, String artista, GeneroMusical genero) {
        super(id, nombre, fecha); 
        this.artista = artista;
        this.genero = genero;
    }


    public String getArtista() {
        return artista;
    }

    public GeneroMusical getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return "EventoMusical{" + "ID=" + getId() + ", Nombre='" + getNombre() + '\'' + ", Fecha=" + getFecha() + ", Artista='" + getArtista() + '\'' + ", Género=" + getGenero() + '}';
    }

    

    @Override
    public String toCSV() {
        return id + "," + nombre + "," + fecha + "," + artista + "," + genero;
    }

    @Override
    public String toHeaderCSV() {
        return "ID,Nombre,Fecha,Artista,Género";
    }

    @Override
    public int compareTo(EventoMusical o) {
        return this.fecha.compareTo(o.getFecha());
    }
    
    public static EventoMusical fromCSV(String eventoMusicalCSV) {
        EventoMusical toReturn = null; 
        String[] values = eventoMusicalCSV.split(",");
        if (values.length == 5) { 
            int id = Integer.parseInt(values[0]);
            String nombre = values[1];
            LocalDate fecha = LocalDate.parse(values[2]); 
            String artista = values[3];
            GeneroMusical genero = GeneroMusical.valueOf(values[4]); 

            toReturn = new EventoMusical(id, nombre, fecha, artista, genero);
        }
        return toReturn;
    }
    
    
}
