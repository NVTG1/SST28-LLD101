package BookMyShow;

import java.time.LocalDateTime;
import java.util.*;

class BookingService {

    private static final int LOCK_TIMEOUT = 5;

    public boolean lockSeats(User user, List<ShowSeat> seats) {

        synchronized (this) {
            for (ShowSeat seat : seats) {
                if (seat.status != SeatStatus.AVAILABLE) {
                    return false;
                }
            }

            for (ShowSeat seat : seats) {
                seat.status = SeatStatus.LOCKED;
                seat.lockedAt = LocalDateTime.now();
            }

            return true;
        }
    }

    public boolean confirmBooking(Booking booking,
                                  List<ShowSeat> seats,
                                  PaymentStrategy strategy) {

        synchronized (this) {

            double total = 0;

            for (ShowSeat seat : seats) {

                if (seat.status != SeatStatus.LOCKED ||
                    isLockExpired(seat)) {
                    return false;
                }

                double price = calculatePrice(seat, booking.show);
                total += price;
            }

            for (ShowSeat seat : seats) {
                seat.status = SeatStatus.BOOKED;
            }

            strategy.pay(total);

            Payment payment = new Payment();
            payment.amount = total;
            payment.status = "SUCCESS";

            booking.payment = payment;
            booking.seats = seats;

            System.out.println("Booking Confirmed!");

            return true;
        }
    }

    private double calculatePrice(ShowSeat seat, Show show) {

        double base = seat.seat.basePrice;

        if (seat.seat.type == SeatType.GOLD) base *= 1.5;
        if (seat.seat.type == SeatType.PLATINUM) base *= 2;

        int total = show.seats.size();
        int booked = 0;

        for (ShowSeat s : show.seats) {
            if (s.status == SeatStatus.BOOKED) booked++;
        }

        double occupancy = (double) booked / total;

        if (occupancy > 0.5) base *= 1.2;
        if (occupancy > 0.8) base *= 1.5;

        return base;
    }

    private boolean isLockExpired(ShowSeat seat) {
        return seat.lockedAt.plusMinutes(LOCK_TIMEOUT)
                .isBefore(LocalDateTime.now());
    }
}