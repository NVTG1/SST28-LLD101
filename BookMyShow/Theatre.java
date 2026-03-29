package BookMyShow;

import java.util.*;

public class Theatre {
    private int id;
    private String name;
    private String city;
    private List<Screen> screens;

    public Theatre(int id, String name, String city, List<Screen> screens) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.screens = screens;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCity(){
        return city;
    }
}