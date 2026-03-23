package ParkingLotDesign;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        ParkingSlot s1 = new ParkingSlot(1, 1, SlotType.SMALL);
        ParkingSlot s2 = new ParkingSlot(2, 1, SlotType.MEDIUM);
        ParkingSlot s3 = new ParkingSlot(3, 1, SlotType.LARGE);

        s1.setDistance(0, 30);
        s2.setDistance(0, 20);
        s3.setDistance(0, 10);

        ParkingLevel level1 = new ParkingLevel(1, Arrays.asList(s1, s2, s3));

        Gate gate = new Gate(0);
        EntryGate entryGate = new EntryGate(gate, new NearestSlot());

        ParkingLot lot = new ParkingLot(
            List.of(level1),
            List.of(entryGate)
        );

        Vehicle bike = new Vehicle("KA01-BIKE", VehicleType.TWO_WHEELER);
        System.out.println("\n--- Bike Entering ---");
        Ticket t1 = lot.parkVehicle(bike, 0);
        System.out.println("--- Bike Exiting ---");
        lot.exitVehicle(t1.getId());

        Vehicle car = new Vehicle("KA01-CAR", VehicleType.CAR);
        System.out.println("\n--- Car Entering ---");
        Ticket t2 = lot.parkVehicle(car, 0);
        System.out.println("--- Car Exiting ---");
        lot.exitVehicle(t2.getId());

        Vehicle bus = new Vehicle("KA01-BUS", VehicleType.BUS);
        System.out.println("\n--- Bus Entering ---");
        Ticket t3 = lot.parkVehicle(bus, 0);
        System.out.println("--- Bus Exiting ---");
        lot.exitVehicle(t3.getId());
    }
}