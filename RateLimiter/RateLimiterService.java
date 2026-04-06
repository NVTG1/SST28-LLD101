// Wrapper
package RateLimiter;

public class RateLimiterService {

    private RateLimiter rateLimiter;

    public RateLimiterService(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public boolean allow(String key) {
        return rateLimiter.allowRequest(key);
    }
}