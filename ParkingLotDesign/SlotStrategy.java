package ParkingLotDesign;

import java.util.*;

public interface SlotStrategy {
    ParkingSlot findSlot(List<ParkingLevel> levels, Vehicle vehicle, Gate gate);
}
