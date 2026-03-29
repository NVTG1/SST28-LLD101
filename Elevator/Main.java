package Elevator;

public class Main {

    public static void main(String[] args) {

        ElevatorController controller = new ElevatorController(3, 10);

        controller.submitExternalRequest(new ExternalRequestImpl(2, Direction.UP));
        controller.submitExternalRequest(new ExternalRequestImpl(7, Direction.DOWN));

        controller.assignElevator();

        controller.submitInternalRequest(0, new InternalRequestImpl(5));

        controller.step();
        controller.step();

        controller.triggerAlarm(1);

        controller.step();
    }
}