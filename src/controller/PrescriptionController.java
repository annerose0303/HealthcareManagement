package controller;

import model.Prescription;
import service.CSVservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrescriptionController {

    private final CSVservice csvService;

    // CSV-loaded rows (display-only)
    private final List<String[]> csvRows = new ArrayList<>();

    // UI-created prescriptions (editable/in-memory)
    private final List<Prescription> created = new ArrayList<>();

    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";

    public PrescriptionController(CSVservice csvService) {
        this.csvService = csvService;
    }

    public List<String[]> getCsvRows() {
        return Collections.unmodifiableList(csvRows);
    }

    public List<Prescription> getCreatedPrescriptions() {
        return Collections.unmodifiableList(created);
    }

    public void loadPrescriptions() throws IOException {
        csvRows.clear();
        csvRows.addAll(csvService.loadPrescriptionsTableRows(PRESCRIPTIONS_FILE));
    }

    public void addPrescriptionAndPersist(Prescription p) throws IOException {
        created.add(p);
        csvService.appendPrescription(PRESCRIPTIONS_FILE, p);
    }

    public int nextPrescriptionId() {
        // Only for UI-created numeric IDs
        return created.stream().mapToInt(Prescription::getPrescriptionId).max().orElse(0) + 1;
    }
}
