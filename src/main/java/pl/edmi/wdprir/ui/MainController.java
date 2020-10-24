package pl.edmi.wdprir.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pl.edmi.wdprir.ui.fractals.FractalShape;
import pl.edmi.wdprir.ui.fractals.MandelbrotSet;

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

    private GraphicsContext gContext;
    private String selectedFractal;
    FractalShape fractal;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initGraphics();
    }

    @FXML
    public void generate(ActionEvent action) {
        System.out.println("Starting to generate fractal...");
        selectedFractal = fractalType.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + selectedFractal);

        boolean isParallel = parallelSwitch.switchOnProperty().get();
        if (fractal != null){
            draw();
            System.out.println("koniec malowania");
            gContext.setFill(Color.RED);
            gContext.fillOval(0, 0, 200, 200);

        }


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
    public void draw(){
        fractal.drawFractal();


    }

    @FXML
    public void saveFractal(ActionEvent actionEvent) {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        CanvasSaver.captureFractal(stage, fractalCanvas);
    }

    private void initGraphics() {
        fractalCanvas = new Canvas();
        gContext = fractalCanvas.getGraphicsContext2D();
        gContext.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
        gContext.setFill(Color.BLACK);
        gContext.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
        fractal = createFractalObject(selectedFractal);

//        gc.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
//        gc.setFill(Color.BLACK);
//        gc.fillRect(0, 0, fractalCanvas.getWidth(), fractalCanvas.getHeight());
    }

    FractalShape createFractalObject(String chosenFractal){
        switch (chosenFractal){
            case "Mandelbrot set":
                System.out.println("wybrano mandelbrot");
                return new MandelbrotSet(fractalCanvas);
            case "Julia set":
                return null;
            default:
                System.out.println("Selected shape doesn't exist in list !!!");
                return null;
        }
    }


}
