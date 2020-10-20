package pl.edmi.wdprir.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public Canvas fractalCanvas;

    @FXML
    public ComboBox<String> fractalType;

    @FXML
    public ToggleParallelSwitch parallelSwitch;

    @FXML
    public BorderPane borderPane;

    private GraphicsContext gc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initGraphics();
    }

    @FXML
    public void generate(ActionEvent action) {
        System.out.println("Starting to generate fractal...");
        String fractalTypeName = fractalType.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + fractalTypeName);

        boolean isParallel = parallelSwitch.switchOnProperty().get();
        // TODO: implement fractal generation...
        if (isParallel) {
            System.out.println("Computing fractal parallel");
            drawCircle();
        } else {
            System.out.println("Computing fractal sequentially");
            drawRect();
        }
    }

    @FXML
    public void saveFractal(ActionEvent actionEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        FileSaver.captureFractal(stage, fractalCanvas);
    }

    private void initGraphics() {
        gc = fractalCanvas.getGraphicsContext2D();
        gc.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
    }

    private void drawCircle() {
        gc.setFill(Color.RED);
        gc.fillOval(0, 0, 200, 200);
    }

    private void drawRect() {
        gc.setFill(Color.BEIGE);
        gc.fillRect(0, 0, 200, 200);
    }
}
