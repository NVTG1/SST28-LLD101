package ParkingLotDesign;

import java.util.*;

public class CalculateAmount {

    private static final Map<SlotType, Integer> rates = Map.of(
        SlotType.SMALL, 10,
        SlotType.MEDIUM, 20,
        SlotType.LARGE, 30
    );

    public static int calculate(Ticket ticket) {
        long exitTime = System.currentTimeMillis();

        long duration = (exitTime - ticket.getEntryTime()) / (1000 * 60 * 60);

        if (duration == 0) duration = 1;

        return (int) duration * rates.get(ticket.getSlot().getType());
    }
}