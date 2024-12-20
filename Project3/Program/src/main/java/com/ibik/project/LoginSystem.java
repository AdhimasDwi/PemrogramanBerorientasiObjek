package com.ibik.project;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginSystem {

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("Login Form");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(300, 150); // 
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setLocationRelativeTo(null);

        // Panel utama untuk login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout()); // Menggunakan GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Spasi antar elemen
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Elemen username
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        loginPanel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        loginPanel.add(usernameField, gbc);

        // Elemen password
        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        loginPanel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        loginPanel.add(passwordField, gbc);

        // Tombol login dan register
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Menyamakan ukuran tombol
        Dimension buttonSize = new Dimension(100, 30); // Lebar 100px dan tinggi 30px
        loginButton.setPreferredSize(buttonSize);
        registerButton.setPreferredSize(buttonSize);

        // Tambahkan tombol register
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST; // Tombol di kiri
        loginPanel.add(registerButton, gbc);

        // Tambahkan tombol login
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST; // Tombol di kanan
        loginPanel.add(loginButton, gbc);

        // Menambahkan panel ke frame
        loginFrame.add(loginPanel, BorderLayout.CENTER);

        // Menampilkan frame
        loginFrame.setVisible(true);

        // Event handler untuk tombol login
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            try (Connection connection = ConnectionDB.getConnection()) {
                String query = "SELECT role FROM users WHERE username = ? AND password = ?";
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String role = rs.getString("role");
                    loginFrame.dispose();
                    SwingUtilities.invokeLater(() -> new ThreatManagement(role).setVisible(true));
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid credentials!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Event handler untuk tombol register
        registerButton.addActionListener(e -> {
            String newUsername = JOptionPane.showInputDialog(loginFrame, "Enter New Username:");
            String newPassword = JOptionPane.showInputDialog(loginFrame, "Enter New Password:");
            String role = "user";

            if (newUsername != null && newPassword != null && !newUsername.isEmpty() && !newPassword.isEmpty()) {
                try (Connection connection = ConnectionDB.getConnection()) {
                    String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(query);
                    ps.setString(1, newUsername);
                    ps.setString(2, newPassword);
                    ps.setString(3, role);

                    if (ps.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(loginFrame, "User registered successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(loginFrame, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
