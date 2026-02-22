import java.util.*;

// ConsoleInput
class ConsoleInput {
    public String readLine() {
        return "";
    }
}

// FakeDbInterface
interface FakeDbInterface {
    void save(StudentRecord r);
    int count();
    List<StudentRecord> all();
}

// FakeDb
class FakeDb implements FakeDbInterface{
    private final List<StudentRecord> rows = new ArrayList<>();

    public void save(StudentRecord r) { rows.add(r); }
    public int count() { return rows.size(); }
    public List<StudentRecord> all() { return Collections.unmodifiableList(rows); }
}

// IdUtil
class IdUtil {
    public static String nextStudentId(int currentCount) {
        int next = currentCount + 1;
        String num = String.format("%04d", next);
        return "SST-2026-" + num;
    }
}

// StudentRecord
class StudentRecord {
    public final String id;
    public final String name;
    public final String email;
    public final String phone;
    public final String program;

    public StudentRecord(String id, String name, String email, String phone, String program) {
        this.id = id; this.name = name; this.email = email; this.phone = phone; this.program = program;
    }

    @Override
    public String toString() {
        return "StudentRecord{id='" + id + "', name='" + name + "', email='" + email + "', phone='" + phone + "', program='" + program + "'}";
    }
}

// TextTable
class TextTable {
    // Now interface instead of class fakeDb
    public static String render3(FakeDbInterface db) {
        StringBuilder sb = new StringBuilder();
        sb.append("| ID             | NAME | PROGRAM |\n");
        for (StudentRecord r : db.all()) {
            sb.append(String.format("| %-14s | %-4s | %-7s |\n", r.id, r.name, r.program));
        }
        return sb.toString();
    }
}

// Parser
class Parser {
    public Map<String, String> parse(String raw){
        Map<String,String> kv = new LinkedHashMap<>();
        String[] parts = raw.split(";");
        for (String p : parts) {
            String[] t = p.split("=", 2);
            if (t.length == 2) kv.put(t[0].trim(), t[1].trim());
        }
        return kv;
    }
}

// ValidateStudent
class ValidateStudent {
    public List<String> validate(Map<String, String> kv){
        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        // validation inline, printing inline
        List<String> errors = new ArrayList<>();
        if (name.isBlank()) errors.add("name is required");
        if (email.isBlank() || !email.contains("@")) errors.add("email is invalid");
        if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))) errors.add("program is invalid");

        return errors;
    }
}

// PrintStudentDetails
class PrintStudentDetails {
    public void printDetails(StudentRecord record, int count){
        System.out.println("OK: created student " + record.id);
        System.out.println("Saved. Total students: " + count);
        System.out.println("CONFIRMATION:");
        System.out.println(record);
    }

    public void printErrors(List<String> err){
        System.out.println("ERROR: cannot register");
        for (String e : err){
            System.out.println("- " + e);
        }
    }

    public void printTable(FakeDbInterface repo){

        System.out.println("-- DB DUMP --");

        FakeDb db = (FakeDb) repo;

        System.out.print(TextTable.render3(db));
    }
}

// OnboardingService
class OnboardingService {

    private final FakeDbInterface db;
    private final Parser parser = new Parser();
    private final ValidateStudent validator = new ValidateStudent();
    private final PrintStudentDetails printer = new PrintStudentDetails();

    // Interface instead of FakeDb class directly
    public OnboardingService(FakeDbInterface db) {
        this.db = db;
    }

    // Using new classes to parse and validate the string provided
    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        Map<String,String> kv = parser.parse(raw);

        List<String> errors = validator.validate(kv);

        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(db.count());

        StudentRecord rec = new StudentRecord(
                id,
                kv.get("name"),
                kv.get("email"),
                kv.get("phone"),
                kv.get("program")
        );

        db.save(rec);

        // Calling another class method for printing
        printer.printDetails(rec, db.count());
    }
}

public class Demo01 {
    public static void main(String[] args) {
        System.out.println("=== Student Onboarding ===");
        // Making reference of interface instead
        FakeDbInterface db = new FakeDb();
        OnboardingService svc = new OnboardingService(db);

        String raw = "name=Riya;email=riya@sst.edu;phone=9876543210;program=CSE";
        svc.registerFromRawInput(raw);

        System.out.println();
        new PrintStudentDetails().printTable(db);
    }
}