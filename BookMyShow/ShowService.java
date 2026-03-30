package BookMyShow;

import java.util.*;

public class ShowService {

    Map<String, List<Show>> screenShows = new HashMap<>();

    public boolean addShow(Show newShow) {

        List<Show> shows = screenShows.getOrDefault(
                newShow.screen.screenId,
                new ArrayList<>()
        );

        for (Show s : shows) {
            if (isOverlapping(s, newShow)) {
                System.out.println("Time conflict!");
                return false;
            }
        }

        shows.add(newShow);
        screenShows.put(newShow.screen.screenId, shows);

        System.out.println("Show added: " + newShow.movie.name +
                " | Screen: " + newShow.screen.screenId +
                " | Time: " + newShow.startTime);

        return true;
    }

    private boolean isOverlapping(Show a, Show b) {
        return b.startTime.isBefore(a.endTime) &&
               b.endTime.isAfter(a.startTime);
    }
}