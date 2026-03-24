package PenDesign;

class BallPen extends Pen {

    public BallPen(Refill refill, StartStrategy startStrategy, CloseStrategy closeStrategy, RefillStrategy refillStrategy) {
        super(refill, startStrategy, closeStrategy, refillStrategy);
    }

    @Override
    public void write(String text) {
        if (!refill.hasInk()) {
            System.out.println("No ink!... Refill");
            return;
        }

        System.out.println("BallPen writing in " + refill.getColour() + ": " + text);
        refill.useInk(text.length());
    }
}
