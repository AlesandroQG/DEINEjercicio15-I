package com.alesandro.ejercicio15c;

import com.alesandro.model.Persona;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

/**
 * Clase que maneja ficheros CSV
 */
public class CSVManager {
    /**
     * Lee un fichero csv y devuelve el resultado en formato lista
     *
     * @param fichero para leer
     * @return lista de personas o null si existe una excepción
     */
    public static ArrayList<Persona> cargar(String fichero) {
        ArrayList<Persona> lista = new ArrayList<Persona>();
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String linea = br.readLine();
            linea = br.readLine();
            while (linea!=null && !linea.isEmpty()) {
                String[] partes = linea.split(",");
                try {
                    Persona p = new Persona(partes[0], partes[1], Integer.parseInt(partes[2]));
                    lista.add(p);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                linea = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return lista;
    }

    /**
     * Guarda en un fichero csv una lista de personas
     *
     * @param fichero donde guardar el archivo resultante
     * @param lista de personas
     * @return si el guardado ha sido correcto o no
     */
    public static boolean guardar(String fichero, ObservableList<Persona> lista) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
            String cabecera = "Nombre,Apellidos,Edad\n";
            bw.write(cabecera);
            for (Persona p:lista) {
                String linea = p.getNombre() + "," + p.getApellidos() + "," + p.getEdad() + "\n";
                bw.write(linea);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
