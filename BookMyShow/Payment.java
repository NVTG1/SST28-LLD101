package BookMyShow;

public class Payment {
    private int id;
    private Booking booking;
    private PaymentMode mode;
    private PaymentStatus status;

    public Payment(int id, Booking booking, PaymentMode mode) {
        this.id = id;
        this.booking = booking;
        this.mode = mode;
        this.status = PaymentStatus.PENDING;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public int getId(){
        return id;
    }
}
