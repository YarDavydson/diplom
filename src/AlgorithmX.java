
public class AlgorithmX implements Algorithm {
    private double x1 = 0;
    private double x2 = 0.2;
    private double epsilon = 0.0001f;
    private double t = 0;
    private double t1 = 0;
    private double y0;

    boolean isPositiveInX1;

    // находит решение функции в окрестности epselon
    // решение положительное
    public double execute(double y0) {
        this.y0 = y0;
        x1 = 0;
        x2 = 0.2;
        if (function1X(x1) - function2X(x1) == 0) {
            x1 = epsilon;
        }
        // знак функции в х1
        isPositiveInX1 = (function1X(x1) - function2X(x1) > 0);
        try {
            // изменяем отрезок x1, x2
            getSectionX1X2();
        } catch (IndexOutOfBoundsException e) {
            // если нет решения, то возвращаем -1
            return -1;
        }
        // находим решение функции и возвращаем результат
        return findResult(epsilon, x1, x2);
    }

    /**
     * Изменяет x1 и x2, на отрезок, на котором функция меняет знаек
     */
    public void getSectionX1X2() {
        // максимальное значение, после которого мы не ищем решение
        double max = Math.pow(2, 12);
        while (isPositiveInX1 == (function1X(x2) - function2X(x2) > 0)) {
            x1 = x2;
            x2 = x2 * 2;
            // если решения функции нет
            if (x2 > max) {
                throw new IndexOutOfBoundsException("Решения нет");
            }
        }
    }

    // значение для M1
    private double function1X(double x) {
        return x;
    }

    // значение для M2
    private double function2X(double x) {
        return Math.tanh(this.t1 * x + this.t * y0);
    }



    /** Находим решение функции f1-f2=0 на интервале x1, x2
     * методом быстрого поиска.
     *
     * @param epsilon - погрешность для которой ищем результат
     * @param x1 - левая начальная граница
     * @param x2 - правая начальная граница
     * @return
     */
    private double findResult(double epsilon, double x1, double x2) {
        double width = x2 - x1;
        double xMid = (x2 + x1) / 2f;
        boolean isPositiveInX3 = (function1X(xMid) - function2X(xMid) > 0);

        while (width > epsilon) {
            if (isPositiveInX3 == isPositiveInX1) {
                x1 = xMid;
            } else {
                x2 = xMid;
            }
            width = x2 - x1;
            xMid = (x2 + x1) / 2f;
            isPositiveInX3 = (function1X(xMid) - function2X(xMid) > 0);
        }
        return xMid;
    }

    public void setT(double t) {
        this.t = t;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }
}
