package com.example.payments;

import java.util.Objects;

// Wrapper Class for FastPayClient to implement PaymentGateway

public class FastPayAdapter implements PaymentGateway {

    private final FastPayClient client;

    public FastPayAdapter(FastPayClient client) {
        this.client = Objects.requireNonNull(client, "client");
    }

    @Override
    public String charge(String customerId, int amountCents) {
        Objects.requireNonNull(customerId, "customerId");
        // Returning the original FastPayClient method payNow
        return client.payNow(customerId, amountCents);
    }
}