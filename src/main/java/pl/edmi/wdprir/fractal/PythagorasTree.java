package pl.edmi.wdprir.fractal;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class PythagorasTree extends FractalShape {
    double tanphi = 1.0;

    public PythagorasTree(Canvas canvas) {
        super(canvas);

    }

    @Override
    public void drawFractal(double reMin, double reMax, double imMin, double imMax, int convergenceSteps) {
        drawPythagorasTree(10000, reMin, imMin, reMax, imMax);
    }

    private void drawPythagorasTree(int n, double x1, double y1, double x2, double y2) {
        if (n > 0) {
            // (1) determine vertices
            double dx = x2 - x1;
            double dy = y1 - y2;
            double x3 = x1 - dy;
            double y3 = y1 - dx;
            double x4 = x2 - dy;
            double y4 = y2 - dx;

            //Drawing // (2) Square vollstndiges
            gContext.setStroke(Color.GREENYELLOW);
            gContext.strokeLine((int) x1, (int) y1, (int) x2, (int) y2);
            gContext.strokeLine((int) x2, (int) y2, (int) x4, (int) y4);
            gContext.strokeLine((int) x4, (int) y4, (int) x3, (int) y3);
            gContext.strokeLine((int) x1, (int) y1, (int) x3, (int) y3);

            // Calculate (3) coordinates of the new vertex
            double v = (x3 + x4) / 2 - (dy / 2 * tanphi);
            double w = (y3 + y4) / 2 - (dx / 2 * tanphi);

            if (dx * dx + dy * dy > 2) {
                drawPythagorasTree(n - 1, x3, y3, v, w);
                drawPythagorasTree(n - 1, v, w, x4, y4);
            }
        }
    }

}