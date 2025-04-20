package Play.Controllers;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class LammpsController {

//    public static void ejecutarExe(String rutaExe) {
//        try {
//            Process proceso = Runtime.getRuntime().exec(rutaExe);
//            proceso.waitFor();
//            System.out.println("El archivo .exe se ha ejecutado correctamente.");
//        } catch (IOException e) {
//            System.out.println("Error al ejecutar el archivo .exe: " + e.getMessage());
//        } catch (InterruptedException e) {
//            System.out.println("Error al esperar a que termine la ejecución del archivo .exe: " + e.getMessage());
//        }
//    }  ESTE METODO UTILIZA EL HILO DE PROCESAIENTO DE MI APLICACION Y LA INAHBILITA POR LO QUE HAY QUE CREARLA EN OTRO HILO DISTINTO.
        // NEXT METODO...

public static void ejecutarExe(String rutaExe) {
    Thread thread = new Thread(() -> {
        try {
            Process proceso = Runtime.getRuntime().exec(rutaExe);
            proceso.waitFor();
            System.out.println("El archivo .exe se ha ejecutado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al ejecutar el archivo .exe: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("Error al esperar a que termine la ejecución del archivo .exe: " + e.getMessage());
        }
    });
    thread.start();
}

//    public static void ejecutarExe(String rutaExe, Stage ventanaPrincipal) {
//        Thread thread = new Thread(() -> {
//            try {
//                Process proceso = Runtime.getRuntime().exec(rutaExe);
//
//                // Obtener el identificador de la ventana del programa .exe
//                long hwnd = Native.getWindowHandle(proceso);
//
//                // Establecer la ventana principal como propietaria
//                Platform.runLater(() -> {
//                    Window window = ventanaPrincipal.getScene().getWindow();
//                    Native.setWindowOwner(hwnd, window);
//                });
//
//                proceso.waitFor();
//                System.out.println("El archivo .exe se ha ejecutado correctamente.");
//            } catch (IOException e) {
//                System.out.println("Error al ejecutar el archivo .exe: " + e.getMessage());
//            } catch (InterruptedException e) {
//                System.out.println("Error al esperar a que termine la ejecución del archivo .exe: " + e.getMessage());
//            }
//        });
//        thread.start();
//    }
}