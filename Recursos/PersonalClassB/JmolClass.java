package Recursos.PersonalClassB;

import org.jmol.api.*;
import org.jmol.adapter.smarter.SmarterJmolAdapter;
import org.jmol.viewer.Viewer; // este parece ser el papa de las casas

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;



public class JmolClass {

    public static void loadAndDisplay3(String path, double newWidth) {

        File file = new File(path);
        String nombreArchivo = file.getName();

        JFrame frame = new JFrame(nombreArchivo);
        JmolAdapter adapter = new SmarterJmolAdapter();
        JmolViewer viewer = Viewer.allocateViewer(frame, adapter);

        JPanel jmolPanel = new JPanel() {
                @Override
                public void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    viewer.renderScreenImage(g, this.getWidth(), this.getHeight());
                }
            };

        frame.add(jmolPanel);
        frame.setSize(500, 766);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icono = toolkit.getImage("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/png/Moho.png");
        frame.setIconImage(icono);
        int numeroEntero = (int) Math.round(newWidth);
        frame.setResizable(true); // habilitar redimensionamiento
        frame.setLocation(numeroEntero, 0); // Establecer la posición de la ventana
        frame.setVisible(true);


            // Cargar un archivo molecular en Jmol
             viewer.openFile(path);

//        if(path.contains(".pdb")){
            // Mostrar la estructura secundaria de la proteína
            //String script = "spacefill 50%; color structure;";

            viewer.evalString("spacefill 25%");
            viewer.evalString("set bonds on");
            viewer.evalString("showMultipleBonds = true");


//        }
        // Agregar un WindowListener para liberar los recursos al cerrar la ventana
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                viewer.dispose();
            }
        });
    }

    public static void ConstructorAndDisplay3(double newWidth) {


        JFrame frame = new JFrame("Constructor Molecular");
        JmolAdapter adapter = new SmarterJmolAdapter();
        JmolViewer viewer = Viewer.allocateViewer(frame, adapter);

        JPanel jmolPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                viewer.renderScreenImage(g, this.getWidth(), this.getHeight());
            }
        };

        frame.add(jmolPanel);
        frame.setSize(500, 766);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image icono = toolkit.getImage("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/png/Moho.png");
        frame.setIconImage(icono);
        int numeroEntero = (int) Math.round(newWidth);
        frame.setResizable(true); // habilitar redimensionamiento
        frame.setLocation(numeroEntero, 0); // Establecer la posición de la ventana
        frame.setVisible(true);


        // Cargar un archivo molecular en Jmol
      //  viewer.openFile(path);

//        if(path.contains(".pdb")){
        // Mostrar la estructura secundaria de la proteína
        //String script = "spacefill 50%; color structure;";

        // Activar el modo constructor de moléculas
        viewer.evalString("modelkit");
//        }
        // Agregar un WindowListener para liberar los recursos al cerrar la ventana
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                viewer.dispose();
            }
        });
    }

    }





