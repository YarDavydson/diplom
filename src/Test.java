import org.math.plot.Plot3DPanel;

import javax.swing.*;

/**
 * Created by YAROSLAV on 10.06.14.
 */
public class Test {
    public static void main(String[] args) {
        double[] x1 = new double[100];
        double[] y1 = new double[100];
        double[][] z1 = new double[100][100];
        for (int i = 0; i < 10; i++) {
            x1[i] = ((double) i - 50) / 10;
            y1[i] = ((double) i - 50) / 10;
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            z1[i][j]=x1[i]*y1[j];
            }
        }

        Plot3DPanel plot = new Plot3DPanel("SOUTH");
//        plot.addScatterPlot("test", x1, y1, z1);
        plot.addGridPlot("test", x1, y1, z1);
        JFrame frame = new JFrame("a plot panel");
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}
