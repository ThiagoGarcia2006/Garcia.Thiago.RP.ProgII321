package service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;


public interface Gestionable <T extends CSVSerializable & Comparable<T>> {
    
    void agregar(T item);

    void eliminar(int id);

    List<T> obtenerTodos();

    void limpiar();

    void ordenar();
    
    void ordenar(Comparator<T> comparator);

    List<T> filtrar(Predicate<T> condicion);
    
    List<T> transformar(Function<T, T> funcion);

    void guardarEnBinario(String path) throws IOException;
    
    void cargarDesdeBinario(String path) throws IOException, ClassNotFoundException;
    
    void guardarEnCSV(String path) throws IOException;
    
    void cargarDesdeCSV(String path, Function<String, T> funcion) throws IOException;
    
    void mostrarTodos();
}
