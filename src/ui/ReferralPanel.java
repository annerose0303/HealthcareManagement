package ui;

import controller.PatientController;
import controller.ReferralController;
import model.Patient;
import model.Referral;

import javax.swing.*;
import java.awt.*;

public class ReferralPanel extends JPanel {

    private final ReferralController referralController;
    private final PatientController patientController;

    private final JTextField patientIdField = new JTextField(10);
    private final JTextField specialtyField = new JTextField(20);
    private final JTextField urgencyField = new JTextField(15);
    private final JTextArea summaryArea = new JTextArea(4, 30);
    private final JTextField recipientField = new JTextField(25);

    public ReferralPanel(ReferralController referralController, PatientController patientController) {
        this.referralController = referralController;
        this.patientController = patientController;

        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));

        form.add(new JLabel("Patient ID (numeric):"));
        form.add(patientIdField);

        form.add(new JLabel("Specialty:"));
        form.add(specialtyField);

        form.add(new JLabel("Urgency:"));
        form.add(urgencyField);

        form.add(new JLabel("Suggested Recipient:"));
        form.add(recipientField);

        form.add(new JLabel("Clinical Summary:"));
        form.add(new JScrollPane(summaryArea));

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton createBtn = new JButton("Create Referral");
        JButton processBtn = new JButton("Process Referral");
        buttons.add(createBtn);
        buttons.add(processBtn);
        add(buttons, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> createReferral());
        processBtn.addActionListener(e -> processReferral());
    }

    private void createReferral() {
        try {
            int patientId = Integer.parseInt(patientIdField.getText().trim());

            Patient patient = patientController.getPatients().stream()
                    .filter(p -> p.getUserId() == patientId)
                    .findFirst()
                    .orElse(null);

            if (patient == null) {
                showError("Patient ID not found. Load patients and use the numeric ID shown.");
                return;
            }

            String specialty = specialtyField.getText().trim();
            String urgency = urgencyField.getText().trim();
            String summary = summaryArea.getText().trim();

            if (specialty.isEmpty() || urgency.isEmpty() || summary.isEmpty()) {
                showError("All fields must be filled.");
                return;
            }

            Referral referral = new Referral(
                    referralController.queueSize() + 1,
                    patient,
                    specialty,
                    urgency,
                    summary
            );

            referralController.createReferral(referral);

            JOptionPane.showMessageDialog(this,
                    "Referral created and added to queue.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            showError("Patient ID must be a NUMBER (e.g. 1, 2, 3).");
        }
    }

    private void processReferral() {
        try {
            String recipient = recipientField.getText().trim();
            if (recipient.isEmpty()) {
                showError("Please enter a suggested recipient.");
                return;
            }

            String text = referralController.processNextReferral(recipient);

            if (text == null) {
                showError("No referrals in queue to process.");
                return;
            }

            JOptionPane.showMessageDialog(this,
                    "Referral processed and written to generated_referrals.txt",
                    "Referral Sent",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            showError("Failed to process referral: " + ex.getMessage());
            System.err.println("Error: " + ex.getMessage());

        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
