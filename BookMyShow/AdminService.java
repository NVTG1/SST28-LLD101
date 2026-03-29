package BookMyShow;

import java.util.*;

public class AdminService {

    private List<Movie> movies = new ArrayList<>();
    private List<Theatre> theatres = new ArrayList<>();
    private List<Show> shows = new ArrayList<>();

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addTheatre(Theatre theatre) {
        theatres.add(theatre);
    }

    public void addShow(Show show) {
        shows.add(show);
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Show> getShows() {
        return shows;
    }
}