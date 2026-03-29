package BookMyShow;

import java.util.*;

public class BookingService {

    private PricingStrategy pricingStrategy;

    public BookingService(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public Booking createBooking(User user, Show show, List<Seat> seats) {

        for (Seat seat : seats) {
            if (show.getSeatStatus().get(seat.getId()) != SeatStatus.AVAILABLE) {
                throw new RuntimeException("Seat not available");
            }
        }

        for (Seat seat : seats) {
            show.getSeatStatus().put(seat.getId(), SeatStatus.BLOCKED);
        }

        Booking booking = new Booking(new Random().nextInt(), user, show, seats);

        double total = 0;
        for (Seat seat : seats) {
            total += pricingStrategy.calculatePrice(seat);
        }

        booking.setTotalPrice(total);

        return booking;
    }

    public void confirmBooking(Booking booking) {
        for (Seat seat : booking.getSeats()) {
            booking.getShow().getSeatStatus().put(seat.getId(), SeatStatus.BOOKED);
        }
        booking.setStatus(BookingStatus.CONFIRMED);
    }
}