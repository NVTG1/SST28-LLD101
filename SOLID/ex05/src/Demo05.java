// Exporter

import java.nio.charset.StandardCharsets;

abstract class Exporter {
    // implied "contract" but not enforced (smell)
    /* Contract: 
    -- request must not be null
    -- Always a valid ExportResult object should be returned*/

    // Changed abstract to final so that contract cannot be changed
    // So that client can safely call export() without knowing its subclasses
    public final ExportResult export(ExportRequest req){
        // request shouldn't be null
        if(req == null){
            throw new IllegalArgumentException("ExportRequest cannot be null");
        }

        return doesExport(req);
    }
    // Subclasses will implement this method only
    // Converts ExportRequest to ExportResult.
    protected abstract ExportResult doesExport(ExportRequest req);
}

// ExportRequest
class ExportRequest {
    public final String title;
    public final String body;

    public ExportRequest(String title, String body) {
        this.title = title;
        this.body = body;
    }
}

// ExportResult
class ExportResult {
    public final String contentType;
    public final byte[] bytes;

    public ExportResult(String contentType, byte[] bytes) {
        this.contentType = contentType;
        this.bytes = bytes;
    }
}

// CsvExporter
class CsvExporter extends Exporter {
    @Override
    public ExportResult doesExport(ExportRequest req) {
        String body = req.body == null ? "" : req.body.replace("\n", " ").replace(",", " ");
        String csv = "title,body\n" + req.title + "," + body + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}

// JsonExporter
class JsonExporter extends Exporter {
    @Override
    public ExportResult doesExport(ExportRequest req) {
        if (req == null) throw new IllegalArgumentException("ExportRequest cannot be null");

        String json = "{\"title\":\"" + escape(req.title) +
                      "\",\"body\":\"" + escape(req.body) + "\"}";

        return new ExportResult("application/json",
                json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}

// PdfExporter
class PdfExporter extends Exporter {

    @Override
    protected ExportResult doesExport(ExportRequest req) {
        if (req.body != null && req.body.length() > 20) {
            throw new IllegalArgumentException("PDF cannot handle content > 20 chars");
        }

        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf",
                fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}

// SampleData
class SampleData {
    public static String longBody() {
        return "Name,Score\nAyaan,82\nRiya,91\n";
    }
}

public class Demo05 {
    public static void main(String[] args) {
        System.out.println("=== Export Demo ===");

        ExportRequest req = new ExportRequest("Weekly Report", SampleData.longBody());
        Exporter pdf = new PdfExporter();
        Exporter csv = new CsvExporter();
        Exporter json = new JsonExporter();

        System.out.println("PDF: " + safe(pdf, req));
        System.out.println("CSV: " + safe(csv, req));
        System.out.println("JSON: " + safe(json, req));
    }

    private static String safe(Exporter e, ExportRequest r) {
        try {
            ExportResult out = e.export(r);
            return "OK bytes=" + out.bytes.length;
        } catch (RuntimeException ex) {
            return "ERROR: " + ex.getMessage();
        }
    }
}
