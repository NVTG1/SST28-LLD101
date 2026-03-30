package BookMyShow;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        TheaterService theaterService = new TheaterService();
        MovieService movieService = new MovieService();
        ShowService showService = new ShowService();
        BookingService bookingService = new BookingService();

        User admin = new User();
        admin.userId = "U1";
        admin.role = UserRole.ADMIN;

        User customer = new User();
        customer.userId = "U2";
        customer.role = UserRole.CUSTOMER;

        Theater theater = new Theater();
        theater.theaterId = "T1";
        theater.name = "PVR";
        theater.city = "Bangalore";

        theaterService.addTheater(theater, admin);

        Screen screen = new Screen();
        screen.screenId = "S1";

        for (int i = 1; i <= 10; i++) {
            Seat seat = new Seat();
            seat.seatId = "Seat-" + i;
            seat.basePrice = 200;

            if (i <= 3) seat.type = SeatType.SILVER;
            else if (i <= 7) seat.type = SeatType.GOLD;
            else seat.type = SeatType.PLATINUM;

            screen.seats.add(seat);
        }

        System.out.println("Seats initialized for Screen: " + screen.screenId);

        theaterService.addScreen("T1", screen, admin);

        Movie movie = new Movie();
        movie.movieId = "M1";
        movie.name = "Inception";
        movie.duration = 120;

        movieService.addMovie(movie, admin);

        Show show = new Show();
        show.showId = "SH1";
        show.movie = movie;
        show.screen = screen;
        show.startTime = LocalDateTime.now();
        show.endTime = show.startTime.plusMinutes(movie.duration);

        for (Seat seat : screen.seats) {
            ShowSeat ss = new ShowSeat();
            ss.seat = seat;
            ss.seatId = seat.seatId;
            ss.status = SeatStatus.AVAILABLE;
            show.seats.add(ss);
        }

        showService.addShow(show);

        List<ShowSeat> selectedSeats = new ArrayList<>();
        selectedSeats.add(show.seats.get(0));
        selectedSeats.add(show.seats.get(5));
        selectedSeats.add(show.seats.get(9));

        Booking booking = new Booking();
        booking.bookingId = "B1";
        booking.user = customer;
        booking.show = show;

        boolean locked = bookingService.lockSeats(customer, selectedSeats);

        if (!locked) {
            System.out.println("Seats not available!");
            return;
        }

        boolean success = bookingService.confirmBooking(
                booking,
                selectedSeats,
                new UPIPayment()
        );

        if (success) {
            System.out.println("\n✅ Booking SUCCESS");
            System.out.println("Total Paid: " + booking.payment.amount);
        }
    }
}