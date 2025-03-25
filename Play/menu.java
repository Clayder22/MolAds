package Play;

import Recursos.PersonalClassF.VentanaEmergente;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;


public class menu {

    public static void main(String[] args) {
        // Lanzar la aplicación JavaFX
        javafx.application.Application.launch(frontend.class, args);

    }

    // Metodo de carga y guarda de archivos(nombre, archivos) y simulado de carga
    static List<String> pathsArchivos = new ArrayList<>();

    public static void buscarArchivo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos Moleculares", "pdb","jmol","cif","mol","mol2"));

        int resultado = fileChooser.showOpenDialog(null);
        if (fileChooser != null) {
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    if (resultado == JFileChooser.APPROVE_OPTION) {
                        File archivoSeleccionado = fileChooser.getSelectedFile();
                        pathsArchivos.add(archivoSeleccionado.getAbsolutePath());
                        System.out.println("Archivo seleccionado: " + archivoSeleccionado.getAbsolutePath());
                        System.out.print(pathsArchivos);
                        for (int i = 0; i <= 100; i++) {
                            updateProgress(i, 50);
                            try {
                                Thread.sleep(30); // Simular la duración de la carga
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                    return null;
                }

            };
            VentanaEmergente ventanaEmergente = new VentanaEmergente(task);
            new Thread(task).start();
            ventanaEmergente.show();

        }else {
            System.out.println("No se seleccionó ningún archivo.");
        }

    }


    public static String obtenerUltimaRuta() {
        if (!pathsArchivos.isEmpty()) {
            return pathsArchivos.getLast();
        } else {
            return null;
        }
    }

    public static String ObtenerDireccionArchivoPorName(String name) {
        // Inicializamos la variable 'path' como null
        String path = null;

        // Recorremos la lista de paths
        for (String archivoPath : pathsArchivos) {
            // Verificamos si el 'name' está contenido en el path del archivo
            if (archivoPath.contains(name)) {
                path = archivoPath; // Guardamos el path en la variable 'path'
                break; // Salimos del bucle si encontramos la primera coincidencia
            }
        }
        return path; // Devolvemos la variable 'path'
    }

    public static String LeerContenidoArchivo(String direccionArchivo) {
        String contenido = "";
        try {
            contenido = new String(Files.readAllBytes(Paths.get(direccionArchivo)));
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
        return contenido;
    }

    public static Image seleccionarImagenDeFondo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Imagen", "jpg"));
        int resultado = fileChooser.showOpenDialog(null);

        Image image = null;

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado0 = fileChooser.getSelectedFile();
            try {
                image = new Image(archivoSeleccionado0.toURI().toURL().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    public static String RutaDeSalidaDeArchivos(){

        // Crear un objeto JFileChooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        // Mostrar el diálogo para seleccionar el directorio
        int result = fileChooser.showOpenDialog(new JFrame());
        String directorioSeleccionado;

        if (result == JFileChooser.APPROVE_OPTION) {
            // Obtener el directorio seleccionado
             directorioSeleccionado = fileChooser.getSelectedFile().getAbsolutePath();

        } else {
             directorioSeleccionado = null;
        }
        return directorioSeleccionado;
    }



}







