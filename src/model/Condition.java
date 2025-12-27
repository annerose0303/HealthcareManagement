package model;

public class Condition {
    private int conditionId;
    private String name;
    private String description;

    public Condition(int conditionId, String name, String description) {
        this.conditionId = conditionId;
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
}

