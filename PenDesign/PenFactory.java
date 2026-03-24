package PenDesign;

public class PenFactory {

    public static Pen createPen(String penType, String mechanismType, Refill refill) {

        StartStrategy startStrategy;
        CloseStrategy closeStrategy;

        if (mechanismType.equalsIgnoreCase("click")) {
            startStrategy = new ClickMechOpen();
            closeStrategy = new ClickMechClose();
        } else if (mechanismType.equalsIgnoreCase("cap")) {
            startStrategy = new CapMechOpen();
            closeStrategy = new CapMechClose();
        } else {
            throw new IllegalArgumentException("Invalid mechanism!");
        }

        if (penType.equalsIgnoreCase("ball")) {
            return new BallPen(refill, startStrategy, closeStrategy, new PenRefill());
        } 
        else if (penType.equalsIgnoreCase("gel")) {
            return new GelPen(refill, startStrategy, closeStrategy, new PenRefill());
        } 
        else {
            throw new IllegalArgumentException("Invalid pen type!");
        }
    }
}