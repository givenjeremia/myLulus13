-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Inang: 127.0.0.1
-- Waktu pembuatan: 22 Jun 2021 pada 05.14
-- Versi Server: 5.5.32
-- Versi PHP: 5.4.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Basis data: `kelulusan_ubaya`
--
CREATE DATABASE IF NOT EXISTS `kelulusan_ubaya` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `kelulusan_ubaya`;

-- --------------------------------------------------------

--
-- Struktur dari tabel `mahasiswa`
--

CREATE TABLE IF NOT EXISTS `mahasiswa` (
  `nrp` char(9) NOT NULL,
  `pin` char(8) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `angkatan` smallint(5) unsigned NOT NULL,
  PRIMARY KEY (`nrp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data untuk tabel `mahasiswa`
--

INSERT INTO `mahasiswa` (`nrp`, `pin`, `nama`, `angkatan`) VALUES
('160419107', '123456', 'Bella', 2019),
('160419118', '123456', 'Given Jeremia', 2019),
('160419135', '123456', 'Salsa', 2019);

-- --------------------------------------------------------

--
-- Struktur dari tabel `mahasiswa_ambil_mk`
--

CREATE TABLE IF NOT EXISTS `mahasiswa_ambil_mk` (
  `nrp` char(9) NOT NULL,
  `kode_mk` char(8) NOT NULL,
  `semester` enum('gasal','genap') DEFAULT NULL,
  `tahun_ambil` smallint(5) unsigned DEFAULT NULL,
  `nisbi` enum('A','AB','B','BC','C','D','E') NOT NULL,
  PRIMARY KEY (`nrp`,`kode_mk`),
  KEY `fk_mahasiswa_has_mk_mk1_idx` (`kode_mk`),
  KEY `fk_mahasiswa_has_mk_mahasiswa_idx` (`nrp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data untuk tabel `mahasiswa_ambil_mk`
--

INSERT INTO `mahasiswa_ambil_mk` (`nrp`, `kode_mk`, `semester`, `tahun_ambil`, `nisbi`) VALUES
('160419107', '1604B011', 'genap', 2020, 'AB'),
('160419107', '1604B021', 'genap', 2020, 'B'),
('160419107', '1608B081', 'gasal', 2021, 'AB'),
('160419118', '1604B011', 'gasal', 2020, 'B'),
('160419118', '1604B031', 'genap', 2021, 'B'),
('160419118', '1604B061', 'gasal', 2020, 'A'),
('160419135', '1604B011', 'gasal', 2020, 'AB'),
('160419135', '1604B021', 'gasal', 2020, 'A'),
('160419135', '1604B031', 'genap', 2020, 'B'),
('160419135', '1604B041', 'genap', 2021, 'A');

-- --------------------------------------------------------

--
-- Struktur dari tabel `mk`
--

CREATE TABLE IF NOT EXISTS `mk` (
  `kode` char(8) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `sks` tinyint(3) unsigned NOT NULL,
  PRIMARY KEY (`kode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data untuk tabel `mk`
--

INSERT INTO `mk` (`kode`, `nama`, `sks`) VALUES
('1604B011', 'Algorithm and Programming', 6),
('1604B021', 'Object Oriented Programming', 6),
('1604B031', 'Data Structure', 3),
('1604B041', 'Numerical Method', 2),
('1604B052', 'Native Mobile Programming', 3),
('1604B061', 'Kepemimpinan dan Etika Profesi', 2),
('1604B071', 'Research Methodology', 2),
('1608B081', 'Tugas Akhir', 5);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `mahasiswa_ambil_mk`
--
ALTER TABLE `mahasiswa_ambil_mk`
  ADD CONSTRAINT `fk_mahasiswa_has_mk_mahasiswa` FOREIGN KEY (`nrp`) REFERENCES `mahasiswa` (`nrp`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_mahasiswa_has_mk_mk1` FOREIGN KEY (`kode_mk`) REFERENCES `mk` (`kode`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
