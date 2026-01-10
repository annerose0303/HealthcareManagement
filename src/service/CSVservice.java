package service;

import model.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving CSV-based data using simple parsing.
 * Only required fields are mapped; unused columns are ignored intentionally.
 */
public class CSVservice {

    private final Path dataDir = Paths.get("data");

    /* ===================== LOAD PATIENTS ===================== */

    public List<Patient> loadPatients(String filename) throws IOException {
        List<Patient> patients = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataDir.resolve(filename))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");

                int id = Integer.parseInt(p[0].trim());
                String name = p[1].trim() + " " + p[2].trim();
                String phone = p[6].trim();
                String email = p[7].trim();
                String nhsNumber = p[4].trim();
                String gpSurgery = p[13].trim();

                patients.add(new Patient(id, name, email, phone, nhsNumber, gpSurgery));
            }
        }
        return patients;
    }

    /* ===================== LOAD CLINICIANS ===================== */

    public List<User> loadClinicians(String filename) throws IOException {
        List<User> clinicians = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataDir.resolve(filename))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] c = line.split(",");

                int id = Integer.parseInt(c[0].trim());
                String name = c[1].trim() + " " + c[2].trim();
                String role = c[3].trim().toLowerCase();
                String specialty = c[4].trim();
                String email = c[7].trim();

                if (role.contains("gp") || role.contains("doctor")) {
                    clinicians.add(new GP(id, name, email));
                } else if (role.contains("nurse")) {
                    clinicians.add(new Nurse(id, name, email));
                } else {
                    clinicians.add(new Specialist(id, name, email, specialty));
                }
            }
        }
        return clinicians;
    }

    /* ===================== LOAD APPOINTMENTS ===================== */

    public List<Appointment> loadAppointments(String filename, List<Patient> patients) throws IOException {
        List<Appointment> appointments = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataDir.resolve(filename))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] a = line.split(",");

                int id = Integer.parseInt(a[0].trim());
                int patientId = Integer.parseInt(a[1].trim());

                LocalDateTime dateTime = LocalDateTime.parse(
                        a[4].trim() + "T" + a[5].trim()
                );

                String status = a[8].trim();
                String reason = a[9].trim();

                Patient patient = patients.stream()
                        .filter(p -> p.getUserId() == patientId)
                        .findFirst()
                        .orElse(null);

                if (patient != null) {
                    appointments.add(new Appointment(id, patient, dateTime, status, reason));
                }
            }
        }
        return appointments;
    }

    /* ===================== LOAD PRESCRIPTIONS ===================== */

    public List<Prescription> loadPrescriptions(String filename) throws IOException {
        List<Prescription> prescriptions = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(dataDir.resolve(filename))) {
            reader.readLine(); // skip header
            String line;

            while ((line = reader.readLine()) != null) {
                String[] p = line.split(",");

                int prescriptionId = Integer.parseInt(p[0].trim());
                int patientId = Integer.parseInt(p[1].trim());
                int clinicianId = Integer.parseInt(p[2].trim());
                int appointmentId = Integer.parseInt(p[3].trim());

                String prescriptionDate = p[4].trim();
                String medicationName = p[5].trim();
                String dosage = p[6].trim();
                String frequency = p[7].trim();

                int durationDays = Integer.parseInt(p[8].trim());
                int quantity = Integer.parseInt(p[9].trim());

                String instructions = p[10].trim();
                String pharmacyName = p[11].trim();
                String status = p[12].trim();
                String issueDate = p[13].trim();
                String collectionDate = p[14].trim();

                prescriptions.add(new Prescription(
                        prescriptionId,
                        patientId,
                        clinicianId,
                        appointmentId,
                        prescriptionDate,
                        medicationName,
                        dosage,
                        frequency,
                        durationDays,
                        quantity,
                        instructions,
                        pharmacyName,
                        status,
                        issueDate,
                        collectionDate
                ));
            }
        }
        return prescriptions;
    }

    /* ===================== APPEND PRESCRIPTION ===================== */

    public void appendPrescription(String filename, Prescription prescription) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                dataDir.resolve(filename),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            writer.newLine();
            writer.write(prescription.toCsvRow());
        }
    } }
