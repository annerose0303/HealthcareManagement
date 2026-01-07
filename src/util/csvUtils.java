
package util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

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
}
