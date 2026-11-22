package sim;

import java.util.ArrayList;

public class Task5 {

    public static void main(String[] args) {
        FunRand.setSeed(42);
        Create c = new Create(2.0);
        Process p1 = new Process(1.0);
        Process p2 = new Process(1.5, 2); // два ідентичні пристрої
        Process p3 = new Process(1.2);
        Dispose d = new Dispose();

        c.setName("CREATE");
        p1.setName("PROCESS 1");
        p2.setName("PROCESS 2");
        p3.setName("PROCESS 3");

        p1.setMaxqueue(5);
        p2.setMaxqueue(5);
        p3.setMaxqueue(5);

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
        model.simulate(1000.0);
    }
}