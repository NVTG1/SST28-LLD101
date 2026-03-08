package com.example.reports;

/**
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - Viewer depends directly on concrete ReportFile
 * - No Proxy involved
 */
public class ReportViewer {

    // Report instead of ReportFile (No direct connection from concrete class but through an abstraction)
    public void open(Report report, User user) {
        report.display(user);
    }
}
