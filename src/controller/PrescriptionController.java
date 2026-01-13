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

    /**
     * Prescriptions are created via UI and appended to CSV.
     * Existing CSV prescriptions are not reloaded (coursework-safe).
     */
    public void loadPrescriptions() {
        // display prescriptions created during runtime
    }

    public void addPrescriptionAndPersist(Prescription prescription) throws IOException {
        prescriptions.add(prescription);
        csvService.appendPrescription(PRESCRIPTIONS_FILE, prescription);
    }

    public int nextPrescriptionId() {
        return prescriptions.stream()
                .mapToInt(Prescription::getPrescriptionId)
                .max()
                .orElse(0) + 1;
    }
}
