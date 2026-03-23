package ParkingLotDesign;

import java.util.*;

class ParkingSlot {
    private int slotId;
    private int levelId;
    private SlotType type;
    private boolean isOccupied;

    private Map<Integer, Integer> distanceFromGate;

    public ParkingSlot(int slotId, int levelId, SlotType type) {
        this.slotId = slotId;
        this.levelId = levelId;
        this.type = type;
        this.isOccupied = false;
        this.distanceFromGate = new HashMap<>();
    }

    public void setDistance(int gateId, int distance) {
        distanceFromGate.put(gateId, distance);
    }

    public int getDistance(int gateId) {
        return distanceFromGate.getOrDefault(gateId, Integer.MAX_VALUE);
    }

    public boolean canFitVehicle(Vehicle v) {
        if (v.getType() == VehicleType.BUS) {
            return type == SlotType.LARGE;
        } else if (v.getType() == VehicleType.CAR) {
            return type == SlotType.MEDIUM || type == SlotType.LARGE;
        } else {
            return true;
        }
    }

    public boolean isAvailable() {
        return !isOccupied;
    }

    public void park() {
        isOccupied = true;
    }

    public void unpark() {
        isOccupied = false;
    }

    public SlotType getType() {
        return type;
    }

    public int getSlotId() {
        return slotId;
    }

    public int getLevelId() {
        return levelId;
    }
}