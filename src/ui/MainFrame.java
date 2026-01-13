package ui;

import controller.ClinicianController;
import controller.PatientController;
import controller.PrescriptionController;
import service.CSVservice;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        super("Healthcare Management System");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        CSVservice  csvService = new CSVservice();

        PatientController patientController = new PatientController(csvService);
        ClinicianController clinicianController = new ClinicianController(csvService);
        PrescriptionController prescriptionController = new PrescriptionController(csvService);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Patients", new PatientPanel(patientController));
        tabs.addTab("Clinicians", new ClinicianPanel(clinicianController));
        tabs.addTab("Prescriptions", new PrescriptionPanel(prescriptionController, patientController));

        add(tabs, BorderLayout.CENTER);

        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}



