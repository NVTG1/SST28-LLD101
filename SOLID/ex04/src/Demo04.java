import java.util.*;

// BookingRequest
class BookingRequest {
    public final int roomType;
    public final List<AddOn> addOns;

    public BookingRequest(int roomType, List<AddOn> addOns) {
        this.roomType = roomType;
        this.addOns = addOns;
    }
}

// AddOn
enum AddOn {
    MESS, LAUNDRY, GYM
}

// AddOnFee
class AddOnFee implements FeeComponent {
    private final AddOn addOn;
    private final Money amount;

    public AddOnFee(AddOn addOn, double amount) {
        this.addOn = addOn;
        this.amount = new Money(amount);
    }

    @Override
    public Money getAmount(BookingRequest req) {
        if (req.addOns.contains(addOn)) return amount;
        return new Money(0.0);
    }
}

// FakeBookingRepo
class FakeBookingRepo {
    public void save(String id, BookingRequest req, Money monthly, Money deposit) {
        System.out.println("Saved booking: " + id);
    }
}

// FeeComponent
interface FeeComponent {
    Money getAmount(BookingRequest req);
}

// HostelFeeComponenet
class HostelFeeCalculator {
    private final FakeBookingRepo repo;
    private final List<FeeComponent> feeComponents;

    public HostelFeeCalculator(FakeBookingRepo repo, List<FeeComponent> feeComponents) {
        this.repo = repo;
        this.feeComponents = feeComponents;
    }

    public void process(BookingRequest req) {
        Money monthly = new Money(0.0);
        for (FeeComponent fc : feeComponents) {
            monthly = monthly.plus(fc.getAmount(req));
        }

        Money deposit = new Money(5000.0);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000));
        repo.save(bookingId, req, monthly, deposit);
    }
}

// LegacyRoomTypes
class LegacyRoomTypes {
    public static final int SINGLE = 1;
    public static final int DOUBLE = 2;
    public static final int TRIPLE = 3;
    public static final int DELUXE = 4;

    public static String nameOf(int t) {
        return switch (t) {
            case SINGLE -> "SINGLE";
            case DOUBLE -> "DOUBLE";
            case TRIPLE -> "TRIPLE";
            default -> "DELUXE";
        };
    }
}

// Money
class Money {
    public final double amount;
    public Money(double amount) { this.amount = round2(amount); }

    public Money plus(Money other) { return new Money(this.amount + other.amount); }

    private static double round2(double x) { return Math.round(x * 100.0) / 100.0; }

    @Override public String toString() { return String.format("%.2f", amount); }
}

// ReceiptPrinter
class ReceiptPrinter {
    public static void print(BookingRequest req, Money monthly, Money deposit) {
        System.out.println("Room: " + LegacyRoomTypes.nameOf(req.roomType) + " | AddOns: " + req.addOns);
        System.out.println("Monthly: " + monthly);
        System.out.println("Deposit: " + deposit);
        System.out.println("TOTAL DUE NOW: " + monthly.plus(deposit));
    }
}

// RoomFee
class RoomFee implements FeeComponent {
    private final int roomType;
    private final Money amount;

    public RoomFee(int roomType, double amount) {
        this.roomType = roomType;
        this.amount = new Money(amount);
    }

    @Override
    public Money getAmount(BookingRequest req) {
        if (req.roomType == roomType) return amount;
        return new Money(0.0);
    }
}

public class Demo04 {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(
            LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS)
        );

        List<FeeComponent> components = Arrays.asList(
            new RoomFee(LegacyRoomTypes.SINGLE, 14000.0),
            new RoomFee(LegacyRoomTypes.DOUBLE, 15000.0),
            new RoomFee(LegacyRoomTypes.TRIPLE, 12000.0),
            new RoomFee(LegacyRoomTypes.DELUXE, 16000.0),

            new AddOnFee(AddOn.MESS, 1000.0),
            new AddOnFee(AddOn.LAUNDRY, 500.0),
            new AddOnFee(AddOn.GYM, 300.0)
        );

        HostelFeeCalculator calc = new HostelFeeCalculator(new FakeBookingRepo(), components);
        calc.process(req);
    }
}
