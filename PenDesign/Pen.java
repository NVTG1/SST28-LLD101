package PenDesign;

abstract class Pen {

    protected Refill refill;

    protected StartStrategy startStrategy;
    protected CloseStrategy closeStrategy;
    protected RefillStrategy refillStrategy;

    public Pen(Refill refill, StartStrategy startStrategy, CloseStrategy closeStrategy, RefillStrategy refillStrategy) {
        this.refill = refill;
        this.startStrategy = startStrategy;
        this.closeStrategy = closeStrategy;
        this.refillStrategy = refillStrategy;
    }

    // Abstract: As any pen will write.
    public abstract void write(String text);

    public void start() {
        startStrategy.start();
    }

    public void close() {
        closeStrategy.close();
    }

    public void refill() {
        this.refill = refillStrategy.refill(this.refill);
    }

    public Refill refill(Refill newRefill) {
        Refill old = this.refill;

        if (!newRefill.getColour().equals(old.getColour())) {
            System.out.println("Color mismatch! Keeping old color: " + old.getColour());
            newRefill = new Refill(old.getColour(), newRefill.getInkLevel());
        }

        this.refill = newRefill;
        System.out.println("Refill inserted");
        return old;
    }
}
