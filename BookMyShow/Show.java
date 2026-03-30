package BookMyShow;

import java.time.LocalDateTime;
import java.util.*;

public class Show {
    String showId;
    Movie movie;
    Screen screen;
    LocalDateTime startTime;
    LocalDateTime endTime;
    List<ShowSeat> seats = new ArrayList<>();
}