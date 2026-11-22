package sim;

import java.util.ArrayList;

public class Task4 {

    static void run(String name, boolean deterministic, double createDelay,
                    double d1, double d2, double d3, int maxQueue) {
        FunRand.setSeed(42);
        Create c = new Create(createDelay);
        Process p1 = new Process(d1);
        Process p2 = new Process(d2);
        Process p3 = new Process(d3);
        Dispose d = new Dispose();
        p1.setMaxqueue(maxQueue);
        p2.setMaxqueue(maxQueue);
        p3.setMaxqueue(maxQueue);
        if (deterministic) { // сталі затримки: результат можна передбачити
            c.setDistribution("");
            p1.setDistribution("");
            p2.setDistribution("");
            p3.setDistribution("");
        }
        c.setNextElement(p1);
        p1.setNextElement(p2);
        p2.setNextElement(p3);
        p3.setNextElement(d);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(d);
        Model model = new Model(list);
        model.setLog(false);
        model.simulate(1000.0);

        int failures = p1.getFailure() + p2.getFailure() + p3.getFailure();
        int inSystem = p1.getState() + p1.getQueue() + p2.getState() + p2.getQueue()
                + p3.getState() + p3.getQueue();
        boolean balance = c.getQuantity() == d.getQuantity() + failures + inSystem;
        System.out.printf("%-10s | %5d %5d %5d | %7d %6d %5d | %5.3f %5.3f %5.3f | %s%n",
                name, c.getQuantity(), d.getQuantity(), failures, p1.getFailure(),
                p2.getFailure(), p3.getFailure(), p1.getMeanLoad(), p2.getMeanLoad(),
                p3.getMeanLoad(), balance ? "OK" : "ERROR");
    }

    public static void main(String[] args) {
        System.out.printf("%-10s | %5s %5s %5s | %7s %6s %5s | %5s %5s %5s | %s%n",
                "exp", "creat", "dispo", "fail", "fP1", "fP2", "fP3", "load1", "load2", "load3", "balance");
        //  назва, детермін., CREATE, P1, P2, P3, макс. черга
        run("1 const", true, 2.0, 1.0, 1.5, 1.2, 5);
        run("2 base", false, 2.0, 1.0, 1.5, 1.2, 5);
        run("3 cr=4.0", false, 4.0, 1.0, 1.5, 1.2, 5);
        run("4 cr=1.0", false, 1.0, 1.0, 1.5, 1.2, 5);
        run("5 cr=0.5", false, 0.5, 1.0, 1.5, 1.2, 5);
        run("6 P2=0.8", false, 2.0, 1.0, 0.8, 1.2, 5);
        run("7 q=0", false, 2.0, 1.0, 1.5, 1.2, 0);
        run("8 q=20", false, 2.0, 1.0, 1.5, 1.2, 20);
    }
}