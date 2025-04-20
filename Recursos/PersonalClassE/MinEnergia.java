package Recursos.PersonalClassE;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MinEnergia extends Application {

    private Label nombreArchivoLabel;
    private Button botonMinimizarEnergia;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Minimización Energética");

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        // Sección para cargar archivo
        nombreArchivoLabel = new Label("Ningún archivo seleccionado.");
        Button botonCargar = new Button("Cargar Archivo .mop");


        // Botón para minimizar energía
        botonMinimizarEnergia = new Button("Minimizar Energía");
        botonMinimizarEnergia.setVisible(false); // Inicialmente oculto

        // HBox para alinear el botón en la parte inferior derecha
        HBox hboxBoton = new HBox();
        hboxBoton.setAlignment(Pos.CENTER_RIGHT);
        hboxBoton.getChildren().add(botonMinimizarEnergia);

        // Añadir elementos al layout
        layout.getChildren().addAll(nombreArchivoLabel, botonCargar, hboxBoton);

        Scene scene = new Scene(layout, 550, 110); // Tamaño de la ventana
        primaryStage.setScene(scene);
        primaryStage.setResizable(false); // Evitar que se pueda cambiar el tamaño de la ventana
        botonCargar.setOnAction(event -> cargarArchivo(primaryStage));
        primaryStage.show();
    }

    private void cargarArchivo(Stage primarystage) {

        Stage ccc = primarystage;

        // Lógica para seleccionar un archivo .mop
        File archivo = selectMopFile();

        if (archivo == null ){
            System.out.println("No se escogio archivo");
            nombreArchivoLabel.setText("Ningún archivo seleccionado.");
        }else {
            nombreArchivoLabel.setText("Archivo seleccionado: " + archivo.getName());
            botonMinimizarEnergia.setVisible(true); // Mostrar el botón al cargar el archivo

            // Especifica la ruta de destino donde quieres copiar el archivo
            String rutaDestino = "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Moléculas_Optimizadas/" + archivo.getName();
            File destino = new File(rutaDestino);
            botonMinimizarEnergia.setOnAction(event -> executeMopac(destino, ccc));


            // Lógica para leer y analizar los archivos de salida .out y .arc
            //        analyzeOutputFiles(selectedFile);

            try {
                // Copiar el archivo al destino
                Files.copy(archivo.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Archivo copiado a: " + destino.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error al copiar el archivo: " + e.getMessage());
            }

        }

    }


private static File selectMopFile() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos .mop", "mop");
    fileChooser.setFileFilter(filter);

    int result = fileChooser.showOpenDialog(null); //ojo con este dato de padre, puede ser que primaryStage resulva el problema de multivisualizacion de ventanas
    if (result == JFileChooser.APPROVE_OPTION) {
        return fileChooser.getSelectedFile();
    } else {
        return null;
    }
}

    private static void executeMopac(File mopFile, Stage ccc) {
        String command = "mopac"; // Cambia esto si mopac no está en el PATH
        String[] cmdArray = {command, mopFile.getAbsolutePath()};

        new Thread(() -> {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(cmdArray);
                processBuilder.redirectErrorStream(true); // Redirigir errores a la salida estándar
                Process process = processBuilder.start();

                // Leer la salida del proceso
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line); // Imprimir la salida en la consola
                    }
                }

                // Esperar a que el proceso termine
                int exitCode = process.waitFor();
                System.out.println("MOPAC ha finalizado la ejecución con código de salida: " + exitCode);

                // Cerrar la ventana después de terminar la ejecución
                Platform.runLater(() -> ccc.close());

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
