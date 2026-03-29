package Elevator;

import java.util.*;

public class ElevatorController {

    private List<Elevator> elevators;
    private Queue<ExternalRequest> requests;
    private ElevatorSelectionStrategy strategy;

    public ElevatorController(int n, int floors) {
        elevators = new ArrayList<>();
        requests = new LinkedList<>();
        strategy = new NearestElevatorStrategy();

        for (int i = 0; i < n; i++) {
            elevators.add(new Elevator(i, floors));
        }
    }

    public void submitExternalRequest(ExternalRequest request) {
        requests.add(request);
    }

    public void submitInternalRequest(int elevatorId, InternalRequest request) {
        elevators.get(elevatorId).addInternalRequest(request);
    }

    public void assignElevator() {
        while (!requests.isEmpty()) {
            ExternalRequest req = requests.poll();
            Elevator e = strategy.selectElevator(elevators, req);

            if (e != null) {
                e.addExternalRequest(req);
                System.out.println("Request assigned to Elevator " + e.getId());
            }
        }
    }

    public void step() {
        for (Elevator e : elevators) {
            e.move();
        }
    }

    public void triggerAlarm(int elevatorId) {
        elevators.get(elevatorId).triggerEmergency();
    }
}