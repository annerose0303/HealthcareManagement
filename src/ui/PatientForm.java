package ui;

import model.Patient;

import javax.swing.*;
import java.awt.*;

public class PatientForm extends JDialog {

    private JTextField nameField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextField phoneField = new JTextField(15);
    private JTextField nhsField = new JTextField(15);
    private JTextField gpField = new JTextField(15);

    private Patient result;

    public PatientForm(Patient existing) {
        setModal(true);
        setTitle(existing == null ? "Add Patient" : "Edit Patient");
        setLayout(new GridLayout(6, 2, 5, 5));

        add(new JLabel("Name:")); add(nameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Phone:")); add(phoneField);
        add(new JLabel("NHS Number:")); add(nhsField);
        add(new JLabel("GP Surgery:")); add(gpField);

        JButton save = new JButton("Save");
        JButton cancel = new JButton("Cancel");
        add(save); add(cancel);

        if (existing != null) {
            nameField.setText(existing.getName());
            emailField.setText(existing.getEmail());
            phoneField.setText(existing.getPhone());
            nhsField.setText(existing.getNhsNumber());
            gpField.setText(existing.getGpSurgery());
        }

        save.addActionListener(e -> {
            result = new Patient(
                    existing == null ? 0 : existing.getUserId(),
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    nhsField.getText(),
                    gpField.getText()
            );
            dispose();
        });

        cancel.addActionListener(e -> {
            result = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(null);
    }

    public Patient showDialog() {
        setVisible(true);
        return result;
    }
}
