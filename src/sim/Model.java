package sim;

import java.util.ArrayList;

// Алгоритм імітації: просування часу за принципом найближчої події.
public class Model {

    private final ArrayList<Element> list;
    private double tnext, tcurr;

    public Model(ArrayList<Element> elements) {
        list = elements;
        tnext = 0.0;
        tcurr = 0.0;
    }

    public void simulate(double time) {
        while (tcurr < time) {
            // 1. знайти найближчу подію
            tnext = Double.MAX_VALUE;
            Element nearest = null;
            for (Element e : list) {
                if (e.getTnext() < tnext) {
                    tnext = e.getTnext();
                    nearest = e;
                }
            }
            if (nearest == null || tnext > time) {
                for (Element e : list) {
                    e.doStatistics(time - tcurr);
                    e.setTcurr(time);
                }
                tcurr = time;
                break;
            }
            System.out.println("\nIt's time for event in " + nearest.getName() + ", time = " + tnext);

            for (Element e : list) {
                e.doStatistics(tnext - tcurr);
            }

            // 2. просунути час
            tcurr = tnext;
            for (Element e : list) {
                e.setTcurr(tcurr);
            }

            // 3. виконати події у всіх елементах, час яких настав
            for (Element e : list) {
                while (e.getTnext() == tcurr) {
                    e.outAct();
                }
            }
            printInfo();
        }
        printResult();
    }

    public void printInfo() {
        for (Element e : list) {
            e.printInfo();
        }
    }

    public void printResult() {
        System.out.println("\n-------------RESULTS-------------");
        for (Element e : list) {
            e.printResult();
        }
    }
}
