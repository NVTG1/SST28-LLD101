package ParkingLotDesign;

public class Ticket {
    private static int counter = 0;

    private int id;
    private Vehicle vehicle;
    private ParkingSlot slot;
    private long entryTime;

    public Ticket(Vehicle vehicle, ParkingSlot slot) {
        this.id = ++counter;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }
}