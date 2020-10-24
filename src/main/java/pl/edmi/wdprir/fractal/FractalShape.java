package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public abstract class FractalShape {

    protected Canvas canvas;
    protected double canvasWidth;
    protected double canvasHeight;
    protected GraphicsContext gContext;

    public FractalShape(Canvas canvas) {
        this.canvas = canvas;
        gContext = canvas.getGraphicsContext2D();
        canvasWidth = canvas.getWidth();
        canvasHeight = canvas.getHeight();
    }

    public abstract void drawFractal();

}
