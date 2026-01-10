package controller;

import model.Appointment;
import model.Patient;
import service.CSVservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppointmentController {

    private final CSVservice csvService;
    private final PatientController patientController;

    private final List<Appointment> appointments = new ArrayList<>();

    private static final String APPOINTMENTS_FILE = "appointments.csv";

    public AppointmentController(CSVservice csvService, PatientController patientController) {
        this.csvService = csvService;
        this.patientController = patientController;
    }

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }

    /**
     * Requires patients loaded first so we can link patient_id -> Patient object.
     */
    public void loadAppointments() throws IOException {
        List<Patient> patients = patientController.getPatients();
        if (patients.isEmpty()) {
            throw new IllegalStateException("Load patients before loading appointments.");
        }
        appointments.clear();
        appointments.addAll(csvService.loadAppointments(APPOINTMENTS_FILE, patients));
    }

    public void addAppointment(Appointment appointment) {
        if (appointment == null) throw new IllegalArgumentException("appointment must not be null");
        appointments.add(appointment);
    }

    public void updateAppointment(int index, Appointment updated) {
        if (updated == null) throw new IllegalArgumentException("updated appointment must not be null");
        if (index < 0 || index >= appointments.size()) throw new IndexOutOfBoundsException("Invalid appointment index");
        appointments.set(index, updated);
    }

    public void deleteAppointment(int index) {
        if (index < 0 || index >= appointments.size()) throw new IndexOutOfBoundsException("Invalid appointment index");
        appointments.remove(index);
    }

    public int nextAppointmentId() {
        return appointments.stream().mapToInt(Appointment::getAppointmentId).max().orElse(0) + 1;
    }
}
