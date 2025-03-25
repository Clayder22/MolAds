package Recursos.PersonalClassB;
import Recursos.PersonalClassE.GeneracionCeldasProces;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.jmol.api.JmolViewer;
import org.jmol.viewer.Viewer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MMH_Automatic {
    private static List<Path> ArchivosProces0 = null;
    private static final String path_Maestro = "E:/Proyecto";

    public static void MMH_MetodoCaptador(List<Path> ArchivosProces, List<Boolean> activete){
        ArchivosProces0 = ArchivosProces;
        // Verificamos que la lista tiene exactamente 4 elementos
        if (activete.size() != 4) {
            throw new IllegalArgumentException("La lista debe contener exactamente 4 elementos.");
        }
        int primeraPos = -1; // Posición del primer true
        int ultimaPos = -1; // Posición del último true
        for (int i = 0; i < activete.size(); i++) {
            if (activete.get(i)) { // Si el valor es true
                if (primeraPos == -1) {
                    primeraPos = i; // Guardamos la primera posición
                }
                ultimaPos = i; // Actualizamos la posición de último true
            }
        }

// -----------------------------LLamados a procesos------------------------------------------ //
       if (primeraPos == 0){     //Proceso ini OptGeometric
           if(ultimaPos == 1){
               ExecutorService executor = Executors.newSingleThreadExecutor(); // Un solo hilo para ejecución secuencial

               // Llamada a los métodos de manera asíncrona
               executor.submit(() -> {
                   try {
                       // Llamada al primer método
                       optimizacionGeometrica(ArchivosProces);

                       // Llamada al segundo método en el hilo de la aplicación
                       Platform.runLater(() -> {
                           generacionCeldas2(); // Pasa el latch al método
                       });
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
               });
                   executor.shutdown(); // Cierra el executor
                   //---------------------------------------------------------------------------Final 1-2
           }else if(ultimaPos == 2){
               System.out.println("Se ejecuta el de 3");
               ExecutorService executor = Executors.newSingleThreadExecutor(); // Un solo hilo para ejecución secuencial

               // Llamada a los métodos de manera asíncrona
               executor.submit(() -> {
                   try {
                       // Llamada al primer método
                       optimizacionGeometrica(ArchivosProces);


                       // Llamada al segundo método en el hilo de la aplicación
                       Platform.runLater(() -> {
                           generacionCeldas2(); // Asegúrate de que esta función llame a latch.countDown() al final
                       });

                       // Espera de 8 segundos antes de llamar al tercer método
                       Thread.sleep(20000); // Esperar 20 segundos. En realida esto debe esperar a que se cierre la ventana

                       Platform.runLater(() -> {
                           ArchivosProces0.add(Path.of("E:/Proyecto/System1.xyz"));
                       });

                       // Llamada al tercer método
                       Platform.runLater(() -> {
                           // Agregar el archivo después de que el usuario ha completado la entrada
                           System.out.println("Ejecutando conversion");
                           ConvertQ(String.valueOf(ArchivosProces0.get(2))); //en este metodo le agrego a la lista "ArchivosProces0" el archivo convertido
                       });

                       // Llamada al tercer método
                       Platform.runLater(() -> {
                           System.out.println("Ejecutando mopac");
                           Path path = ArchivosProces0.get(3); // Obtener el Path en la posición 3
                           File file = path.toFile(); // Convertir Path a File
                           executeMopac(file);
                       });

                   } catch (Exception e) {
                       e.printStackTrace();
                   } finally {
                       // Deberíamos cerrar el executor aquí, se asegura de que se ejecuta al finalizar el bloque
                       executor.shutdown(); // Cierra el executor
                   }
               });
           }else if(ultimaPos == 3){
               System.out.println("Se ejecuta el de 4");
               ExecutorService executor = Executors.newSingleThreadExecutor(); // Un solo hilo para ejecución secuencial

               // Llamada a los métodos de manera asíncrona
               executor.submit(() -> {
                   try {
                       // Llamada al primer método
                       optimizacionGeometrica(ArchivosProces);

                       // Llamada al segundo método en el hilo de la aplicación
                       Platform.runLater(() -> {
                           generacionCeldas2(); // Asegúrate de que esta función llame a latch.countDown() al final
                       });

                       // Espera de 8 segundos antes de llamar al tercer método
                       Thread.sleep(20000); // Esperar 8 segundos


                       // Agregar el archivo después de que el usuario ha completado la entrada
                       Platform.runLater(() -> {
                       ArchivosProces0.add(Path.of("E:/Proyecto/System1.xyz"));
                       });

                       // Llamada al tercer método
                       Platform.runLater(() -> {
                           System.out.println("Ejecutando conversion");
                           ConvertQ(String.valueOf(ArchivosProces0.get(3)));  //en este caso es el archivo 4, ya que orden lammps es el 3ero
                       });

                       // Llamada al tercer método
                       Platform.runLater(() -> {
                           System.out.println("Ejecutando mopac");
                           Path path = ArchivosProces0.get(4); // Obtener el Path en la posición 3
                           File file = path.toFile(); // Convertir Path a File
                           executeMopac(file);
                       });

                       Platform.runLater(() -> {
                           try {
                               // Obtener todos los archivos en el directorio que terminan con .out
                               Files.list(Paths.get(path_Maestro))
                                       .filter(file -> file.toString().endsWith(".out")) // Filtrar archivos que terminan en .out
                                       .forEach(ArchivosProces0::add); // Agregar el path a la lista
                           } catch (IOException e) {
                               e.printStackTrace(); // Manejar la excepción en caso de que la ruta no sea válida o haya un error de IO
                           }
                       });

                       Platform.runLater(() -> {
                           extractCoordinatesMOPtoXYZ(String.valueOf(ArchivosProces0.get(5)));
                       });

                       Platform.runLater(() -> {
                           try {
                               // Obtener todos los archivos en el directorio que terminan con .out
                               Files.list(Paths.get(path_Maestro))
                                       .filter(file -> file.toString().endsWith("M.xyz")) // Filtrar archivos que terminan en .out
                                       .forEach(ArchivosProces0::add); // Agregar el path a la lista
                           } catch (IOException e) {
                               e.printStackTrace(); // Manejar la excepción en caso de que la ruta no sea válida o haya un error de IO
                           }
                       });

                       Platform.runLater(() -> {
                           // convertir xyz a data
                           });

                       Platform.runLater(() -> {
                           // ejecutar lammps obteniendo #deLista 2, el comando de lectura de archivo y modificandolo al nombre del data creado
                       });

                   } catch (Exception e) {
                       e.printStackTrace();
                   } finally {
                       // Deberíamos cerrar el executor aquí, se asegura de que se ejecuta al finalizar el bloque
                       executor.shutdown(); // Cierra el executor
                   }
               });

           }
       }else if(primeraPos == 1){   //Proceso ini GenerarCeldas
           if(ultimaPos == 2){

           }else if(ultimaPos == 3){

           }

       }else if(primeraPos == 2){  //Proceso Ini MiniEnergi

       }

       }
// ---------------------------------------------Fin----------------------------------------------- //



// ----------------------------Método para optimización Geometrica----------------------------
    public static void optimizacionGeometrica(List<Path> ArchivosProces) {
        String rutaArchivoSeleccionado = ArchivosProces.getFirst().toString();
        String rutaDirectorioField = "E:/Proyecto";
        String rutaGuardado = null;
        try {
            String extension = "";
            int index = rutaArchivoSeleccionado.lastIndexOf(".");
            if (index > 0 && index < rutaArchivoSeleccionado.length() - 1) {
                extension = rutaArchivoSeleccionado.substring(index); // Incluye el punto
            }

            String ruta = rutaDirectorioField + "/";
            // Crear el nombre del archivo optimizado
            String nombreArchivoOptimizacion = new File(rutaArchivoSeleccionado).getName().replace(extension, "_opt" + ".pdb");
            rutaGuardado = ruta + nombreArchivoOptimizacion;
            // Crear una instancia de JmolViewer
            JmolViewer viewer = Viewer.allocateViewer(null, null);
            // Cargar el archivo en el visor
            viewer.openFile(rutaArchivoSeleccionado);
            // Realizar la optimización geométrica
            viewer.evalString("set autobond true"); // Forzar el autobonding antes de optimizar
            viewer.evalString("minimize"); // Ejecutar el comando de optimización
            // Esperar hasta 10 segundos (10,000 ms)
            Thread.sleep(3000); // Esperar 3 segundos

            viewer.evalString("write " + rutaGuardado); // Guardar el archivo optimizado
            // Mostrar mensaje de éxito
            System.out.println("Optimización completada. Archivo guardado en: " + rutaGuardado);

        } catch (Exception e) {
            System.err.println("Error durante la optimización: " + e.getMessage());
        }
        ArchivosProces0.set(0, Path.of(rutaGuardado)); //Actualizando la lista de paths con el archivo optimizado
    }
// ----------------------------Método para optimización Geometrica----------------------------




// ----------------------------Método para Generacion de Celdas----------------------------
    public static void generacionCeldas2() {
        GeneracionCeldasProces play = new GeneracionCeldasProces();
        Stage primaryStage7 = new Stage();
        play.start(primaryStage7);
    }
// ----------------------------Método para Generacion de Celdas----------------------------


// ----------------------------Método para Minimizacion de Energia----------------------------


// ----------------------------Método para Minimizacion de Energia----------------------------




// ----------------------------Método para Calculos Termodinamicos----------------------------
    public void calculosTermodinamicos(String pathData, String pathLammps, String PathDestino, String PathComplement1, String PathComplement2) {

    }
// ----------------------------Método para Calculos Termodinamicos----------------------------






    //--------------------------------- Convertir XYZ a MOP----------------------------------------
    public static void ConvertQ(String archivoXYZ) {
        if (!archivoXYZ.endsWith(".xyz")) {   // El archivo generado por celdas debe terminar en xyz
            System.out.println("El archivo no es de tipo .xyz");
            return;
        }

        try {
            // Replicar el archivo con _M en el nombre
            File originalFile = new File(archivoXYZ);
            String nuevaRuta = originalFile.getParent() + File.separator +
                    originalFile.getName().replace(".xyz", "_M.mop");
            File copiaArchivo = new File(nuevaRuta);

            // Crear la copia del archivo
            Files.copy(originalFile.toPath(), copiaArchivo.toPath(), StandardCopyOption.REPLACE_EXISTING);

            // Leer el contenido de la copia para modificarlo
            BufferedReader reader = new BufferedReader(new FileReader(copiaArchivo));
            StringBuilder contenido = new StringBuilder();
            String linea;

            // Almacenar todas las líneas en una lista
            List<String> lineas = new ArrayList<>();
            while ((linea = reader.readLine()) != null) {
                lineas.add(linea);
            }
            reader.close();

            // Eliminar las primeras dos líneas si existen
            if (lineas.size() > 2) {
                lineas = lineas.subList(2, lineas.size());
            } else {
                lineas.clear(); // Si hay menos de 2 líneas, vaciar la lista
            }

            // Agregar las nuevas líneas
            contenido.append("PM6").append(System.lineSeparator());
            contenido.append("• MolAdso_V1.0 •").append(System.lineSeparator());
            contenido.append("                ").append(System.lineSeparator());

            // Agregar el contenido restante
            for (String l : lineas) {
                contenido.append(l).append(System.lineSeparator());
            }

            // Guardar el contenido modificado en la copia del archivo
            try (BufferedWriter writerCopia = new BufferedWriter(new FileWriter(copiaArchivo))) {
                writerCopia.write(contenido.toString());
            }
            ArchivosProces0.add(Path.of(copiaArchivo.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //--------------------------------- Convertir XYZ a MOP----------------------------------------


    //--------------------------------- Ejecutar MOPAC---------------------------------------------
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
    //--------------------------------- Ejecutar MOPAC---------------------------------------------

    public static String TomaElPath(int x){
        String c = String.valueOf(ArchivosProces0.get(x));

        return c;
    }

    public static void extractCoordinatesMOPtoXYZ(String inputFilePath) {
        // Definir el nombre del archivo de salida
        String outputFilePath = inputFilePath.substring(0, inputFilePath.lastIndexOf('.')) + ".xyz";

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            int cartesianCount = 0; // Contador para identificar la segunda aparición
            boolean coordinatesStarted = false;

            while ((line = reader.readLine()) != null) {
                // Comprobar si la línea contiene "CARTESIAN COORDINATES"
                if (line.contains("CARTESIAN COORDINATES")) {
                    cartesianCount++;
                    if (cartesianCount == 2) { // Solo nos interesa la segunda aparición
                        coordinatesStarted = true;
                        continue; // Pasar a la siguiente línea
                    } else {
                        continue; // Ignorar la primera aparición
                    }
                }

                // Si hemos encontrado la sección de coordenadas, procesamos las líneas
                if (coordinatesStarted) {
                    // Si encontramos una línea en blanco, terminamos la captura
                    if (line.trim().isEmpty()) {
                        break;
                    }

                    // Extraer solo las coordenadas (sin el índice)
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length >= 4) { // Asegurarse de que hay al menos 4 elementos (índice, elemento, x, y, z)
                        writer.write(parts[1] + " " + parts[2] + " " + parts[3]);
                        writer.newLine();
                    }
                }
            }

            System.out.println("Coordenadas extraídas y guardadas en: " + outputFilePath);
            ArchivosProces0.add(Path.of(outputFilePath));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void executeLAMMPS (String path1){
        Thread thread = new Thread(() -> {
            try {
                // Comando para ejecutar lmp.exe
                String comando = "lmp.exe -in script.lmp";
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando);
                builder.directory(new File(path1)); // Estableciendo el directorio de trabajo
                Process proceso = builder.start();

                // Leer salida y errores
                BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
                String line;

                // Leer salida del proceso
                while ((line = reader.readLine()) != null) {
                    String finalLine = line;
                    //Platform.runLater(() -> outputTextArea.appendText(finalLine + "\n"));
                }

                // Leer errores del proceso
                while ((line = errorReader.readLine()) != null) {
                    String finalLine = line;
                    //Platform.runLater(() -> outputTextArea.appendText("Error: " + finalLine + "\n"));
                }

                // Esperar a que el proceso finalice
                proceso.waitFor();

                // Mostrar ventana emergente al finalizar el proceso
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("Proceso Completado");
                    alert.setOnCloseRequest(e -> {
                    });
                    alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre
                });

            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                // Manejo de errores
                System.out.println("Error en el calculo termodinamico de lammps");
            }
        });
        thread.start(); // Iniciar el hilo
    }


}

