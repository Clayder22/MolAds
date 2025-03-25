package Recursos.PersonalClassE;

import Recursos.PersonalClassB.MMH_Automatic;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Recursos.PersonalClassB.GenerateCells.readPDB;
import static Recursos.PersonalClassB.MMH_Automatic.TomaElPath;

public class GeneracionCeldasProces extends Application {

    private Label nombreArchivoLabel;
    private Label nombreArchivoLabel1;
    private Button botonGenerarCeldas;
    private TextField cantidadCeldasFieldx, cantidadAdsorbente, cantidadCeldasField0, cantidadCeldasField1, cantidadCeldasField2;
    private CheckBox campoX, campoY, campoZ;
    private int archivoCargado = 0;
    private GridPane gridCoordenadas, gridCantidades; // Mover la declaración aquí para acceso global
    private ArrayList<String> listaPaths = new ArrayList<>(); // Declaración de la lista de Strings
    private ArrayList<Integer> listaAgentes = new ArrayList<>(); // Declaración de la lista de Strings
    // Agregar una lista para almacenar los TextFields
    private ObservableList<TextField> listaTextFields = FXCollections.observableArrayList();
    private Button botonAgregar;
    private Button botonCargar1;
    private static double Cryx;
    private static double Cryy;
    private static double Cryz;
    private static double menor;

    @Override
    public void start(Stage primaryStage7) {

        listaPaths.add(TomaElPath(1));
        listaPaths.add(TomaElPath(0));

        primaryStage7.setTitle("Generación de Celdas");

        // Layout principal
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        String Estylo = (
                "-fx-border-width: 0.5px; "
                        + "-fx-border-color: black;"
                        + "-fx-border-radius: 3px; "
                        + "-fx-background-color: white;"
                        + "-fx-text-fill: black; "
                        + "-fx-font-family: 'Times New Roman';"
                        + "-fx-background-radius: 3px;"
                        + "-fx-font-size: 15px;");

        String Estylo2 = (
                         "-fx-text-fill: black; "
                        + "-fx-font-family: 'Times New Roman';"
                        + "-fx-font-size: 16px;");

        // ---> Creacion y Loading de archivos <---

        cantidadAdsorbente = new TextField();
        cantidadAdsorbente.setText("1");
        cantidadAdsorbente.setMaxWidth(25);
        //cantidadAdsorbente.setStyle(Estylo); // Aplicar estilo al campo de texto

        // Sección para la cantidad de celdas
        Label cantidadCeldasLabel = new Label("Cantidad de Celdas:");
        cantidadCeldasLabel.setStyle(Estylo2); // Aplicar estilo al Label

        cantidadCeldasFieldx = new TextField();
        cantidadCeldasFieldx.setText("1");
        cantidadCeldasFieldx.setMaxWidth(25);
        //cantidadCeldasFieldx.setStyle(Estylo); // Aplicar estilo al campo de texto

        // Configurar el TextFormatter para permitir solo números del 1 al 9
        TextFormatter<Integer> formatter = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("[1-9]")) {
                return change; // Permitir el cambio
            }
            return null; // Rechazar el cambio
        });

        // Configurar el TextFormatter para permitir solo números del 1 al 9
        TextFormatter<Integer> formatterr = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty() || newText.matches("[1-9]")) {
                return change; // Permitir el cambio
            }
            return null; // Rechazar el cambio
        });

        // Configurar el TextFormatter para permitir solo números del 1 al 20
        TextFormatter<Integer> formatter2 = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            // Verificar si el nuevo texto está vacío o está en el rango de 1 a 20
            if (newText.isEmpty() || newText.matches("(100|[1-9][0-9]?|[1-9])$")) {
                return change; // Permitir el cambio
            }
            return null; // Rechazar el cambio
        });

        // Configurar el TextFormatter para permitir solo números del 1 al 20
        TextFormatter<Integer> formatter22 = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            // Verificar si el nuevo texto está vacío o está en el rango de 1 a 20
            if (newText.isEmpty() || newText.matches("(100|[1-9][0-9]?|[1-9])$")) {
                return change; // Permitir el cambio
            }
            return null; // Rechazar el cambio
        });

        // Configurar el TextFormatter para permitir solo números del 1 al 20
        TextFormatter<Integer> formatter222 = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            // Verificar si el nuevo texto está vacío o está en el rango de 1 a 20
            if (newText.isEmpty() || newText.matches("^(100|[1-9][0-9]?|[1-9])$")) {
                return change; // Permitir el cambio
            }
            return null; // Rechazar el cambio
        });

        cantidadCeldasFieldx.setTextFormatter(formatter);
        cantidadAdsorbente.setTextFormatter(formatterr);

        Label cantidadCeldasLabel0 = new Label("Cantidad de moléculas adsorbentes: ");
        cantidadCeldasLabel0.setStyle(Estylo2); // Aplicar estilo al Label

        HBox descarga = new HBox(cantidadCeldasLabel0, cantidadAdsorbente);

         // Crear el HBox y agregar márgenes
        HBox celdas = new HBox(cantidadCeldasLabel, cantidadCeldasFieldx);
        celdas.setSpacing(10);
        HBox.setMargin(celdas, new Insets(20, 0, 50, 0)); // Espacio arriba y abajo

        cantidadCeldasField0 = new TextField();
        cantidadCeldasField0.setPromptText("1");
        cantidadCeldasField0.setMaxWidth(50);
        cantidadCeldasField0.setEditable(false);
        cantidadCeldasField0.setTextFormatter(formatter2);
        cantidadCeldasField0.setStyle(Estylo2); // Aplicar estilo al TextField

        cantidadCeldasField1 = new TextField();
        cantidadCeldasField1.setPromptText("1");
        cantidadCeldasField1.setMaxWidth(50);
        cantidadCeldasField1.setEditable(false);
        cantidadCeldasField1.setTextFormatter(formatter22);
        cantidadCeldasField1.setStyle(Estylo2); // Aplicar estilo al TextField

        cantidadCeldasField2 = new TextField();
        cantidadCeldasField2.setPromptText("1");
        cantidadCeldasField2.setMaxWidth(50);
        cantidadCeldasField2.setEditable(false);
        cantidadCeldasField2.setTextFormatter(formatter222);
        cantidadCeldasField2.setStyle(Estylo2); // Aplicar estilo al TextField

        // Sección para coordenadas X, Y, Z
        Label AgregarMolec = new Label("Agregar especies moleculares o iónicas:");
        AgregarMolec.setStyle(Estylo2); // Aplicar estilo al Label

        Label labelX = new Label("H₂O:");
        labelX.setStyle(Estylo2); // Aplicar estilo al Label
        campoX = new CheckBox(",  Cantidades");
        campoX.setStyle(Estylo2); // Aplicar estilo al CheckBox

        Label labelY = new Label("Etanol:");
        labelY.setStyle(Estylo2); // Aplicar estilo al Label
        campoY = new CheckBox(",  Cantidades");
        campoY.setStyle(Estylo2); // Aplicar estilo al CheckBox

        Label labelZ = new Label("Ion sodio:");
        labelZ.setStyle(Estylo2); // Aplicar estilo al Label
        campoZ = new CheckBox(",  Cantidades");
        campoZ.setStyle(Estylo2); // Aplicar estilo al CheckBox


        campoX.selectedProperty().addListener((observable, oldValue, newValue) -> {
            cantidadCeldasField0.setEditable(newValue); // Desbloquear o bloquear el TextField
            if (!newValue) {
                cantidadCeldasField0.clear(); // Limpiar el campo si se desmarca
            }else if(newValue){
                cantidadCeldasField0.setText("1");
            }
        });

        campoY.selectedProperty().addListener((observable, oldValue, newValue) -> {
            cantidadCeldasField1.setEditable(newValue); // Desbloquear o bloquear el TextField
            if (!newValue) {
                cantidadCeldasField1.clear(); // Limpiar el campo si se desmarca
            }else if(newValue){
                cantidadCeldasField1.setText("1");
            }
        });

        campoZ.selectedProperty().addListener((observable, oldValue, newValue) -> {
            cantidadCeldasField2.setEditable(newValue); // Desbloquear o bloquear el TextField
            if (!newValue) {
                cantidadCeldasField2.clear(); // Limpiar el campo si se desmarca
            } else {
                if (cantidadCeldasField2.getText().isEmpty()) {
                    cantidadCeldasField2.setText("1"); // Establecer un valor predeterminado solo si está vacío
                }
            }
        });

        // Organizar coordenadas en un GridPane
        gridCoordenadas = new GridPane(); // Usar la variable global
        gridCoordenadas.setVgap(10);
        gridCoordenadas.add(AgregarMolec, 0, 3); // Añadir el enunciado

        // Organizar coordenadas en un GridPane
        gridCantidades = new GridPane(); // Usar la variable global
        gridCantidades.setVgap(10);
        gridCantidades.add(celdas, 1, 0); // Añadir el enunciado

        // HBox para alinear etiquetas y campos a la derecha
        HBox hBoxX = new HBox(labelX, campoX, cantidadCeldasField0);
        HBox hBoxY = new HBox(labelY, campoY, cantidadCeldasField1);
        HBox hBoxZ = new HBox(labelZ, campoZ, cantidadCeldasField2);

        hBoxX.setSpacing(5);
        hBoxY.setSpacing(5);
        hBoxZ.setSpacing(5);

// Añadir HBoxes al GridPane
        gridCoordenadas.add(hBoxX, 0, 5);
        gridCoordenadas.add(hBoxY, 0, 6);
        gridCoordenadas.add(hBoxZ, 0, 7);

// Espacio de 10 píxeles después del campo Z
        VBox.setMargin(hBoxZ, new Insets(50, 0, 50, 0));

// Línea divisoria
        Line lineaDivisoria0 = new Line(0, 0, 500, 0);
        lineaDivisoria0.setStroke(Color.GRAY);
        lineaDivisoria0.setStrokeWidth(2);

        Line lineaDivisoria1 = new Line(0, 0, 500, 0);
        lineaDivisoria1.setStroke(Color.GRAY);
        lineaDivisoria1.setStrokeWidth(2);

        // HBox para los botones
        HBox hboxBotones = new HBox(40);
        hboxBotones.setAlignment(Pos.CENTER);

        botonGenerarCeldas = new Button("Continuar");
        botonGenerarCeldas.setStyle(Estylo); // Aplicar estilo al botón


        botonGenerarCeldas.setOnAction(event -> {
            //if (checkboxes tal esta marcado, obtener path predefinido a lista path)
            if(campoX.isSelected()){
                listaPaths.add("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/MoleculasPredefinidas/agua.pdb");
            }
            if(campoY.isSelected()){
                listaPaths.add("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/MoleculasPredefinidas/etanol.pdb");
            }
            if(campoZ.isSelected()){
                listaPaths.add("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/MoleculasPredefinidas/IonS.pdb");
            }
            String text = cantidadCeldasFieldx.getText();
            int cicloCelds = Integer.parseInt(text);

            for(int l = 0; l < cicloCelds; l++){
                GenerarArchivo(listaPaths, cantidadAdsorbente, cantidadCeldasField0, cantidadCeldasField1, cantidadCeldasField2, listaTextFields, l);
            }
            iniciarCarga(layout,primaryStage7);

        }); //El mas importante

        // Botón para agregar nuevas estructuras
        botonAgregar = new Button("Agregar");
        botonAgregar.setStyle(Estylo); // Aplicar estilo al botón
        botonAgregar.setOnAction(event -> agregarEstructura(Estylo2));

        // Añadir el botón de agregar al gridCoordenadas
        gridCoordenadas.add(botonAgregar, 0, 8); // Colocarlo debajo de los checkboxes

        // Dividir el HBox en dos mitades
        HBox hboxDerDown = new HBox(botonGenerarCeldas);
        hboxDerDown.setAlignment(Pos.BOTTOM_RIGHT);

        // Añadir los HBoxes al layout
        layout.getChildren().addAll(lineaDivisoria0, gridCantidades, descarga, lineaDivisoria1, gridCoordenadas, hboxDerDown);

        // Cargar la imagen de la textura
        Image image0 = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/SubFija.jpg");

        // Crear el fondo con la imagen de la textura
        BackgroundImage backgroundImage = new BackgroundImage(image0, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        // Establecer el fondo en el Pane
        layout.setBackground(background);

        // Configurar la escena
        Scene scene = new Scene(layout, 550, 400); // Aumentar ancho de la ventana

        Image image = new Image("C:/Users/Julio C/Desktop/Salva de tesis 7_3_2024/MMH_V4/Recursos/RecursosGraficos/png/Hexos.png");
        // Establecer el icono en la ventana principal
        primaryStage7.getIcons().add(image);

        // Establece el color de fondo con opacidad
        primaryStage7.setScene(scene);
        primaryStage7.setResizable(true); // Evitar que se pueda cambiar el tamaño de la ventana
        primaryStage7.show();
    }

    private void cargarArchivo(Label x) {
        FileChooser fileChooser = new FileChooser();

        // Establecer un filtro para archivos .pdb
        FileChooser.ExtensionFilter filtroPDB = new FileChooser.ExtensionFilter("Archivos PDB (*.pdb)", "*.pdb");
        fileChooser.getExtensionFilters().add(filtroPDB);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            // Guardar la ruta completa del archivo
            listaPaths.add(file.getAbsolutePath());
            String nombreArchivo = file.getName(); // Nombre del archivo
            x.setText(nombreArchivo); // Tomar solo el nombre final
            if (!(Objects.equals(nombreArchivoLabel.getText(), " ")) && !(Objects.equals(nombreArchivoLabel1.getText(), " ")) && !Objects.equals(cantidadCeldasFieldx.getText(), "1")) {
                cantidadCeldasFieldx.setText("1");
                cantidadAdsorbente.setText("1");
            }
            archivoCargado += 1;
        }
    }

    private void agregarEstructura(String Estylo2) {
        System.out.println("Estoy en el metodo");
        Label labelW = new Label();
        labelW.setStyle(Estylo2);
        cargarArchivo(labelW);

        if (archivoCargado == 0) {
            System.out.println("No cargastes nada");
            return; // Si no hay archivo, salir del método
        }

        HBox nuevaEntrada = new HBox(10);
        nuevaEntrada.setAlignment(Pos.CENTER_LEFT);

        // Crear un nuevo TextField para las cantidades
        TextField textF1 = new TextField();
        textF1.setPromptText("#");
        textF1.setPrefWidth(50);
        textF1.setEditable(false); // Iniciar bloqueado
        textF1.setStyle(Estylo2);

        // Configurar el TextFormatter para permitir solo números del 1 al 20
        TextFormatter<Integer> formatterNew = new TextFormatter<>(new IntegerStringConverter(), null, change -> {
            String newText = change.getControlNewText();
            // Verificar si el nuevo texto está vacío o está en el rango de 1 a 20
            if (newText.isEmpty() || newText.matches("^(1\\d?|20|[1-9])$")) {
                return change; // Permitir el cambio
            }
            return null; // Rechazar el cambio
        });

        textF1.setTextFormatter(formatterNew);

        // Agregar el TextField a la lista
        listaTextFields.add(textF1);

        // Crear un nuevo CheckBox
        CheckBox nuevoCheckBox = new CheckBox(campoX.getText());
        nuevoCheckBox.setStyle(Estylo2);

        // Agregar un ChangeListener al CheckBox
        nuevoCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            textF1.setEditable(newValue); // Desbloquear o bloquear el TextField
            if (!newValue) {
                textF1.clear(); // Limpiar el campo si se desmarca
            }
        });

        // Añadir al HBox
        nuevaEntrada.getChildren().addAll(labelW, nuevoCheckBox, textF1);

        // Añadir el nuevo HBox al gridCoordenadas
        gridCoordenadas.add(nuevaEntrada, 0, gridCoordenadas.getRowCount());
    }


    private void GenerarArchivo(List<String> listaPath, TextField adsorbentes, TextField agua, TextField etanol, TextField sodio, ObservableList<TextField> listaTextFields, int repeticion) {


        List<Integer> RepeticionesMol = new ArrayList<>();

        String text = adsorbentes.getText();
        int adsorbentesNum0 = Integer.parseInt(text);
        RepeticionesMol.add(adsorbentesNum0);

        RepeticionesMol.add(1); //para adsorbato

//        for (TextField textField : listaTextFields) {
//            String texto = textField.getText();
//            if(!Objects.equals(texto, "#")){
//                int valor = Integer.parseInt(texto);
//                RepeticionesMol.add(valor);
//            }
//        }

        String sd0 = agua.getText();
        if(campoX.isSelected()){
            int valorr = Integer.parseInt(sd0);
            RepeticionesMol.add(valorr);
        }

        String sd1 = etanol.getText();
        if(campoY.isSelected()) {
            int valorr1 = Integer.parseInt(sd1);
            RepeticionesMol.add(valorr1);
        }

        String sd2 = sodio.getText();
        if(campoZ.isSelected()) {
            int valorr2 = Integer.parseInt(sd2);
            RepeticionesMol.add(valorr2);
        }

        //        if (listaTextFields.isEmpty()) {
//            System.out.println("No hay campos de texto disponibles.");
//        }else {
//            for (TextField textField : listaTextFields) {
//                String valor = textField.getText();
//                // Procesar el valor según sea necesario
//                System.out.println("TextField Adicional: " + valor);
//            }
//        }
//        System.out.println(celdas.getText());
//        System.out.println(adsorbentes.getText());
//        System.out.println(agua.pdb.getText());
//        System.out.println(etanol.getText());
//        System.out.println(sodio.getText());
//        if (listaPath.isEmpty()) {
//            System.out.println("No hay paths disponibles.");
//        }else {
//            for (String path : listaPath) {
//                // Procesar el valor según sea necesario
//                System.out.println("Path: " + path);
//            }
//        }

        //Construir listas u otro componentes almacenativos para captar todos los datos de los archivos

        List<Double> cajasTerciarias = new ArrayList<>();
        List<Double> EjesX = new ArrayList<>();
        List<Double> EjesY = new ArrayList<>();
        List<Double> EjesZ = new ArrayList<>();
        List<Integer> cicloAtoms = new ArrayList<>();
        List<String> lineas = new ArrayList<>();

        for (String path : listaPath) {
            try {
                Object[] result = readPDB(path);
                List<List> atom = (List<List>) result[0]; //Lista maestra por archivo
                cicloAtoms.add((int) result[1]);  //Lista de cantidaes de atomos por archivo

                List<String> contenido = atom.get(4);
                lineas.addAll(contenido);
                contenido.clear();

                List<Double> Repartidora = atom.get(3); //sacando parametros de box para el archivo y almacendo x,y,z de 3 en 3
                cajasTerciarias.addAll(Repartidora);
                Repartidora.clear();
                Repartidora = atom.get(0);
                //guardar Xs
                EjesX.addAll(Repartidora);
                Repartidora.clear();
                Repartidora = atom.get(1);
                //guardar Ys
                EjesY.addAll(Repartidora);
                Repartidora.clear();
                Repartidora = atom.get(2);
                //guardar Zs
                EjesZ.addAll(Repartidora);
                Repartidora.clear();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
           }
        System.out.println("Al principio tengo " + cicloAtoms + " Archivos cargados");

        double BigBoxX;
        double BigBoxY;
        double BigBoxZ;
        double diagonalRota;
        double mayor;
        double mayor1;

        //Calculo de dimensiones
        //Adsorbente
        BigBoxX = cajasTerciarias.get(0);
        BigBoxY = cajasTerciarias.get(1);
        BigBoxZ = cajasTerciarias.get(2);

        menor = Math.min(BigBoxX, Math.min(BigBoxY, BigBoxZ));
        mayor = Math.max(BigBoxX, Math.max(BigBoxY, BigBoxZ));

        if (mayor == BigBoxX){
             mayor1 = Math.max(BigBoxY, BigBoxZ);
        }else if(mayor == BigBoxY){
             mayor1 = Math.max(BigBoxX, BigBoxZ);
        }else{
             mayor1 = Math.max(BigBoxX, BigBoxY);
        }

        diagonalRota = Math.sqrt(Math.pow(mayor, 2) + Math.pow(mayor1, 2) + Math.pow(menor, 2)); //diagonal ahora es la nueva distancia de la caja


        //---------------------------Declaracion de variables--------------------------

        double a = 0;
        double b = 0;
        double c = 0;
        for(int z = 3; z < cajasTerciarias.size(); z=z+3){
            a += cajasTerciarias.get(z);
            b += cajasTerciarias.get(z+1);
            c += cajasTerciarias.get(z+2);
        }

        //Comprobar quien es menor Y sumandole a los atomos diagonalRota/2
        int inicio = 0;
        int finall = cicloAtoms.get(0);
        double newValor;
        //Rotando adsorbato
        // Crear un objeto Random para generar números aleatorios
        Random random = new Random();
        // Generar ángulos aleatorios entre 0 y 45 grados
        double anguloXGrados = random.nextDouble() * 45; // Para el eje X
        double anguloYGrados = random.nextDouble() * 45; // Para el eje Y
        // Convertir a radianes
        double anguloXRadianos = Math.toRadians(anguloXGrados);
        double anguloYRadianos = Math.toRadians(anguloYGrados);

        if (menor == BigBoxX){
            BigBoxX = BigBoxX + diagonalRota;

            // Obtener el texto del campo de texto
            String texto = adsorbentes.getText();
            int adsorbentesNum;
            adsorbentesNum = Integer.parseInt(texto);
            //Crear un numero random para generar adsorbentes por ejes
            // Generar un número aleatorio entre 0 y el valor base
            double alea = random.nextDouble() * adsorbentesNum/2.0; // Genera un número aleatorio entre 0 y baseValue
            // Redondear el número a int
            int roundedAlea = (int) Math.round(alea);
            int aleaSpecial = random.nextInt(adsorbentesNum-roundedAlea) + 1;
            int alea2;
            if (adsorbentesNum -(roundedAlea + aleaSpecial)==0){
                alea2=1;
            }else{
                alea2=adsorbentesNum -(roundedAlea + aleaSpecial);
            }

            //calculando dimensiones totales de la caja
             Cryx = (aleaSpecial * (BigBoxX + cajasTerciarias.get(3))) + ((a/2)*(RepeticionesMol.getFirst()));
             Cryy = (roundedAlea * BigBoxY) + ((b/2)*((RepeticionesMol.getFirst())));
             Cryz = (alea2 * BigBoxZ) + ((c/2)*(RepeticionesMol.getFirst()));

            for(int u = inicio; u < finall; u++){
                //System.out.println("valor guardado:");
                //System.out.println(EjesZ.get(u));
                //System.out.println("----Obtenido---");
                newValor = EjesX.get(u)+(diagonalRota);
                EjesX.set(u,newValor);
                //System.out.println(EjesZ.get(u));

                double x = EjesX.get(u);
                double y = EjesY.get(u);
                double z = EjesZ.get(u);
                // Rotación alrededor del eje Y
                double nuevoX = z * Math.sin(anguloYRadianos) + x * Math.cos(anguloYRadianos);
                double nuevoZ = z * Math.cos(anguloYRadianos) - x * Math.sin(anguloYRadianos);
                EjesX.set(u,nuevoX);
                EjesZ.set(u,nuevoZ);

                x = nuevoX;
                // Rotación alrededor del eje Z
                double nuevoY = y * Math.cos(anguloXGrados) - x * Math.sin(anguloXGrados);
                nuevoX = y * Math.sin(anguloXGrados) + x * Math.cos(anguloXGrados);
                EjesY.set(u,nuevoY);
                EjesX.set(u,nuevoX);
            }
        }else if(menor == BigBoxY){
            BigBoxY = BigBoxY + diagonalRota;

            // Obtener el texto del campo de texto
            String texto = adsorbentes.getText();
            int adsorbentesNum;
            adsorbentesNum = Integer.parseInt(texto);
            //Crear un numero random para generar adsorbentes por ejes
            // Generar un número aleatorio entre 0 y el valor base
            double alea = random.nextDouble() * adsorbentesNum/2.0; // Genera un número aleatorio entre 0 y baseValue
            // Redondear el número a int
            int roundedAlea = (int) Math.round(alea);
            int aleaSpecial = random.nextInt(adsorbentesNum-roundedAlea) + 1;
            int alea2;
            if (adsorbentesNum -(roundedAlea + aleaSpecial)==0){
                alea2=1;
            }else{
                alea2=adsorbentesNum -(roundedAlea + aleaSpecial);
            }

            //calculando dimensiones totales de la caja
             Cryx = (alea2 * (BigBoxX)) + ((a/2)*(RepeticionesMol.getFirst()-1));
             Cryy = roundedAlea * (BigBoxY + cajasTerciarias.get(4)) + ((b/2)*(RepeticionesMol.getFirst()-1));
             Cryz = (aleaSpecial * (BigBoxZ)) + ((c/2)*(RepeticionesMol.getFirst()-1));

            for(int u = inicio; u < finall; u++){
                //System.out.println("valor guardado:");
                //System.out.println(EjesZ.get(u));
                //System.out.println("----Obtenido---");
                newValor = EjesY.get(u)+(diagonalRota/2);
                EjesY.set(u,newValor);
                //System.out.println(EjesZ.get(u));

                double x = EjesX.get(u);
                double y = EjesY.get(u);
                double z = EjesZ.get(u);
                // Rotación alrededor del eje X
                double nuevoY = y * Math.cos(anguloXRadianos) - z * Math.sin(anguloXRadianos);
                double nuevoZ = y * Math.sin(anguloXRadianos) + z * Math.cos(anguloXRadianos);
                EjesY.set(u,nuevoY);
                EjesZ.set(u,nuevoZ);

                y = EjesY.get(u);
                // Rotación alrededor del eje Z
                double nuevoX = x * Math.cos(anguloYRadianos) - y * Math.sin(anguloYRadianos);
                nuevoY = x * Math.sin(anguloYRadianos) + y * Math.cos(anguloYRadianos);
                EjesX.set(u,nuevoX);
                EjesY.set(u,nuevoY);
            }
        }else{
            BigBoxZ = BigBoxZ + diagonalRota;

            // Obtener el texto del campo de texto
            String texto = adsorbentes.getText();
            int adsorbentesNum;
            adsorbentesNum = Integer.parseInt(texto);
            //Crear un numero random para generar adsorbentes por ejes
            // Generar un número aleatorio entre 0 y el valor base
            double alea = random.nextDouble() * adsorbentesNum/2.0; // Genera un número aleatorio entre 0 y baseValue
            // Redondear el número a int
            int roundedAlea = (int) Math.round(alea);
            int aleaSpecial = random.nextInt(adsorbentesNum-roundedAlea) + 1;
            int alea2;
            if (adsorbentesNum -(roundedAlea + aleaSpecial)==0){
                alea2=1;
            }else{
                alea2=adsorbentesNum -(roundedAlea + aleaSpecial);
            }

            //calculando dimensiones totales de la caja
             Cryx = (alea2 * (BigBoxX)) + ((a/2)*(RepeticionesMol.getFirst()));
             Cryy = (aleaSpecial * (BigBoxY)) + ((b/2)*(RepeticionesMol.getFirst()));
             Cryz = (roundedAlea * (BigBoxZ + cajasTerciarias.get(5))) + ((c/2)*(RepeticionesMol.getFirst()));

            for(int u = inicio; u < finall; u++){
                //System.out.println("valor guardado:");
                //System.out.println(EjesZ.get(u));
                //System.out.println("----Obtenido---");
                newValor = EjesZ.get(u)+(diagonalRota/2);
                EjesZ.set(u,newValor);
                //System.out.println(EjesZ.get(u));

                double x = EjesX.get(u);
                double y = EjesY.get(u);
                double z = EjesZ.get(u);
                // Rotación alrededor del eje X
                double nuevoY = y * Math.cos(anguloXRadianos) - z * Math.sin(anguloXRadianos);
                double nuevoZ = y * Math.sin(anguloXRadianos) + z * Math.cos(anguloXRadianos);
                EjesY.set(u,nuevoY);
                EjesZ.set(u,nuevoZ);

                z = EjesZ.get(u);
                // Rotación alrededor del eje Y
                double nuevoX = z * Math.sin(anguloYRadianos) + x * Math.cos(anguloYRadianos);
                nuevoZ = z * Math.cos(anguloYRadianos) - x * Math.sin(anguloYRadianos);
                EjesX.set(u,nuevoX);
                EjesZ.set(u,nuevoZ);
            }
        }

         List<Double> desperdicioX = new ArrayList<>();
         List<Double> desperdicioY = new ArrayList<>();
         List<Double> desperdicioZ = new ArrayList<>();
        //Correr adsorbato y adsorbente a la mitead de los cryx, cryY y cryZ
        for(int u = 0; u < cicloAtoms.getFirst(); u++) {
            desperdicioX.add(EjesX.get(u));
            desperdicioY.add(EjesZ.get(u));
            desperdicioZ.add(EjesZ.get(u));
        }
        double mitadX = (findMax(desperdicioX)-findMin(desperdicioX))/2;
        double mitadY = (findMax(desperdicioY)-findMin(desperdicioY))/2;
        double mitadZ = (findMax(desperdicioZ)-findMin(desperdicioZ))/2;

        System.out.println("mitadx"+ mitadX);
        System.out.println("mitady"+ mitadY);
        System.out.println("mitadz"+ mitadZ);

        String p = "E:/Proyecto";
        int ciclo = cicloAtoms.size();
        writePDB(cicloAtoms, lineas, EjesX, EjesY, EjesZ, p, RepeticionesMol, cajasTerciarias, repeticion, ciclo, mitadX, mitadY, mitadZ); //integra cristal en el metodo ya completo

        //primero entra adsorbato (Molecula pequena)
        //segundo entra adsorbente (Molecula grande)
        //mas agentes
        //luego agrego las que ya tengo, agua, sodio, etanol

    }

    // Ajustar la función de colisión
    private static boolean hayColision(double NewMaxX, double NewMaxY, double NewMaxZ, double NewMinX, double NewMinY, double NewMinZ,  List<Double> EjeX,  List<Double> EjeY,  List<Double> EjeZ, List<Integer> cicloAtomos, int molecula, int moleculasIncorporadas) {
        List<Boolean> saliendo = new ArrayList<>();

        // molecula es la cantidad de elementos en la lista cicloAtomos
            List<Double> IntermediaX = new ArrayList<>();
            List<Double> IntermediaY = new ArrayList<>();
            List<Double> IntermediaZ = new ArrayList<>();
            //si molecula es 0 no habra repeticiones, pero tengo entrada 1
            // Seleccion de rangos atomicos de una molecula mediante limi y limite
            for (int t = 0; t < moleculasIncorporadas; t++) {
                IntermediaX.clear();
                IntermediaY.clear();
                IntermediaZ.clear();

                int limite = 0;
                int limi = 0;

                for(int s = -1; s < t; s++) {
                    limite += cicloAtomos.get(s+1);
                    if (s != -1) {
                        limi += cicloAtomos.get(s);
                    }

                }

                if(limi !=0) {
                    limi--;
                }

                for (int q = limi; q < limite; q++) {  // En toodos los for que hago esto tengo que restar uno
                    IntermediaX.add(EjeX.get(q));
                    IntermediaY.add(EjeY.get(q));
                    IntermediaZ.add(EjeZ.get(q));
                }
                double ExiMinX = findMin(IntermediaX);
                double ExiMaxX = findMax(IntermediaX);

                double ExiMinY = findMin(IntermediaY);
                double ExiMaxY = findMax(IntermediaY);

                double ExiMinZ = findMin(IntermediaZ);
                double ExiMaxZ = findMax(IntermediaZ);

                saliendo.add(NewMaxX > ExiMinX ||
                        NewMinX < ExiMaxX &&
                        NewMaxY > ExiMinY ||
                        NewMinY < ExiMaxY &&
                        NewMaxZ > ExiMinZ ||
                        NewMinZ < ExiMaxZ);

//                                saliendo.add(NewMaxX > ExiMaxX ||  //hacer una lista boleana y si todo es true salir false
//                                             NewMaxX < ExiMinX &&
//                                             NewMinX > ExiMaxX ||
//                                             NewMinX < ExiMinX &&
//                                             NewMaxY > ExiMaxY ||  //y
//                                             NewMaxY < ExiMinY &&
//                                             NewMinY > ExiMaxY ||
//                                             NewMinY < ExiMinY &&
//                                             NewMaxZ > ExiMaxZ ||  //z
//                                             NewMaxZ < ExiMinZ &&
//                                             NewMinZ > ExiMaxZ ||
//                                             NewMinZ < ExiMinZ);

            }

        if (saliendo.stream().allMatch(Boolean::booleanValue)) {
            return  false; // Si todos son verdaderos, variable es falso
        } else {
            return true; // Si hay al menos un falso, variable es verdadero
        }

    }

    public static void writePDB(List<Integer>cicloAtoms, List<String> lineas, List<Double> EjesX, List<Double> EjesY, List<Double> EjesZ, String path, List<Integer> RepeticionesMol,List<Double> cajasTerciarias, int Repeticion, int ciclodeAtomosBasic, double mitadX, double mitadY, double mitadZ) {

        String indice = String.valueOf(Repeticion+1);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path + "/System" + indice +".xyz"))) {
            int moleculasIncorporadas = cicloAtoms.size();
            Random random = new Random();
            int cajas=0;
            for(int ye = 0; ye < ciclodeAtomosBasic; ye++) { //ciclodeAtomosBasic es
                System.out.println("ciclodeAtomosBasic es = "+ ciclodeAtomosBasic);
                for (int j = 0; j < RepeticionesMol.get(ye); j++) {
                    double anchoMoleculax = cajasTerciarias.get(cajas); // Dimensión de la molécula
                    double anchoMoleculay = cajasTerciarias.get(cajas+1); // Dimensión de la molécula
                    double anchoMoleculaz = cajasTerciarias.get(cajas+2); // Dimensión de la molécula

                    boolean colocado = false;
                    int demasiado = 0;

                    while (!colocado) {
                        List <Double> RepeX = new ArrayList<>();
                        List <Double> RepeY = new ArrayList<>();
                        List <Double> RepeZ = new ArrayList<>();
                        System.out.println("Molecula: " + ye + " Repeticion: " + j);
                        int limite0 = 0;
                        int limite1 = 0;

                        double MinPosX = 0;
                        double MinPosY = 0;
                        double MinPosZ = 0;
                        double MaxPosX = 0;
                        double MaxPosY = 0;
                        double MaxPosZ = 0;

                        if(ye == 0 && j == 0){   //Adsorbente primario
                            MaxPosX = Cryx;
                            MaxPosY = Cryy;
                            MaxPosZ = Cryz;
                            MinPosX = Cryx/2;
                            MinPosY = Cryy/2;
                            MinPosZ = Cryz/2;

                        }else if(ye == 0 && j != 0){
                            if(menor == cajasTerciarias.get(0)) {  //Adsorbentes n
                                MaxPosX = mitadX + (j * menor);
                                MaxPosY = mitadY + (random.nextDouble() * 10*j);
                                MaxPosZ = mitadZ + (random.nextDouble() * 10*j);
                                MinPosX = anchoMoleculax + (j * menor);
                                MinPosY = anchoMoleculay + (j * menor);
                                MinPosZ = anchoMoleculaz + (j * menor);
                            }else if(menor == cajasTerciarias.get(1)){
                                MaxPosX = mitadX + (random.nextDouble() * 10*j);
                                MaxPosY = mitadY + (j * menor);
                                MaxPosZ = mitadZ + (random.nextDouble() * 10*j);
                                MinPosX = anchoMoleculax + (j * menor);
                                MinPosY = anchoMoleculay + (j * menor);
                                MinPosZ = anchoMoleculaz + (j * menor);
                            }else{
                                MaxPosX = mitadX + (random.nextDouble() * 10*j);
                                MaxPosY = mitadY + (random.nextDouble() * 10*j);
                                MaxPosZ = mitadZ + (j * menor);
                                MinPosX = anchoMoleculax + (j * menor);
                                MinPosY = anchoMoleculay + (j * menor);
                                MinPosZ = anchoMoleculaz + (j * menor);
                            }
                            //las mitades no estan bien calculadas, o rota luego y se pierde la mitad registrada
                        }else if (ye == 1){     //adsorbato unico
                            MaxPosX = mitadX - (random.nextDouble() * 5);
                            MaxPosY = mitadY - (random.nextDouble() * 5);
                            MaxPosZ = mitadZ - (random.nextDouble() * 5);
                            MinPosX = mitadX;
                            MinPosY = mitadY;
                            MinPosZ = mitadZ;
                        }else {
                            // Generar posiciones aleatorias
                            MaxPosX = random.nextDouble() * Cryx;
                            MaxPosY = random.nextDouble() * Cryy;
                            MaxPosZ = random.nextDouble() * Cryz;
                            MinPosX = 0;
                            MinPosY = 0;
                            MinPosZ = 0;
                        }
                        for(int m = -1; m < ye; m++){
                            if(m != -1){
                                limite0 += cicloAtoms.get(m);
                            }
                            limite1 += cicloAtoms.get(m+1);
                        }

                            // Verificar colisiones
                            if (!hayColision(MaxPosX, MaxPosY, MaxPosZ, MinPosX, MinPosY, MinPosZ, EjesX, EjesY, EjesZ, cicloAtoms, ye, moleculasIncorporadas)) {
                                if (j == 0) {
                                    if (ye == 0) {
                                        System.out.println("Ingresando adsorbente primario");
                                    }else if (ye == 1){
                                        for (int g = cicloAtoms.get(0); g < cicloAtoms.get(0)+cicloAtoms.get(1); g++) {
                                            EjesX.set(g, EjesX.get(g) + MaxPosX);
                                            EjesY.set(g, EjesY.get(g) + MaxPosY);
                                            EjesZ.set(g, EjesZ.get(g) + MaxPosZ);
                                        }
                                    }else {
                                        for (int g = limite0; g < limite1; g++) {
                                            EjesX.set(g, EjesX.get(g) + MaxPosX);
                                            EjesY.set(g, EjesY.get(g) + MaxPosY);
                                            EjesZ.set(g, EjesZ.get(g) + MaxPosZ);
                                        }
                                    }
                                } else {
                                    moleculasIncorporadas++;
                                    if (ye == 0) {
                                        for (int g = 0; g < cicloAtoms.get(ye); g++) {
                                            RepeX.add(EjesX.get(g) + MaxPosX);
                                            RepeY.add(EjesY.get(g) + MaxPosY);
                                            RepeZ.add(EjesZ.get(g) + MaxPosZ);
                                            lineas.add(lineas.get(g));
                                        }
                                        EjesX.addAll(RepeX);
                                        EjesY.addAll(RepeY);
                                        EjesZ.addAll(RepeZ);
                                        cicloAtoms.add(cicloAtoms.get(ye));
                                        } else {
                                        for (int g = limite0; g < limite1; g++) {
                                            RepeX.add(EjesX.get(g) + MaxPosX);
                                            RepeY.add(EjesY.get(g) + MaxPosY);
                                            RepeZ.add(EjesZ.get(g) + MaxPosZ);
                                            lineas.add(lineas.get(g));
                                        }
                                        EjesX.addAll(RepeX);
                                        EjesY.addAll(RepeY);
                                        EjesZ.addAll(RepeZ);
                                        cicloAtoms.add(limite1 - limite0);
                                        int ejeMenor = random.nextInt(3);
                                        GenerarRotarGuardar(EjesX, EjesY, EjesZ, limite0, limite1, ejeMenor);
                                    }
                                }
                                colocado = true; // Se ha colocado la molécula
                            }
                        demasiado++;
                        if(demasiado > 1000){
                            //ya no cabe mas nada
                            colocado = true;
                            System.out.println(" La colocacion tuvo un ciclo mayor a 1000 y no se coloco la molecula.");
                        }
                    }
                }
                cajas = cajas + 3;
            }


//            int atomCount = 1; // Contador de átomos
//            int moleculeCount = 1; // Contador de moléculas
//            int totalAtomsInCurrentMolecule = cicloAtoms.get(0); // Total de átomos en la primera molécula
//            int currentMoleculeIndex = 0; // Índice para cicloAtoms
//            int Dinamo = 1;
//            int repe = 1;
//            String sss = "error";
//            String sss1 = "error";
//            System.out.println(RepeticionesMol);
//            System.out.println(cicloAtoms);
            writer.write(String.valueOf(lineas.size()));
            writer.newLine(); // Agregar nueva línea
            writer.write("MolAdso: Version 1.0.0 2024-10-22");
            writer.newLine(); // Agregar nueva línea
            for (int cx = 0; cx < lineas.size(); cx++) {
                    String peon = lineas.get(cx);
                    String symbol1 = getAtomSymbol(peon);
                    String lineaOriginal = "                                                                        ";
                        StringBuilder nuevaLinea = new StringBuilder(lineaOriginal);

                        // Formatear las nuevas coordenadas a 8 caracteres con 3 decimales
                        String nuevoX = String.format("%8.3f", EjesX.get(cx));
                        String nuevoY = String.format("%8.3f", EjesY.get(cx));
                        String nuevoZ = String.format("%8.3f", EjesZ.get(cx));

                        nuevaLinea.replace(0, 2, symbol1);
                        nuevaLinea.replace(7, 15, nuevoX);
                        nuevaLinea.replace(22, 30, nuevoY);
                        nuevaLinea.replace(36, 44, nuevoZ);



                        // Verificar si hemos alcanzado el total de átomos en la molécula actual
//                        if (atomCount > totalAtomsInCurrentMolecule) {
//                            // Cambiar a la siguiente molécula
//                            currentMoleculeIndex++;
//                            if (currentMoleculeIndex < cicloAtoms.size()) {
//                                moleculeCount++;
//                                totalAtomsInCurrentMolecule += cicloAtoms.get(currentMoleculeIndex);
//                                if (Dinamo == RepeticionesMol.get(repe-1)) {
//                                    repe++;
////                                    moleculeCount++; // Incrementar el número de la molécula
//                                    // ciclodeAtomosBasic numero de molleculas elementales
//                                    Dinamo = 1;
//                                }else{
//                                    Dinamo++;
//                                }
//                            }
//                        }
//
//                        sss = String.valueOf(atomCount);
//                        sss1 = String.valueOf(moleculeCount);
//
//
//                        String nuevaaLinea = reemplazarNumeros(String.valueOf(nuevaLinea), sss, sss1);

                        // Escribir la nueva línea en el archivo
                        writer.write(String.valueOf(nuevaLinea));
                        writer.newLine(); // Agregar nueva línea
//                        // Incrementar el contador de átomos
//                        atomCount++;


            }

        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
        }
    }

    public static String getAtomSymbol(String line) {
        // Usar split para dividir la cadena por espacios
        String[] parts = line.trim().split("\\s+");

        // El símbolo del átomo está en la tercera posición (índice 2)
        String atomSymbol = parts[2];

        // Eliminar números del símbolo del átomo
        atomSymbol = atomSymbol.replaceAll("[0-9]", "");

        return atomSymbol;
    }


    public static void GenerarRotarGuardar(List<Double> EjesX, List<Double> EjesY, List<Double> EjesZ, int inicio, int finales, int ejeSmall){

            Random random = new Random();
            // Generar ángulos aleatorios entre 0 y 45 grados
            double anguloXGrados = random.nextDouble() * 45; // Para el eje X
            double anguloYGrados = random.nextDouble() * 45; // Para el eje Y
            // Convertir a radianes
            double anguloXRadianos = Math.toRadians(anguloXGrados);
            double anguloYRadianos = Math.toRadians(anguloYGrados);

            if (ejeSmall == 0) {
                for(int u = inicio; u < finales; u++){
                    double x = EjesX.get(u);
                    double y = EjesY.get(u);
                    double z = EjesZ.get(u);
                    // Rotación alrededor del eje Y
                    double nuevoX = z * Math.sin(anguloYRadianos) + x * Math.cos(anguloYRadianos);
                    double nuevoZ = z * Math.cos(anguloYRadianos) - x * Math.sin(anguloYRadianos);
                    EjesX.set(u,nuevoX);
                    EjesZ.set(u,nuevoZ);

                    x = nuevoX;
                    // Rotación alrededor del eje Z
                    double nuevoY = y * Math.cos(anguloXGrados) - x * Math.sin(anguloXGrados);
                    nuevoX = y * Math.sin(anguloXGrados) + x * Math.cos(anguloXGrados);
                    EjesY.set(u,nuevoY);
                    EjesX.set(u,nuevoX);
                }

            } else if (ejeSmall == 1) {
                for(int u = inicio; u < finales; u++) {

                    double x = EjesX.get(u);
                    double y = EjesY.get(u);
                    double z = EjesZ.get(u);
                    // Rotación alrededor del eje X
                    double nuevoY = y * Math.cos(anguloXRadianos) - z * Math.sin(anguloXRadianos);
                    double nuevoZ = y * Math.sin(anguloXRadianos) + z * Math.cos(anguloXRadianos);
                    EjesY.set(u, nuevoY);
                    EjesZ.set(u, nuevoZ);

                    y = EjesY.get(u);
                    // Rotación alrededor del eje Z
                    double nuevoX = x * Math.cos(anguloYRadianos) - y * Math.sin(anguloYRadianos);
                    nuevoY = x * Math.sin(anguloYRadianos) + y * Math.cos(anguloYRadianos);
                    EjesX.set(u, nuevoX);
                    EjesY.set(u, nuevoY);
                }
            } else {
                for (int u = inicio; u < finales; u++) {
                    double x = EjesX.get(u);
                    double y = EjesY.get(u);
                    double z = EjesZ.get(u);
                    // Rotación alrededor del eje X
                    double nuevoY = y * Math.cos(anguloXRadianos) - z * Math.sin(anguloXRadianos);
                    double nuevoZ = y * Math.sin(anguloXRadianos) + z * Math.cos(anguloXRadianos);
                    EjesY.set(u,nuevoY);
                    EjesZ.set(u,nuevoZ);

                    z = EjesZ.get(u);
                    // Rotación alrededor del eje Y
                    double nuevoX = z * Math.sin(anguloYRadianos) + x * Math.cos(anguloYRadianos);
                    nuevoZ = z * Math.cos(anguloYRadianos) - x * Math.sin(anguloYRadianos);
                    EjesX.set(u,nuevoX);
                    EjesZ.set(u,nuevoZ);
                }
            }
    }

    public static double findMax(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        double max = values.get(0);
        for (double value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static double findMin(List<Double> values) {
        if (values == null || values.isEmpty()) {
            throw new IllegalArgumentException("La lista no puede estar vacía");
        }
        double min = values.get(0);
        for (double value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private static String reemplazarNumeros(String linea, String nuevoNumeroAtomico, String nuevoNumeroMolecula) {
        // Patrón para encontrar los números
        Pattern pattern = Pattern.compile("\\b\\d+\\b");
        Matcher matcher = pattern.matcher(linea);
        int longitud = nuevoNumeroAtomico.length();

        StringBuilder nuevaLinea = new StringBuilder(linea);
        int contador = 0;

        // Reemplazar los números encontrados
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            matcher = pattern.matcher(nuevaLinea); // Reiniciar el matcher con la nueva línea
            matcher.region(end+longitud, nuevaLinea.length()); // Ajustar la región para la búsqueda


            // Reemplazar el primer número (número de átomo)
            if (contador == 0) {
                nuevaLinea.replace(start, end, nuevoNumeroAtomico);
            }
            // Reemplazar el segundo número (número de molécula)
            else if (contador == 1) {
                nuevaLinea.replace(start, end, nuevoNumeroMolecula);
                break; // Salir del bucle después de reemplazar el segundo número
            }
            contador++;
        }

        return nuevaLinea.toString();
    }

    private void iniciarCarga(VBox layout, Stage primaryStage) {
        // Limpiar todos los componentes del contenedor
        layout.getChildren().clear();

        // Cambiar el tamaño de la ventana
        primaryStage.setWidth(200);
        primaryStage.setHeight(100);

        // Crear una barra de progreso
        ProgressBar progressBar = new ProgressBar(0);
        // Alinear la barra de progreso al centro
        layout.getChildren().add(progressBar);  // Corregido aquí

        // Crear una Timeline para la barra de carga (10 segundos)
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            primaryStage.close(); // Cerrar la ventana al terminar la carga
        }));

        // Actualizar la barra de progreso cada 100 ms (0.1 segundos)
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Task para actualizar el progreso de la barra cada 100 ms
        new Thread(() -> {
            try {
                for (int i = 0; i <= 100; i++) {
                    final double progress = i / 100.0; // Progreso de 0 a 1
                    Thread.sleep(100); // Esperar 100 ms
                    // Actualizar la barra de progreso en el hilo de la interfaz de usuario
                    javafx.application.Platform.runLater(() -> progressBar.setProgress(progress));
                }
                timeline.stop(); // Detener el timeline después de completar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

}





