package controller;

import service.CSVservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClinicianController {

    private final CSVservice csvService;
    private final List<String[]> cliniciansTableRows = new ArrayList<>();

    private static final String CLINICIANS_FILE = "clinicians.csv";

    public ClinicianController(CSVservice csvService) {
        this.csvService = csvService;
    }

    public List<String[]> getCliniciansTableRows() {
        return Collections.unmodifiableList(cliniciansTableRows);
    }

    public void loadCliniciansTableRows() throws IOException {
        cliniciansTableRows.clear();
        cliniciansTableRows.addAll(csvService.loadCliniciansTableRows(CLINICIANS_FILE));
    }
}
