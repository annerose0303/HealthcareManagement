package ui;

import controller.*;
import service.CSVservice;

import javax.swing.*;
import java.awt.*;
import controller.AppointmentController;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Healthcare Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        CSVservice csvService = new CSVservice();

        PatientController patientController = new PatientController(csvService);
        ClinicianController clinicianController = new ClinicianController(csvService);
        PrescriptionController prescriptionController = new PrescriptionController(csvService);
        ReferralController referralController = new ReferralController();
        AppointmentController appointmentController = new AppointmentController(csvService, patientController);


        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Patients", new PatientPanel(patientController));
        tabs.addTab("Clinicians", new ClinicianPanel(clinicianController));
        tabs.addTab("Prescriptions", new PrescriptionPanel(prescriptionController, patientController));
        tabs.addTab("Referrals", new ReferralPanel(referralController, patientController));
        tabs.addTab("Appointments", new AppointmentPanel(appointmentController, patientController));
        add(tabs, BorderLayout.CENTER);

        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}



