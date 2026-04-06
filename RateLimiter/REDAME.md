# Pluggable Rate Limiting System

## Overview

This project implements a **rate limiting system** to control how many times an external (paid) API can be called.

👉 Important:

* Rate limiting is applied **only when making external API calls**
* Not every client request is rate limited

---

## Features

* Supports multiple rate limiting algorithms
* Implemented:

  * Fixed Window Counter
  * Sliding Window Counter
* Easy to switch between algorithms
* Works with different keys:

  * User
  * Tenant
  * API key
* Thread-safe and simple design

---

## How It Works

1. Client sends request
2. Business logic runs
3. If external API is needed → rate limiter is checked
4. If allowed → external API is called
5. If denied → request is rejected or handled safely

---

## Algorithms

### Fixed Window Counter

* Counts requests in a fixed time window (e.g., 1 minute)
* Simple and fast
* May allow sudden bursts at time boundaries

---

### Sliding Window Counter

* Tracks request timestamps
* Only allows requests within last time window
* More accurate but uses more memory

---

## Design

### Main Components

* `RateLimiter` → interface for all algorithms
* `FixedWindowCounter` → fixed window logic
* `SlidingWindowRateCounter` → sliding window logic
* `RateLimiterService` → used by business logic

---

## Example Usage

```java
RateLimiter limiter = new FixedWindowCounter(20, 60000);
// or
RateLimiter limiter = new SlidingWindowCounter(20, 60000);

RateLimiterService service = new RateLimiterService(limiter);

if (service.allow("T1")) {
    // call external API
} else {
    // reject request
}
```

---

## Extensibility

* New algorithms can be added easily
  (e.g., Token Bucket, Leaky Bucket)
* No changes needed in existing code

---

## Trade-offs

* Fixed Window → simple but less accurate
* Sliding Window → accurate but slightly heavier

---
