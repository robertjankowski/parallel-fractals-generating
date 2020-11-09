package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class BurningShip extends FractalShape {

    public final static double BURNING_SHIP_RE_MIN = -2;
    public final static double BURNING_SHIP_RE_MAX = 1;
    public final static double BURNING_SHIP_IM_MIN = -2;
    public final static double BURNING_SHIP_IM_MAX = 1;

    public BurningShip(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void drawFractal(double reMin, double reMax, double imMin, double imMax) {
        drawMandelbrot(reMin, reMax, imMin, imMax);
    }

    private void drawMandelbrot(double reMin, double reMax, double imMin, double imMax) {
        double precision = Math.max((reMax - reMin) / canvasWidth, (imMax - imMin) / canvasHeight);
        int convergenceSteps = 2000;
        for (double z = reMin, xR = 0; xR < canvasWidth; z += precision, xR++) {
            for (double zi = imMin, yR = 0; yR < canvasHeight; zi += precision, yR++) {
                double convergenceValue = checkConvergence(zi, z, convergenceSteps);
                double t1 = convergenceValue / convergenceSteps;
                double c1 = Math.min(255 * 2 * t1, 255);
                double c2 = Math.max(255 * (2 * t1 - 1), 0);

                if (convergenceValue != convergenceSteps) {
                    gContext.setFill(Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0));
                } else {
                    gContext.setFill(Color.PURPLE); // Convergence Color
                }
                gContext.fillRect(xR, yR, 1, 1);
            }
        }
        gContext.setFill(Color.BLACK); // revert color to base
    }

    private int checkConvergence(double y, double x, int convergenceSteps) {
        double z = 0;
        double zi = 0;
        for (int i = 0; i < convergenceSteps; i++) {
            z = z*z - zi*zi + x;
            zi = Math.abs(2*z*zi) + y;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }
        return convergenceSteps;
    }
}