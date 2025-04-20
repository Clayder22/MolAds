package Play.Controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class JmolController {
    public static void openJarFile(String jarFilePath) {
        File file = new File(jarFilePath);
        if (Desktop.isDesktopSupported() && file.exists()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.open(file);
            } catch (IOException e) {
                System.out.println("Error al abrir el archivo: " + e.getMessage());
            }
        } else {
            System.out.println("Desktop no es compatible o el archivo no existe");
        }
    }
}





