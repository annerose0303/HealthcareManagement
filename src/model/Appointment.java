package model;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentId;
    private Patient patient;
    private LocalDateTime date;
    private String status;
    private String reason;

    public Appointment(int appointmentId, Patient patient,
                       LocalDateTime date, String status, String reason) {
        this.appointmentId = appointmentId;
        this.patient = patient;
        this.date = date;
        this.status = status;
        this.reason = reason;
    }

    public int getAppointmentId() { return appointmentId; }
    public Patient getPatient() { return patient; }
    public LocalDateTime getDate() { return date; }
    public String getStatus() { return status; }
    public String getReason() { return reason; }
}