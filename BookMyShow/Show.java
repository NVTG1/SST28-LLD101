package BookMyShow;

import java.util.*;

public class Show {
    private int id;
    private Movie movie;
    private Screen screen;
    private Date startTime;
    private Map<Integer, SeatStatus> seatStatus = new HashMap<>();

    public Show(int id, Movie movie, Screen screen, Date startTime) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;

        for (Seat seat : screen.getSeats()) {
            seatStatus.put(seat.getId(), SeatStatus.AVAILABLE);
        }
    }

    public Map<Integer, SeatStatus> getSeatStatus() {
        return seatStatus;
    }

    public Screen getScreen() {
        return screen;
    }
}