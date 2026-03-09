package com.example.payments;

// Should implement PaymentGateway but doesn't
public class FastPayClient {
    public String payNow(String custId, int amountCents) {
        return "FP#"+ custId + ":" + amountCents;
    }
}
