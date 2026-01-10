package controller;

import model.Patient;
import service.CSVservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatientController {

    private final CSVservice csvService;
    private final List<Patient> patients = new ArrayList<>();

    private static final String PATIENTS_FILE = "patients.csv";

    public PatientController(CSVservice csvService) {
        this.csvService = csvService;
    }

    public List<Patient> getPatients() {
        return Collections.unmodifiableList(patients);
    }

    public void loadPatients() throws IOException {
        patients.clear();
        patients.addAll(csvService.loadPatients(PATIENTS_FILE));
    }

    public void addPatient(Patient patient) {
        if (patient == null) throw new IllegalArgumentException("patient must not be null");
        patients.add(patient);
    }

    public void updatePatient(int index, Patient updated) {
        if (updated == null) throw new IllegalArgumentException("updated patient must not be null");
        if (index < 0 || index >= patients.size()) throw new IndexOutOfBoundsException("Invalid patient index");
        patients.set(index, updated);
    }

    public void deletePatient(int index) {
        if (index < 0 || index >= patients.size()) throw new IndexOutOfBoundsException("Invalid patient index");
        patients.remove(index);
    }

    public int nextPatientId() {
        return patients.stream().mapToInt(Patient::getUserId).max().orElse(0) + 1;
    }
}
