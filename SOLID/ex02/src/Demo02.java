import java.util.*;

// MenuItem
class MenuItem {
    public final String id;
    public final String name;
    public final double price;

    public MenuItem(String id, String name, double price) {
        this.id = id; this.name = name; this.price = price;
    }
}

// OrderLine
class OrderLine {
    public final String itemId;
    public final int qty;

    public OrderLine(String itemId, int qty) {
        this.itemId = itemId; this.qty = qty;
    }
}

// TaxRules
class TaxRules {
    public static double taxPercent(String customerType) {
        // hard-coded policy (smell)
        if ("student".equalsIgnoreCase(customerType)) return 5.0;
        if ("staff".equalsIgnoreCase(customerType)) return 2.0;
        return 8.0;
    }
}

// TaxCalculationInterface
interface TaxCalculationInterface {
    double calculateTax(String customerType, double subtotal);
}

// TaxCalculation
// Implementing the TaxCalculation as taxes can be dynamic
class TaxCalculation implements TaxCalculationInterface {
    public double calculateTax(String customerType, double subtotal) {
        if ("student".equalsIgnoreCase(customerType)) return subtotal * 0.05;
        if ("staff".equalsIgnoreCase(customerType)) return subtotal * 0.02;
        return subtotal * 0.08;
    }
}

// FileStoreInterface
// To remove tight coupling between the CafetriaSystem and FileStore class
interface FileStoreInterface {
    void save(String id, String content);
    int countLines(String id);
}

// FileStore
class FileStore implements FileStoreInterface {
    private final Map<String,String> files = new HashMap<>();

    public void save(String id, String content) {
        files.put(id, content);
    }

    public int countLines(String id) {
        String c = files.getOrDefault(id,"");
        if (c.isEmpty()) return 0;
        return c.split("\n").length;
    }
}

// DiscountRules
class DiscountRules {
    public static double discountAmount(String customerType, double subtotal, int distinctLines) {
        // hard-coded policy (smell)
        if ("student".equalsIgnoreCase(customerType)) {
            if (subtotal >= 180.0) return 10.0;
            return 0.0;
        }
        if ("staff".equalsIgnoreCase(customerType)) {
            if (distinctLines >= 3) return 15.0;
            return 5.0;
        }
        return 0.0;
    }
}

// DiscountCalculationInterface
interface DiscountCalculationInterface {
    double calculateDiscount(String customerType, double subtotal, int distinctLines);
}

// DiscountCalculation
// Discounts can be dynamic therefore, interface implementation
class DiscountCalculation implements DiscountCalculationInterface {
    public double calculateDiscount(String customerType, double subtotal, int distinctLines) {
        if ("student".equalsIgnoreCase(customerType)) {
            if (subtotal >= 180) return 10;
            return 0;
        }

        if ("staff".equalsIgnoreCase(customerType)) {
            if (distinctLines >= 3) return 15;
            return 5;
        }

        return 0;
    }
}

// InvoiceFormatter
class InvoiceFormatter {
    public String formatInvoice(
            String invoiceId,
            List<OrderLine> lines,
            Map<String, MenuItem> menu,
            double subtotal,
            double tax,
            double discount,
            double total) {
        StringBuilder out = new StringBuilder();

        out.append("Invoice# ").append(invoiceId).append("\n");

        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            out.append(String.format("- %s x%d = %.2f\n", item.name, l.qty, lineTotal));
        }

        out.append(String.format("Subtotal: %.2f\n", subtotal));
        out.append(String.format("Tax(5%%): %.2f\n", tax)); // keep output same
        out.append(String.format("Discount: -%.2f\n", discount));
        out.append(String.format("TOTAL: %.2f\n", total));

        return out.toString();
    }
}

// CalculatePrice
class CalculatePrice {
    public double priceTotal(List<OrderLine> lines, Map<String, MenuItem> menu){
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
        }

        return subtotal;
    } 
}

// CafeteriaSystem
class CafeteriaSystem {

    private final Map<String, MenuItem> menu = new LinkedHashMap<>();
    private final FileStoreInterface store;
    private final TaxCalculationInterface taxCalc;
    private final DiscountCalculationInterface discountCalc;
    private final InvoiceFormatter formatter;

    private int invoiceSeq = 1000;

    public CafeteriaSystem(TaxCalculationInterface taxCalc,
                           DiscountCalculationInterface discountCalc,
                           FileStoreInterface store,
                           InvoiceFormatter formatter) {

        this.taxCalc = taxCalc;
        this.discountCalc = discountCalc;
        this.store = store;
        this.formatter = formatter;
    }

    public void addToMenu(MenuItem i) {
        menu.put(i.id, i);
    }

    public void checkout(String customerType, List<OrderLine> lines) {

        String invId = "INV-" + (++invoiceSeq);

        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = menu.get(l.itemId);
            subtotal += item.price * l.qty;
        }

        double tax = taxCalc.calculateTax(customerType, subtotal);
        double discount = discountCalc.calculateDiscount(customerType, subtotal, lines.size());
        double total = subtotal + tax - discount;

        String invoiceText = formatter.formatInvoice(
                invId, lines, menu, subtotal, tax, discount, total
        );

        System.out.print(invoiceText);
        store.save(invId, invoiceText);

        System.out.println("Saved invoice: " + invId +
                " (lines=" + store.countLines(invId) + ")");
    }
}

public class Demo02 {
    public static void main(String[] args) {

        System.out.println("=== Cafeteria Billing ===");

        TaxCalculationInterface taxCalc = new TaxCalculation();
        DiscountCalculationInterface discountCalc = new DiscountCalculation();
        FileStoreInterface store = new FileStore();
        InvoiceFormatter formatter = new InvoiceFormatter();

        CafeteriaSystem sys = new CafeteriaSystem(
                taxCalc, discountCalc, store, formatter
        );

        sys.addToMenu(new MenuItem("M1", "Veg Thali", 80.00));
        sys.addToMenu(new MenuItem("C1", "Coffee", 30.00));
        sys.addToMenu(new MenuItem("S1", "Sandwich", 60.00));

        List<OrderLine> order = List.of(
                new OrderLine("M1", 2),
                new OrderLine("C1", 1)
        );

        sys.checkout("student", order);
    }
}
