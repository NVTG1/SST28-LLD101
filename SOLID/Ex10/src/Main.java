public class Main {
    public static void main(String[] args) {
        // Main responsible for creating objects
        // TransportBookingService uses them
        // This is Dependency Injection
        System.out.println("=== Transport Booking ===");

        TripRequest req = new TripRequest(
            "23BCS1010",
            new GeoPoint(12.97, 77.59),
            new GeoPoint(12.93, 77.62)
        );

        // Creating Dependencies
        // We dont care about how the code is calculating distance we know it calculates

        DistanceService dist = new DistanceCalculator();
        DriverService alloc = new DriverAllocator();
        PaymentService pay = new PaymentGateway();
        // DriverService alloc = new MockDriverAllocator();
        // PaymentService pay = new MockPaymentGateway();

        // Creting object of TransportBookingService
        TransportBookingService svc =
            new TransportBookingService(dist, alloc, pay);

        svc.book(req);
    }
}