package model;

public class Specialist extends User{

    private String specialty;

    public Specialist(int userId, String name, String email, String specialty) {
        super(userId, name, email, "Specialist");
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }

}
