package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Healthcare Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Patients", placeholderPanel("Patients panel (next step: JTable + CRUD)"));
        tabs.addTab("Clinicians", placeholderPanel("Clinicians panel (next step: JTable + CRUD)"));
        tabs.addTab("Appointments", placeholderPanel("Appointments panel (next step: JTable + CRUD)"));
        tabs.addTab("Prescriptions", placeholderPanel("Prescriptions panel (next step: JTable + CRUD)"));
        tabs.addTab("Referrals", placeholderPanel("Referrals panel (next step: Singleton + output text file)"));

        add(tabs, BorderLayout.CENTER);
    }

    private JPanel placeholderPanel(String text) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.add(new JLabel(text));
        return panel;
    }
}
