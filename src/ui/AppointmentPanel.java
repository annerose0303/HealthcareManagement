package ui;

import controller.AppointmentController;
import controller.PatientController;
import model.Appointment;
import model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;

public class AppointmentPanel extends JPanel {

    private final AppointmentController appointmentController;
    private final PatientController patientController;

    private final DefaultTableModel tableModel;
    private final JTable table;

    public AppointmentPanel(AppointmentController appointmentController, PatientController patientController) {
        this.appointmentController = appointmentController;
        this.patientController = patientController;

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new Object[]{"Appointment ID", "Patient ID", "Patient Name", "Date/Time", "Status", "Reason"}, 0
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

        loadBtn.addActionListener(e -> loadAppointmentsAsync());
        addBtn.addActionListener(e -> addAppointment());
        editBtn.addActionListener(e -> editAppointment());
        deleteBtn.addActionListener(e -> deleteAppointment());
    }

    private void ensurePatientsLoadedOrAsk() {
        if (!patientController.getPatients().isEmpty()) return;

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
            }
        }
    }

    private void loadAppointmentsAsync() {
        ensurePatientsLoadedOrAsk();

        if (patientController.getPatients().isEmpty()) {
            showError("Appointments require patients. Load patients first.");
            return;
        }

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                appointmentController.loadAppointments();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    refreshTable();
                } catch (Exception ex) {
                    showError("Failed to load appointments: " + ex.getMessage());
                    System.err.println("Appointment load error: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void addAppointment() {
        ensurePatientsLoadedOrAsk();
        if (patientController.getPatients().isEmpty()) {
            showError("Load patients first.");
            return;
        }

        int newId = appointmentController.nextAppointmentId();
        AppointmentForm form = new AppointmentForm(newId, patientController.getPatients(), null);
        Appointment created = form.showDialog();

        if (created != null) {
            appointmentController.addAppointment(created);
            refreshTable();
        }
    }

    private void editAppointment() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Select an appointment to edit.");
            return;
        }

        Appointment existing = appointmentController.getAppointments().get(row);
        AppointmentForm form = new AppointmentForm(existing.getAppointmentId(), patientController.getPatients(), existing);
        Appointment updated = form.showDialog();

        if (updated != null) {
            appointmentController.updateAppointment(row, updated);
            refreshTable();
        }
    }

    private void deleteAppointment() {
        int row = table.getSelectedRow();
        if (row < 0) {
            showError("Select an appointment to delete.");
            return;
        }

        appointmentController.deleteAppointment(row);
        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Appointment a : appointmentController.getAppointments()) {
            Patient p = a.getPatient();
            LocalDateTime dt = a.getDate(); // matches your model getter

            tableModel.addRow(new Object[]{
                    a.getAppointmentId(),
                    p == null ? "" : p.getUserId(),
                    p == null ? "" : p.getName(),
                    dt == null ? "" : dt.toString(),
                    a.getStatus(),
                    a.getReason()
            });
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
