package PenDesign;

public class Refill {
    private String colour;
    private int inkLevel;

    public Refill(String colour, int inkLevel){
        this.colour = colour;
        this.inkLevel = inkLevel;
    }

    public boolean hasInk(){
        return inkLevel > 0;
    }

    public void useInk(int amount){
        inkLevel = Math.max(0, inkLevel - amount);
    }

    public String getColour(){
        return colour;
    }

    public int getInkLevel(){
        return inkLevel;
    }
}
