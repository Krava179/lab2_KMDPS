package sim;

import java.util.ArrayList;

public class Task6 {

    public static void main(String[] args) {
        FunRand.setSeed(42);
        Create c = new Create(1.0);
        Process p1 = new Process(0.6);
        Process p2 = new Process(1.0);
        Process p3 = new Process(0.8);
        Dispose d = new Dispose();

        c.setName("CREATE");
        p1.setName("PROCESS 1");
        p2.setName("PROCESS 2");
        p3.setName("PROCESS 3");

        p1.setMaxqueue(10);
        p2.setMaxqueue(10);
        p3.setMaxqueue(10);

        // CREATE -> PROCESS 1
        c.setNextElement(p1);
        // з PROCESS 1: 60 % у PROCESS 2, 40 % у PROCESS 3
        p1.addNextElement(p2, 0.6);
        p1.addNextElement(p3, 0.4);
        // з PROCESS 2: 80 % на вихід, 20 % повернення в PROCESS 1
        p2.addNextElement(d, 0.8);
        p2.addNextElement(p1, 0.2);
        // з PROCESS 3: усі на вихід
        p3.setNextElement(d);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(d);
        Model model = new Model(list);
        model.simulate(1000.0);
    }
}