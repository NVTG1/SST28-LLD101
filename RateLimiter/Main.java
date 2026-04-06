package RateLimiter;

public class Main {
    public static void main(String[] args) {

        // 20 requests per minute

        // RateLimiter limiter = new FixedWindowCounter(20, 60000);

        RateLimiter limiter = new SlidingWindowCounter(5, 60000);
        
        RateLimiterService service = new RateLimiterService(limiter);

        String user = "T1";

        for (int i = 0; i <= 20; i++) {
            if (service.allow(user)) {
                System.out.println("Allowed");
            } else {
                System.out.println("Blocked");
            }
        }
    }
}