import java.util.*;

// EligibilityEngine
class EligibilityEngine {
    private final FakeEligibilityStore store;
    private final List<EligibilityRule> rules;

    public EligibilityEngine(FakeEligibilityStore store, List<EligibilityRule> rules) {
        this.store = store;
        this.rules = rules;
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        String status = "ELIGIBLE";

        // Checks for all the rules in the classes implementing the EligibilityRule interface
        for (EligibilityRule rule : rules) {
            if (!rule.check(s)) {
                status = "NOT_ELIGIBLE";
                reasons.add(rule.reason());
            }
        }

        return new EligibilityEngineResult(status, reasons);
    }
}

// EligibilityEngineResult
class EligibilityEngineResult {
    // Checks whether eleigible or not eligible 
    public final String status;      
    public final List<String> reasons;

    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}

// EligibilityRule
interface EligibilityRule {
    // Returns true if eligible
    boolean check(StudentProfile s);   
    // Gives reason if not eligible
    String reason();                    
}

// FakeEligibilityStore
class FakeEligibilityStore {
    public void save(String roll, String status) {
        System.out.println("Saved evaluation for roll=" + roll);
    }
}

// LegacyFlags
class LegacyFlags {
    public static final int NONE = 0;
    public static final int WARNING = 1;
    public static final int SUSPENDED = 2;

    public static String nameOf(int f) {
        return switch (f) {
            case WARNING -> "WARNING";
            case SUSPENDED -> "SUSPENDED";
            default -> "NONE";
        };
    }
}

// Numbers
class Numbers {
    public static double clamp(double x, double a, double b) {
        return Math.max(a, Math.min(b, x));
    }
}

// ReportPrinter
class ReportPrinter {
    public void print(StudentProfile s, EligibilityEngineResult r) {
        System.out.println("Student: " + s.name + " (CGR=" + String.format("%.2f", s.cgr)
                + ", attendance=" + s.attendancePct + ", credits=" + s.earnedCredits
                + ", flag=" + LegacyFlags.nameOf(s.disciplinaryFlag) + ")");
        System.out.println("RESULT: " + r.status);
        for (String reason : r.reasons) System.out.println("- " + reason);
        if (r.reasons.isEmpty() && "ELIGIBLE".equals(r.status)) {
            // keep behavior stable even if empty
        }
    }
}

// All classes implement the EligibilityRule interface to enforce their own rules.
// RuleAttendance
class RuleAttendance implements EligibilityRule{
    private final int minAttendance;
    public RuleAttendance(int minAttendance) { this.minAttendance = minAttendance; }

    @Override
    public boolean check(StudentProfile s) {
        return s.attendancePct >= minAttendance;
    }

    @Override
    public String reason() {
        return "attendance below " + minAttendance;
    }
}

// RuleCgr
class RuleCgr implements EligibilityRule{
    private final double minCgr;
    public RuleCgr(double minCgr) { this.minCgr = minCgr; }

    @Override
    public boolean check(StudentProfile s) {
        return s.cgr >= minCgr;
    }

    @Override
    public String reason() {
        return "CGR below " + minCgr;
    }
}

// RuleCredit
class RuleCredit implements EligibilityRule {
    private final int minCredits;
    public RuleCredit(int minCredits) { this.minCredits = minCredits; }

    @Override
    public boolean check(StudentProfile s) {
        return s.earnedCredits >= minCredits;
    }

    @Override
    public String reason() {
        return "credits below " + minCredits;
    }
}

// RuleDisciplinary
class RuleDisciplinary implements EligibilityRule {

    @Override
    public boolean check(StudentProfile s) {
        return s.disciplinaryFlag == LegacyFlags.NONE;
    }

    @Override
    public String reason() {
        return "disciplinary flag present";
    }
}

// RuleInput
class RuleInput {
    public double minCgr = 8.0;
    public int minAttendance = 75;
    public int minCredits = 20;
}

// StudentProfile
class StudentProfile {
    public final String rollNo;
    public final String name;
    public final double cgr;
    public final int attendancePct;
    public final int earnedCredits;
    public final int disciplinaryFlag;

    public StudentProfile(String rollNo, String name, double cgr, int attendancePct, int earnedCredits, int disciplinaryFlag) {
        this.rollNo = rollNo; this.name = name; this.cgr = cgr;
        this.attendancePct = attendancePct; this.earnedCredits = earnedCredits;
        this.disciplinaryFlag = disciplinaryFlag;
    }
}

public class Demo03 {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");

        StudentProfile s = new StudentProfile(
                "23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE
        );

        List<EligibilityRule> rules = Arrays.asList(
                new RuleDisciplinary(),
                new RuleCgr(8.0),
                new RuleAttendance(75),
                new RuleCredit(20)
        );

        EligibilityEngine engine = new EligibilityEngine(new FakeEligibilityStore(), rules);
        engine.runAndPrint(s);
    }
}