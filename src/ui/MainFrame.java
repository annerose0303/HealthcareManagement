package ui;

import controller.PatientController;
import service.CSVservice;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Healthcare Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CSVservice csvService = new CSVservice();
        PatientController patientController = new PatientController(csvService);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Patients", new PatientPanel(patientController));

        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

