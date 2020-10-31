package pl.edmi.wdprir.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
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

    public Slider zoomSlider;

    private GraphicsContext gContext;

    private Point2D zoomStart;
    private double reMin;
    private double reMax;
    private double imMin;
    private double imMax;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initGraphics();
    }

    @FXML
    public void generate(ActionEvent action) {
        System.out.println("Starting to generate fractal...");
        String selectedFractal = fractalType.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + selectedFractal);

        selectAndDrawFractal(false);

        boolean isParallel = parallelSwitch.switchOnProperty().get();
        // TODO: implement fractal generation...
        //        if (isParallel) {
        //
        //        } else {
        //
        //        }
    }

    private void selectAndDrawFractal(boolean isZoom) {
        String selectedFractal = fractalType.getSelectionModel().getSelectedItem();
        if (selectedFractal != null) {
            createFractalObject(selectedFractal, isZoom).ifPresent(f -> {
                clearCanvas();
                f.drawFractal(reMin, reMax, imMin, imMax);
            });
        }
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

    private Optional<FractalShape> createFractalObject(String chosenFractal, boolean isZoom) {
        switch (chosenFractal) {
            case "Mandelbrot set":
                if (!isZoom) {
                    reMin = MandelbrotSet.MANDELBROT_RE_MIN;
                    reMax = MandelbrotSet.MANDELBROT_RE_MAX;
                    imMin = MandelbrotSet.MANDELBROT_IM_MIN;
                    imMax = MandelbrotSet.MANDELBROT_IM_MAX;
                }
                return Optional.of(new MandelbrotSet(fractalCanvas));
            case "Julia set":
                if (!isZoom) {
                    // TODO: set re and im bounds
                }
                return Optional.empty();
            default:
                System.err.println("Selected shape doesn't exist in the list !!!");
                return Optional.empty();
        }
    }

    @FXML
    public void setStartPoint(MouseEvent mouseEvent) {
        zoomStart = new Point2D(mouseEvent.getX(), mouseEvent.getY());
    }

    @FXML
    public void setEndPoint(MouseEvent mouseEvent) {
        double xMinRatio = Math.min(zoomStart.getX(), mouseEvent.getX()) / fractalCanvas.getWidth();
        double xMaxRatio = Math.max(zoomStart.getX(), mouseEvent.getX()) / fractalCanvas.getWidth();
        double yMinRatio = Math.min(zoomStart.getY(), mouseEvent.getY()) / fractalCanvas.getHeight();
        double yMaxRatio = Math.max(zoomStart.getY(), mouseEvent.getY()) / fractalCanvas.getHeight();
        double xLength = reMax - reMin;
        double yLength = imMax - imMin;

        double zoom = zoomSlider.valueProperty().get();
        reMin += zoom * xLength * xMinRatio;
        reMax -= zoom * xLength * (1 - xMaxRatio);
        imMin += zoom * yLength * yMinRatio;
        imMax -= zoom * yLength * (1 - yMaxRatio);
        selectAndDrawFractal(true);
    }
}
