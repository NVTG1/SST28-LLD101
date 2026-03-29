package Elevator;

import java.util.*;

public interface ElevatorSelectionStrategy {
    Elevator selectElevator(List<Elevator> elevators, ExternalRequest request);
}