package sim;

// Обслуговування одним пристроєм з чергою обмеженої довжини
public class Process extends Element {

    private int queue, maxqueue, failure;
    private double busyTime; // сумарний час, коли пристрій був зайнятий

    public Process(double delay) {
        super(delay);
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
    }

    @Override
    public void inAct() {
        if (super.getState() == 0) {
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        } else {
            if (getQueue() < getMaxqueue()) {
                setQueue(getQueue() + 1);
            } else {
                failure++;
            }
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        super.setTnext(Double.MAX_VALUE);
        super.setState(0);
        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            super.setState(1);
            super.setTnext(super.getTcurr() + super.getDelay());
        }
        if (super.getNextElement() != null) {
            super.getNextElement().inAct();
        }
    }

    @Override
    public void doStatistics(double delta) {
        busyTime += super.getState() * delta; // state = 1, якщо пристрій зайнятий
    }

    // Середнє завантаження пристрою за час моделювання
    public double getMeanLoad() {
        return busyTime / super.getTcurr();
    }

    public int getFailure() { return failure; }
    public int getQueue() { return queue; }
    public void setQueue(int queue) { this.queue = queue; }
    public int getMaxqueue() { return maxqueue; }
    public void setMaxqueue(int maxqueue) { this.maxqueue = maxqueue; }

    @Override
    public void printInfo() {
        super.printInfo();
        System.out.println("queue = " + queue + " failure = " + failure);
    }

    @Override
    public void printResult() {
        super.printResult();
        System.out.println("failure = " + failure);
        System.out.println("mean load = " + getMeanLoad());
    }
}
