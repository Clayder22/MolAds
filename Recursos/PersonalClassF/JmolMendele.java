package Recursos.PersonalClassF;

import javax.swing.*;
import java.awt.*;

public class JmolMendele {
    public static void createAndShowGUI() {
        // Crear la ventana
        JFrame frame = new JFrame("Tabla Periódica");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambiar a DISPOSE_ON_CLOSE
        frame.setSize(620, 400); // Ajustamos el tamaño de la ventana
        frame.setResizable(false); // Hacer la ventana no redimensionable

        // Crear un panel para organizar los componentes
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Título
        JLabel titleLabel = new JLabel("Tabla Periódica", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Establecer fuente y tamaño

        // Cargar y escalar la imagen
        ImageIcon originalIcon = new ImageIcon("Recursos/RecursosGraficos/png/Tabla_Periodica.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(600, 310, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        // Imagen
        JLabel imageLabel = new JLabel(scaledIcon, JLabel.CENTER);

        // Añadir el título y la imagen al panel
        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Añadir el panel a la ventana
        frame.add(panel);
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frame.setVisible(true);
    }
}