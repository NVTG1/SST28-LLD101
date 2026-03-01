public class EvaluationPipeline {
    private final PlagiarismCheck plagiarismChecker;
    private final CodeGrade codeGrader;
    private final ReportWrite reportWriter;
    private final Rubric rubric;

    // Dependencies Injected
    // Now references are called of instances instead of objects of direct concrete classes
    // Object creation moved out of Evaluation Pipeline
    public EvaluationPipeline(
            PlagiarismCheck plagiarismChecker,
            CodeGrade codeGrader,
            ReportWrite reportWriter,
            Rubric rubric) {

        this.plagiarismChecker = plagiarismChecker;
        this.codeGrader = codeGrader;
        this.reportWriter = reportWriter;
        this.rubric = rubric;
    }

    public void evaluate(Submission sub) {

        int plag = plagiarismChecker.check(sub);
        System.out.println("PlagiarismScore=" + plag);

        int code = codeGrader.grade(sub, rubric);
        System.out.println("CodeScore=" + code);

        String reportName = reportWriter.write(sub, plag, code);
        System.out.println("Report written: " + reportName);

        int total = plag + code;
        String result = (total >= 90) ? "PASS" : "FAIL";
        System.out.println("FINAL: " + result + " (total=" + total + ")");
    }
}
