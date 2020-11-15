package pl.edmi.wdprir.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.edmi.wdprir.fractal.*;

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
    public Slider iterationSlider;
    public Label timerLabel;

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
        selectAndDrawFractal(false, isParallel());
    }

    private void selectAndDrawFractal(boolean isZoom, boolean isParallel) {
        long start = System.currentTimeMillis();
        String selectedFractal = fractalType.getSelectionModel().getSelectedItem();
        if (selectedFractal != null) {
            createFractalObject(selectedFractal, isZoom).ifPresent(f -> {
                clearCanvas();
                f.drawFractal(reMin, reMax, imMin, imMax, (int) iterationSlider.getValue(), isParallel);
            });
        }
        long end = System.currentTimeMillis();
        timerLabel.setText("Elapsed: " + (end - start) + " ms");
    }

    private boolean isParallel() {
        return parallelSwitch.switchOnProperty().get();
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
                    reMin = JuliaSet.JULIA_RE_MIN;
                    reMax = JuliaSet.JULIA_RE_MAX;
                    imMin = JuliaSet.JULIA_IM_MIN;
                    imMax = JuliaSet.JULIA_IM_MAX;
                }
                return Optional.of(new JuliaSet(fractalCanvas));
            case "Burning Ship":
                if (!isZoom) {
                    reMin = BurningShip.BURNING_SHIP_RE_MIN;
                    reMax = BurningShip.BURNING_SHIP_RE_MAX;
                    imMin = BurningShip.BURNING_SHIP_IM_MIN;
                    imMax = BurningShip.BURNING_SHIP_IM_MAX;
                }
                return Optional.of(new BurningShip(fractalCanvas));
            case "Pythagoras Tree":
                if (!isZoom) {
                    double a = Math.min(fractalCanvas.getHeight(), fractalCanvas.getWidth()) / 5;
                    reMin = fractalCanvas.getWidth() / 2 - a / 2;
                    reMax = fractalCanvas.getWidth() / 2 + a / 2;
                    imMin = fractalCanvas.getHeight() - a / 2;
                    imMax = imMin;
                }
                return Optional.of(new PythagorasTree(fractalCanvas));
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
        selectAndDrawFractal(true, isParallel());
    }

    @FXML
    public void repaintFractal() {
        selectAndDrawFractal(true, isParallel());
    }
}
