package sim;

import java.util.Random;

public class FunRand {

    private static Random rnd = new Random();

    // Задає початкове значення генератора
    public static void setSeed(long seed) {
        rnd = new Random(seed);
    }

        // Рівномірне випадкове число з інтервалу (0, 1)
    public static double next() {
        double a = 0;
        while (a == 0) {
            a = rnd.nextDouble();
        }
        return a;
    }

    // Експоненціальний розподіл із середнім timeMean
    public static double Exp(double timeMean) {
        double a = 0;
        while (a == 0) {
            a = rnd.nextDouble();
        }
        return -timeMean * Math.log(a);
    }

    // Рівномірний розподіл на інтервалі (timeMin, timeMax)
    public static double Unif(double timeMin, double timeMax) {
        double a = 0;
        while (a == 0) {
            a = rnd.nextDouble();
        }
        return timeMin + a * (timeMax - timeMin);
    }

    // Нормальний розподіл; від'ємні значення відсікаються
    public static double Norm(double timeMean, double timeDeviation) {
        double a = timeMean + timeDeviation * rnd.nextGaussian();
        return Math.max(a, 0.0);
    }
}
