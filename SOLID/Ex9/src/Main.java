public class Main {
    public static void main(String[] args) {
        // Main responsible for creating objects
        // Evaluation Pipeline uses them
        // This is Dependency Injection
        System.out.println("=== Evaluation Pipeline ===");

        Submission sub = new Submission("23BCS1007", "public class A{}", "A.java");

        // Creating Dependencies
        // We dont care about how the code is checking plagiarism we know it checks
        PlagiarismCheck pc = new PlagiarismChecker();
        CodeGrade grader = new CodeGrader();
        // CodeGrade grader = new StrictCodeGrader();
        ReportWrite writer = new ReportWriter();
        Rubric rubric = new Rubric();

        // Creating object of Evaluation Pipeline
        EvaluationPipeline pipeline =
            new EvaluationPipeline(pc, grader, writer, rubric);

        pipeline.evaluate(sub);
    }
}