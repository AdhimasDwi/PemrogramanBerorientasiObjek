-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Dec 19, 2024 at 01:17 PM
-- Server version: 8.0.30
-- PHP Version: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `project3`
--

-- --------------------------------------------------------

--
-- Table structure for table `mitigation_steps`
--

CREATE TABLE `mitigation_steps` (
  `id` int NOT NULL,
  `threat_id` int NOT NULL,
  `step_number` int NOT NULL,
  `step_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `mitigation_steps`
--

INSERT INTO `mitigation_steps` (`id`, `threat_id`, `step_number`, `step_description`) VALUES
(1, 1, 1, 'Analisis kebijakan SDN saat ini untuk menemukan kesalahan konfigurasi.'),
(2, 1, 2, 'Gunakan alat simulasi untuk menguji kebijakan sebelum diterapkan.'),
(3, 1, 3, 'Perbarui kebijakan yang salah dan uji di lingkungan terkendali.'),
(4, 1, 4, 'Terapkan kebijakan baru ke lingkungan produksi.'),
(5, 1, 5, 'Pantau lalu lintas jaringan untuk memastikan perbaikan.'),
(6, 2, 1, 'Audit log API untuk menemukan percobaan akses tanpa otorisasi.'),
(7, 2, 2, 'Terapkan manajemen kunci API dan kontrol akses.'),
(8, 2, 3, 'Batasi akses API menggunakan IP whitelisting atau rate limiting.'),
(9, 2, 4, 'Perbarui perangkat lunak API untuk menutup celah keamanan.'),
(10, 2, 5, 'Pantau penggunaan API untuk mendeteksi pola tidak normal.'),
(11, 3, 1, 'Identifikasi sumber penggunaan sumber daya berlebihan.'),
(12, 3, 2, 'Tambah sumber daya controller atau gunakan redundansi.'),
(13, 3, 3, 'Terapkan pembatasan laju (*rate limiting*) untuk mengurangi dampak.'),
(14, 3, 4, 'Perbarui perangkat lunak controller untuk mengoptimalkan sumber daya.'),
(15, 3, 5, 'Pantau controller untuk mencegah kejadian serupa di masa depan.'),
(16, 4, 1, 'Verifikasi autentikasi lalu lintas SDN menggunakan tanda tangan digital.'),
(17, 4, 2, 'Gunakan protokol enkripsi seperti TLS untuk komunikasi SDN.'),
(18, 4, 3, 'Perbarui sertifikat switch dan controller secara berkala.'),
(19, 4, 4, 'Audit log jaringan untuk tanda serangan penyadapan.'),
(20, 4, 5, 'Pantau lalu lintas jaringan untuk mendeteksi aktivitas mencurigakan.'),
(21, 5, 1, 'Identifikasi perangkat IoT yang diretas melalui analisis lalu lintas.'),
(22, 5, 2, 'Isolasi perangkat IoT yang terpengaruh dari jaringan utama.'),
(23, 5, 3, 'Perbarui firmware perangkat IoT yang diretas.'),
(24, 5, 4, 'Terapkan segmentasi jaringan untuk membatasi akses perangkat IoT.'),
(25, 5, 5, 'Pantau perangkat IoT untuk mendeteksi perilaku abnormal.'),
(26, 6, 1, 'Analisis tabel penerusan switch untuk menemukan anomali.'),
(27, 6, 2, 'Hapus dan bangun ulang tabel penerusan switch.'),
(28, 6, 3, 'Terapkan keamanan port untuk mencegah spoofing alamat MAC.'),
(29, 6, 4, 'Perbarui firmware switch untuk menutup kerentanan.'),
(30, 6, 5, 'Pantau switch untuk mendeteksi aktivitas merusak.'),
(31, 7, 1, 'Identifikasi penggunaan bandwidth untuk semua layanan.'),
(32, 7, 2, 'Prioritaskan layanan penting melalui teknik rekayasa lalu lintas.'),
(33, 7, 3, 'Terapkan kebijakan QoS untuk menjamin alokasi bandwidth.'),
(34, 7, 4, 'Pantau alokasi bandwidth secara real-time dan sesuaikan jika diperlukan.'),
(35, 7, 5, 'Tinjau dan perbarui kebijakan alokasi sumber daya jaringan.'),
(36, 8, 1, 'Audit log akses untuk menemukan aktivitas tanpa otorisasi.'),
(37, 8, 2, 'Ganti kredensial dan terapkan autentikasi multi-faktor.'),
(38, 8, 3, 'Batasi akses ke antarmuka menggunakan daftar kontrol akses (ACL).'),
(39, 8, 4, 'Perbarui perangkat lunak untuk menutup celah keamanan.'),
(40, 8, 5, 'Pantau antarmuka untuk mendeteksi pola akses mencurigakan.'),
(41, 9, 1, 'Identifikasi jalur yang terpengaruh dalam jaringan melalui analisis lalu lintas.'),
(42, 9, 2, 'Isolasi atau hapus elemen jaringan berbahaya.'),
(43, 9, 3, 'Terapkan jalur alternatif untuk melewati jalur yang terpengaruh.'),
(44, 9, 4, 'Terapkan kebijakan keamanan untuk mencegah manipulasi paket.'),
(45, 9, 5, 'Pantau jaringan secara kontinu untuk mendeteksi aktivitas dropping.'),
(46, 10, 1, 'Audit alat monitoring jaringan untuk memeriksa versi lama.'),
(47, 10, 2, 'Perbarui alat monitoring ke versi terbaru yang aman.'),
(48, 10, 3, 'Batasi akses alat monitoring hanya untuk personel yang berwenang.'),
(49, 10, 4, 'Aktifkan fitur logging dan audit pada alat monitoring.'),
(50, 10, 5, 'Pantau alat monitoring untuk mendeteksi aktivitas mencurigakan.'),
(56, 13, 1, 'Identifikasi dan Analisis Serangan'),
(57, 13, 2, 'Gunakan Firewall dan Filter Trafik'),
(58, 13, 3, 'Manfaatkan Sistem Mitigasi DDoS'),
(59, 13, 4, 'Perkuat Infrastruktur Jaringan'),
(60, 13, 5, 'Persiapkan Protokol Respon Serangan');

-- --------------------------------------------------------

--
-- Table structure for table `threats`
--

CREATE TABLE `threats` (
  `id` int NOT NULL,
  `name_threat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sdn_layer` enum('Application Layer','Control Layer','Infrastructure Layer') NOT NULL,
  `status` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Detected'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `threats`
--

INSERT INTO `threats` (`id`, `name_threat`, `sdn_layer`, `status`) VALUES
(1, 'Kebijakan SDN salah konfigurasi menyebabkan kemacetan lalu lintas', 'Application Layer', 'Mitigated'),
(2, 'Akses API tanpa otorisasi', 'Application Layer', 'Mitigated'),
(3, 'Kegagalan controller akibat kehabisan sumber daya', 'Control Layer', 'Detected'),
(4, 'Serangan man-in-the-middle pada lalu lintas SDN', 'Control Layer', 'Mitigated'),
(5, 'Perangkat IoT diretas dan digunakan untuk serangan botnet', 'Infrastructure Layer', 'Mitigated'),
(6, 'Tabel penerusan switch dirusak', 'Infrastructure Layer', 'Mitigated'),
(7, 'Pembagian bandwidth tidak mencukupi untuk layanan kritis', 'Application Layer', 'Detected'),
(8, 'Antarmuka manajemen jaringan diretas', 'Control Layer', 'Detected'),
(9, 'Paket dijatuhkan oleh elemen jaringan berbahaya', 'Infrastructure Layer', 'Mitigated'),
(10, 'Alat monitoring jaringan usang atau tidak aman', 'Application Layer', 'Mitigated'),
(13, 'Serangan DDoS Membanjiri Perangkat Jaringan Fisik', 'Infrastructure Layer', 'Detected');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(10) NOT NULL
) ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'admin1', 'admin123', 'admin'),
(2, 'user1', 'user123', 'user'),
(3, '232310004@studentibik.ac.id', '232310004', 'admin'),
(4, 'user2', 'mypassword', 'user'),
(5, 'asep1', 'asep123', 'user'),
(6, 'Toriq', 'Toriq123', 'user'),
(7, 'Fadly', 'Fadly123', 'admin'),
(8, 'asep', 'asep123', 'user'),
(9, 'Adhimas Dwi Putra', '121212', 'user'),
(10, 'Fadly Syraj', '123456', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `mitigation_steps`
--
ALTER TABLE `mitigation_steps`
  ADD PRIMARY KEY (`id`),
  ADD KEY `threat_id` (`threat_id`);

--
-- Indexes for table `threats`
--
ALTER TABLE `threats`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `mitigation_steps`
--
ALTER TABLE `mitigation_steps`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `threats`
--
ALTER TABLE `threats`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `mitigation_steps`
--
ALTER TABLE `mitigation_steps`
  ADD CONSTRAINT `mitigation_steps_ibfk_1` FOREIGN KEY (`threat_id`) REFERENCES `threats` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
