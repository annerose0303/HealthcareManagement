package ui;

import controller.PatientController;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class PatientPanel extends JPanel {

    private final PatientController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public PatientPanel(PatientController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Name", "Email", "Phone", "NHS Number", "GP Surgery"}, 0
        );

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton loadBtn = new JButton("Load");
        JButton addBtn = new JButton("Add");
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");

        buttons.add(loadBtn);
        buttons.add(addBtn);
        buttons.add(editBtn);
        buttons.add(deleteBtn);

        add(buttons, BorderLayout.SOUTH);

        // Actions
        loadBtn.addActionListener(e -> loadPatients());
        addBtn.addActionListener(e -> addPatient());
        editBtn.addActionListener(e -> editPatient());
        deleteBtn.addActionListener(e -> deletePatient());
    }

    /* ===================== ACTIONS ===================== */

    private void loadPatients() {
        try {
            controller.loadPatients();
            refreshTable();
        } catch (IOException ex) {
            showError(ex.getMessage());
        }
    }

    private void addPatient() {
        PatientForm form = new PatientForm(null);
        Patient p = form.showDialog();
        if (p != null) {
            controller.addPatient(p);
            refreshTable();
        }
    }

    private void editPatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Select a patient to edit.");
            return;
        }

        Patient existing = controller.getPatients().get(row);
        PatientForm form = new PatientForm(existing);
        Patient updated = form.showDialog();

        if (updated != null) {
            controller.updatePatient(row, updated);
            refreshTable();
        }
    }

    private void deletePatient() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Select a patient to delete.");
            return;
        }
        controller.deletePatient(row);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Patient p : controller.getPatients()) {
            tableModel.addRow(new Object[]{
                    p.getUserId(),
                    p.getName(),
                    p.getEmail(),
                    p.getPhone(),
                    p.getNhsNumber(),
                    p.getGpSurgery()
            });
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
