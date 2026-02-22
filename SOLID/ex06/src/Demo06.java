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
class EmailSender implements NotificationSender {
    private final AuditLog audit;

    public EmailSender(AuditLog audit) {
        this.audit = audit;
    }

    @Override
    public void send(Notification n) {
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
interface NotificationSender {
    // Contract: No change in meaning when sending notification
    void send(Notification n);
}

// SenderConfig
class SenderConfig {
    public int maxLen = 160;
}

//SmsSender
class SmsSender implements NotificationSender {
    private final AuditLog audit;

    public SmsSender(AuditLog audit) {
        this.audit = audit;
    }

    @Override
    public void send(Notification n) {
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}

// ValidateNotification
class ValidateNotification {
    public static void WhatsAppValidation(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) {
            throw new IllegalArgumentException("phone must start with + and country code");
        }
    }
}

// WhatsAppSender
class WhatsAppSender implements NotificationSender {

    private final AuditLog audit;

    public WhatsAppSender(AuditLog audit) {
        this.audit = audit;
    }

    @Override
    public void send(Notification n) {
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}

public class Demo06 {
    public static void main(String[] args) {

        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification(
                "Welcome",
                "Hello and welcome to SST!",
                "riya@sst.edu",
                "9876543210"
        );

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        email.send(n);
        sms.send(n);

        try {
            ValidateNotification.WhatsAppValidation(n);
            wa.send(n);
        } catch (RuntimeException ex) {
            System.out.println("WA ERROR: " + ex.getMessage());
            audit.add("WA failed");
        }

        System.out.println("AUDIT entries=" + audit.size());
    }
}
