package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.Canvas;

public class JuliaSet extends FractalShape {

    public final static double JULIA_RE_MIN = -1.5;
    public final static double JULIA_RE_MAX = 1.5;
    public final static double JULIA_IM_MIN = -1.5;
    public final static double JULIA_IM_MAX = 1.5;

    public JuliaSet(Canvas canvas) {
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

    private int checkConvergence(double z, double zi, int convergenceSteps) {
        double ci = -0.23;
        double c = -0.72;
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