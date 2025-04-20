package Recursos.PersonalClassE;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jmol.api.JmolViewer;
import org.jmol.viewer.Viewer;

import java.io.File;
import java.util.Objects;

public class OptGeometric extends Application {

    // Variable para almacenar la ruta completa en la clase
    private String rutaArchivoSeleccionado;
    private Label nombreArchivoLabel;
    private Button botonOptimizar;
    private File archivo;
    private ComboBox<String> comboBox;
    private TextField rutaDirectorioField; // Campo para mostrar la ruta del directorio

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Optimización Geométrica");

        BorderPane layout = new BorderPane();

        Pane botonPane = new Pane();

        // Etiqueta para mostrar el nombre del archivo
        nombreArchivoLabel = new Label("Ningún archivo seleccionado.");
        layout.setTop(nombreArchivoLabel);

        // Botón para cargar el archivo
        Button botonCargar = new Button("Cargar Archivo");
        botonCargar.setOnAction(event -> cargarArchivo());
        botonCargar.setLayoutX(20); // Posición horizontal
        botonCargar.setLayoutY(30); // Posición vertical

        // Agregar el botón Cargar al Pane
        botonPane.getChildren().add(botonCargar);

        // Crear un VBox para el Label y el ComboBox
        VBox vbox = new VBox();
        Label nombreArchivoLabel2 = new Label("Seleccione extensión de salida");
        comboBox = new ComboBox<>(); //  Cambia esto a
        comboBox.getItems().addAll(".pdb", ".xyz", ".mol", ".cif");
        comboBox.setValue(".pdb"); // Valor predeterminado

        // Ajustar el tamaño y la posición
        vbox.setSpacing(10); // Añade un espacio de 10 píxeles entre los componentes
        vbox.getChildren().addAll(nombreArchivoLabel2, comboBox);
        vbox.setLayoutX(5); // Ajusta la posición horizontal
        vbox.setLayoutY(90);  // Ajusta la posición vertical
        botonPane.getChildren().add(vbox); // Agregar el VBox al Pane

        // Título para la ubicación
        Label tituloUbicacion = new Label("Agregue una ubicación sin espacios en el path:");
        tituloUbicacion.setLayoutX(20); // Posición horizontal
        tituloUbicacion.setLayoutY(200); // Posición vertical
        botonPane.getChildren().add(tituloUbicacion);

        // Botón para cargar directorio
        Button botonCargarDirectorio = new Button("Cargar Directorio");
        botonCargarDirectorio.setLayoutX(20); // Posición horizontal
        botonCargarDirectorio.setLayoutY(230); // Posición vertical
        botonCargarDirectorio.setOnAction(event -> cargarDirectorio());
        botonPane.getChildren().add(botonCargarDirectorio);

        // Campo de texto para mostrar la ruta del directorio
        rutaDirectorioField = new TextField();
        rutaDirectorioField.setLayoutX(20);
        rutaDirectorioField.setLayoutY(250); // Ajusta la posición vertical
        rutaDirectorioField.setPrefWidth(500); // Ajusta el ancho
        rutaDirectorioField.setEditable(false); // No editable por el usuario
        botonPane.getChildren().add(rutaDirectorioField);

        // Botón "Optimizar Geometrías", inicialmente oculto
        botonOptimizar = new Button("Optimizar Geometrías");
        botonOptimizar.setVisible(false);
        botonOptimizar.setOnAction(event -> {
            optimizarGeometria();
        });

        layout.setBottom(botonOptimizar);
        BorderPane.setAlignment(botonOptimizar, javafx.geometry.Pos.BOTTOM_RIGHT);

        // Establecer el Pane en el centro del BorderPane
        layout.setLeft(botonPane);

        Scene scene = new Scene(layout, 550, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void cargarDirectorio() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(null);

        if (selectedDirectory != null) {
            // Obtener la ruta y reemplazar "\" por "/"
            String path = selectedDirectory.getAbsolutePath().replace("\\", "/");

            // Verificar si la ruta contiene espacios
            if (path.contains(" ")) {
                // Mostrar alerta si contiene espacios
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Path Espaciado!");
                alert.setHeaderText(null);
                alert.setContentText("La ruta seleccionada contiene espacios. Por favor, elija otro directorio.");
                alert.showAndWait();
            } else {
                rutaDirectorioField.setText(path); // Mostrar la ruta en el campo de texto
                if (archivo != null) {
                    botonOptimizar.setVisible(true); // Mostrar el botón de optimización
                }
            }
        } else {
            rutaDirectorioField.setText("Ningún directorio seleccionado.");
        }
    }

    private void cargarArchivo() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Archivos PDB", "*.pdb"),
                new FileChooser.ExtensionFilter("Archivos XYZ", "*.xyz"),
                new FileChooser.ExtensionFilter("Archivos CIF", "*.cif"),
                new FileChooser.ExtensionFilter("Archivos MOL", "*.mol"),
                new FileChooser.ExtensionFilter("Todos los Archivos", "*.*")
        );

        archivo = fileChooser.showOpenDialog(null);

        if (archivo != null) {
            rutaArchivoSeleccionado = archivo.getAbsolutePath(); // Guarda la ruta completa
            nombreArchivoLabel.setText("Archivo seleccionado: " + archivo.getName());
            if (!Objects.equals(rutaDirectorioField.getText(), "")){
                botonOptimizar.setVisible(true); // Mostrar el botón de optimización
            }
        } else {
            nombreArchivoLabel.setText("Ningún archivo seleccionado.");
            botonOptimizar.setVisible(false); // Ocultar el botón si no se selecciona un archivo
        }
    }

    private void optimizarGeometria() {
        try {
            String extension = "";
            int index = rutaArchivoSeleccionado.lastIndexOf(".");
            if (index > 0 && index < rutaArchivoSeleccionado.length() - 1) {
                extension = rutaArchivoSeleccionado.substring(index); // Incluye el punto
            }

            String ruta = rutaDirectorioField.getText() + "/";
            // Crear el nombre del archivo optimizado
            String nombreArchivoOptimizacion = new File(rutaArchivoSeleccionado).getName().replace(extension, "_opt" + comboBox.getValue());
            String rutaGuardado = ruta + nombreArchivoOptimizacion;
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

            // Mostrar alerta de finalización
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("Proceso de generación de celdas completado. \n Archivo guardado en: " + rutaGuardado);
            alert.showAndWait(); // Esperar a que el usuario cierre la alerta

            // Cerrar la ventana actual (si es necesario)
            Stage stage = (Stage) botonOptimizar.getScene().getWindow();
            stage.close(); // Descomentar si deseas cerrar la ventana
        } catch (Exception e) {
            System.err.println("Error durante la optimización: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}