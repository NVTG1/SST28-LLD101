package Elevator;

public class InternalRequestImpl implements InternalRequest {
    private int destinationFloor;

    public InternalRequestImpl(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}