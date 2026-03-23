package PenDesign;

public class PenRefill implements RefillStrategy{
    public Refill refill(Refill oldRefill){
        System.out.println("New refill added");
        String colour = oldRefill.getColour();
        return new Refill(colour, 100);
    }
}
