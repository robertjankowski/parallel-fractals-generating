package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.Canvas;

public class BurningShip extends FractalShape {

    public final static double BURNING_SHIP_RE_MIN = -2;
    public final static double BURNING_SHIP_RE_MAX = 1;
    public final static double BURNING_SHIP_IM_MIN = -2;
    public final static double BURNING_SHIP_IM_MAX = 1;

    public BurningShip(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void drawFractal(double reMin, double reMax, double imMin, double imMax, int convergenceSteps) {
        ComplexNumberFractals.drawFractal(
                canvasWidth,
                canvasHeight,
                gContext,
                reMin,
                reMax,
                imMin,
                imMax,
                convergenceSteps,
                this::checkConvergence);
    }

    private int checkConvergence(double y, double x, int convergenceSteps) {
        double z = 0;
        double zi = 0;
        for (int i = 0; i < convergenceSteps; i++) {
            z = z * z - zi * zi + x;
            zi = Math.abs(2 * z * zi) + y;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }
        return convergenceSteps;
    }
}