package ui;

import model.Prescription;

import javax.swing.*;
import java.awt.*;

public class PrescriptionForm extends JDialog {

    private final JTextField patientIdField = new JTextField(10);
    private final JTextField clinicianIdField = new JTextField(10);
    private final JTextField appointmentIdField = new JTextField(10);

    private final JTextField medicationField = new JTextField(20);
    private final JTextField dosageField = new JTextField(15);
    private final JTextField frequencyField = new JTextField(15);

    private final JTextField durationField = new JTextField(10);
    private final JTextField quantityField = new JTextField(10);

    private final JTextField instructionsField = new JTextField(25);
    private final JTextField pharmacyField = new JTextField(20);

    private final int prescriptionId;
    private Prescription result;

    public PrescriptionForm(int prescriptionId) {
        this.prescriptionId = prescriptionId;

        setModal(true);
        setTitle("Add Prescription");
        setLayout(new GridLayout(12, 2, 5, 5));

        add(new JLabel("Patient ID:")); add(patientIdField);
        add(new JLabel("Clinician ID:")); add(clinicianIdField);
        add(new JLabel("Appointment ID:")); add(appointmentIdField);

        add(new JLabel("Medication Name:")); add(medicationField);
        add(new JLabel("Dosage:")); add(dosageField);
        add(new JLabel("Frequency:")); add(frequencyField);

        add(new JLabel("Duration Days:")); add(durationField);
        add(new JLabel("Quantity:")); add(quantityField);

        add(new JLabel("Instructions:")); add(instructionsField);
        add(new JLabel("Pharmacy Name:")); add(pharmacyField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        add(save); add(cancel);

        save.addActionListener(e -> onSave());
        cancel.addActionListener(e -> { result = null; dispose(); });

        pack();
        setLocationRelativeTo(null);
    }

    private void onSave() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText());
            int clinicianId = Integer.parseInt(clinicianIdField.getText());
            int appointmentId = Integer.parseInt(appointmentIdField.getText());

            String medication = medicationField.getText().trim();
            String dosage = dosageField.getText().trim();
            String frequency = frequencyField.getText().trim();

            int durationDays = Integer.parseInt(durationField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());

            String instructions = instructionsField.getText().trim();
            String pharmacy = pharmacyField.getText().trim();

            if (medication.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Medication name is required.", "Validation", JOptionPane.WARNING_MESSAGE);
                return;
            }


            result = new Prescription(
                    prescriptionId,
                    patientId,
                    clinicianId,
                    appointmentId,
                    medication,
                    dosage,
                    frequency,
                    durationDays,
                    quantity,
                    instructions,
                    pharmacy
            );

            dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric fields.", "Validation", JOptionPane.WARNING_MESSAGE);
        }
    }

    public Prescription showDialog() {
        setVisible(true);
        return result;
    }
}
