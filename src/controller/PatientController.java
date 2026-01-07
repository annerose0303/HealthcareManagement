package controller;

import model.Patient;
import util.csvUtils;

import java.util.ArrayList;
import java.util.List;

public class PatientController {

    private final List<Patient> patients = new ArrayList<>();

    public void loadPatients(String path) {
        patients.clear();
        patients.addAll(csvUtils.loadPatients(path));
    }

    public List<Patient> getPatients() {
        return new ArrayList<>(patients);
    }

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void deletePatientByIndex(int index) {
        if (index >= 0 && index < patients.size()) {
            patients.remove(index);
        }
    }
}

