package ui;

import controller.PatientController;
import controller.PrescriptionController;
import model.Prescription;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PrescriptionPanel extends JPanel {

    private final PrescriptionController prescriptionController;
    private final PatientController patientController;

    private final DefaultTableModel tableModel;
    private final JTable table;

    public PrescriptionPanel(PrescriptionController prescriptionController, PatientController patientController) {
        this.prescriptionController = prescriptionController;
        this.patientController = patientController;

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new Object[]{"Prescription ID", "Patient ID", "Clinician ID", "Appointment ID", "Medication", "Dosage", "Frequency", "Status", "Issue Date"}, 0
        );
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton loadBtn = new JButton("Load");
        JButton addBtn = new JButton("Add (Append to CSV)");

        buttons.add(loadBtn);
        buttons.add(addBtn);

        add(buttons, BorderLayout.SOUTH);

        loadBtn.addActionListener(e -> loadPrescriptionsAsync());
        addBtn.addActionListener(e -> addPrescriptionAsync());
    }

    private void loadPrescriptionsAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                prescriptionController.loadPrescriptions();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    refreshTable();
                } catch (Exception ex) {
                    showError("Failed to load prescriptions: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void addPrescriptionAsync() {
        // Ensure patients are loaded so we can validate patient IDs
        if (patientController.getPatients().isEmpty()) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Patients are not loaded. Load patients now?",
                    "Load Patients",
                    JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    patientController.loadPatients();
                } catch (Exception ex) {
                    showError("Failed to load patients: " + ex.getMessage());
                    return;
                }
            }
        }

        PrescriptionForm form = new PrescriptionForm(prescriptionController.nextPrescriptionId());
        Prescription newPrescription = form.showDialog();
        if (newPrescription == null) return;

        // Validate patient exists (simple safety check)
        boolean patientExists = patientController.getPatients().stream()
                .anyMatch(p -> p.getUserId() == newPrescription.getPatientId());

        if (!patientExists) {
            showError("Patient ID not found. Load patients and use a valid patient ID.");
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                prescriptionController.addPrescriptionAndPersist(newPrescription);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    refreshTable();
                    JOptionPane.showMessageDialog(PrescriptionPanel.this,
                            "Prescription added and appended to prescriptions.csv",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    showError("Failed to add prescription: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Prescription p : prescriptionController.getPrescriptions()) {
            tableModel.addRow(new Object[]{
                    p.getPrescriptionId(),
                    p.getPatientId(),
                    p.getClinicianId(),
                    p.getAppointmentId(),
                    p.getMedicationName(),
                    p.getDosage(),
                    p.getFrequency(),
                    p.getStatus(),
                    p.getIssueDate()
            });
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
