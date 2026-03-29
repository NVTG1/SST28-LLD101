package BookMyShow;

public class DefaultPricingStrategy implements PricingStrategy {

    public double calculatePrice(Seat seat) {
        if (seat.getType() == SeatType.GOLD) return 200;
        if (seat.getType() == SeatType.SILVER) return 150;
        return 300;
    }
}