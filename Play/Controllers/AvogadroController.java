package Play.Controllers;

import java.io.IOException;

public class AvogadroController {
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
}
