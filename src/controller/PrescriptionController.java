package controller;

import model.Prescription;
import service.CSVservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrescriptionController {

    private final CSVservice csvService;
    private final List<Prescription> prescriptions = new ArrayList<>();

    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";

    public PrescriptionController(CSVservice csvService) {
        this.csvService = csvService;
    }

    public List<Prescription> getPrescriptions() {
        return Collections.unmodifiableList(prescriptions);
    }

    public void loadPrescriptions() throws IOException {
        prescriptions.clear();
        prescriptions.addAll(csvService.loadPrescriptions(PRESCRIPTIONS_FILE));
    }

    /**
     * Adds to in-memory list AND appends to prescriptions.csv for coursework evidence.
     */
    public void addPrescriptionAndPersist(Prescription prescription) throws IOException {
        if (prescription == null) throw new IllegalArgumentException("prescription must not be null");
        prescriptions.add(prescription);
        csvService.appendPrescription(PRESCRIPTIONS_FILE, prescription);
    }

    public void updatePrescription(int index, Prescription updated) {
        if (updated == null) throw new IllegalArgumentException("updated prescription must not be null");
        if (index < 0 || index >= prescriptions.size()) throw new IndexOutOfBoundsException("Invalid prescription index");
        prescriptions.set(index, updated);
        // Note: we do NOT rewrite the CSV here (simple coursework constraint).
    }

    public void deletePrescription(int index) {
        if (index < 0 || index >= prescriptions.size()) throw new IndexOutOfBoundsException("Invalid prescription index");
        prescriptions.remove(index);
        // Note: we do NOT rewrite the CSV here (simple coursework constraint).
    }

    public int nextPrescriptionId() {
        return prescriptions.stream().mapToInt(Prescription::getPrescriptionId).max().orElse(0) + 1;
    }
}

