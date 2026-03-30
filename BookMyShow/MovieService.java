package BookMyShow;

import java.util.*;

public class MovieService {

    List<Movie> movies = new ArrayList<>();

    public void addMovie(Movie movie, User user) {
        if (user.role != UserRole.ADMIN) {
            throw new RuntimeException("Unauthorized");
        }

        movies.add(movie);
        System.out.println("Movie added: " + movie.name);
    }
}