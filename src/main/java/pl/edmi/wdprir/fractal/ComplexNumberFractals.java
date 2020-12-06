package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public final class ComplexNumberFractals {

    public static void drawFractal(double canvasWidth,
                                   double canvasHeight,
                                   GraphicsContext gContext,
                                   double reMin,
                                   double reMax,
                                   double imMin,
                                   double imMax,
                                   int convergenceSteps,
                                   ConvergenceFunction f) {
        double precision = Math.max((reMax - reMin) / canvasWidth, (imMax - imMin) / canvasHeight);
        for (double z = reMin, xR = 0; xR < canvasWidth; z += precision, xR++) {
            for (double zi = imMin, yR = 0; yR < canvasHeight; zi += precision, yR++) {
                double convergenceValue = f.apply(zi, z, convergenceSteps);
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

}
