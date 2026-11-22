package sim;

// Вихід з моделі: рахує вимоги, що завершили обслуговування.
public class Dispose extends Element {

    public Dispose() {
        super();
        setName("DISPOSE");
        setTnext(Double.MAX_VALUE); 
    }

    @Override
    public void inAct() {
        super.outAct(); // збільшує лічильник
    }
}
