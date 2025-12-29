package model;

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

    public Patient getPatient() { return patient; }
    public String getUrgency() { return urgency; }
    public String getClinicalSummary() { return clinicalSummary; }
}
