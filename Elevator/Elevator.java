package Elevator;

import java.util.*;

public class Elevator {

    private int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;

    private TreeSet<Integer> upStops;
    private TreeSet<Integer> downStops;

    private Panel panel;
    private Door door;
    private Display display;

    public Elevator(int id, int totalFloors) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;

        upStops = new TreeSet<>();
        downStops = new TreeSet<>(Collections.reverseOrder());

        panel = new Panel(totalFloors);
        door = new Door();
        display = new Display();
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public ElevatorState getState() {
        return state;
    }

    public boolean isIdle() {
        return state == ElevatorState.IDLE;
    }

    public Panel getPanel(){
        return panel;
    }

    public void addInternalRequest(InternalRequest request) {
        int floor = request.getDestinationFloor();
        if (floor > currentFloor) upStops.add(floor);
        else downStops.add(floor);
    }

    public void addExternalRequest(ExternalRequest request) {
        int floor = request.getSourceFloor();
        if (floor > currentFloor) upStops.add(floor);
        else downStops.add(floor);
    }

    public void move() {
        if (state == ElevatorState.EMERGENCY) return;

        if (!upStops.isEmpty()) {
            direction = Direction.UP;
            state = ElevatorState.MOVING;
            int next = upStops.pollFirst();
            goToFloor(next);

        } else if (!downStops.isEmpty()) {
            direction = Direction.DOWN;
            state = ElevatorState.MOVING;
            int next = downStops.pollFirst();
            goToFloor(next);

        } else {
            direction = Direction.IDLE;
            state = ElevatorState.IDLE;
        }
    }

    private void goToFloor(int floor) {
        System.out.println("Elevator " + id + " going to " + floor);
        currentFloor = floor;
        display.show(currentFloor, direction);
        door.open();
        door.close();
    }

    public void triggerEmergency() {
        state = ElevatorState.EMERGENCY;
        upStops.clear();
        downStops.clear();
        System.out.println("Elevator " + id + " stopped (EMERGENCY)");
    }
}