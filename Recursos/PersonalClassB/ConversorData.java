package Recursos.PersonalClassB;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ConversorData {

    public static void convert(String inputFile, String outputDir, String outputFormat) {
        try {
            // Verificar existencia de archivo y directorio
            File inputFileObj = new File(inputFile);
            File outputDirObj = new File(outputDir);

            if (!inputFileObj.exists()) {
                System.err.println("El archivo de entrada no existe: " + inputFile);
                return;
            }

            if (!outputDirObj.exists()) {
                System.err.println("La carpeta de salida no existe: " + outputDir);
                return;
            }

            // Comando para ejecutar el conversor con los parámetros correctos usando ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/APIs_Crudas/OpenBabel/main.exe", inputFile, outputDir, outputFormat);
            processBuilder.redirectErrorStream(true); // Redirigir salida de error a la salida estándar

            // Iniciar el proceso
            Process process = processBuilder.start();

            // Leer y mostrar la salida del proceso
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Leer el código de salida
            int exitCode = process.waitFor();
            System.out.println("Proceso finalizado con código de salida: " + exitCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}