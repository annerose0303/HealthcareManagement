package model;

public class Drug {

    private int drugId;
    private String name;
    private String dosage;
    private String frequency;

    public Drug(int drugId, String name, String dosage, String frequency) {
        this.drugId = drugId;
        this.name = name;
        this.dosage = dosage;
        this.frequency = frequency;
    }

    public String getName() { return name; }
}
