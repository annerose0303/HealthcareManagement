package service;

import model.Appointment;
import model.Patient;
import model.Prescription;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving CSV-based data using simple parsing.
 * External CSV IDs are treated as strings; internal numeric IDs are generated.
 */
public class CSVservice {

    private final Path dataDir = Paths.get("data");

    /* ===================== LOAD PATIENTS ===================== */

    public List<Patient> loadPatients(String filename) throws IOException {
        List<Patient> patients = new ArrayList<>();
        int generatedId = 1;

        try (BufferedReader reader = Files.newBufferedReader(dataDir.resolve(filename))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] p = line.split(",", -1);
                if (p.length < 14) continue;

                String name = p[1].trim() + " " + p[2].trim();
                String phone = p[6].trim();
                String email = p[7].trim();
                String nhsNumber = p[4].trim();
                String gpSurgery = p[13].trim();

                patients.add(new Patient(
                        generatedId++,
                        name,
                        email,
                        phone,
                        nhsNumber,
                        gpSurgery
                ));
            }
        }
        return patients;
    }

    /* ===================== LOAD CLINICIANS  ===================== */

    /**
     * Loads clinicians as raw table rows for display-only UI.
     * No GP/Nurse/Specialist objects are created.
     */
    public List<String[]> loadCliniciansTableRows(String filename) throws IOException {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataDir.resolve(filename))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] c = line.split(",", -1);
                if (c.length < 12) continue;

                // clinician_id,first_name,last_name,title,speciality,...,phone,email,...,workplace_type
                String id = c[0].trim();
                String name = c[1].trim() + " " + c[2].trim();
                String title = c[3].trim();
                String speciality = c[4].trim();
                String phone = c[6].trim();
                String email = c[7].trim();
                String workplaceType = c[9].trim();

                rows.add(new String[]{
                        id, title, name, email, phone, speciality, workplaceType
                });
            }
        }
        return rows;
    }

    /* ===================== LOAD APPOINTMENTS ===================== */

    public List<Appointment> loadAppointments(String filename, List<Patient> patients) throws IOException {
        List<Appointment> appointments = new ArrayList<>();
        int generatedAppointmentId = 1;

        if (patients.isEmpty()) {
            return appointments;
        }

        try (BufferedReader reader = Files.newBufferedReader(dataDir.resolve(filename))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] a = line.split(",", -1);
                if (a.length < 13) continue;

                // appointment_date (index 4) + appointment_time (index 5)
                String date = a[4].trim();
                String time = a[5].trim();

                LocalDateTime dateTime;
                try {
                    dateTime = LocalDateTime.parse(date + "T" + time);
                } catch (Exception e) {
                    continue; // skip malformed rows safely
                }

                // status (index 8), reason_for_visit (index 9)
                String status = a[8].trim();
                String reason = a[9].trim();

                // Assign patients safely in a round-robin fashion
                Patient patient = patients.get(
                        (generatedAppointmentId - 1) % patients.size()
                );

                appointments.add(new Appointment(
                        generatedAppointmentId++,
                        patient,
                        dateTime,
                        status,
                        reason
                ));
            }
        }

        return appointments;
    }


    /* ===================== APPEND PRESCRIPTION ===================== */

    public void appendPrescription(String filename, Prescription prescription) throws IOException {
        Files.createDirectories(dataDir);

        try (BufferedWriter writer = Files.newBufferedWriter(
                dataDir.resolve(filename),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        )) {
            writer.newLine();
            writer.write(prescription.toCsvRow());
        }
    }
}
