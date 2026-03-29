package Elevator;

import java.util.*;

public class NearestElevatorStrategy implements ElevatorSelectionStrategy {

    public Elevator selectElevator(List<Elevator> elevators, ExternalRequest request) {
        Elevator best = null;
        int min = Integer.MAX_VALUE;

        for (Elevator e : elevators) {
            if (e.getState() == ElevatorState.EMERGENCY) continue;

            int dist = Math.abs(e.getCurrentFloor() - request.getSourceFloor());

            if (dist < min) {
                min = dist;
                best = e;
            }
        }

        return best;
    }
}