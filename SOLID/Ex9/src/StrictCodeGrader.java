public class StrictCodeGrader implements CodeGrade {
    public int grade(Submission s, Rubric r) {
        return 60; // stricter fixed score
    }
}