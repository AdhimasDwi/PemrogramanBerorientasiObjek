package com.ibik.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ThreatManagement extends JFrame {
    private JTable threatTable;
    private JButton viewStepsButton, deleteThreatButton, addThreatButton;
    private String userRole;

    public ThreatManagement(String role) {
        this.userRole = role;

        setTitle("SDN Threat Management");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table setup
        DefaultTableModel threatModel = new DefaultTableModel(new String[]{"ID", "No", "Name_Threat", "SDN_Layer", "Status"}, 0);
        threatTable = new JTable(threatModel);
        JScrollPane scrollPane = new JScrollPane(threatTable);

        // Hide ID column (for internal use only)
        threatTable.getColumnModel().getColumn(0).setMinWidth(0);
        threatTable.getColumnModel().getColumn(0).setMaxWidth(0);
        threatTable.getColumnModel().getColumn(0).setWidth(0);

        // Buttons
        viewStepsButton = new JButton("View Mitigation Steps");
        deleteThreatButton = new JButton("Delete Threat");
        addThreatButton = new JButton("Add Threat");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(viewStepsButton);
        if ("admin".equalsIgnoreCase(userRole)) {
            buttonPanel.add(deleteThreatButton);
            buttonPanel.add(addThreatButton);
        }

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        loadThreats(threatModel);

        // Event Listeners
        viewStepsButton.addActionListener(e -> viewMitigationSteps());
        if ("admin".equalsIgnoreCase(userRole)) {
            deleteThreatButton.addActionListener(e -> deleteThreat(threatModel));
            addThreatButton.addActionListener(e -> addThreat(threatModel));
        }
    }

    private void loadThreats(DefaultTableModel model) {
        model.setRowCount(0);
        try (Connection connection = ConnectionDB.getConnection()) {
            String query = "SELECT * FROM threats";
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int no = 1;
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        no++,
                        rs.getString("name_threat"),
                        rs.getString("sdn_layer"),
                        rs.getString("status")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void viewMitigationSteps() {
        int selectedRow = threatTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a threat to view mitigation steps.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int threatId = (int) threatTable.getValueAt(selectedRow, 0);
        DefaultTableModel stepsModel = new DefaultTableModel(new String[]{"Step Number", "Step Description"}, 0);

        try (Connection connection = ConnectionDB.getConnection()) {
            String query = "SELECT step_number, step_description FROM mitigation_steps WHERE threat_id = ? ORDER BY step_number";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, threatId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                stepsModel.addRow(new Object[]{
                        rs.getInt("step_number"),
                        rs.getString("step_description")
                });
            }

            JTable stepsTable = new JTable(stepsModel);
            JOptionPane.showMessageDialog(this, new JScrollPane(stepsTable), "Mitigation Steps", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteThreat(DefaultTableModel model) {
        int selectedRow = threatTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a threat to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int threatId = (int) threatTable.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this threat?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try (Connection connection = ConnectionDB.getConnection()) {
            // Delete mitigation steps related to the threat
            String deleteStepsQuery = "DELETE FROM mitigation_steps WHERE threat_id = ?";
            PreparedStatement psSteps = connection.prepareStatement(deleteStepsQuery);
            psSteps.setInt(1, threatId);
            psSteps.executeUpdate();

            // Delete the threat
            String deleteThreatQuery = "DELETE FROM threats WHERE id = ?";
            PreparedStatement psThreat = connection.prepareStatement(deleteThreatQuery);
            psThreat.setInt(1, threatId);

            if (psThreat.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this, "Threat deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadThreats(model);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete threat.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addThreat(DefaultTableModel model) {
        String nameThreat = JOptionPane.showInputDialog(this, "Enter Threat Name:");
        String sdnLayer = JOptionPane.showInputDialog(this, "Enter SDN Layer:");

        if (nameThreat == null || sdnLayer == null || nameThreat.isEmpty() || sdnLayer.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        DefaultListModel<String> mitigationSteps = new DefaultListModel<>();
        boolean addingSteps = true;

        while (addingSteps) {
            String step = JOptionPane.showInputDialog(this, "Enter a mitigation step (leave empty to stop):");
            if (step == null || step.isEmpty()) {
                addingSteps = false;
            } else {
                mitigationSteps.addElement(step);
            }
        }

        if (mitigationSteps.isEmpty()) {
            JOptionPane.showMessageDialog(this, "At least one mitigation step is required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection connection = ConnectionDB.getConnection()) {
            connection.setAutoCommit(false);

            String queryThreat = "INSERT INTO threats (name_threat, sdn_layer, status) VALUES (?, ?, 'Detected')";
            PreparedStatement psThreat = connection.prepareStatement(queryThreat, Statement.RETURN_GENERATED_KEYS);
            psThreat.setString(1, nameThreat);
            psThreat.setString(2, sdnLayer);
            psThreat.executeUpdate();

            ResultSet generatedKeys = psThreat.getGeneratedKeys();
            int threatId = -1;
            if (generatedKeys.next()) {
                threatId = generatedKeys.getInt(1);
            }

            String querySteps = "INSERT INTO mitigation_steps (threat_id, step_number, step_description) VALUES (?, ?, ?)";
            PreparedStatement psSteps = connection.prepareStatement(querySteps);

            for (int i = 0; i < mitigationSteps.size(); i++) {
                psSteps.setInt(1, threatId);
                psSteps.setInt(2, i + 1);
                psSteps.setString(3, mitigationSteps.get(i));
                psSteps.addBatch();
            }

            psSteps.executeBatch();
            connection.commit();

            JOptionPane.showMessageDialog(this, "Threat and mitigation steps added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadThreats(model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ThreatManagement("admin").setVisible(true));
    }
}
