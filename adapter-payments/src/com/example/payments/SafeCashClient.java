package com.example.payments;

// Should implement PaymentGateway but doesn't
public class SafeCashClient {
    public SafeCashPayment createPayment(int amount, String user) {
        return new SafeCashPayment(amount, user);
    }
}
