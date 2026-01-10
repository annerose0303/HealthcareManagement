package model;

import java.time.LocalDate;

public class Prescription {

    private final int prescriptionId;
    private final int patientId;
    private final int clinicianId;
    private final int appointmentId;

    private final String prescriptionDate;   // keep as String for CSV simplicity
    private final String medicationName;
    private final String dosage;
    private final String frequency;

    private final int durationDays;
    private final int quantity;

    private final String instructions;
    private final String pharmacyName;
    private final String status;
    private final String issueDate;
    private final String collectionDate;

    /**
     * Full constructor matching prescriptions.csv columns (15 fields).
     * Use this when loading rows directly from the CSV file.
     */
    public Prescription(
            int prescriptionId,
            int patientId,
            int clinicianId,
            int appointmentId,
            String prescriptionDate,
            String medicationName,
            String dosage,
            String frequency,
            int durationDays,
            int quantity,
            String instructions,
            String pharmacyName,
            String status,
            String issueDate,
            String collectionDate
    ) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.appointmentId = appointmentId;
        this.prescriptionDate = nullToEmpty(prescriptionDate);
        this.medicationName = nullToEmpty(medicationName);
        this.dosage = nullToEmpty(dosage);
        this.frequency = nullToEmpty(frequency);
        this.durationDays = durationDays;
        this.quantity = quantity;
        this.instructions = nullToEmpty(instructions);
        this.pharmacyName = nullToEmpty(pharmacyName);
        this.status = nullToEmpty(status);
        this.issueDate = nullToEmpty(issueDate);
        this.collectionDate = nullToEmpty(collectionDate);
    }

    /**
     * Simplified constructor for UI-created prescriptions.
     * Keeps CSV fields consistent, fills non-essential columns safely.
     */
    public Prescription(
            int prescriptionId,
            int patientId,
            int clinicianId,
            int appointmentId,
            String medicationName,
            String dosage,
            String frequency,
            int durationDays,
            int quantity,
            String instructions,
            String pharmacyName
    ) {
        this(
                prescriptionId,
                patientId,
                clinicianId,
                appointmentId,
                LocalDate.now().toString(),                 // prescriptionDate
                medicationName,
                dosage,
                frequency,
                durationDays,
                quantity,
                instructions,
                pharmacyName,
                "ACTIVE",                                   // status default
                LocalDate.now().toString(),                 // issueDate default
                ""                                          // collectionDate default
        );
    }

    private static String nullToEmpty(String s) {
        return s == null ? "" : s.trim();
    }

    public int getPrescriptionId() { return prescriptionId; }
    public int getPatientId() { return patientId; }
    public int getClinicianId() { return clinicianId; }
    public int getAppointmentId() { return appointmentId; }

    public String getPrescriptionDate() { return prescriptionDate; }
    public String getMedicationName() { return medicationName; }
    public String getDosage() { return dosage; }
    public String getFrequency() { return frequency; }

    public int getDurationDays() { return durationDays; }
    public int getQuantity() { return quantity; }

    public String getInstructions() { return instructions; }
    public String getPharmacyName() { return pharmacyName; }
    public String getStatus() { return status; }
    public String getIssueDate() { return issueDate; }
    public String getCollectionDate() { return collectionDate; }

    /** Header matching your prescriptions.csv (spelling corrected to prescription_id). */
    public static String csvHeader() {
        return "prescription_id,patient_id,clinician_id,appointment_id,prescription_date,medication_name," +
                "dosage,frequency,duration_days,quantity,instructions,pharmacy_name,status,issue_date,collection_date";
    }

    /** Produces one CSV row in the same column order as the header. */
    public String toCsvRow() {
        return prescriptionId + "," +
                patientId + "," +
                clinicianId + "," +
                appointmentId + "," +
                prescriptionDate + "," +
                medicationName + "," +
                dosage + "," +
                frequency + "," +
                durationDays + "," +
                quantity + "," +
                instructions + "," +
                pharmacyName + "," +
                status + "," +
                issueDate + "," +
                collectionDate;
    }
}
