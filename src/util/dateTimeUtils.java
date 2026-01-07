
package util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class dateTimeUtils {
    private dateTimeUtils() {}

    //  common formats used in CSVs
    private static final DateTimeFormatter[] DT_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    };

    private static final DateTimeFormatter[] D_FORMATS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE,
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
    };

    public static LocalDateTime parseDateTime(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;

        for (DateTimeFormatter f : DT_FORMATS) {
            try { return LocalDateTime.parse(s, f); } catch (DateTimeParseException ignored) {}
        }

        LocalDate d = parseDate(s);
        return d == null ? null : d.atStartOfDay();
    }

    public static LocalDate parseDate(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;

        for (DateTimeFormatter f : D_FORMATS) {
            try { return LocalDate.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        return null;
    }
}
