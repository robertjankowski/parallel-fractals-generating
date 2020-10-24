package pl.edmi.wdprir.ui.fractals;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class FractalShape {
    protected Canvas canvas;
    protected double canvasWidth, canvasHeight;
    protected GraphicsContext gContext;

    public abstract void drawFractal();

    public FractalShape(Canvas canvas) {
        this.canvas = canvas;
        gContext = canvas.getGraphicsContext2D();

        initCanvasWidthHeight();
    }


    public void initCanvasWidthHeight() {
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
    }
}
