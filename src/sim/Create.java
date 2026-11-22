package sim;

// Генератор вимог
public class Create extends Element {

    public Create(double delay) {
        super(delay);
        super.setTnext(0.0); // імітація починається з події Create
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(super.getTcurr() + super.getDelay());
        passOn();
    }
}
