package PenDesign;

class GelPen extends Pen {

    public GelPen(Refill refill) {
        super(refill,
              new CapMechOpen(),
              new CapMechClose(),
              new PenRefill());
    }

    @Override
    public void write(String text) {
        if (!refill.hasInk()) {
            System.out.println("No ink!.....Refill");
            return;
        }

        System.out.println("GelPen smooth writing in " + refill.getColour() + ": " + text);
        refill.useInk(text.length());
    }
}