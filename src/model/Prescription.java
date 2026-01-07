package model;

import java.time.LocalDate;

public class Prescription {

    private int prescriptionId;
    private Patient patient;
    private Condition condition;
    private Drug drug;
    private LocalDate dateIssued;

    public Prescription(int prescriptionId, Patient patient,
                        Condition condition, Drug drug, LocalDate dateIssued) {
        this.prescriptionId = prescriptionId;
        this.patient = patient;
        this.condition = condition;
        this.drug = drug;
        this.dateIssued = dateIssued;
    }

    public Patient getPatient() { return patient; }
    public Drug getDrug() { return drug; }
    public LocalDate getDateIssued() {
        return dateIssued;
    }
    public Condition getCondition() {
        return condition;
    }

    public int getPrescriptionId() {
        return prescriptionId;
    }

}
