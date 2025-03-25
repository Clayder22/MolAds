package Recursos.PersonalClassB;

import org.openbabel.OBConversion;
import org.openbabel.OBMol;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OpenBabelClass {
    static{
        System.setProperty("java.library.path", "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/LibreriasAPIs/openbabel.jar");
        try {
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (Exception ex) {
            System.err.println("Error al configurar java.library.path: " + ex.getMessage());
        }
    }

    public static void ConversorO_P_pdbtoMOL (String inputFile, String CarpetaDestino){
    //---------------------------------------------------------------------
        // Ruta del archivo de origen
        Path origen = Paths.get(inputFile);

        // Ruta del archivo de destino
        Path destino = Paths.get(CarpetaDestino);

        try {
            // Copiar el archivo de origen al destino
            Files.copy(origen, destino);

            System.out.println("Archivo copiado exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al copiar el archivo: " + e.getMessage());
        }
    //-----------------------------------------------------------------------
        String nombreArchivo = new File(inputFile).getName();
        String star = CarpetaDestino + nombreArchivo;

        // Crear una instancia de OBMol para almacenar la estructura molecular
        OBMol mol = new OBMol();
        // Crear una instancia de OBConversion
        OBConversion obConversion = new OBConversion();


        // Definir el formato de archivo de entrada y salida
        String inputFileFormat = "pdb"; // Formato de archivo de entrada (por ejemplo, pdb)
        String outputFileFormat = "mol"; // Formato de archivo de salida


        // Leer la estructura molecular del archivo de entrada
        obConversion.SetInFormat(inputFileFormat);
        obConversion.ReadFile(mol, star);

        // Realizar operaciones con la estructura molecular, por ejemplo, cálculos, optimizaciones, etc.


        // Guardar la estructura molecular en un archivo de salida
        obConversion.SetOutFormat(outputFileFormat);
        obConversion.WriteFile(mol, nombreArchivo);

    }


}
