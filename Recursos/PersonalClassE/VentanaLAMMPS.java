package Recursos.PersonalClassE;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;


public class VentanaLAMMPS extends Application {


    private TextArea outputTextArea;
    private File scriptFile;
    private FileWriter scriptWriter;


    @Override
    public void start(Stage cuaternaryStage) {

        String path = "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Cálculos_Termodinámicos";
        String Allpath = "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Cálculos_Termodinámicos/script.lmp";

        cuaternaryStage.setX(20);
        cuaternaryStage.setY(75);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Establecer la imagen de fondo
        Image image = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/SubFija.jpg"); // Cambia el path a la ruta de tu imagen
        BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, true, true));


        addButton("Tipo de material", "Type_Material", gridPane);
        addButton("Tipo de simulacion", "Type_of_Simulation", gridPane);
        addButton("Crear caja", "create_box", gridPane);
        addButton("Dimensiones periódicas", "Periodic_Contorno", gridPane);

        GridPane gridPane2 = new GridPane();
        gridPane2.setHgap(10);
        gridPane2.setVgap(10);

        addButton("Definir potencial", "define_potential", gridPane2);
        addButton("Energía del sistema", "add_particles2", gridPane2);
        addButton("Tiempo de simulación", "define_potential3", gridPane2);

        HBox predefinidosItem = new HBox(gridPane, gridPane2);
        predefinidosItem.setSpacing(30);
        predefinidosItem.setPadding(new javafx.geometry.Insets(20, 30, 0, 60));

        outputTextArea = new TextArea();
        //outputTextArea.setBorder();
        //outputTextArea.setBackground("");
        outputTextArea.setPrefSize(100, 500);

        Label titleLabel = new Label("Comandos Predefinidos");
        titleLabel.setStyle("-fx-text-fill: rgba(128, 128, 128, 0.8); -fx-font-size: 16px;");


        Label datoslammps = new Label(".lammps/lmp");
        Label datosdata = new Label(".data");

        Button cargarPredefinido = new Button("Buscar archivo");
        Button cargarPredefinido2 = new Button("Buscar archivo");

        VBox superiorboton = new VBox(2, cargarPredefinido, cargarPredefinido2);
        superiorboton.setPadding(new javafx.geometry.Insets(10, 0, 0, 5));
        VBox superiorlabel = new VBox(10, datoslammps, datosdata);
        superiorlabel.setPadding(new javafx.geometry.Insets(17, 0, 0, 2));
        superiorlabel.setStyle("-fx-text-fill: rgba(128, 128, 128, 0.5); -fx-font-size: 12px;");
        HBox predefinidoscargas = new HBox(4, superiorboton, superiorlabel);
        predefinidoscargas.setPadding(new javafx.geometry.Insets(10, 20, 10, 55));

        Button generateButton = new Button("Generar Archivo");
        generateButton.setVisible(false);
        Button executeButton = new Button("Ejecutar");
        executeButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: black; -fx-font-size: 16px;");
        executeButton.setOnMouseEntered(e -> executeButton.setStyle("-fx-background-color: #FF4500; -fx-text-fill: black; -fx-font-size: 16px;"));
        executeButton.setOnMouseExited(e -> executeButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: black; -fx-font-size: 16px;"));
        executeButton.setVisible(false);

        // Listener para detectar cambios en outputTextArea
        outputTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            // Muestra el botón si hay texto en outputTextArea
            generateButton.setVisible(!newValue.trim().isEmpty());
            if (!cargarPredefinido.isVisible() && !cargarPredefinido2.isVisible()) {
                generateButton.setVisible(false);
            }
            datoslammps.setVisible(newValue.trim().isEmpty());
            cargarPredefinido.setVisible(newValue.trim().isEmpty());
        });

        generateButton.setOnAction(event -> {
            // Crear el archivo de script de LAMMPS
            createScriptFile(path);
            if (!cargarPredefinido2.isVisible() && !cargarPredefinido.isVisible()) {
                executeButton.setVisible(true);
            }
            generateButton.setVisible(false);
            cargarPredefinido.setVisible(false);
            datoslammps.setVisible(false);
        });

        executeButton.setOnAction(event -> {
            Thread thread = new Thread(() -> {
                try {
                    // Comando para ejecutar lmp.exe
                    String comando = "lmp.exe -in script.lmp";
                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando);
                    builder.directory(new File(path)); // Estableciendo el directorio de trabajo
                    Process proceso = builder.start();

                    // Limpiar el área de texto al inicio
                    Platform.runLater(() -> {
                        outputTextArea.clear();
                        outputTextArea.appendText("Ejecutando lmp.exe...\n");
                    });

                    // Leer salida y errores
                    BufferedReader reader = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
                    BufferedReader errorReader = new BufferedReader(new InputStreamReader(proceso.getErrorStream()));
                    String line;

                    // Leer salida del proceso
                    while ((line = reader.readLine()) != null) {
                        String finalLine = line;
                        Platform.runLater(() -> outputTextArea.appendText(finalLine + "\n"));
                    }

                    // Leer errores del proceso
                    while ((line = errorReader.readLine()) != null) {
                        String finalLine = line;
                        Platform.runLater(() -> outputTextArea.appendText("Error: " + finalLine + "\n"));
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
                            // Puedes cerrar el cuaternaryStage aquí si es necesario
                            cuaternaryStage.close();
                        });
                        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre
                    });

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    // Manejo de errores
                    Platform.runLater(() -> outputTextArea.appendText("Ha ocurrido un error: " + e.getMessage() + "\n"));
                }
            });

            thread.start(); // Iniciar el hilo
        });


        cargarPredefinido.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos LAMMPS", "*.lammps", "*.lmp");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(cuaternaryStage);

            if (selectedFile != null && (selectedFile.getName().endsWith(".lammps") || selectedFile.getName().endsWith(".lmp"))) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        outputTextArea.appendText(line + "\n");
                    }
                    reader.close();

                    File destinationDirectory = new File(path);
                    File originalFile = selectedFile;
                    File destinationFile = new File(destinationDirectory, "script" + ".lmp");
                    Files.copy(originalFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    generateButton.setVisible(false);
                    if (!cargarPredefinido2.isVisible()) {
                        executeButton.setVisible(true);
                    }
                    cargarPredefinido.setVisible(false);
                    datoslammps.setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        cargarPredefinido2.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de datos", "*.data", "data.*");
            fileChooser.getExtensionFilters().add(extFilter);
            File selectedFile = fileChooser.showOpenDialog(cuaternaryStage);

            if (selectedFile != null && (selectedFile.getName().endsWith(".data")) || selectedFile != null && (selectedFile.getName().startsWith("data."))) {
                try {
//                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        outputTextArea.appendText(line + "\n");
//                    }
//                    reader.close();

                    File destinationDirectory = new File(path);
                    File destinationFile = new File(destinationDirectory, selectedFile.getName());
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    if (!cargarPredefinido.isVisible()) {
                        executeButton.setVisible(true);
                    }
                    if (!generateButton.isVisible()) {
                        executeButton.setVisible(true);
                    }
                    cargarPredefinido2.setVisible(false);
                    datosdata.setVisible(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        HBox ten = new HBox(20, generateButton, executeButton);

        ten.setPadding(new javafx.geometry.Insets(50, 0, 0, 0));
        ten.setAlignment(Pos.BASELINE_RIGHT);

        VBox tin = new VBox(outputTextArea);
        tin.setPadding(new javafx.geometry.Insets(15, 20, 10, 60));

        VBox tan = new VBox(titleLabel);
        tan.setPadding(new javafx.geometry.Insets(10, 0, 10, 60));

        VBox root = new VBox(predefinidoscargas, tin, tan, predefinidosItem, ten);

        root.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(root, 620, 625);

        cuaternaryStage.setTitle("Orden LAMMPS");
        cuaternaryStage.setScene(scene);
        cuaternaryStage.show();
    }

    private void createScriptFile(String path) {
        try {
            scriptFile = new File(path + "/script.lmp");
            scriptWriter = new FileWriter(scriptFile);

            String texto = outputTextArea.getText(); // Obtener el texto del TextPane
            scriptWriter.write(texto); // Escribir el texto en el archivo

            scriptWriter.close(); // Cerrar el FileWriter una vez se ha terminado de escribir

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void executeScript() {
//        try {
//            scriptWriter.write(outputTextArea.getText());
//            scriptWriter.flush();
//            // Ejecutar LAMMPS con el archivo de script
//            Process process = Runtime.getRuntime().exec("C:/Users/Julio C/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/LAMMPS 64-bit 2Aug2023 with GUI/LAMMPS-Shell");
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                final String finalLine = line;
//                Platform.runLater(() -> outputTextArea.appendText(finalLine + "\n"));
//            }
//
//            int exitCode = process.waitFor();
//            System.out.println("Proceso terminado con código de salida: " + exitCode);
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

//    public void executeScript(String path) {
//        // Mostrar ventana de carga
//        ProgressDialog loadingDialog = new ProgressDialog("Cargando...", "Espere por favor.");
//        loadingDialog.show();
//
//        Task<Void> task = new Task<Void>() {
//            @Override
//            protected Void call() throws Exception {
//                try {
//                    // Cambiar de directorio
//                    String cambioDirectorio = "cmd /c cd " + path;
//                    ProcessBuilder builderCd = new ProcessBuilder("cmd.exe", "/c", cambioDirectorio);
//                    Process procesoCd = builderCd.start();
//                    procesoCd.waitFor();
//
//                    String comando = "cmd /c lmp.exe -in script.lammps";
//                    ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", comando);
//                    builder.directory(new File(path));
//                    Process proceso = builder.start();
//                    proceso.waitFor();
//                } catch (IOException | InterruptedException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void succeeded() {
//                // Ocultar ventana de carga al finalizar
//                loadingDialog.hide();
//            }
//        };
//
//        Thread thread = new Thread(task);
//        thread.start();
//    }

    // Crear botones de funciones LAMMPS predefinidas
    private void addButton(String buttonText, String commandText, GridPane gridPane) {
        Button button = new Button(buttonText);
        button.setStyle("-fx-background-color: #ADD8E6; -fx-text-fill: black;");

        button.setOnAction(event -> {
            String command = generateCommand(commandText);
            outputTextArea.appendText(command + "\n");
        });

        gridPane.add(button, 0, gridPane.getChildren().size());
    }

    private String generateCommand(String commandText) {
        String fileName = "LammpsComandos/" + commandText + ".txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error al leer el archivo";
        }

        return content.toString();
    }

    private String getFileExtension(File file) {
        String extension = "";
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            extension = file.getName().substring(i);
        }
        return extension;
    }

//// Crea el archivo "script.lammps" en la misma carpeta que tu programa Java
//File scriptFile = new File("script.lammps");
//// Escribe los comandos de LAMMPS en el archivo
//FileWriter writer = new FileWriter(scriptFile);
//writer.write("create_box 3\natom_style atomic\ncreate_atoms 1 single 0.0 0.0 0.0\npair_style lj/cut 2.5\npair_coeff 1 1 1.0 1.0\nboundary p p p\nfix 1 all nvt temp 300.0 300.0 100.0\nrun 1000");
//writer.close();

}