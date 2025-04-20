package Play.Controllers;

import javafx.application.Platform;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

// falta implementar la ecepcion de que si no se abre archivo y se cierra la ventana de bu9squeda no de error sutil.
public class MopacController {

    public static void main() {

        // Lógica para seleccionar un archivo .mop
        File selectedFile = selectMopFile();

        if (selectedFile == null ){
            System.out.println("No se escogio archivo");
        }else {

            // Lógica para ejecutar MOPAC con el archivo seleccionado
            executeMopac(selectedFile);

            // Lógica para leer y analizar los archivos de salida .out y .arc
//        analyzeOutputFiles(selectedFile);
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

    private static void executeMopac(File mopFile) {
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


            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

//    private static void analyzeOutputFiles(File mopFile) {
//        // Lógica para leer y analizar los archivos de salida .out y .arc generados por MOPAC
//    }

}
