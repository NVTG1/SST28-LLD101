package Elevator;

public class Button {
    private int floor;
    private boolean pressed;

    public Button(int floor) {
        this.floor = floor;
    }

    public void press() {
        pressed = true;
    }

    public void reset() {
        pressed = false;
    }

    public int getFloor() {
        return floor;
    }

    public boolean getPressed(){
        return pressed;
    }
}