package ui;

import controller.PatientController;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientPanel extends JPanel {

    private final PatientController controller = new PatientController();
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Email", "Phone"}, 0
    );
    private final JTable table = new JTable(tableModel);

    public PatientPanel() {
        setLayout(new BorderLayout());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

        loadPatients();
    }

    private JPanel createButtons() {
        JPanel panel = new JPanel();

        JButton loadBtn = new JButton("Load");
        JButton addBtn = new JButton("Add");
        JButton deleteBtn = new JButton("Delete");

        loadBtn.addActionListener(e -> loadPatients());
        addBtn.addActionListener(e -> addPatient());
        deleteBtn.addActionListener(e -> deletePatient());

        panel.add(loadBtn);
        panel.add(addBtn);
        panel.add(deleteBtn);

        return panel;
    }

    private void loadPatients() {
        controller.loadPatients("data/patients.csv");
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Patient p : controller.getPatients()) {
            tableModel.addRow(new Object[]{
                    p.getUserId(),     // IMPORTANT: User ID, not patientID
                    p.getName(),
                    p.getEmail(),
                    p.getPhone()
            });
        }
    }

    private void addPatient() {
        JTextField name = new JTextField();
        JTextField email = new JTextField();
        JTextField phone = new JTextField();

        Object[] fields = {
                "Name:", name,
                "Email:", email,
                "Phone:", phone
        };

        int result = JOptionPane.showConfirmDialog(
                this, fields, "Add Patient", JOptionPane.OK_CANCEL_OPTION
        );

        if (result != JOptionPane.OK_OPTION) return;

        int nextId = controller.getPatients().stream()
                .mapToInt(Patient::getUserId)
                .max()
                .orElse(0) + 1;

        Patient patient = new Patient(
                nextId,
                name.getText().trim(),
                email.getText().trim(),
                phone.getText().trim(),
                "", ""
        );

        controller.addPatient(patient);
        refreshTable();
    }

    private void deletePatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a patient to delete");
            return;
        }
        controller.deletePatientByIndex(row);
        refreshTable();
    }
}

