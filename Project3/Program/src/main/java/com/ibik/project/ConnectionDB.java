package com.ibik.project;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/project3"; // Ganti sesuai URL database Anda
    private static final String USER = "root"; // Ganti dengan username database
    private static final String PASSWORD = ""; // Ganti dengan password database

    // Metode untuk mendapatkan koneksi
    public static Connection getConnection() {
        try {
            // Mendapatkan koneksi database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal terhubung ke database!");
        }
    }

    // Fungsi main untuk pengujian
    public static void main(String[] args) {
        // Pengujian koneksi ke database
        try (Connection conn = ConnectionDB.getConnection()) {
            if (conn != null) {
                System.out.println("Berhasil terhubung ke database MySQL!");
            }
        } catch (SQLException e) {
            System.out.println("Koneksi ke database gagal!");
            e.printStackTrace();
        }
    }
}

