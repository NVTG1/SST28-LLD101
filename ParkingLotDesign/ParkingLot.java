package ParkingLotDesign;

import java.util.*;

class ParkingLot {

    private List<ParkingLevel> levels;
    private Map<Integer, EntryGate> gates;
    private Map<Integer, Ticket> activeTickets;

    public ParkingLot(List<ParkingLevel> levels, List<EntryGate> gateList) {
        this.levels = levels;
        this.gates = new HashMap<>();
        this.activeTickets = new HashMap<>();

        for (EntryGate g : gateList) {
            gates.put(g.getGateId(), g);
        }
    }

    public Ticket parkVehicle(Vehicle v, int gateId) {
        EntryGate gate = gates.get(gateId);

        if (gate == null) {
            System.out.println("Invalid gate");
            return null;
        }

        ParkingSlot slot = gate.assignSlot(levels, v);

        if (slot == null) {
            System.out.println("No slot available");
            return null;
        }

        slot.park();

        Ticket ticket = new Ticket(v, slot);
        activeTickets.put(ticket.getId(), ticket);

        System.out.println("Parked at Level " + slot.getLevelId() +
                           ", Slot " + slot.getSlotId() +
                           ", Ticket ID: " + ticket.getId());

        return ticket;
    }

    public void exitVehicle(int ticketId) {
        Ticket ticket = activeTickets.get(ticketId);

        if (ticket == null) {
            System.out.println("Invalid ticket");
            return;
        }

        int amount = CalculateAmount.calculate(ticket);

        ticket.getSlot().unpark();
        activeTickets.remove(ticketId);

        System.out.println("Total fee: " + amount);
    }
}