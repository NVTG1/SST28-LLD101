package com.example.reports;

/**
 * TODO (student):
 * Implement Proxy responsibilities here:
 * - access check
 * - lazy loading
 * - caching of RealReport within the same proxy
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;

    private RealReport realReport; // cached instance

    private final AccessControl accessControl = new AccessControl();

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        // Starter placeholder: intentionally incorrect.
        // Students should remove direct real loading on every call.
        // RealReport report = new RealReport(reportId, title, classification);
        // report.display(user);

        // Checks whether the client can access the report or not (Access Control)
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("ACCESS DENIED for " + user.getName()
                    + " to report " + reportId);
            return;
        }

        // Checking if ever object is created or not (Lazy Loading)
        if (realReport == null) {
            System.out.println("[proxy] creating RealReport...");
            realReport = new RealReport(reportId, title, classification);
        }

        // Reusing the cached realReport object only 
        realReport.display(user);
    }
}
