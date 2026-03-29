package BookMyShow;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Seat s1 = new Seat(1, 1, 1, SeatType.GOLD);
        Seat s2 = new Seat(2, 1, 2, SeatType.SILVER);

        Screen screen = new Screen(1, "Screen 1", Arrays.asList(s1, s2));

        Movie movie = new Movie(1, "Inception", 120, "Sci-Fi");

        Show show = new Show(1, movie, screen, new Date());

        User user = new User(1, "User1");

        BookingService bookingService = new BookingService(new DefaultPricingStrategy());

        Booking booking = bookingService.createBooking(user, show, Arrays.asList(s1, s2));

        Payment payment = new Payment(1, booking, PaymentMode.UPI);

        PaymentService paymentService = new PaymentService();

        if (paymentService.processPayment(payment)) {
            bookingService.confirmBooking(booking);
        }

        System.out.println("Booking Confirmed!");
    }
}