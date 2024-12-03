package recuperatoriospthiagogarcia321;

import config.AppConfig;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import model.EventoMusical;
import model.GeneroMusical;
import service.GestorEventos;


public class Test {

    
    public static void main(String[] args) {
        try {
            
            // Crear instancia del gestor
            GestorEventos<EventoMusical> gestor = new GestorEventos<>();

            // Agregar eventos musicales
            gestor.agregar(new EventoMusical(1, "Concierto de Rock", LocalDate.of(2024, 12, 10), "Band XYZ", GeneroMusical.ROCK));
            gestor.agregar(new EventoMusical(2, "Festival de Jazz", LocalDate.of(2024, 11, 20), "Jazz Masters", GeneroMusical.JAZZ));
            gestor.agregar(new EventoMusical(3, "Fiesta Electrónica", LocalDate.of(2024, 12, 5), "DJ Beat", GeneroMusical.ELECTRONICA));
            gestor.agregar(new EventoMusical(4, "Recital de Pop", LocalDate.of(2025, 1, 15), "Pop Stars", GeneroMusical.POP));

            // Mostrar eventos agregados
            System.out.println("Eventos iniciales:");
            gestor.mostrarTodos();

            // Filtrar eventos por género
            System.out.println("\nFiltrar por género: ROCK");
            List<EventoMusical> rockEventos = gestor.filtrar(evento -> evento.getGenero() == GeneroMusical.ROCK);
            rockEventos.forEach(System.out::println);

            // Filtrar eventos por rango de fechas
            System.out.println("\nFiltrar por rango de fechas (2024-12-01 a 2024-12-31):");
            List<EventoMusical> rangoFechas = gestor.filtrar(evento -> 
                evento.getFecha().isAfter(LocalDate.of(2024, 11, 30)) &&
                evento.getFecha().isBefore(LocalDate.of(2025, 1, 1))
            );
            rangoFechas.forEach(System.out::println);

            // Ordenar eventos por nombre
            System.out.println("\nEventos ordenados por nombre:");
            gestor.ordenar((evento1, evento2) -> evento1.getNombre().compareTo(evento2.getNombre()));
            gestor.mostrarTodos();

            // Ordenar eventos por fecha
            System.out.println("\nEventos ordenados por fecha:");
            gestor.ordenar((evento1, evento2) -> evento1.getFecha().compareTo(evento2.getFecha()));
            gestor.mostrarTodos();

            
            // Guardar y cargar en archivo binario
            gestor.guardarEnBinario(AppConfig.PATH_SER.toString());
            GestorEventos<EventoMusical> gestorBinario = new GestorEventos<>();
            gestorBinario.cargarDesdeBinario(AppConfig.PATH_SER.toString());
            System.out.println("\nEventos cargados desde archivo binario:");
            gestorBinario.mostrarTodos();

            // Guardar y cargar en archivo CSV
            gestor.guardarEnCSV(AppConfig.PATH_CSV.toString());
            GestorEventos<EventoMusical> gestorCSV = new GestorEventos<>();
            gestorCSV.cargarDesdeCSV(AppConfig.PATH_CSV.toString(), EventoMusical::fromCSV);
            System.out.println("\nEventos cargados desde archivo CSV:");
            gestorCSV.mostrarTodos();

            // Eliminar evento
            System.out.println("\nEliminar evento con índice 1:");
            gestor.eliminar(1);
            gestor.mostrarTodos();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }


        
        
    }
    
}
