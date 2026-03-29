package BookMyShow;

public class Movie {
    private int id;
    private String name;
    private int duration;
    private String genre;

    public Movie(int id, String name, int duration, String genre) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public int getId(){
        return id;
    }

    public int getDuration(){
        return duration;
    }

    public String getGenre(){
        return genre;
    }
}