package pl.edmi.wdprir.fractal;

public interface ConvergenceFunction {
    double apply(double z, double zi, int convergenceSteps);
}
