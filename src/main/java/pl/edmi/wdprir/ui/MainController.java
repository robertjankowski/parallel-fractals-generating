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
import pl.edmi.wdprir.fractal.FractalShape;
import pl.edmi.wdprir.fractal.MandelbrotSet;

import java.net.URL;
import java.util.Optional;
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

    private GraphicsContext gContext;
    private FractalShape fractal;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initGraphics();
    }

    @FXML
    public void generate(ActionEvent action) {
        System.out.println("Starting to generate fractal...");
        String selectedFractal = fractalType.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + selectedFractal);

        if (selectedFractal != null) {
            createFractalObject(selectedFractal).ifPresent(f -> {
                clearCanvas();
                f.drawFractal();
            });
        }

        boolean isParallel = parallelSwitch.switchOnProperty().get();
        // TODO: implement fractal generation...
        //        if (isParallel) {
        //            System.out.println("Computing fractal parallel");
        //            drawCircle();
        //        } else {
        //            System.out.println("Computing fractal sequentially");
        //            drawRect();
        //        }
    }

    @FXML
    public void saveFractal(ActionEvent actionEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        CanvasSaver.captureFractal(stage, fractalCanvas);
    }

    private void initGraphics() {
        gContext = fractalCanvas.getGraphicsContext2D();
        clearCanvas();
    }

    private void clearCanvas() {
        gContext.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
        gContext.setFill(Color.BLACK);
        gContext.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
    }

    private Optional<FractalShape> createFractalObject(String chosenFractal) {
        switch (chosenFractal) {
            case "Mandelbrot set":
                return Optional.of(new MandelbrotSet(fractalCanvas));
            case "Julia set":
                return Optional.empty();
            default:
                System.err.println("Selected shape doesn't exist in list !!!");
                return Optional.empty();
        }
    }

}
