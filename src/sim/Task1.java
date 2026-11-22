package sim;

import java.util.ArrayList;

public class Task1 {

    public static void main(String[] args) {
        FunRand.setSeed(42);
        Create c = new Create(2.0);
        Process p = new Process(1.0);
        Dispose d = new Dispose();
        c.setName("CREATOR");
        p.setName("PROCESSOR");
        c.setDistribution("exp");
        p.setDistribution("exp");
        p.setMaxqueue(5);
        c.setNextElement(p);
        p.setNextElement(d);

        ArrayList<Element> list = new ArrayList<>();
        list.add(c);
        list.add(p);
        list.add(d);
        Model model = new Model(list);
        model.simulate(1000.0);
    }
}
