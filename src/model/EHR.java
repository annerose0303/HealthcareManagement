package model;

import java.util.ArrayList;
import java.util.List;

public class EHR {

    private Patient patient;
    private List<String> records = new ArrayList<>();

    public EHR(Patient patient) {
        this.patient = patient;
    }

    public void addRecord(String record) {
        records.add(record);
    }

    public List<String> getRecords() {
        return records;
    }
}
