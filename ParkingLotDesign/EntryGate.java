package ParkingLotDesign;

import java.util.*;
public class EntryGate {
    private Gate gate;
    private SlotStrategy strategy;

    public EntryGate(Gate gate, SlotStrategy strategy) {
        this.gate = gate;
        this.strategy = strategy;
    }

    public ParkingSlot assignSlot(List<ParkingLevel> levels, Vehicle v) {
        return strategy.findSlot(levels, v, gate);
    }

    public int getGateId() {
        return gate.getGateId();
    }
}