package model;


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
}
