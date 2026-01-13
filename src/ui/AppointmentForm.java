package ui;

import model.Appointment;
import model.Patient;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class AppointmentForm extends JDialog {

    private final int appointmentId;
    private final JComboBox<Patient> patientBox;
    private final JTextField dateTimeField = new JTextField(20); // yyyy-MM-ddTHH:mm
    private final JTextField statusField = new JTextField(15);
    private final JTextField reasonField = new JTextField(25);

    private Appointment result;

    public AppointmentForm(int appointmentId, List<Patient> patients, Appointment existing) {
        this.appointmentId = appointmentId;

        setModal(true);
        setTitle(existing == null ? "Add Appointment" : "Edit Appointment");
        setLayout(new GridLayout(5, 2, 6, 6));

        patientBox = new JComboBox<>(patients.toArray(new Patient[0]));
        patientBox.setRenderer((list, value, index, isSelected, cellHasFocus) ->
                new JLabel(value == null ? "" : (value.getUserId() + " - " + value.getName()))
        );

        add(new JLabel("Patient:"));
        add(patientBox);

        add(new JLabel("Date/Time (yyyy-MM-ddTHH:mm):"));
        add(dateTimeField);

        add(new JLabel("Status:"));
        add(statusField);

        add(new JLabel("Reason:"));
        add(reasonField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        add(save);
        add(cancel);

        if (existing != null) {
            patientBox.setSelectedItem(existing.getPatient());
            dateTimeField.setText(
                    existing.getDate() == null ? "" : existing.getDate().toString()
            );
            statusField.setText(existing.getStatus());
            reasonField.setText(existing.getReason());
        }
        else {
            // sensible default
            dateTimeField.setText("2026-01-13T09:00");
            statusField.setText("Scheduled");
        }

        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> { result = null; dispose(); });

        pack();
        setLocationRelativeTo(null);
    }

    private void onSave() {
        Patient patient = (Patient) patientBox.getSelectedItem();
        if (patient == null) {
            JOptionPane.showMessageDialog(this, "Select a patient.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dtText = dateTimeField.getText().trim();
        String status = statusField.getText().trim();
        String reason = reasonField.getText().trim();

        if (dtText.isEmpty() || status.isEmpty() || reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDateTime dt;
        try {
            dt = LocalDateTime.parse(dtText);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Date/Time must be like 2026-01-13T09:00", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        result = new Appointment(appointmentId, patient, dt, status, reason);
        dispose();
    }

    public Appointment showDialog() {
        setVisible(true);
        return result;
    }
}
