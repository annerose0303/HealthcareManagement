package model;

public class Patient extends User {

    private String phone;
    private String nhsNumber;
    private String gpSurgery;

    public Patient(int userId, String name, String email, String phone,
                   String nhsNumber, String gpSurgery) {
        super(userId, name, email, "Patient");
        this.phone = phone;
        this.nhsNumber = nhsNumber;
        this.gpSurgery = gpSurgery;
    }

    public String getPhone() { return phone; }
    public String getNhsNumber() { return nhsNumber; }
    public String getGpSurgery() { return gpSurgery; }

}
