package BookMyShow;

import java.util.*;

public class TheaterService {

    Map<String, Theater> theaters = new HashMap<>();

    public void addTheater(Theater theater, User user) {
        if (user.role != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized");
        }

        theaters.put(theater.theaterId, theater);
        System.out.println("Theater added: " + theater.name);
    }

    public void addScreen(String theaterId, Screen screen, User user) {
        if (user.role != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized");
        }

        Theater theater = theaters.get(theaterId);
        theater.screens.add(screen);

        System.out.println("Screen added to theater: " + theater.name);
    }
}