package sim;

// Універсальний елемент моделі. Create, Process і Dispose його нащадки
public class Element {
    private String name;
    private double tnext;
    private double delayMean, delayDev;
    private String distribution;
    private int quantity;
    private double tcurr;
    private int state;
    private Element nextElement;
    private static int nextId = 0;
    private int id;

    public Element() {
        this(1.0);
    }

    public Element(double delay) {
        tnext = Double.MAX_VALUE;
        delayMean = delay;
        distribution = "exp";
        tcurr = 0.0;
        state = 0;
        nextElement = null;
        id = nextId++;
        name = "element" + id;
    }

    public Element(String nameOfElement, double delay) {
        this(delay);
        name = nameOfElement;
    }

    public double getDelay() {
        double delay = getDelayMean();
        if ("exp".equalsIgnoreCase(getDistribution())) {
            delay = FunRand.Exp(getDelayMean());
        } else if ("norm".equalsIgnoreCase(getDistribution())) {
            delay = FunRand.Norm(getDelayMean(), getDelayDev());
        } else if ("unif".equalsIgnoreCase(getDistribution())) {
            delay = FunRand.Unif(getDelayMean(), getDelayDev());
        } 
        return delay;
    }

    // Вхід вимоги в елемент
    public void inAct() {
    }

    // Вихід вимоги з елемента
    public void outAct() {
        quantity++;
    }

    public void printResult() {
        System.out.println(getName() + "  quantity = " + quantity);
    }

    public void printInfo() {
        System.out.println(getName() + " state= " + state + " quantity = " + quantity + " tnext= " + tnext);
    }

    public double getDelayDev() { return delayDev; }
    public void setDelayDev(double delayDev) { this.delayDev = delayDev; }
    public String getDistribution() { return distribution; }
    public void setDistribution(String distribution) { this.distribution = distribution; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public double getTcurr() { return tcurr; }
    public void setTcurr(double tcurr) { this.tcurr = tcurr; }
    public int getState() { return state; }
    public void setState(int state) { this.state = state; }
    public Element getNextElement() { return nextElement; }
    public void setNextElement(Element nextElement) { this.nextElement = nextElement; }
    public double getTnext() { return tnext; }
    public void setTnext(double tnext) { this.tnext = tnext; }
    public double getDelayMean() { return delayMean; }
    public void setDelayMean(double delayMean) { this.delayMean = delayMean; }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
