package BookMyShow;

public class PaymentService {

    public boolean processPayment(Payment payment) {
        payment.setStatus(PaymentStatus.SUCCESS);
        return true;
    }
}