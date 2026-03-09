package com.example.payments;

import java.util.Objects;

// Wrapper Class for SafeCashClient that implements PaymentGateway
public class SafeCashAdapter implements PaymentGateway {

    private final SafeCashClient client;

    public SafeCashAdapter(SafeCashClient client) {
        this.client = Objects.requireNonNull(client, "client");
    }

    @Override
    public String charge(String customerId, int amountCents) {

        Objects.requireNonNull(customerId, "customerId");

        // Calling the original createPayment method
        SafeCashPayment payment =
                client.createPayment(amountCents, customerId);

        return payment.confirm();
    }

}