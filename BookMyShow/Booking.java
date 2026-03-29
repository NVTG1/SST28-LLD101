package BookMyShow;

import java.util.*;

public class Booking {
    private int id;
    private User user;
    private Show show;
    private List<Seat> seats;
    private BookingStatus status;
    private double totalPrice;

    public Booking(int id, User user, Show show, List<Seat> seats) {
        this.id = id;
        this.user = user;
        this.show = show;
        this.seats = seats;
        this.status = BookingStatus.PENDING;
    }

    public int getId(){
        return id;
    }

    public User getUser(){
        return user;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setTotalPrice(double price) {
        this.totalPrice = price;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public Show getShow() {
        return show;
    }

    public BookingStatus geStatus(){
        return status;
    }

    public double getTotalPrice(){
        return totalPrice;
    }
}