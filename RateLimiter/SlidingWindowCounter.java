package RateLimiter;

import java.util.*;

public class SlidingWindowCounter implements RateLimiter {

    private int maxRequests;
    private long windowSizeInMillis;

    private Map<String, Queue<Long>> requestTimestamps = new HashMap<>();

    public SlidingWindowCounter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
    }

    @Override
    public synchronized boolean allowRequest(String key) {
        long currentTime = System.currentTimeMillis();

        requestTimestamps.putIfAbsent(key, new LinkedList<>());
        Queue<Long> queue = requestTimestamps.get(key);

        // remove old requests
        while (!queue.isEmpty() && currentTime - queue.peek() > windowSizeInMillis) {
            queue.poll();
        }

        if (queue.size() < maxRequests) {
            queue.offer(currentTime);
            return true;
        }

        return false;
    }
}