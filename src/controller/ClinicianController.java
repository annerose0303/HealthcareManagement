package controller;

import model.User;
import service.CSVservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClinicianController {

    private final CSVservice csvService;
    private final List<User> clinicians = new ArrayList<>();

    private static final String CLINICIANS_FILE = "clinicians.csv";

    public ClinicianController(CSVservice csvService) {
        this.csvService = csvService;
    }

    public List<User> getClinicians() {
        return Collections.unmodifiableList(clinicians);
    }

    public void loadClinicians() throws IOException {
        clinicians.clear();
        clinicians.addAll(csvService.loadClinicians(CLINICIANS_FILE));
    }

    public void addClinician(User clinician) {
        if (clinician == null) throw new IllegalArgumentException("clinician must not be null");
        clinicians.add(clinician);
    }

    public void updateClinician(int index, User updated) {
        if (updated == null) throw new IllegalArgumentException("updated clinician must not be null");
        if (index < 0 || index >= clinicians.size()) throw new IndexOutOfBoundsException("Invalid clinician index");
        clinicians.set(index, updated);
    }

    public void deleteClinician(int index) {
        if (index < 0 || index >= clinicians.size()) throw new IndexOutOfBoundsException("Invalid clinician index");
        clinicians.remove(index);
    }
}
