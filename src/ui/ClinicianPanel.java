package ui;

import controller.ClinicianController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClinicianPanel extends JPanel {

    private final ClinicianController controller;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ClinicianPanel(ClinicianController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new Object[]{
                        "Clinician ID",
                        "Title",
                        "Name",
                        "Email",
                        "Phone",
                        "Speciality",
                        "Workplace Type"
                },
                0
        );

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton loadBtn = new JButton("Load Clinicians");
        bottom.add(loadBtn);
        add(bottom, BorderLayout.SOUTH);

        loadBtn.addActionListener(e -> loadCliniciansAsync());
    }

    private void loadCliniciansAsync() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.loadCliniciansTableRows();
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    refreshTable();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            ClinicianPanel.this,
                            "Failed to load clinicians: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (String[] row : controller.getCliniciansTableRows()) {
            tableModel.addRow(row);
        }
    }
}
