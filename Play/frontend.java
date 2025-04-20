package Play;

import Play.Controllers.AvogadroController;
import Play.Controllers.JmolController;
import Play.Controllers.LammpsController;
import Play.Controllers.MopacController;
import Recursos.PersonalClassB.*;
import Recursos.PersonalClassE.*;
import Recursos.PersonalClassF.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressIndicator;

import javafx.scene.input.MouseButton;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.control.Tooltip;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;

import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.layout.StackPane;



import javafx.scene.layout.*;
import javafx.stage.StageStyle;

import javax.swing.*;

import static Play.menu.obtenerUltimaRuta;

//import static Recursos.PersonalClassB.JmolExample.getVisualComponent;
//import static Recursos.PersonalClassB.JmolExample.loadAndDisplay;


public class frontend extends Application {

    private String selectedFilePath;
    private TextArea textArea;
    @Override
    public void start(Stage primaryStage) {

        //Indicador segundario de carga
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false); // Inicialmente oculto

        //Boones extendibles para barra horizontal superior
        Menu inicioMenu = new Menu(" Inicio ");
             MenuItem BuscarItem = new MenuItem(" Buscar ");
             Menu ComponentesItem = new Menu(" Componentes ");
                                  MenuItem visor = new MenuItem(" Repositorio local ");
                                  MenuItem  texta = new  MenuItem (" Campos de texto ");

        Menu procesMenu = new Menu(" Procesos ");
                      MenuItem ProItemHmm = new MenuItem(" HMM ");
                      Menu ProItemSelect = new Menu(" Seleccionar ");
                            MenuItem ceroProcesItem = new MenuItem(" Modelar Estructura ");
                            MenuItem OneProcesItem = new MenuItem(" Optimizar Geometrias ");
                            MenuItem TwoProcesItem = new MenuItem(" Generar Celdas ");
                            MenuItem ThreeProcesItem = new MenuItem(" Minimizar Energía ");
                            MenuItem FourProcesItem = new MenuItem(" Cálculos Termodinámicos ");


        Menu configuracionMenu = new Menu(" Configuración ");
        Menu programasMenu = new Menu(" Dependencias ");
        Menu helpMenu = new Menu(" Ayuda ");
             Menu Softwars = new Menu ("Programas");
                  MenuItem OpenBabelHelp = new MenuItem("OpenBabel");
                  MenuItem JmolHelp = new MenuItem("Jmol");
                  MenuItem AvogadroHelp = new MenuItem("Avogadro");
                  MenuItem MOPACHelp = new MenuItem("MOPAC");
                  MenuItem LAMMPSHelp = new MenuItem("LAMMPS");
             Menu Proceswars = new Menu ("Procesos");
                  MenuItem procesConstruccion = new MenuItem("Construcción Molecular");
                  MenuItem procesOptimi = new MenuItem("Optimización Geométrica");
                  MenuItem procesCell = new MenuItem("Generador de Celdas");
                  MenuItem procesMini = new MenuItem("Minimización de Energía");
                  MenuItem procesOrdenLammps = new MenuItem("Cálculos Termodinámicos");
             Menu Documentacion = new Menu ("Documentación");
                  MenuItem OpenBabelHelpD = new MenuItem("Web OpenBabel");
                  MenuItem JmolHelpD = new MenuItem("Web Jmol");
                  MenuItem AvogadroHelpD = new MenuItem("Web Avogadro");
                  MenuItem MOPACHelpD = new MenuItem("Web MOPAC");
                  MenuItem LAMMPSHelpD = new MenuItem("Web LAMMPS");




        // Crear barra de menú
        MenuBar menuBar = new MenuBar(inicioMenu, procesMenu, configuracionMenu, programasMenu, helpMenu);
        menuBar.setMaxWidth(Double.MAX_VALUE); //extendiendola completamente horizontal
        DropShadow dropShadow = new DropShadow();
        menuBar.setEffect(dropShadow);


        // Crear botones adicionales para el menú "Configuración"
        MenuItem OneconfiguracionMenu = new MenuItem(" Personalizar Fondo ");

        // Crear botones adicionales para el menú "Dependencias"
        MenuItem jmolMenuItem = new MenuItem(" Jmol ");
        MenuItem OpenBMenuItem = new MenuItem(" Open Babel ");
        MenuItem AvogadroMenuItem = new MenuItem(" Avogadro ");
        MenuItem MOPACMenuItem = new MenuItem(" MOPAC ");
        MenuItem LammpsMenuItem = new MenuItem(" LAMMPS ");



        ComponentesItem.getItems().addAll(visor, texta);

        // Agregar los botones al menú "Inicio"
        inicioMenu.getItems().addAll(BuscarItem,ComponentesItem);

        // Agregar los botones al menú "Procesos"
        procesMenu.getItems().addAll(ProItemHmm, ProItemSelect);

        ProItemSelect.getItems().addAll(ceroProcesItem, OneProcesItem, TwoProcesItem,ThreeProcesItem,FourProcesItem);

        // Agregar los botones al menú "Configuración"
        configuracionMenu.getItems().addAll(OneconfiguracionMenu);

        helpMenu.getItems().addAll(Softwars);
        Softwars.getItems().addAll(OpenBabelHelp, JmolHelp, AvogadroHelp, MOPACHelp,LAMMPSHelp);

        helpMenu.getItems().addAll(Proceswars);
        Proceswars.getItems().addAll(procesConstruccion, procesOptimi, procesCell, procesMini,procesOrdenLammps);

        helpMenu.getItems().addAll(Documentacion);
        Documentacion.getItems().addAll(OpenBabelHelpD, JmolHelpD, AvogadroHelpD, MOPACHelpD,LAMMPSHelpD);

        // Agregar los botones al menú "Dependencias"
        programasMenu.getItems().addAll(jmolMenuItem, OpenBMenuItem, AvogadroMenuItem,  MOPACMenuItem, LammpsMenuItem);

        // Crear contenedor HBox para la barra horizontal
        HBox hBox = new HBox(menuBar);

        // Establecer que la barra de menú se expanda para cubrir a lo ancho.
        HBox.setHgrow(menuBar, Priority.ALWAYS);

        menuBar.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
        menuBar.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        menuBar.getMenus().forEach(menu -> {
            menu.setStyle("-fx-font-size: 14px;"); //
            menu.getItems().forEach(item -> {
                if (item != null) {
                    item.setStyle("-fx-font-size: 14px;"); //
                }
            });
        });

        // Botón para cargar archivo
//        Button cargarArchivoButton = new Button("Buscar");
//        cargarArchivoButton.setStyle("-fx-background-color: rgba(42,121,147,0.93);");
//        cargarArchivoButton.setPrefSize(80, 35);

        // Botón para mostrar archivo
//        Button mostrarArchivoButton = new Button("Mostrar Reciente");
//        mostrarArchivoButton.setStyle("-fx-background-color: rgba(42,121,147,0.93);");
//        mostrarArchivoButton.setPrefSize(180, 35);

        // datos en horizontal para mostrar requerimientos
        HBox requerimientosBox = new HBox();
        requerimientosBox.setPrefHeight(50);
        CheckBox requerimiento1 = new CheckBox("Archivo: Adsorvente ○      ");
        CheckBox requerimiento2 = new CheckBox("Archivo: Adsorvato •      ");
        CheckBox requerimiento3 = new CheckBox("Archivo: Campo de Fuerza ▓     ");
       // requerimientosBox.getChildren().add(requerimiento1);


        // Crear un StackPane para el área transparente
        StackPane transparentPane = new StackPane();
        // Establecer el estilo para que el StackPane sea transparente
        transparentPane.setStyle("-fx-background-color: rgba(200, 255, 255, 0.3);"); // Color de fondo transparente con opacidad 0.5
        transparentPane.setPrefWidth(250); // Ancho preferido
        transparentPane.setPrefHeight(200);

        VBox vbox = new VBox(20); // Espacio de 10 entre cada nodo
        vbox.setAlignment(Pos.TOP_LEFT); // Alinear en el centro
        vbox.setPadding((new Insets(30, 0, 100, 0)));

        // Actualizar tree
        Button Actualizar = new Button("Actualizar");
        // Estilo inicial para el botón
        Actualizar.setStyle("-fx-background-color: transparent; " + // Fondo transparente
                "-fx-border-color: transparent; " + // Borde transparente
                "-fx-text-fill: black; " + // Color del texto
                "-fx-font-family: 'Times New Roman'; " + // Fuente
                "-fx-font-size: 14px;"); // Tamaño de fuente
        // Evento para cambiar el color del texto al pasar el mouse
        Actualizar.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
            Actualizar.setStyle("-fx-background-color: transparent; " +
                    "-fx-border-color: transparent; " +
                    "-fx-text-fill: white; " + // Cambiar texto a blanco
                    "-fx-font-family: 'Times New Roman'; " +
                    "-fx-font-size: 14px;");
        });
        // Evento para volver al color original al salir el mouse
        Actualizar.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
            Actualizar.setStyle("-fx-background-color: transparent; " +
                    "-fx-border-color: transparent; " +
                    "-fx-text-fill: black; " + // Volver a negro
                    "-fx-font-family: 'Times New Roman'; " +
                    "-fx-font-size: 14px;");
        });
        // Evento para cambiar el borde al hacer clic
        Actualizar.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Actualizar.setStyle("-fx-background-color: transparent; " +
                    "-fx-border-color: white; " + // Cambiar borde a blanco
                    "-fx-text-fill: white; " + // Mantener texto blanco
                    "-fx-font-family: 'Times New Roman'; " +
                    "-fx-font-size: 14px;");
        });
        // Evento para restablecer el borde al soltar el clic
        Actualizar.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            Actualizar.setStyle("-fx-background-color: transparent; " +
                    "-fx-border-color: transparent; " + // Volver a borde transparente
                    "-fx-text-fill: white; " + // Mantener texto blanco
                    "-fx-font-family: 'Times New Roman'; " +
                    "-fx-font-size: 14px;");
        });

        Label label3 = new Label("Label 3");
        label3.setFont(Font.font("Times New Roman", 16));
        label3.setStyle("-fx-font-weight: bold;");

        Label label4 = new Label("Label 4");
        label4.setFont(Font.font("Times New Roman", 16));
        label4.setStyle("-fx-font-weight: bold;");

        File directory1 = new File("Recursos/Repositorio_Local/Modelación_Molecular");
        File directory2 = new File("Recursos/Repositorio_Local/Moléculas_Optimizadas");
        File directory3 = new File("Recursos/Repositorio_Local/Generación_de_Celdas");
        File directory4 = new File("Recursos/Repositorio_Local/Cálculos_Termodinámicos");
        File directory5 = new File("Recursos/Repositorio_Local/Archivos_Moleculares");
        File directory6 = new File("Recursos/Repositorio_Local/Proyecto1");
        File central = new File("Recursos/Repositorio_Local");

        List<File> paths = new ArrayList<>();
        paths.add(central);
        paths.add(directory1);
        paths.add(directory2);
        paths.add(directory3);
        paths.add(directory4);
        paths.add(directory5);
        paths.add(directory6);


        // Create the TreeItems
        TreeItem<File> rootItem0 = new TreeItem<>();
        rootItem0.setExpanded(true);
        List<TreeItem<File>> rootItems = createTreeViews(paths);

        // Create the TreeView and set the root
        TreeView<File> treeView = new TreeView<>();
        treeView.setStyle("-fx-focus-color: white; -fx-blend-mode: multiply; -fx-background-color: white");
        treeView.setRoot(new TreeItem<>(central));


        // Add the TreeItems to the TreeView
        for (TreeItem<File> rootItem : rootItems) {
            treeView.getRoot().getChildren().add(rootItem);
        }

        treeView.setCellFactory(param -> new CustomTreeCell());

        vbox.getChildren().addAll(Actualizar,treeView);

        transparentPane.getChildren().add(vbox);
        transparentPane.getChildren().add(progressIndicator);


        // Crear los botones
//        Button button3 = new Button("Optimizar Geometría");
//        Button button4 = new Button("Generar Celdas");
//        Button button5 = new Button("Minimizar Energías");
//        Button button6 = new Button("Cálculos Termodinámicos");

        // Cambiar el tamaño de los botones
//        double buttonWidth = 220; // Ancho personalizado
//        double buttonHeight = 35; // Alto personalizado
//        button3.setPrefSize(buttonWidth, buttonHeight);
//        button4.setPrefSize(buttonWidth, buttonHeight);
//        button5.setPrefSize(buttonWidth, buttonHeight);
//        button6.setPrefSize(buttonWidth, buttonHeight);
//        String buttonStyle = "-fx-font-weight: bold; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-family: fantasy; -fx-background-color: rgba(30,31,34,0.82);";
//        button3.setStyle(buttonStyle);
//        button4.setStyle(buttonStyle);
//        button5.setStyle(buttonStyle);
//        button6.setStyle(buttonStyle);
//        cargarArchivoButton.setStyle(buttonStyle);
//        mostrarArchivoButton.setStyle(buttonStyle);


        //CONTENEDOR
        BorderPane root = new BorderPane();

        // Cargar la imagen de la textura
        Image image0 = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/Fija.jpg");

        // Crear el fondo con la imagen de la textura
        BackgroundImage backgroundImage = new BackgroundImage(image0, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        // Establecer el fondo en el Pane
        root.setBackground(background);

        Image image = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/png/Hexos.png");
        // Establecer el icono en la ventana principal
        primaryStage.getIcons().add(image);

        // Crear el campo de texto
        textArea = new TextArea();
        textArea.setWrapText(false);
        textArea.setPrefWidth(200); // Establecer ancho
        textArea.setPrefHeight(450); // Establecer alto
        textArea.setStyle("-fx-font-size: 10pt;");

        // Deshabilitar la edición del texto
        textArea.setEditable(false);
        // Deshabilitar el foco en el componente para que el usuario no pueda interactuar con él
        textArea.setFocusTraversable(false);

        // Crear campo de texto secundario
        TextArea textArea2 = new TextArea();
        textArea2.setWrapText(true);
        textArea2.setPrefWidth(200); // Establecer ancho
        textArea2.setPrefHeight(150); // Establecer alto
        textArea2.setStyle("-fx-font-size: 10pt;");

        // Deshabilitar la edición del texto
        textArea2.setEditable(false);
        // Deshabilitar el foco en el componente para que el usuario no pueda interactuar con él
        textArea2.setFocusTraversable(false);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle(
                "-fx-background-color: rgba(14,22,24,0.24);" + // Color de fondo del ScrollPane
                        "-fx-background: rgba(36,43,45,0.4);" // Color de fondo del ScrollPane
        );

        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setContent(textArea2);
        scrollPane2.setFitToWidth(true);
        scrollPane2.setFitToHeight(true);
        scrollPane2.setStyle(
                "-fx-background-color: rgba(14,22,24,0.47);" + // Color de fondo del ScrollPane
                        "-fx-background: rgba(36,43,45,0.95);" // Color de fondo del ScrollPane
        );

        // Crear la barra de herramientas DESPLEGABLE con botones
        MenuButton menuButton = new MenuButton("Archivo");
        MenuButton menuButton2 = new MenuButton("Visualizar Estructura");
        MenuButton menuButton3 = new MenuButton("Lista de Archivos");
        MenuButton menuButton4 = new MenuButton("Convertir Archivo");

        MenuItem item0 = new MenuItem("Editar");
        MenuItem item3 = new MenuItem("Guardar como:");
        MenuItem item4 = new MenuItem("Limpiar");


        //ArchCarg.getItems().addAll();//Aqui va el metodo para que cuando se carguen varios archivos se pueda acceder desde aqui

        menuButton.getItems().addAll(item0, item3, item4);

        HBox botonerA = new HBox(menuButton,menuButton2,menuButton3, menuButton4);


        //CustomNode customNode = new CustomNode("Ejemplo");

        // En construccion------------------------------------------------------------
       // MenuBar menuBar = new MenuBar();
       // Menu fileMenu = new Menu("File");
        //MenuItem openMenuItem = new MenuItem("Open");
       // fileMenu.getItems().add(openMenuItem);
        //menuBar.getMenus().add(fileMenu);

       // TabPane tabPane = new TabPane();
       // scrollPane.setContent(tabPane);
        //root.setTop(menuBar);
        //root.setCenter(tabPane);

        //openMenuItem.setOnAction(e -> {
       //     // Lógica para abrir un nuevo archivo y cargarlo en un nuevo Tab dentro del TabPane
       //     Tab tab = new Tab("New File");
       //     tab.setContent(textArea);
       //     tabPane.getTabs().add(tab);
       // });
        // -----------------------------------------------------------------------------------

        // Crear el contenedor para el campo de texto de forma vertical(Vbox) derecha
        VBox buttonBox2 = new VBox(0); // Espaciado entre los botones

        // Establecer la opacidad de los hijos del VBox
        botonerA.setOpacity(0.9);
        scrollPane.setOpacity(0.9);
        scrollPane2.setOpacity(0.9);

        buttonBox2.getChildren().addAll(botonerA, scrollPane, scrollPane2);
        // buttonBox2.setOpacity(0.75);

        // Ajustar los hemisferios al tamaño de la ventana
        // buttonBox.prefWidthProperty().bind(primaryStage.widthProperty().divide(2));
        buttonBox2.prefWidthProperty().bind(primaryStage.widthProperty().divide(2.4));

        // Aplicar un Padding al BorderPane para crear la sangría en toda la ventana
        // root.setPadding(new Insets(4, 30, 30, 40));

        // Agregar margen a los VBox
        // hBox.setPadding(new javafx.geometry.Insets(0, 0, 0, 0));

        buttonBox2.setPadding(new Insets(30, 20, 30, 0));  // 10 pixeles de margen a la izquierda

        // Colocar los elementos en el contenedor BorderPane
        root.setTop(hBox);
        root.setLeft(transparentPane);
        root.setRight(buttonBox2); // Colocar el ScrollPane en el lado derecho

        // Aplicar estilos de superficie al layout
        //String cssLayout = "-fx-background-color: rgba(150,40,102,0.91);";
        //root.setStyle(cssLayout);

        // Cargar la imagen desde un archivo PNG
//        Image imageee = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/png/aqua.png");
        // Crear un ImageView para mostrar la imagen
//        ImageView imageView = new ImageView(imageee);
//        imageView.setTranslateX(720); // Ajustar la posición en el eje X
//        imageView.setTranslateY(560); // Ajustar la posición en el eje Y
//        imageView.setFitWidth(161); // Establecer el ancho
//        imageView.setFitHeight(79); // Establecer el alto
//        imageView.setOpacity(0.7);


        // Configurar la escena
        Scene scene = new Scene(root); // Ancho y alto de la ventana
//        root.getChildren().addAll(imageView);

        // Cargar el archivo CSS para aplicar los estilos personalizados
        //scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/Recursos/StyleBarra.css")).toExternalForm());

        // Cargar archivo CSS
        // scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("StyleLetra.css")).toExternalForm());

        //inicioMenu.getStyleClass().add("StyleLetra");
       // configuracionMenu.getStyleClass().add("menu-StyleLetra");
       // programasMenu.getStyleClass().add("StyleLetra");
       // helpMenu.getStyleClass().add("StyleLetra"); // Agregar clase CSS al Menu "Help"
        String as = "×";

        // Crear tooltips con la información deseada
        //Tooltip tooltip1 = new Tooltip(" La visualización de estructuras tridimensionales, especialmente de moléculas, es fundamental en química y biología computacional. Permite a los científicos y estudiantes comprender mejor la geometría, la conformación y las interacciones de las moléculas, lo que es crucial para el diseño de fármacos, el estudio de reacciones químicas, la predicción de propiedades moleculares, entre otros aspectos." +
       //         "                    Archivos aceptados: PDB; XYZ; MOL/MOL2; SDF; CIF; CML; gjf, g03, g09");
        //Tooltip tooltip2 = new Tooltip("Click izquierdo para mostrar contenido\n" + "Click derecho para visualización");
        //Tooltip tooltip3 = new Tooltip("Información del Botón 3");
       // Tooltip tooltip4 = new Tooltip("Información del Botón 4");
       // Tooltip tooltip5 = new Tooltip("Información del Botón 5");
        //Tooltip tooltip6 = new Tooltip("Información del Botón 6");
        // Asignar tooltips a los botones( esto muestra al quedarse el mause en el boton un gran cartel con la info) redundante
        //Tooltip.install(button1, tooltip1);
        //Tooltip.install(label2, tooltip2);
        //Tooltip.install(button3, tooltip3);
        //Tooltip.install(button4, tooltip4);
        //Tooltip.install(button5, tooltip5);
        //Tooltip.install(button6, tooltip6);

//        button3.setOnMouseEntered(e -> textArea2.setText(tooltip3.getText()));
//        button4.setOnMouseEntered(e -> textArea2.setText(tooltip4.getText()));
//        button5.setOnMouseEntered(e -> textArea2.setText(tooltip5.getText()));
//        button6.setOnMouseEntered(e -> textArea2.setText(tooltip6.getText()));

        //-----------------Acciones-------------------------Acciones--------------------------Acciones---------------//
        Actualizar.setOnAction(event -> {
            //---------------/*//UpdateTreeView//*/----------------//
            TreeViewUpdater treeViewUpdater = new TreeViewUpdater();
            treeViewUpdater.monitorDirectoryChanges(treeView, paths);
        });

        OneProcesItem.setOnAction(event -> abrirVentanaOptGeometric());

        TwoProcesItem.setOnAction(event -> {
            abrirVentanaGeneracionCeldas(treeView, paths);
        });

        ThreeProcesItem.setOnAction(event -> {
            abrirVentanaMinEnergy();
        });

        treeView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.getValue().isFile()) {
                selectedFilePath = selectedItem.getValue().getPath();
                // Llamar al método de otra clase para cargar el contenido del archivo
                String fileContent = menu.LeerContenidoArchivo(selectedFilePath);
                // Mostrar el contenido en el TextArea
                textArea.setText(fileContent);
            }else{
                System.out.println("Carpeta seleccionada");
            }
        });

        treeView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                TreeItem<File> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if (selectedItem != null && selectedItem.getValue() != null) {
                    String path = selectedItem.getValue().getAbsolutePath();
                    String fileName = selectedItem.getValue().getName();

                    Screen screen = Screen.getPrimary();
                    Rectangle2D bounds = screen.getVisualBounds();
                    // Calcular dos tercios del ancho de la pantalla
                    double twoThirdsScreenWidth = bounds.getWidth() * 2 / 3;
                    // Verificar si la ventana es mayor que dos tercios de la pantalla en dirección horizontal

                    double newWidth = 0;
                    if (primaryStage.getWidth() >= twoThirdsScreenWidth) {
                        // Calcular nuevas dimensiones para la ventana principal
                        newWidth = bounds.getWidth() / 1.6;
                        double newHeight = bounds.getHeight();
                        // Configurar la ventana principal con las nuevas dimensiones y posición
                        primaryStage.setWidth(newWidth);
                        primaryStage.setHeight(newHeight);
                        System.out.println("Printeoooooo");
                    }else{
                        newWidth = bounds.getWidth() / 1.6;
                    }

                    JmolClass.loadAndDisplay3(path, newWidth);
                    textArea2.setText("Archivo molecular " + fileName + " visualizado");
                }
            }
        });



        item0.setOnAction(e -> {
            // habilitar la edición del texto

            if(item0.getText() == "Editar") {
                textArea.setEditable(true);
                // habilitar el foco en el componente para que el usuario no pueda interactuar con él
                textArea.setFocusTraversable(true);
                item0.setText("Editar" + as);
                textArea2.setText("Edicion habilitada");
            }else{
                textArea.setEditable(false);
                // Deshabilitar el foco en el componente para que el usuario no pueda interactuar con él
                textArea.setFocusTraversable(false);
                // Establecer un cursor personalizado para el JTextArea
                // Establecer un cursor personalizado para el TextArea
                item0.setText("Editar");
                textArea2.setText("Edicion deshabilitada");
            }
        });

        item4.setOnAction(e -> {
            textArea.clear();
        });

        visor.setOnAction(e -> {
            transparentPane.setVisible(!transparentPane.isVisible());
        });

        texta.setOnAction(e -> {
            buttonBox2.setVisible(!buttonBox2.isVisible());
        });

        ProItemHmm.setOnAction(event -> {
            VentanaProces secondaryWindow = new VentanaProces();
            secondaryWindow.start(new Stage());
        });

        // Agregar acción al botón "Jmol"
        jmolMenuItem.setOnAction(e -> {
            String jarFilePath = "APIs_Crudas/Jmol/Jmol.jar";
            JmolController.openJarFile(jarFilePath);
        });

        OpenBMenuItem.setOnAction(e -> {
            String openBabelExePath = "APIs_Crudas/OpenBabel/OpenBabel_exe/obgui.exe";
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(openBabelExePath);
                processBuilder.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

       //---------------------Ayudas al usuario-----------------------------------------

        //-----------Ayudas al usuario programa------------
        OpenBabelHelp.setOnAction(event -> {
            textArea2.setText("Open Babel es un software diseñado para la conversión de formatos de archivos químicos y la manipulación de datos químicos.\n");
            textArea2.appendText("Puede manejar una amplia variedad de estructuras químicas, incluyendo moléculas orgánicas, inorgánicas, y macromoléculas.\n");
            textArea2.appendText("Permite la conversión entre diferentes formatos de archivo utilizados en química, como SMILES, InChI, SDF, y muchos otros.\n");
            textArea2.appendText("Puede convertir entre una amplia variedad de formatos de archivos químicos. Algunos de los formatos más comunes que soporta incluyen:\n");
            textArea2.appendText("SMILES (Simplified Molecular Input Line Entry System)\n");
            textArea2.appendText("InChI (International Chemical Identifier)\n");
            textArea2.appendText("SDF (Structure Data File)\n");
            textArea2.appendText("MOL (MDL Molfile)\n");
            textArea2.appendText("MOL2 (Tripos Mol2 format)\n");
            textArea2.appendText("PDB (Protein Data Bank)\n");
            textArea2.appendText("XYZ (Cartesian coordinates)\n");
            textArea2.appendText("CML (Chemical Markup Language)\n");
            textArea2.appendText("TGF (Tripos Generic Format)\n");
            textArea2.appendText("BRIDGE (Bridge format)\n");
            textArea2.appendText("CIF (Crystallographic Information File)\n");
            textArea2.appendText("JSON (JavaScript Object Notation)\n");
            textArea2.appendText("XML (Extensible Markup Language)\n");
        });
        JmolHelp.setOnAction(event -> {
            textArea2.setText("Jmol es una aplicación de visualización molecular en 3D que permite a los usuarios interactuar con estructuras químicas y biológicas.\n");
            textArea2.appendText("Permite diferentes funcionaliades como visulaziacion y construccion molecular, ademas de optimizacion geométrica.\n");
            textArea2.appendText("Puede importar y visualizar una variedad de formatos de archivo:\n");
            textArea2.appendText("PDB: Archivos de la base de datos de proteínas.\n");
            textArea2.appendText("SDF: Archivos de datos estructurales.\n");
            textArea2.appendText("MOL: Archivos de formato MDL.\n");
            textArea2.appendText("XYZ: Archivos de coordenadas cartesianas.\n");
            textArea2.appendText("CML: Archivos de lenguaje de marcado químico.\n");
        });
        AvogadroHelp.setOnAction(event -> {
            textArea2.setText("Avogadro es un software de modelado molecular y visualización en 3D, diseñado para facilitar la creación y manipulación de estructuras químicas.\n") ;
            textArea2.appendText("Permite realizar optimizaciones de geometría para encontrar la configuración más estable de una molécula.\n");
            textArea2.appendText("Utiliza métodos de cálculo como MMFF (Force Field), AM1, y PM3, que son adecuados para diferentes tipos de moléculas..\n");
            textArea2.appendText("Es compatible con varios formatos de archivo, incluyendo:\n");
            textArea2.appendText("MOL (MDL Molfile)\n");
            textArea2.appendText("PDB (Protein Data Bank)\n");
            textArea2.appendText("XYZ (Cartesian coordinates)\n");
            textArea2.appendText("CIF (Crystallographic Information File)\n");
            textArea2.appendText("JSON (JavaScript Object Notation)\n");
        });
        MOPACHelp.setOnAction(event -> {
            textArea2.setText("MOPAC (Molecular Orbital PACkage) es un software de química computacional utilizado para realizar cálculos de química cuántica y modelado molecular.\n");
            textArea2.appendText("Utiliza métodos de mecánica cuántica, principalmente la teoría de funcional de densidad (DFT) y métodos semi-empíricos, para calcular las propiedades electrónicas y geométricas de las moléculas.\n");
            textArea2.appendText("Permite realizar cálculos utilizando varios métodos semi-empíricos, como AM1, PM3, y PM6.\n");
            textArea2.appendText("Permite calcular propiedades moleculares como energías de enlace, dipolos eléctricos, espectros UV-Vis, y más.\n");
            textArea2.appendText("Es compatible con varios formatos de archivo de entrada, incluyendo:\n");
            textArea2.appendText("MOP: Archivo de entrada principal que contiene la estructura molecular y los parámetros de cálculo.\n");
            textArea2.appendText("MOL: Formato de archivo que describe estructuras moleculares, que puede ser convertido para su uso en MOPAC.\n");
            textArea2.appendText("XYZ: Formato simple que describe la posición de los átomos en el espacio tridimensional.\n");
            textArea2.appendText("Como formatos de salida incluye:\n");
            textArea2.appendText("OUT: Archivo de salida que contiene los resultados del cálculo, incluyendo energías, geometrías optimizadas y propiedades electrónicas.\n");
            textArea2.appendText("LOG: Similar al archivo .out, proporciona un registro detallado de los cálculos realizados.\n");
            textArea2.appendText("ARC: Se usa principalmente para almacenar geometrías moleculares a lo largo de un cálculo.\n");
        });
        LAMMPSHelp.setOnAction(event -> {
            textArea2.setText("LAMMPS (Large-scale Atomic/Molecular Massively Parallel Simulator) es un software de simulación molecular diseñado para realizar cálculos de dinámica molecular y simulaciones de materiales a gran escala.\n");
            textArea2.appendText("Permite la creación y manipulación de celdas unitarias.\n");
            textArea2.appendText("Ademas, los usuarios pueden agregar átomos a la celda unitaria.\n");
            textArea2.appendText("Permite realizar simulaciones de equilibrio en sistemas cerrados, donde se pueden calcular propiedades termodinámicas como energía interna, entropía, y capacidad calorífica.\n");
            textArea2.appendText("Calcula la energía total del sistema, que incluye contribuciones cinéticas y potenciales.\n");
            textArea2.appendText("Facilita la recolección y análisis de datos termodinámicos.\n");
            textArea2.appendText("LAMMPS funciona principalmente a través de la línea de comandos.\n");
            textArea2.appendText("Es compatible con varios formatos de archivo de entrada, incluyendo:\n");
            textArea2.appendText("DATA: Contienen información sobre la configuración inicial del sistema.\n");
        });
        //-------------Ayudas al usuario procesos--------------
        procesConstruccion.setOnAction(event -> {
            textArea2.setText("♦ La construccion molecular se realiza con el programa Jmol.\n");
            textArea2.appendText("♦ Se muestra la tabla peridica para la seleccion de los diferentes atomos a construir.\n");
            textArea2.appendText("♦ Permite optimizar geometria.\n");
            textArea2.appendText("♦ Crear dobles enlaces.\n");
        });
        procesOptimi.setOnAction(event -> {
            textArea2.setText("♦ La optimizacion geometrica se realiza con el programa Avogadro\n");
            textArea2.appendText("♦ Se busca un archivo el cual desee optimizar de extension .pdb.\n");
            textArea2.appendText("♦ Se ajusta el formato de salida.\n");
            textArea2.appendText("♦ Se introduce el directorio sin espacios en su path.\n");
        });
        procesCell.setOnAction(event -> {
            textArea2.setText("♦ La generacion de celdas se realiza con el programa LAMMPS\n");
            textArea2.appendText("♦ Se genera un sistema molecular entre el adsorvente y el absorvato a partir de la cantidad de celdas ingresadas por el usuarion. \n");
            textArea2.appendText("♦ Los archivos resultantes se generarán automaticamente en el directorio del archivo de entrada. \n");
        });
        procesMini.setOnAction(event -> {
            textArea2.setText("♦ La minimizacion de energia se realiza con el programa MOPAC\n");
            textArea2.appendText("♦ Seleccione el archivo de entrada '.mop' para iniciar. \n");
            textArea2.appendText("♦ Los archivos resultantes se generarán automaticamente en el directorio del archivo de entrada. \n");
            textArea2.appendText("  ► Archivo.out \n");
            textArea2.appendText("  ► Archivo.arc \n");
        });
        procesOrdenLammps.setOnAction(event -> {
            textArea2.setText("♦ Configure su archivo script.lammps mediante la campo proporcionado \n");
            textArea2.appendText("♦ Los archivos resultantes se generarán automaticamente en el directorio del archivo de entrada. \n");
        });
        //--------Ayudas al usuario documentacion---------
        OpenBabelHelpD.setOnAction(event -> {
        String url0 = "https://jmol.sourceforge.net/docs/";
        abrirEnNavegador(url0);
        });
        JmolHelpD.setOnAction(event -> {
            String url1 = "https://openbabel.org/docs/index.html";
            abrirEnNavegador(url1);
        });
        AvogadroHelpD.setOnAction(event -> {
            String url2 ="https://www.openchemistry.org/projects/avogadro2/";
            abrirEnNavegador(url2);
        });
        MOPACHelpD.setOnAction(event -> {
            String url3 = "http://openmopac.net/manual/";
            abrirEnNavegador(url3);
        });
        LAMMPSHelpD.setOnAction(event -> {
            String url4 ="https://docs.lammps.org/Manual.html";
            abrirEnNavegador(url4);
        });
//        AvogadroHelp.setOnAction(event -> {
//            textArea2.setText("https://mvnrepository.com/search?q=avogadro"); //Buscar como redireccionar automaticamente
//                });
//        MOPACHelp.setOnAction(event -> {
//            textArea2.setText("♦ Seleccione el archivo de entrada '.mop' para iniciar. \n");
//            textArea2.appendText("♦ Los archivos resultantes se generarán automaticamente en el directorio del archivo de entrada. \n");
//            textArea2.appendText("  ► Archivo.out \n");
//            textArea2.appendText("  ► Archivo.arc \n");
//        });
//
//        LAMMPSHelp.setOnAction(event -> {
//            textArea2.setText("♦ Configure su archivo script.lammps mediante la campo proporcionado \n");
//            textArea2.appendText("♦ Los archivos resultantes se generarán automaticamente en el directorio del archivo de entrada. \n");
//        });
        //---------------------Ayudas al usuario-----------------------------------------


        MOPACMenuItem.setOnAction(event1 -> { MopacController.main();
        });

        LammpsMenuItem.setOnAction(event -> {
            LammpsController.ejecutarExe("E:/Tesis/Empresas/Herramienta Computacional para el estudio de procesos de adsorción/Programas/8.LAMMPS/Con GUI/LAMMPS 64-bit 2Aug2023 with GUI/bin/lammps-gui.exe"); // , primaryStage);
        });

        AvogadroMenuItem.setOnAction(event -> {
            AvogadroController.ejecutarExe("C:/Program Files/Avogadro2/bin/avogadro2.exe");
        });

        FourProcesItem.setOnAction(event -> {
            VentanaLAMMPS play = new VentanaLAMMPS();
            Stage cuaternaryStage = new Stage();

            // Configurar evento para ejecutar código al cerrar la ventana
            cuaternaryStage.setOnHiding(e -> {
                //---------------/*//UpdateTreeView//*/----------------//
                TreeViewUpdater treeViewUpdater = new TreeViewUpdater();
                treeViewUpdater.monitorDirectoryChanges(treeView, paths);
            });

            play.start(cuaternaryStage);
        });


        //-----------------------------------------------------------------------------------------------//
        // Asumiendo que el método está dentro de alguna clase y que tienes acceso a 'primaryStage'
        ceroProcesItem.setOnAction(event -> {
            textArea2.setText("Construcción molecular activada");

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            // Calcular dos tercios del ancho de la pantalla
            double twoThirdsScreenWidth = bounds.getWidth() * 2 / 3;
            double newWidth;

            // Verificar si la ventana es mayor que dos tercios de la pantalla en dirección horizontal
            if (primaryStage.getWidth() >= twoThirdsScreenWidth) {
                // Calcular nuevas dimensiones para la ventana principal
                newWidth = bounds.getWidth() / 1.6;
                double newHeight = bounds.getHeight();
                // Configurar la ventana principal con las nuevas dimensiones y posición
                primaryStage.setWidth(newWidth);
                primaryStage.setHeight(newHeight);
            } else {
                newWidth = bounds.getWidth() / 1.6;
            }

            JmolClass.ConstructorAndDisplay3(newWidth);

            // Mostrar la ventana de ayuda
            SwingUtilities.invokeLater(JmolMendele::createAndShowGUI);

            //---------------/*//UpdateTreeView//*/----------------//
            TreeViewUpdater treeViewUpdater = new TreeViewUpdater();
            treeViewUpdater.monitorDirectoryChanges(treeView, paths);
        });


        OneconfiguracionMenu.setOnAction(e -> {
            Image image4 = menu.seleccionarImagenDeFondo();
            BackgroundImage backgroundImage0 = new BackgroundImage(image4, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background background0 = new Background(backgroundImage0);
            // Limpiar el contenido existente del BorderPane
            root.getChildren().clear();
            root.setBackground(background0);
            // Colocar los elementos en el contenedor BorderPane
            root.setTop(hBox);
            root.setLeft(transparentPane);
            root.setRight(buttonBox2);

            primaryStage.setScene(scene);
            primaryStage.setTitle("");
            primaryStage.show();
        });

        //Accion Buscar

        BuscarItem.setOnAction(e -> {
            menu.buscarArchivo();
            String direccion = obtenerUltimaRuta(); // Acceder a las direcciones de archivos desde el backend

            if (direccion != null) {
                String nombreArchivo = new File(direccion).getName(); // Obtener el nombre del archivo con extensión
                MenuItem ite = new MenuItem(nombreArchivo); // Crear un botón con el nombre del archivo
                MenuItem ite2 = new MenuItem(nombreArchivo);
                menuButton2.getItems().addAll(ite);
                menuButton3.getItems().addAll(ite2);
                textArea2.setText("Archivo: " + nombreArchivo + " añadido.");

                // Verificar si el archivo tiene la extensión .pdb
                MenuItem submore = null;
                if (nombreArchivo.endsWith(".pdb")) {
                    //submore = new MenuItem("mol");
                    //MenuItem submore2 = new MenuItem("pdb");
                    MenuItem submore3 = new MenuItem("xyz");
                    MenuItem submore4 = new MenuItem("cif");
                    MenuItem submore5 = new MenuItem("data");
                    Menu more = new Menu(nombreArchivo);
                    more.getItems().addAll(submore3, submore4, submore5);
                    menuButton4.getItems().addAll(more);

                    submore5.setOnAction(event -> {
                        // Hacer visible el ProgressIndicator
                        progressIndicator.setVisible(true);
                        // Crear un Task para ejecutar el proceso en segundo plano
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                // Llamar al método que puede demorar
                                ConversorData.convert(menu.ObtenerDireccionArchivoPorName(nombreArchivo),
                                        "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Archivos_Moleculares",
                                        "LAMMPS-DATA");
                                return null;
                            }
                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                // Actualizar la interfaz de usuario cuando el proceso haya terminado
                                textArea2.setText("Archivo: " + nombreArchivo + " Convertido a " + nombreArchivo + ".lammps-data");
                                TreeViewUpdater treeViewUpdaterz = new TreeViewUpdater();
                                treeViewUpdaterz.monitorDirectoryChanges(treeView, paths);
                                // Ocultar el ProgressIndicator
                                progressIndicator.setVisible(false);
                            }
                            @Override
                            protected void failed() {
                                super.failed();
                                // Manejar el error
                                textArea2.setText("Error al convertir el archivo.");
                                progressIndicator.setVisible(false);
                            }
                        };
                        // Ejecutar el Task en un nuevo hilo
                        new Thread(task).start();
                    });

                    submore4.setOnAction(event -> {
                        // Hacer visible el ProgressIndicator
                        progressIndicator.setVisible(true);
                        // Crear un Task para ejecutar el proceso en segundo plano
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                // Llamar al método que puede demorar
                                ConversorData.convert(menu.ObtenerDireccionArchivoPorName(nombreArchivo),
                                        "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Archivos_Moleculares",
                                        "CIF");
                                return null;
                            }
                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                // Actualizar la interfaz de usuario cuando el proceso haya terminado
                                textArea2.setText("Archivo: " + nombreArchivo + " Convertido a " + nombreArchivo + ".cif");
                                TreeViewUpdater treeViewUpdaterz = new TreeViewUpdater();
                                treeViewUpdaterz.monitorDirectoryChanges(treeView, paths);
                                // Ocultar el ProgressIndicator
                                progressIndicator.setVisible(false);
                            }
                            @Override
                            protected void failed() {
                                super.failed();
                                // Manejar el error
                                textArea2.setText("Error al convertir el archivo.");
                                progressIndicator.setVisible(false);
                            }
                        };
                        // Ejecutar el Task en un nuevo hilo
                        new Thread(task).start();
                    });

                    submore3.setOnAction(event -> {
                        // Hacer visible el ProgressIndicator
                        progressIndicator.setVisible(true);
                        // Crear un Task para ejecutar el proceso en segundo plano
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                // Llamar al método que puede demorar
                                ConversorData.convert(menu.ObtenerDireccionArchivoPorName(nombreArchivo),
                                        "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Archivos_Moleculares",
                                        "XYZ");
                                return null;
                            }
                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                // Actualizar la interfaz de usuario cuando el proceso haya terminado
                                textArea2.setText("Archivo: " + nombreArchivo + " Convertido a " + nombreArchivo + ".xyz");
                                TreeViewUpdater treeViewUpdaterz = new TreeViewUpdater();
                                treeViewUpdaterz.monitorDirectoryChanges(treeView, paths);
                                // Ocultar el ProgressIndicator
                                progressIndicator.setVisible(false);
                            }
                            @Override
                            protected void failed() {
                                super.failed();
                                // Manejar el error
                                textArea2.setText("Error al convertir el archivo.");
                                progressIndicator.setVisible(false);
                            }
                        };
                        // Ejecutar el Task en un nuevo hilo
                        new Thread(task).start();
                    });

                } else if ((nombreArchivo.endsWith(".xyz"))) {
                    MenuItem submore1 = new MenuItem("mop");

                    Menu more = new Menu(nombreArchivo);
                    more.getItems().addAll(submore1);
                    menuButton4.getItems().addAll(more);

                    submore1.setOnAction(event -> {
                        // Hacer visible el ProgressIndicator
                        progressIndicator.setVisible(true);
                        // Crear un Task para ejecutar el proceso en segundo plano
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                // Llamar al método que puede demorar
                                ConvertQO(menu.ObtenerDireccionArchivoPorName(nombreArchivo), "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Archivos_Moleculares");//url de archivo y lugar de salida
                                return null;
                            }
                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                // Actualizar la interfaz de usuario cuando el proceso haya terminado
                                textArea2.setText("Archivo: " + nombreArchivo + " Convertido a " + nombreArchivo + ".mop");
                                TreeViewUpdater treeViewUpdaterz = new TreeViewUpdater();
                                treeViewUpdaterz.monitorDirectoryChanges(treeView, paths);
                                // Ocultar el ProgressIndicator
                                progressIndicator.setVisible(false);
                            }
                            @Override
                            protected void failed() {
                                super.failed();
                                // Manejar el error
                                textArea2.setText("Error al convertir el archivo.");
                                progressIndicator.setVisible(false);
                            }
                        };
                        // Ejecutar el Task en un nuevo hilo
                        new Thread(task).start();
                    });

                }else if ((nombreArchivo.endsWith("cif"))){
                    MenuItem submore2 = new MenuItem("pdb");
                    MenuItem submore3 = new MenuItem("xyz");
                    //MenuItem submore4 = new MenuItem("cif");
                    MenuItem submore5 = new MenuItem("data");

                    submore2.setOnAction(event -> {
                        // Hacer visible el ProgressIndicator
                        progressIndicator.setVisible(true);
                        // Crear un Task para ejecutar el proceso en segundo plano
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                // Llamar al método que puede demorar
                                ConversorData.convert(menu.ObtenerDireccionArchivoPorName(nombreArchivo),
                                        "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Archivos_Moleculares",
                                        "PDB");
                                return null;
                            }
                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                // Actualizar la interfaz de usuario cuando el proceso haya terminado
                                textArea2.setText("Archivo: " + nombreArchivo + " Convertido a " + nombreArchivo + ".pdb");
                                // Actualizar el TreeView
                                TreeViewUpdater treeViewUpdaterz = new TreeViewUpdater();
                                treeViewUpdaterz.monitorDirectoryChanges(treeView, paths);
                                // Ocultar el ProgressIndicator
                                progressIndicator.setVisible(false);
                            }
                            @Override
                            protected void failed() {
                                super.failed();
                                // Manejar el error
                                textArea2.setText("Error al convertir el archivo.");
                                progressIndicator.setVisible(false);
                            }
                        };
                        // Ejecutar el Task en un nuevo hilo
                        new Thread(task).start();
                    });

                    submore3.setOnAction(event -> {
                        // Hacer visible el ProgressIndicator
                        progressIndicator.setVisible(true);
                        // Crear un Task para ejecutar el proceso en segundo plano
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                // Llamar al método que puede demorar
                                ConversorData.convert(menu.ObtenerDireccionArchivoPorName(nombreArchivo),
                                        "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Archivos_Moleculares",
                                        "XYZ");
                                return null;
                            }
                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                // Actualizar la interfaz de usuario cuando el proceso haya terminado
                                textArea2.setText("Archivo: " + nombreArchivo + " Convertido a " + nombreArchivo + ".xyz");
                                // Actualizar el TreeView
                                TreeViewUpdater treeViewUpdaterz = new TreeViewUpdater();
                                treeViewUpdaterz.monitorDirectoryChanges(treeView, paths);
                                // Ocultar el ProgressIndicator
                                progressIndicator.setVisible(false);
                            }
                            @Override
                            protected void failed() {
                                super.failed();
                                // Manejar el error
                                textArea2.setText("Error al convertir el archivo.");
                                progressIndicator.setVisible(false);
                            }
                        };
                        // Ejecutar el Task en un nuevo hilo
                        new Thread(task).start();
                    });

                    submore5.setOnAction(event -> {
                        // Hacer visible el ProgressIndicator
                        progressIndicator.setVisible(true);
                        // Crear un Task para ejecutar el proceso en segundo plano
                        Task<Void> task = new Task<Void>() {
                            @Override
                            protected Void call() throws Exception {
                                // Llamar al método que puede demorar
                                ConversorData.convert(menu.ObtenerDireccionArchivoPorName(nombreArchivo),
                                        "C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/Repositorio_Local/Archivos_Moleculares",
                                        "LAMMPS-DATA");
                                return null;
                            }
                            @Override
                            protected void succeeded() {
                                super.succeeded();
                                // Actualizar la interfaz de usuario cuando el proceso haya terminado
                                textArea2.setText("Archivo: " + nombreArchivo + " Convertido a " + nombreArchivo + ".lammps-data");
                                // Actualizar el TreeView
                                TreeViewUpdater treeViewUpdaterz = new TreeViewUpdater();
                                treeViewUpdaterz.monitorDirectoryChanges(treeView, paths);
                                // Ocultar el ProgressIndicator
                                progressIndicator.setVisible(false);
                            }
                            @Override
                            protected void failed() {
                                super.failed();
                                // Manejar el error
                                textArea2.setText("Error al convertir el archivo.");
                                progressIndicator.setVisible(false);
                            }
                        };
                        // Ejecutar el Task en un nuevo hilo
                        new Thread(task).start();
                    });

                } // Agregar else para demas extensiones o else if


                    ite2.setOnAction(event -> {
                    // Acción al presionar el botón del archivo
                    String contenidoArchivo = menu.LeerContenidoArchivo(direccion); // Método para leer el contenido del archivo
                    textArea.setText(contenidoArchivo); // Mostrar el contenido del archivo en el TextArea
                    textArea2.setText("Archivo: " + nombreArchivo + " cargado.");
                    // Deshabilitar la edición del texto
                    textArea.setEditable(false);
                    // Deshabilitar el foco en el componente para que el usuario no pueda interactuar con él
                    textArea.setFocusTraversable(false);
                });

                ite.setOnAction(event -> {
                    textArea2.setText("Archivo: " + nombreArchivo + " visualizado.");

                    Screen screen = Screen.getPrimary();
                    Rectangle2D bounds = screen.getVisualBounds();
                    // Calcular dos tercios del ancho de la pantalla
                    double twoThirdsScreenWidth = bounds.getWidth() * 2 / 3;
                    // Verificar si la ventana es mayor que dos tercios de la pantalla en dirección horizontal
                    double newWidth;
                    if (primaryStage.getWidth() >= twoThirdsScreenWidth) {
                        // Calcular nuevas dimensiones para la ventana principal
                        newWidth = bounds.getWidth() / 1.6;
                        double newHeight = bounds.getHeight();
                        // Configurar la ventana principal con las nuevas dimensiones y posición
                        primaryStage.setWidth(newWidth);
                        primaryStage.setHeight(newHeight);
                    } else {
                        newWidth = bounds.getWidth() / 1.6;
                    }

                    JmolClass.loadAndDisplay3(direccion, newWidth);

                });

//                submore.setOnAction(event -> {
//                    String RutaDestino = RutaDeSalidaDeArchivos();
//                    textArea2.appendText("Ruta establecida: " + RutaDestino + "\n");
//                    // problema visual con el boton. Se increpa que es por las llamadas y saltos de clase.
//                    if (RutaDestino != null) {
//                        OpenBabelClass.ConversorO_P_pdbtoMOL(direccion, RutaDestino);
//                        textArea2.setText("Archivo: " + nombreArchivo + " convertido exitosamente. \n");
//                        textArea2.setText(obtenerUltimaRuta() + " añadido a la lista de archivos ");
//                    }
//                });
            }

        });


//        mostrarArchivoButton.setOnAction(e -> {
//            String ultimaRuta = menu.obtenerUltimaRuta();
//            if (ultimaRuta != null) {
//                try (BufferedReader br = new BufferedReader(new FileReader(ultimaRuta))) {
//                    String linea;
//                    StringBuilder contenido = new StringBuilder();
//                    while ((linea = br.readLine()) != null) {
//                        contenido.append(linea).append("\n");
//                    }
//                    textArea.setText(contenido.toString());
//                } catch (Exception ex) {
//                    textArea.setText("Error al leer el archivo");
//                }
//            } else {
//                textArea.setText("No hay rutas de archivos disponibles");
//            }
//        });

        item3.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Archivos de texto", "*.txt"),
                    new FileChooser.ExtensionFilter("Archivos Protein Data Bank", "*.pdb"),
                    new FileChooser.ExtensionFilter("Archivos CIF", "*.cif"),
                    new FileChooser.ExtensionFilter("Archivos JMol", "*.jmol")
            );
            File file = fileChooser.showSaveDialog(primaryStage);

            if (file != null) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textArea.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        ArchCarg.setOnAction(e -> {
//            List<String> direccionesArchivos = Run.obtenerDireccionesArchivos(); // Acceder a las direcciones de archivos desde el backend
//
//            for (String direccion : direccionesArchivos) {
//                String nombreArchivo = new File(direccion).getName(); // Obtener el nombre del archivo con extensión
//                MenuItem ite = new MenuItem(nombreArchivo); // Crear un botón con el nombre del archivo
//                ArchCarg.getItems().addAll(ite);
//                System.out.print(Run.obtenerDireccionesArchivos());
//                direccionesArchivos.clear();
//
//                ite.setOnAction(event -> {
//                    // Acción al presionar el botón del archivo
//                    String contenidoArchivo = Run.LeerContenidoArchivo(direccion); // Método para leer el contenido del archivo
//                    textArea.setText(contenidoArchivo); // Mostrar el contenido del archivo en el TextArea
//                });
//            }
//        });



        primaryStage.initStyle(StageStyle.DECORATED); //aqui hay opciones para quitar la barra superior de la ventana

        // Obtener las dimensiones de la pantalla primaria
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());

        // Configurar el escenario (Stage)
        primaryStage.setScene(scene);
        primaryStage.setTitle("MolAds");
        primaryStage.show();


    }


    //*-------------------------Metodos--------------------------------*
    private void abrirEnNavegador(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url)); // Abrir la URL en el navegador
            } catch (IOException | URISyntaxException e) {
                System.err.println("Error al intentar abrir la URL: " + e.getMessage());
            }
        } else {
            System.err.println("La funcionalidad de escritorio no es soportada en este sistema.");
        }
    }

    private List<TreeItem<File>> createTreeViews(List<File> directories) {
        List<TreeItem<File>> rootItems = new ArrayList<>();

        for (File directory : directories) {
            TreeItem<File> rootItem = new TreeItem<>(directory);
            rootItem.setExpanded(true);

            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file != null) { // Comprobar si el archivo no es nulo
                        TreeItem<File> item = new TreeItem<>(file);
                        rootItem.getChildren().add(item);

                        if (file.isDirectory()) {
                            item.getChildren().addAll(createTreeViews(Collections.singletonList(file)).get(0).getChildren());
                        }
                    }
                }
            }

            rootItems.add(rootItem);
        }

        return rootItems;
    }

    private void abrirVentanaOptGeometric() {
        OptGeometric ventanaSecundaria = new OptGeometric();
        Stage nuevoStage = new Stage();
        try {
            ventanaSecundaria.start(nuevoStage); // Inicia la ventana secundaria
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }

    private void abrirVentanaGeneracionCeldas(TreeView treeView, List paths) {
        GeneracionCeldasSample ventanaTercearia = new GeneracionCeldasSample();
        Stage nuevoStage = new Stage();
        try {
            ventanaTercearia.start(nuevoStage); // Inicia la ventana secundaria
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }
        //---------------/*//UpdateTreeView//*/----------------//
        TreeViewUpdater treeViewUpdater = new TreeViewUpdater();
        treeViewUpdater.monitorDirectoryChanges(treeView, paths);
    }

    private void abrirVentanaMinEnergy() {
        MinEnergia ventanaCuaternaria = new MinEnergia();
        Stage nuevoStage = new Stage();
        try {
            ventanaCuaternaria.start(nuevoStage); // Inicia la ventana secundaria
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }
    }


    private static class CustomTreeCell extends TreeCell<File> {
        @Override
        protected void updateItem(File item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getName());
                setGraphic(null);
            }
        }
    }

    public static void ConvertQO(String archivoXYZ, String urlSalida) {
        if (!archivoXYZ.endsWith(".xyz")) {   // Verificar que el archivo sea .xyz
            System.out.println("El archivo no es de tipo .xyz");
            return;
        }

        File originalFile = new File(archivoXYZ);
        if (!originalFile.exists()) {
            System.out.println("El archivo original no existe: " + archivoXYZ);
            return;
        }

        // Crear el nombre del archivo de salida con extensión .out
        String nombreSalida = originalFile.getName().replace(".xyz", ".mop");
        File copiaArchivo = new File(urlSalida, nombreSalida); // Asegúrate de que urlSalida sea un directorio válido

        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
