package RateLimiter;

import java.util.*;

public  class FixedWindowCounter implements RateLimiter {

    private int maxRequests;
    private long windowSizeInMillis;

    private Map<String, Integer> counter = new HashMap<>();
    private Map<String, Long> windowStart = new HashMap<>();

    public FixedWindowCounter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    @Override
    public synchronized boolean allowRequest(String key) {
        long currentTime = System.currentTimeMillis();

        windowStart.putIfAbsent(key, currentTime);

        long start = windowStart.get(key);

        if (currentTime - start >= windowSizeInMillis) {
            windowStart.put(key, currentTime);
            counter.put(key, 0);
        }

        counter.putIfAbsent(key, 0);

        if (counter.get(key) < maxRequests) {
            counter.put(key, counter.get(key) + 1);
            return true;
        }

        return false;
    }
}