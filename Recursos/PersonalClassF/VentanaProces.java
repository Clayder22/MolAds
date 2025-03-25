package Recursos.PersonalClassF;

import Recursos.PersonalClassB.MMH_Automatic;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.*;
import java.util.*;
import Recursos.PersonalClassB.MMH_Automatic;

import java.io.File;

public class VentanaProces extends Application {

    // Ruta de la carpeta a verificar
    private static final String DIRECTORY_PATH = "E:/Proyecto"; // Ajusta esta ruta según sea necesario


    private Button selectedButton1 = null;
    private Button selectedButton2 = null;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage secondaryStage) {
        List<Path>ArchivosProces=new ArrayList<>();

        checkDirectoryAndShowAlert();

        //boton mistico
        Button nada = new Button("           ");
        nada.setStyle(" -fx-background-color: rgba(255, 255, 255, 0); -fx-font-size: 12px;");


        //Orden de ventana
        String Entre1 = "Seleccione el Proceso Inicial";
        String Entre2 = "Seleccione el Proceso Final";
        String Entre3 = "Reúna los Archivos Especificados";

        Label contentLabel = new Label(Entre1);
        Label contentLabel2 = new Label(Entre2);
        Label contentLabel3 = new Label(Entre3);

        contentLabel.setStyle(" -fx-font-weight: bold; -fx-font-size: 18px;-fx-font-family: 'Times New Roman'");
        contentLabel.setPadding(new Insets(30, 0, 0, 225));
        contentLabel2.setStyle(" -fx-font-weight: bold; -fx-font-size: 18px;-fx-font-family: 'Times New Roman'");
        contentLabel2.setPadding(new Insets(30, 0, 0, 225));
        contentLabel3.setStyle(" -fx-font-weight: bold; -fx-font-size: 18px;-fx-font-family: 'Times New Roman'");
        contentLabel3.setPadding(new Insets(30, 0, 0, 160));

        // Crear botones
        Button button0 = new Button("Atrás");
        VBox botoncito = new VBox(button0);
        botoncito.setPadding(new Insets(40, 0, 0, 15));
        botoncito.setVisible(false);

        Button button01 = new Button(" >>> Comenzar");
        button01.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(255, 0, 0, 0.1); -fx-font-weight: bold; -fx-font-size: 15px;-fx-font-family: 'Times New Roman'");

        HBox superior = new HBox();
        superior.getChildren().addAll(botoncito, contentLabel);

        //para cambiar la oracion
        HBox superior2 = new HBox();
        superior2.getChildren().addAll(botoncito, contentLabel2);

        //para cambiar la oracion
        HBox superior3 = new HBox();
        superior3.getChildren().addAll(botoncito, contentLabel3);

        Button button2 = createButton(" Optimizar Geometrias ");
        Button button3 = createButton(" Generar Celdas ");
        Button button4 = createButton(" Minimizar Energía");
        Button button5 = createButton(" Cálculos Termodinámicos");

        // Establecer el estilo del texto en negritas para el botón
        button0.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-family: 'Times New Roman'");
        button2.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-family: 'Times New Roman'");
        button3.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-family: 'Times New Roman'");
        button4.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-family: 'Times New Roman'");
        button5.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-family: 'Times New Roman'");

        // Crear el contenedor para los botones
        VBox botonera = new VBox(25, button2, button3, button4, button5);
        botonera.setPadding(new Insets(90, 0, 0, 80));

        CheckBox requerimiento1 = new CheckBox("Archivo Molecular (Adsorbato)");
        requerimiento1.setUserData("1");
        CheckBox requerimiento2 = new CheckBox("Archivo Molecular (Adsorbente)");
        requerimiento2.setUserData("2");
        CheckBox requerimiento3 = new CheckBox("Archivo Molecular (Adsorbente-Adsorbato)");
        requerimiento3.setUserData("3");
        CheckBox requerimiento4 = new CheckBox("Archivo: .lammps/lmp");
        requerimiento4.setUserData("4");


        requerimiento1.setStyle("-fx-border-color: white; -fx-font-weight: bold; -fx-font-size: 12px;");
        requerimiento2.setStyle("-fx-border-color: white; -fx-font-weight: bold; -fx-font-size: 12px;");
        requerimiento4.setStyle("-fx-border-color: white; -fx-font-weight: bold; -fx-font-size: 12px;");
        requerimiento3.setStyle("-fx-border-color: white; -fx-font-weight: bold; -fx-font-size: 12px;");

        requerimiento1.setVisible(false);
        requerimiento2.setVisible(false);
        requerimiento4.setVisible(false);
        requerimiento3.setVisible(false);

        button01.setVisible(false);

        VBox requerimientosBox = new VBox(25, requerimiento1, requerimiento2, requerimiento3, requerimiento4, nada, button01);
        requerimientosBox.setPrefHeight(10);

        requerimientosBox.setPadding(new Insets(120, 100, 0, 80));

        requerimiento1.setOnAction(event -> handleCheckBoxClick(ArchivosProces, button01,requerimientosBox,requerimiento1, "pdb", "cif", "mol", "xyz"));
        requerimiento2.setOnAction(event -> handleCheckBoxClick(ArchivosProces, button01,requerimientosBox,requerimiento2, "pdb",  "cif", "mol", "xyz"));
        requerimiento3.setOnAction(event -> handleCheckBoxClick(ArchivosProces,button01,requerimientosBox,requerimiento3, "xyz"));
        requerimiento4.setOnAction(event -> handleCheckBoxClick(ArchivosProces,button01,requerimientosBox,requerimiento4, "lammps", "lmp"));

        //Contenedor Maestro
        BorderPane root = new BorderPane();
        root.setTop(superior);
        root.setRight(requerimientosBox);
        root.setLeft(botonera);

        // Establecer la acción de los botones
        button0.setOnAction(event -> atras(button01, botonera, botoncito, root, superior, requerimientosBox, DIRECTORY_PATH));
        button2.setOnAction(event -> handleButtonClick(button2, botonera, botoncito, root, superior2, superior3, requerimientosBox));
        button3.setOnAction(event -> handleButtonClick(button3, botonera, botoncito, root, superior2, superior3, requerimientosBox));
        button4.setOnAction(event -> handleButtonClick(button4, botonera, botoncito, root, superior2, superior3, requerimientosBox));
        button5.setOnAction(event -> handleButtonClick(button5, botonera, botoncito, root, superior2, superior3, requerimientosBox));

        button01.setOnAction(event -> {
            // Limpiar la ventana de todos los componentes
            root.getChildren().clear();
            BorderPane newRoot = new BorderPane();

            // Crear e inicializar el ProgressIndicator
            ProgressIndicator progressIndicator = new ProgressIndicator();
            progressIndicator.setVisible(true);

            // Usar un VBox para centrar el ProgressIndicator
            VBox vbox = new VBox(progressIndicator);
            vbox.setStyle("-fx-alignment: center;");

            // Añadir el VBox al centro del BorderPane
            newRoot.setCenter(vbox);
            secondaryStage.setScene(new Scene(newRoot, 300, 200));
            secondaryStage.show();

            // Crear un Task para ejecutar el método en segundo plano
            Task<Void> task = new Task<>() {
                @Override
                protected Void call() throws Exception {
                    // Llama al método que realiza el procesamiento
                    MMH_Automatic.MMH_MetodoCaptador(ArchivosProces, listaProcesActivete(button2, button3, button4, button5));
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    // Cerrar la ventana una vez que el proceso haya terminado
                    secondaryStage.close();
                }

                @Override
                protected void failed() {
                    super.failed();
                    // Manejar el error si es necesario
                    System.err.println("El proceso falló.");
                    secondaryStage.close(); // Cerrar la ventana incluso si falla
                }
            };

            // Ejecutar el Task en un hilo de fondo
            new Thread(task).start();
        });

        // Cargar la imagen de la textura
        Image image0 = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/SubFija.jpg");
        // Crear el fondo con la imagen de la textura
        BackgroundImage backgroundImage = new BackgroundImage(image0, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        // Establecer el fondo en el Pane
        root.setBackground(background);
        Image image = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/png/Hexos.png");
        // Establecer el icono en la ventana principal
        secondaryStage.getIcons().add(image);

        // Crear la escena y mostrar la ventana
        Scene scene = new Scene(root, 700, 550);
        secondaryStage.setScene(scene);
        secondaryStage.setTitle("Selección de Procesos");
        // Deshabilitar la maximización de la ventana
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(175);
        return button;
    }


    private void handleButtonClick(Button button, VBox botonera, Pane botoncito, BorderPane root, HBox superior2, HBox superior3, VBox requerimientosBox) {
        if (selectedButton1 == null) {
            root.setTop(superior2);
            selectedButton1 = button;
            button.setStyle("-fx-border-color: lightblue; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-family: 'Times New Roman'");
        } else if (selectedButton2 == null && button == selectedButton1) {
            button.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-family: 'Times New Roman'");
            selectedButton1 = null;
        } else if (selectedButton2 == null) {
            root.setTop(superior3);
            selectedButton2 = button;
            // Ocultar los botones que no están entre los seleccionados
            boolean shouldHide = true;
            for (Node node : ((VBox) button.getParent()).getChildren()) {
                if (node instanceof Button) {
                    if (node == selectedButton1 || node == selectedButton2) {
                        shouldHide = !shouldHide; // Cambiar el estado para los botones entre los seleccionados
                    } else if (shouldHide) {
                        node.setVisible(false);
                    }
                }
            }
            mostrarCheckBoxes(botonera, requerimientosBox);
            botoncito.setVisible(true); //visibilidad a boton "atras"
            cambiarEstiloBotonesVisibles(botonera);
        }
    }

    private void cambiarEstiloBotonesVisibles(Pane contenedor) {
        for (Node nodo : contenedor.getChildren()) {
            if (nodo instanceof Button boton && nodo.isVisible()) {
                boton.setStyle("-fx-border-color: lightblue; -fx-border-width: 2px; -fx-background-color: rgba(200, 255, 255, 0.3); -fx-font-weight: bold; -fx-font-size: 13px;-fx-font-size: 13px;-fx-font-family: 'Times New Roman'");
            }
        }
    }

    public void atras(Button button01, Pane contenedor, Pane botoncito, BorderPane root, HBox superior, VBox requerimientosBox, String carpetaPath) {
        // Resto de tu código para manejar botones y UI
        button01.setVisible(false);
        for (Node nodo : contenedor.getChildren()) {
            if (nodo instanceof Button boton && !nodo.isVisible()) {
                boton.setVisible(true);
            }
            selectedButton1 = null;
            selectedButton2 = null;
            root.setTop(superior);
            nodo.setStyle("-fx-border-color: white; -fx-border-width: 2px; -fx-background-color: rgba(5, 5, 5, 0.1); -fx-font-weight: bold; -fx-font-size: 13px; -fx-font-family: 'Times New Roman'");
            botoncito.setVisible(false); // visibilidad a boton "atras"

            for (Node node : requerimientosBox.getChildren()) {
                if (node instanceof CheckBox && node.isVisible()) {
                    ((CheckBox) node).setVisible(false);
                }
            }
        }

        // Borrar todos los archivos de la carpeta especificada
        try {
            Path carpeta = Paths.get(carpetaPath);
            Files.list(carpeta)
                    .forEach(file -> {
                        try {
                            Files.delete(file);
                        } catch (IOException e) {
                            e.printStackTrace(); // Manejo de excepciones
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        // Habilitar todos los CheckBox en la clase y quitar la palomita
        for (Node node : requerimientosBox.getChildren()) {
            if (node instanceof CheckBox checkBox) {
                checkBox.setDisable(false); // Habilitar el CheckBox
                checkBox.setSelected(false); // Quitar la palomita
            }
        }
    }

    // Método para mostrar los CheckBoxes en el VBox de los checkpoints segun los preocesos
    public void mostrarCheckBoxes(VBox botonera, VBox requerimientosBox) {

        if (botonera.getChildren().get(2).isVisible() && botonera.getChildren().get(3).isVisible() && !botonera.getChildren().get(0).isVisible() && !botonera.getChildren().get(1).isVisible()) {
            //Implementar ventana para preguntar al usuario si tiene el adsorvente y adsorvanto separados en dos archivos o junntos y en cuanto a eso sustituir
            //requerimientosBox.getChildren().get(2).setVisible(true);
            //requerimientosBox.getChildren().get(3).setVisible(true);
            //por:
            //requerimientosBox.getChildren().get(5).setVisible(true);
        }

        if (botonera.getChildren().get(0).isVisible()) {
            requerimientosBox.getChildren().getFirst().setVisible(true);
            requerimientosBox.getChildren().get(1).setVisible(true);
        }
        if (botonera.getChildren().get(1).isVisible()) {
            requerimientosBox.getChildren().getFirst().setVisible(true);
            requerimientosBox.getChildren().get(1).setVisible(true);
        }
        if (botonera.getChildren().get(2).isVisible()) {
            requerimientosBox.getChildren().getFirst().setVisible(true);
            requerimientosBox.getChildren().get(1).setVisible(true);
        }
        if (botonera.getChildren().get(3).isVisible()) {
            requerimientosBox.getChildren().get(3).setVisible(true);
            requerimientosBox.getChildren().get(4).setVisible(true);
            requerimientosBox.getChildren().get(5).setVisible(true);
        }


//        int numElementos = Math.min(botonera.getChildren().size(), requerimientosBox.getChildren().size());
//        for (int i = 0; i < numElementos; i++) {
//            System.out.println(".");
//            Node boton = botonera.getChildren().get(i);
//            Node checkBox = requerimientosBox.getChildren().get(i);
//            if (boton.isVisible() && checkBox instanceof CheckBox) {
//                System.out.println("if");
//                checkBox.setVisible(true);
//            }
//        }
    }


    private void handleCheckBoxClick(List<Path>ArchivosProces,Button button01,VBox requerimientosBox,CheckBox checkBox, String... fileTypes) {
        if (checkBox.isSelected()) { // Solo ejecutar si el checkbox está seleccionado
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecciona un Archivo");

            // Verificar las extensiones y agregarlas como filtros individuales
            for (String fileType : fileTypes) {
                if (!fileType.isEmpty()) {
                    // Crear un filtro para cada tipo de archivo
                    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos " + fileType.toUpperCase(), "*." + fileType);
                    fileChooser.getExtensionFilters().add(extFilter);
                }
            }

            // Mostrar el cuadro de diálogo
            File file = fileChooser.showOpenDialog(checkBox.getScene().getWindow());

            if (file != null) {
                // Obtener el prefijo de UserData
                String prefix = checkBox.getUserData() + "."; // '1.', '2.', etc.

                // Definir la nueva ruta de destino con el nuevo nombre
                Path destinationPath = Path.of("E:/Proyecto/" + prefix + file.getName()); // Cambia esta ruta a la deseada
                ArchivosProces.add(destinationPath);
                try {
                    // Copiar el archivo a la nueva ubicación
                    Files.copy(file.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Archivo copiado a: " + destinationPath.toString());

                    // Desactivar el checkbox después de una selección exitosa
                    checkBox.setDisable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error al copiar el archivo: " + e.getMessage());
                    // Fijar el checkbox de nuevo si hay un error
                    checkBox.setSelected(false);
                }
            } else {
                // Si no seleccionó un archivo, no hacemos nada especial
                checkBox.setSelected(false);
            }
            actualizarBotonVisible(button01,requerimientosBox);
        }
    }

    private void checkDirectoryAndShowAlert() {
        Path directory = Paths.get(DIRECTORY_PATH);
        if (Files.exists(directory) && Files.isDirectory(directory)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
                if (stream.iterator().hasNext()) {
                    // Si hay archivos en la carpeta, mostrar la alerta
                    showAlert(directory);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(Path directory) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Borrado automático");
        alert.setHeaderText(null);
        alert.setContentText("Hemos detectado archivos en la ubicación de su proyecto, " +
                "para continuar, traspase y borre la información o continúe y se borrará automáticamente de su carpeta.");

        ButtonType closeButton = new ButtonType("Cerrar");
        ButtonType continueButton = new ButtonType("Continuar", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(closeButton, continueButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(closeButton) == continueButton) {
            deleteFilesInDirectory(directory);
        }
    }

    private void deleteFilesInDirectory(Path directory) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path file : stream) {
                Files.delete(file);
                System.out.println("Archivo eliminado: " + file.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actualizarBotonVisible(Button button01, VBox requerimientosBox) {
        // Verificar si todos los CheckBox visibles (excluyendo los dos últimos) están seleccionados
        boolean todosSeleccionados = true;
        int totalCheckBoxes = requerimientosBox.getChildren().size();

        for (int i = 0; i < totalCheckBoxes - 2; i++) { // Excluir los dos últimos
            Node node = requerimientosBox.getChildren().get(i);
            if (node instanceof CheckBox checkBox && checkBox.isVisible()) {
                if (!checkBox.isSelected()) {
                    todosSeleccionados = false;
                    break; // Salir del bucle si hay uno no seleccionado
                }
            }
        }
        // Mostrar u ocultar el botón según la condición
        button01.setVisible(todosSeleccionados);
    }

    public List<Boolean> listaProcesActivete(Button button2, Button button3, Button button4, Button button5) {
        List<Boolean> acti = new ArrayList<>();

        // Verificar la visibilidad de cada botón y agregar el resultado a la lista
        acti.add(button2.isVisible());
        acti.add(button3.isVisible());
        acti.add(button4.isVisible());
        acti.add(button5.isVisible());

        return acti;
    }



}








