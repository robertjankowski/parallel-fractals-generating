package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ParallelComplexNumberFractals {

    public static void drawFractal(double canvasWidth,
                                   double canvasHeight,
                                   GraphicsContext gContext,
                                   double reMin,
                                   double reMax,
                                   double imMin,
                                   double imMax,
                                   int convergenceSteps,
                                   ConvergenceFunction f) {
        // GraphicsContext is not thread safe so we have to use lock...
        Lock lock = new ReentrantLock();
        double precision = Math.max((reMax - reMin) / canvasWidth, (imMax - imMin) / canvasHeight);
        IntStream.range(0, (int) canvasWidth + 1)
                .parallel()
                .forEach(
                        xR -> {
                            double z = reMin + precision * xR;
                            IntStream.range(0, (int) canvasHeight).forEach(yR -> {
                                double zi = imMin + precision * yR;
                                double convergenceValue = f.apply(zi, z, convergenceSteps);
                                double t1 = convergenceValue / convergenceSteps;
                                double c1 = Math.min(255 * 2 * t1, 255);
                                double c2 = Math.max(255 * (2 * t1 - 1), 0);
                                try {
                                    lock.lock();
                                    if (convergenceValue != convergenceSteps) {
                                        gContext.setFill(Color.color(c2 / 255.0, c1 / 255.0, c2 / 255.0));
                                    } else {
                                        gContext.setFill(Color.PURPLE); // Convergence Color
                                    }
                                    gContext.fillRect(xR, yR, 1, 1);
                                } finally {
                                    lock.unlock();
                                }
                            });
                        });
    }
}
