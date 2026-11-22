package sim;

import java.util.Arrays;

// Обслуговування одним або кількома ідентичними пристроями з чергою обмеженої довжини
public class Process extends Element {

    private int queue, maxqueue, failure;
    private double busyTime; // сумарний час зайнятості пристроїв
    private final int channels; // кількість ідентичних пристроїв
    private final double[] channelNext; // момент звільнення кожного пристрою

    public Process(double delay) {
        this(delay, 1);
    }

    public Process(double delay, int channels) {
        super(delay);
        this.channels = channels;
        channelNext = new double[channels];
        Arrays.fill(channelNext, Double.MAX_VALUE); // усі пристрої вільні
        queue = 0;
        maxqueue = Integer.MAX_VALUE;
    }

    @Override
    public void inAct() {
        int free = freeChannel();
        if (free >= 0) {
            // є вільний пристрій: починаємо обслуговування
            channelNext[free] = super.getTcurr() + super.getDelay();
            super.setState(super.getState() + 1); // state = кількість зайнятих пристроїв
            updateTnext();
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
        int i = earliestChannel(); // пристрій, який щойно закінчив обслуговування
        channelNext[i] = Double.MAX_VALUE;
        super.setState(super.getState() - 1);
        if (getQueue() > 0) {
            setQueue(getQueue() - 1);
            channelNext[i] = super.getTcurr() + super.getDelay();
            super.setState(super.getState() + 1);
        }
        updateTnext();
        if (super.getNextElement() != null) {
            super.getNextElement().inAct();
        }
    }

    // індекс вільного пристрою або -1, якщо всі зайняті
    private int freeChannel() {
        for (int i = 0; i < channels; i++) {
            if (channelNext[i] == Double.MAX_VALUE) {
                return i;
            }
        }
        return -1;
    }

    // індекс пристрою з найближчим моментом звільнення
    private int earliestChannel() {
        int best = 0;
        for (int i = 1; i < channels; i++) {
            if (channelNext[i] < channelNext[best]) {
                best = i;
            }
        }
        return best;
    }

    // tnext елемента = найближчий момент звільнення серед усіх пристроїв
    private void updateTnext() {
        double min = Double.MAX_VALUE;
        for (double t : channelNext) {
            if (t < min) {
                min = t;
            }
        }
        super.setTnext(min);
    }

    @Override
    public void doStatistics(double delta) {
        busyTime += super.getState() * delta; // state = кількість зайнятих пристроїв
    }

    // Середнє завантаження одного пристрою за час моделювання
    public double getMeanLoad() {
        return busyTime / (super.getTcurr() * channels);
    }

    public int getChannels() { return channels; }
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