package PenDesign;

public class BallPen extends Pen{
    public BallPen(Refill refill){
        super(refill, new ClickMechOpen(), new ClickMechClose(), new PenRefill());
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
