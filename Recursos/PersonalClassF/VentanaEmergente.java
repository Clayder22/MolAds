package Recursos.PersonalClassF;

import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.concurrent.Task;

public class VentanaEmergente extends Stage {
    private ProgressBar progressBar;

    public VentanaEmergente(Task<Void> task) {
        setTitle("Carga de Proceso");
        setWidth(350);
        setHeight(150);

        ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(300);
        progressBar.setStyle("-fx-accent: rgba(7,4,4,0.51);");

        StackPane root = new StackPane();
        root.getChildren().add(progressBar);

        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);

        task.setOnSucceeded(event -> {
            close(); // Cierra la ventana emergente cuando el Task finaliza
        });
        progressBar.progressProperty().bind(task.progressProperty());
    }
}