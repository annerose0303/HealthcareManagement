package model;

import java.time.LocalDateTime;

public class Referral {

    private int referralId;
    private Patient patient;
    private String specialty;
    private String urgency;
    private String clinicalSummary;
    private String status;

    public Referral(int referralId, Patient patient,
                    String specialty, String urgency, String clinicalSummary) {
        this.referralId = referralId;
        this.patient = patient;
        this.specialty = specialty;
        this.urgency = urgency;
        this.clinicalSummary = clinicalSummary;
        this.status = "Created";
    }

    // ===================== GETTERS =====================

    public int getReferralId() {
        return referralId;
    }

    public Patient getPatient() {
        return patient;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getClinicalSummary() {
        return clinicalSummary;
    }

    public String getStatus() {
        return status;
    }

    // ===================== REFERRAL TEXT =====================

    /**
     * Generates human-readable referral text to simulate email output.
     * This text is persisted by the Singleton ReferralService.
     */
    public String createReferralText(String suggestedRecipient) {
        StringBuilder sb = new StringBuilder();

        sb.append("========== REFERRAL ==========\n");
        sb.append("Generated: ").append(LocalDateTime.now()).append("\n");
        sb.append("Referral ID: ").append(referralId).append("\n");
        sb.append("Urgency: ").append(urgency).append("\n");
        sb.append("Specialty: ").append(specialty).append("\n");
        sb.append("Suggested Recipient: ").append(suggestedRecipient).append("\n\n");

        sb.append("Patient Details\n");
        sb.append("Name: ").append(patient.getName()).append("\n");
        sb.append("NHS Number: ").append(patient.getNhsNumber()).append("\n");
        sb.append("Phone: ").append(patient.getPhone()).append("\n");
        sb.append("Email: ").append(patient.getEmail()).append("\n");
        sb.append("GP Surgery: ").append(patient.getGpSurgery()).append("\n\n");

        sb.append("Clinical Summary\n");
        sb.append(clinicalSummary).append("\n\n");

        sb.append("Status: ").append(status).append("\n");
        sb.append("========== END REFERRAL ==========\n\n");

        return sb.toString();
    }
}
