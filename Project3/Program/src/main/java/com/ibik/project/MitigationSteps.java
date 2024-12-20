package com.ibik.project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MitigationSteps {

    public static void displaySteps(int threatId) {
        DefaultTableModel stepsModel = new DefaultTableModel(new String[]{"Step Number", "Step Description"}, 0);

        try (Connection connection = ConnectionDB.getConnection()) {
            String query = "SELECT step_number, step_description FROM mitigation_steps WHERE threat_id = ?";
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
            JOptionPane.showMessageDialog(null, new JScrollPane(stepsTable), "Mitigation Steps", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
