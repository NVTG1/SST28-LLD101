import java.util.*;

// AuditLog
class AuditLog {
    private final List<String> entries = new ArrayList<>();
    public void add(String e) { entries.add(e); }
    public int size() { return entries.size(); }
}

// ConsolePreview
class ConsolePreview {
    public void preview(String s) { System.out.println(s); }
}

// EmailSender
class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    protected void doesSend(Notification n) {
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }
}

// Notification
class Notification {
    public final String subject;
    public final String body;
    public final String email;
    public final String phone;

    public Notification(String subject, String body, String email, String phone) {
        this.subject = subject; this.body = body; this.email = email; this.phone = phone;
    }
}


// NotificationSender
abstract class NotificationSender {
    protected final AuditLog audit;

    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    // final method means a contract: 
    // There will always be a notification
    public final void send(Notification n) {
        if (n == null) throw new IllegalArgumentException("Notification cannot be null");
        doesSend(n); // subclasses implement only this
    }

    // This method will handle the specifications of each subclass
    protected abstract void doesSend(Notification n); 
}

// SenderConfig
class SenderConfig {
    public int maxLen = 160;
}

//SmsSender
class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    protected void doesSend(Notification n) {
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}

// WhatsAppSender
class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    protected void doesSend(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) {
            throw new RuntimeException("phone must start with + and country code");
        }
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}

public class Demo06 {
    public static void main(String[] args) {

        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        email.send(n);
        sms.send(n);
        try {
            wa.send(n);
        } catch (RuntimeException ex) {
            System.out.println("WA ERROR: " + ex.getMessage());
            audit.add("WA failed");
        }

        System.out.println("AUDIT entries=" + audit.size());
    }
}
