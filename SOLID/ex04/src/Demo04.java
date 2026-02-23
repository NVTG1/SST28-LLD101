import java.util.*;

// AddOn
enum AddOn {
    MESS, LAUNDRY, GYM
}

// RoomPricing: So that we can compute prices specifically for a roomType by creating a class that implements RoomPricing
// Now the FeeCalculator doesn't need to know the RoomType
interface RoomPricing {
    Money monthly();

    String name();
}

// SingleRoom
class SingleRoom implements RoomPricing {

    public Money monthly() {
        return new Money(14000);
    }

    public String name() {
        return "SINGLE";
    }
}

// DoubleRoom
class DoubleRoom implements RoomPricing {

    public Money monthly() {
        return new Money(15000);
    }

    public String name() {
        return "DOUBLE";
    }
}

// TripleRoom
class TripleRoom implements RoomPricing {

    public Money monthly() {
        return new Money(12000);
    }

    public String name() {
        return "TRIPLE";
    }
}

// DeluxeRoom
class DeluxeRoom implements RoomPricing {

    public Money monthly() {
        return new Money(16000);
    }

    public String name() {
        return "DELUXE";
    }
}

// AddOnPricing
// FeeCalculator doesn't need to know the type of AddOns
interface AddOnPricing {
    Money price();

    String name();
}

// MessAddOn
class MessAddOn implements AddOnPricing {

    public Money price() {
        return new Money(1000);
    }

    public String name() {
        return "MESS";
    }

    @Override
    public String toString() {
        return name();
    }
}

// LaundryAddOn
class LaundryAddOn implements AddOnPricing {

    public Money price() {
        return new Money(500);
    }

    public String name() {
        return "LAUNDRY";
    }
       
    @Override
    public String toString() {
        return name();
    }

}

// GymAddOn
class GymAddOn implements AddOnPricing {

    public Money price() {
        return new Money(300);
    }

    public String name() {
        return "GYM";
    }
       
    @Override
    public String toString() {
        return name();
    }
}

// FeeCalculator
class FeeCalculator {

    public Money calculate(RoomPricing room, List<AddOnPricing> addons) {

        Money total = room.monthly();

        for (AddOnPricing a : addons)
            total = total.plus(a.price());

        return total;
    }
}

// ReceiptPrinter
class ReceiptPrinter {

    public void print(RoomPricing room,
            List<AddOnPricing> addons,
            Money monthly,
            Money deposit) {

        System.out.println("Room: " + room.name() +" | AddOns: " + addons);
        System.out.println("Monthly: " + monthly);
        System.out.println("Deposit: " + deposit);
        System.out.println("TOTAL DUE NOW: " + monthly.plus(deposit));
    }
}

// Money
class Money {
   public final double amount;

   public Money(double var1) {
      this.amount = round2(var1);
   }

   public Money plus(Money var1) {
      return new Money(this.amount + var1.amount);
   }

   private static double round2(double var0) {
      return (double)Math.round(var0 * 100.0) / 100.0;
   }

   public String toString() {
      return String.format("%.2f", this.amount);
   }
}

// FakeBookingRepo
// Responsible for saving
 class FakeBookingRepo {
    public void save(String id, BookingRequest req, Money monthly, Money deposit) {
        System.out.println("Saved booking: " + id);
    }
}

// BookingRequest
class BookingRequest {
    public final int roomType;
    public final List<AddOn> addOns;

    public BookingRequest(int roomType, List<AddOn> addOns) {
        this.roomType = roomType;
        this.addOns = addOns;
    }
}


public class Demo04 {
 public static void main(String[] args)
    {

        System.out.println(
        "=== Hostel Fee Calculator ==="
        );

        RoomPricing room = new DoubleRoom();

        List<AddOnPricing> addons =
        List.of(
            new LaundryAddOn(),
            new MessAddOn()
        );

        FeeCalculator calc = new FeeCalculator();

        Money monthly =
        calc.calculate(room, addons);

        Money deposit =new Money(5000);
        String bookingId ="H-" + (7000 + new java.util.Random(1).nextInt(1000));
        new ReceiptPrinter().print(room, addons,monthly, deposit);
        
        BookingRequest req =new BookingRequest(2,List.of(AddOn.LAUNDRY, AddOn.MESS));
        new FakeBookingRepo()
        .save(bookingId, req, monthly, deposit);
    }
}