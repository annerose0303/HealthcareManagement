
package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import model.Patient;



public final class csvUtils {
    private csvUtils() {}

    public static List<Map<String, String>> readAsMaps(Path path) throws IOException {
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
        if (lines.isEmpty()) return List.of();

        List<String> headers = parseLine(lines.get(0));
        List<Map<String, String>> rows = new ArrayList<>();

        for (int i = 1; i < lines.size(); i++) {
            String raw = lines.get(i);
            if (raw == null || raw.trim().isEmpty()) continue;

            List<String> values = parseLine(raw);
            Map<String, String> row = new LinkedHashMap<>();
            for (int c = 0; c < headers.size(); c++) {
                String key = headers.get(c);
                String val = c < values.size() ? values.get(c) : "";
                row.put(key, val);
            }
            rows.add(row);
        }
        return rows;
    }

    public static void writeRows(Path path, List<String> headers, List<List<String>> rows) throws IOException {
        Files.createDirectories(path.getParent() == null ? Path.of(".") : path.getParent());
        try (BufferedWriter w = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            w.write(toCsvLine(headers));
            w.newLine();
            for (List<String> row : rows) {
                w.write(toCsvLine(row));
                w.newLine();
            }
        }
    }

    public static List<String> parseLine(String line) {
        List<String> out = new ArrayList<>();
        if (line == null) return out;

        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Escaped quote
                    cur.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                out.add(cur.toString().trim());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        out.add(cur.toString().trim());
        return out;
    }

    public static String toCsvLine(List<String> values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append(escape(values.get(i)));
        }
        return sb.toString();
    }

    private static String escape(String v) {
        if (v == null) return "";
        boolean needsQuotes = v.contains(",") || v.contains("\"") || v.contains("\n") || v.contains("\r");
        String s = v.replace("\"", "\"\"");
        return needsQuotes ? "\"" + s + "\"" : s;
    }

    public static List<Patient> loadPatients(String path) {
        try {


            List<Map<String, String>> rows = readAsMaps(Paths.get(path));
            List<Patient> patients = new ArrayList<>();

            for (Map<String, String> row : rows) {
                String rawId = row.getOrDefault("patient_id", "0").trim();
                int id = parsePatientId(rawId);


                String first = row.getOrDefault("first_name", "").trim();
                String last = row.getOrDefault("last_name", "").trim();
                String name = (first + " " + last).trim();

                String email = row.getOrDefault("email", "").trim();
                String phone = row.getOrDefault("phone_number", "").trim();
                String nhs = row.getOrDefault("nhs_number", "").trim();
                String gpSurgeryId = row.getOrDefault("gp_surgery_id", "").trim();

                patients.add(new Patient(id, name, email, phone, nhs, gpSurgeryId));
            }

            return patients;

        } catch (Exception e) {
            throw new RuntimeException("Failed to load patients from: " + path, e);
        }
    }

    private static int parsePatientId(String rawId) {
        if (rawId == null || rawId.isBlank()) return 0;

        // Handles formats like "P001", "P12", as well as plain "123"
        String digits = rawId.replaceAll("[^0-9]", "");
        if (digits.isBlank()) return 0;

        return Integer.parseInt(digits);
    }


}
