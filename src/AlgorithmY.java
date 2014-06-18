
public class AlgorithmY implements Algorithm{
    private double y1 = 0;
    private double y2 = 0.2;
    private double epsilon = 0.0001f;
    private double t = 0;
    private double t1 = 0;
    private double x0;

    boolean isPositiveInY1;

    // находит решение функции в окрестности epselon
    // решение положительное
    public double execute(double x0) {
        this.x0 = x0;
        y1 = 0;
        y2 = 0.2;
        if (function1Y(y1) - function2Y(y1) == 0) {
            y1 = epsilon;
        }
        // знак функции в х1
        isPositiveInY1 = (function1Y(y1) - function2Y(y1) > 0);
        try {
            // изменяем отрезок y1, y2
            getSectionY1Y2();
        } catch (IndexOutOfBoundsException e) {
            // если нет решения, то возвращаем -1
            return -1;
        }
        // находим решение функции и возвращаем результат
        return findResult(epsilon, y1, y2);
    }

    /**
     * Изменяет y1 и y2, на отрезок, на котором функция меняет знаек
     */
    public void getSectionY1Y2() {
        // максимальное значение, после которого мы не ищем решение
        double max = Math.pow(2, 12);
        while (isPositiveInY1 == (function1Y(y2) - function2Y(y2) > 0)) {
            y1 = y2;
            y2 = y2 * 2;
            // если решения функции нет
            if (y2 > max) {
                throw new IndexOutOfBoundsException("Решения нет");
            }
        }
    }

    // значение для M1
    private double function1Y(double y) {
        return y;
    }

    // значение для M2
    private double function2Y(double y) {
        return Math.tanh(this.t * y + this.t1 * x0);
    }



    /** Находим решение функции f1-f2=0 на интервале y1, y2
     * методом быстрого поиска.
     *
     * @param epsilon - погрешность для которой ищем результат
     * @param y1 - левая начальная граница
     * @param y2 - правая начальная граница
     * @return
     */
    private double findResult(double epsilon, double y1, double y2) {
        double width = y2 - y1;
        double yMid = (y2 + y1) / 2f;
        boolean isPositiveInMidY = (function1Y(yMid) - function2Y(yMid) > 0);

        while (width > epsilon) {
            if (isPositiveInMidY == isPositiveInY1) {
                y1 = yMid;
            } else {
                y2 = yMid;
            }
            width = y2 - y1;
            yMid = (y2 + y1) / 2f;
            isPositiveInMidY = (function1Y(yMid) - function2Y(yMid) > 0);
        }
        return yMid;
    }

    public void setT(double t) {
        this.t = t;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }
}
