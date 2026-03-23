package ParkingLotDesign;

import java.util.*;
class NearestSlot implements SlotStrategy {

    @Override
    public ParkingSlot findSlot(List<ParkingLevel> levels, Vehicle vehicle, Gate gate) {

        List<ParkingSlot> candidates = new ArrayList<>();

        for (ParkingLevel level : levels) {
            for (ParkingSlot slot : level.getSlots()) {
                if (slot.isAvailable() && slot.canFitVehicle(vehicle)) {
                    candidates.add(slot);
                }
            }
        }

        if (candidates.isEmpty()) return null;

        candidates.sort((a, b) -> {
            int typeCompare = a.getType().ordinal() - b.getType().ordinal();
            if (typeCompare != 0) return typeCompare;

            return a.getDistance(gate.getGateId()) - b.getDistance(gate.getGateId());
        });

        return candidates.get(0);
    }
}