package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.Canvas;

public class MandelbrotSet extends FractalShape {

    public final static double MANDELBROT_RE_MIN = -2;
    public final static double MANDELBROT_RE_MAX = 1;
    public final static double MANDELBROT_IM_MIN = -1.2;
    public final static double MANDELBROT_IM_MAX = 1.2;

    public MandelbrotSet(Canvas canvas) {
        super(canvas);
    }

    @Override
    public void drawFractal(double reMin,
                            double reMax,
                            double imMin,
                            double imMax,
                            int convergenceSteps,
                            boolean isParallel) {
        if (isParallel) {
            ParallelComplexNumberFractals.drawFractal(
                    canvasWidth,
                    canvasHeight,
                    gContext,
                    reMin,
                    reMax,
                    imMin,
                    imMax,
                    convergenceSteps,
                    this::checkConvergence);
        } else {
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
    }

    private int checkConvergence(double ci, double c, int convergenceSteps) {
        double z = 0;
        double zi = 0;
        for (int i = 0; i < convergenceSteps; i++) {
            double ziT = 2 * (z * zi);
            double zT = z * z - (zi * zi);
            z = zT + c;
            zi = ziT + ci;

            if (z * z + zi * zi >= 4.0) {
                return i;
            }
        }
        return convergenceSteps;
    }
}