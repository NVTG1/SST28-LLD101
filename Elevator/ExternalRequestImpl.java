package Elevator;

public class ExternalRequestImpl implements ExternalRequest {
    private int sourceFloor;
    private Direction direction;

    public ExternalRequestImpl(int sourceFloor, Direction direction) {
        this.sourceFloor = sourceFloor;
        this.direction = direction;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public Direction getDirection() {
        return direction;
    }
}