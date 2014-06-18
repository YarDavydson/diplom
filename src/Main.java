import com.sun.deploy.util.ArrayUtil;
import org.math.plot.Plot3DPanel;
//import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;


public class Main {
    public static double epselon = 0.0005d;

    public static void main(String[] args) {
        //класс, который содержит алгоритм
        AlgorithmX algX = new AlgorithmX();
        AlgorithmY algY = new AlgorithmY();
        double[] x1 = new double[100000];
        double[] y1 = new double[100000];
        double[] z1 = new double[100000];
        double[] T = new double[100000];
        double[] T1 = new double[100000];
        double[] J12 = new double[100000];
        int i = 1;
//        double j12= 0.5;
        double j1 = 3.2;
        double j2 = 6.4;
        int tSize = 0;
        for (double t = 0.01; t < 7; t += 0.03) {
            tSize++;
//            double j2=t/2;
            for (double j12 = 0.01; j12 < j1; j12 += 0.03) {
                double x;
                double y;
                algX.setT(j12 / t);
//                algX.setT(j12/t1);
                algX.setT1(j1 / t);
//                algX.setT1(j1/t1);
                algY.setT(j2 / t);
                algY.setT1(j12 / t);
                for (double y0 = 0.001; y0 <= 1; y0 += 0.01f) {
                    x = algX.execute(y0);
                    y = algY.execute(x);

                    if (y == -1 || x == -1) {
                        System.out.println(t + " решения нет");
                        continue;
                    }
                    if (Math.abs(y0 - y) < 0.01d) {
                        x1[i] = x;
                        y1[i] = y;
                        T[i] = t;
                        T1[i] = t;
                        J12[i] = j12;
//                        T1[i] = t1;
                        z1[i] = x+y ;
                        i++;
//                        System.out.println("Найден результат при " + "t=" + t + ",t1=" + t1 + ": y=" + y + ", x=" + x + ", z=" + (-t*x*x -t1*x*y) );
//                        System.out.println("Найден результат при " + "t=" + t + ": y=" + y + ", x=" + x + ",j12/t="+ j12/t +"j1/kt="+j1/t+"j2/kt="+j2/t);

//                    double M1 = Math.tanh(j1 / t * x + j12 / t * y);
//                    double M2 = Math.tanh(j2 / t * y + j12 / t * x);
//                        if(t > 6){
//                            System.out.println("Найден результат при " + "kT=" + t + ": y1=" + x + ", y2=" + y + ",j12/t="+ j12/t +"j1/kt="+j1/t+"j2/kt="+j2/t);
//                        System.out.println("     Проверочка: М1 & y1      "+M1+"    &     "+x+"    M2 & y2    "+M2+"   &   "+y);
//                        };

                        break;
                    }

                }
            }
        }
        double tRes[] = new double[tSize];
        double y1Res[] = new double[tSize];
        double t1Res[] = new double[tSize];
        double x1Res[] = new double[tSize];
        for (int j = 0; j < y1.length; j++) {
            int idx1 = (int) (T[j] / 0.1);
            int idx2 = (int) (T1[j] / 0.1);
            tRes[idx1] = T[j];
            t1Res[idx2] = T1[j];
            if (y1Res[idx1] < y1[j]) {
                y1Res[idx1] = y1[j];
            }
            if (x1Res[idx2] < x1[j]) {
                x1Res[idx2] = x1[j];
            }
        }

        double maxZ = 0;
        for (double n : z1) {
            if (n > maxZ) {
                maxZ = n;
            }
        }

        int k = 20;
        double delta = (double) maxZ / k;
        double min = 0;
        double max = delta;
        double resZ[][] = new double[k][100000];
        double resT[][] = new double[k][100000];
        double resJ12[][] = new double[k][100000];
        int resId[] = new int[k];
        for (int n = 0; n < k; n++) {
            for (int j = 0; j < z1.length; j++) {
                if (z1[j] >= min && z1[j] <= max) {
                    resZ[n][resId[n]] = z1[j];
                    resT[n][resId[n]] = T[j];
                    resJ12[n][resId[n]] = J12[j];
                    resId[n] = resId[n] + 1;
                }
            }
            max += delta;
            min += delta;
        }

        Plot3DPanel plot = new Plot3DPanel("SOUTH");
        for (int n = 0; n < resZ.length; n++) {
            Color color = new Color(255 * n / k,100 * n / k, 50 );
            plot.addScatterPlot("E" + n, color, resT[n], resJ12[n], resZ[n]);
        }

//        plot.addScatterPlot("E", T, J12, z1);
//        plot.addScatterPlot("M2", Color.RED, T1, J12, x1);
//        plot.addBarPlot("PLOT", x1, y1, z1);

//        Plot2DPanel plot = new Plot2DPanel("SOUTH");
//        plot.addScatterPlot("M1", tRes, y1Res);
//        plot.addScatterPlot("M2", Color.RED, T1, J12, x1);
//        plot.addBarPlot("PLOT", x1, y1, z1);

        JFrame frame = new JFrame("a plot panel");
        frame.setSize(600, 600);
        frame.setContentPane(plot);
        frame.setVisible(true);
    }
}
