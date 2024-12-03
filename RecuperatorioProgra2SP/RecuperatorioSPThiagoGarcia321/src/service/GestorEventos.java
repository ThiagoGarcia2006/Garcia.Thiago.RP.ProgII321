package service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;



public class GestorEventos <T extends CSVSerializable & Comparable<T>> implements Gestionable<T>  {
    
    private List<T> items = new ArrayList<>(); 

    @Override
    public void agregar(T item) {
        if(item == null){
            throw new IllegalArgumentException ("No puedo almacenar algo nulo");
        }
        items.add(item);
    }

    @Override
    public void eliminar(int id) {
        validarIndice(id);
        items.remove(id);

        for (T item: items){
            System.out.println(item);
        }
    }
    
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= items.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + indice);
        }
    }

    @Override
    public List<T> obtenerTodos() {
        return new ArrayList<>(items);
    }

    @Override
    public void limpiar() {
        items.clear();
    }

    @Override
    public void ordenar() {
        if (items == null || items.isEmpty()) {
            System.out.println("No hay elementos para ordenar.");
        } else {
            Collections.sort(items);
            System.out.println("Elementos ordenados por el criterio natural.");
        }
    }
    
    @Override
    public void ordenar(Comparator<T> comparator) {
        if (items == null || items.isEmpty()) {
            System.out.println("No hay elementos para ordenar.");
        } else if (comparator == null) {
            throw new IllegalArgumentException("El Comparator no puede ser nulo.");
        } else {
            items.sort(comparator);
            System.out.println("Elementos ordenados según el criterio personalizado.");
        }
    }

    @Override
    public List<T> filtrar(Predicate<T> condicion) {
        List<T> toReturn = new ArrayList<>();
        for(T item: items){
            if(condicion.test(item)){
                toReturn.add(item);
            }
        }
        return toReturn;
    }
    
    @Override
    public List<T> transformar(Function<T, T> function) {
        List<T> toReturn = new ArrayList<>();
        for(T item : items){
            toReturn.add(function.apply(item));
        }
        return toReturn;
    }

    @Override
    public void cargarDesdeBinario(String path) throws IOException, ClassNotFoundException{
        items.clear();
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path));
        items.addAll((List<T>) entrada.readObject());
        entrada.close();
    }

    @Override
    public void guardarEnBinario(String path) throws IOException{
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(path));
        salida.writeObject(items);
        salida.close();
    }


    @Override
    public void guardarEnCSV(String path) throws IOException{
           BufferedWriter bw = new BufferedWriter(new FileWriter(path));
           bw.write(items.get(0).toHeaderCSV());
           bw.newLine();
           for(T item : items){
               bw.write(item.toCSV() + "\n");
           }
           bw.close();
    }


    @Override
    public void cargarDesdeCSV(String path, Function<String, T> transformadora) throws IOException{
        items.clear();
        BufferedReader br = new BufferedReader(new FileReader(path));
           String linea;
           br.readLine();
           while ((linea = br.readLine()) != null){
               items.add(transformadora.apply(linea));
           }
    }

    @Override
    public void mostrarTodos() {
        items.forEach(System.out::println);
    }
 
    
}
