public class MockPaymentGateway implements PaymentService {
    public String charge(String studentId, double amount) {
        return "TEST-TXN";
    }
}