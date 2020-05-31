-- MySQL dump 10.10
--
-- Host: localhost    Database: skripsi_apps
-- ------------------------------------------------------
-- Server version	5.0.18-nt

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `barang`
--

DROP TABLE IF EXISTS `barang`;
CREATE TABLE `barang` (
  `barang_id` bigint(20) NOT NULL default '0',
  `kategori_barang_id` bigint(20) default '0',
  `kode` varchar(15) default NULL,
  `barcode` varchar(25) default NULL,
  `nama` varchar(50) default NULL,
  `satuan` varchar(15) default NULL,
  `alpha` decimal(1,1) default '0.0',
  PRIMARY KEY  (`barang_id`),
  UNIQUE KEY `kode` (`kode`),
  UNIQUE KEY `nama` (`nama`),
  UNIQUE KEY `barcode` (`barcode`),
  KEY `kategoriId` (`kategori_barang_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--


/*!40000 ALTER TABLE `barang` DISABLE KEYS */;
LOCK TABLES `barang` WRITE;
INSERT INTO `barang` VALUES (283064955447491,283064955428059,'000001','000001','Sapu Lidi','PCS','0.0'),(283065898929627,283064955428059,'00002','00002','Sendok Garpu','PCS','0.0'),(283065898971281,283064955428059,'0003','0003','Indomiee','PCS','0.0');
UNLOCK TABLES;
/*!40000 ALTER TABLE `barang` ENABLE KEYS */;

--
-- Table structure for table `kategori_barang`
--

DROP TABLE IF EXISTS `kategori_barang`;
CREATE TABLE `kategori_barang` (
  `kategori_barang_id` bigint(20) NOT NULL default '0',
  `kode` varchar(10) default NULL,
  `nama` varchar(25) default NULL,
  PRIMARY KEY  (`kategori_barang_id`),
  UNIQUE KEY `kode` (`kode`),
  UNIQUE KEY `nama` (`nama`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kategori_barang`
--


/*!40000 ALTER TABLE `kategori_barang` DISABLE KEYS */;
LOCK TABLES `kategori_barang` WRITE;
INSERT INTO `kategori_barang` VALUES (283064955428059,'001','Kebutuhan Rumah Tangga');
UNLOCK TABLES;
/*!40000 ALTER TABLE `kategori_barang` ENABLE KEYS */;

--
-- Table structure for table `pengguna`
--

DROP TABLE IF EXISTS `pengguna`;
CREATE TABLE `pengguna` (
  `pengguna_id` bigint(20) NOT NULL default '0',
  `nama` varchar(25) default NULL,
  `username` varchar(15) default NULL,
  `password` varchar(32) default NULL,
  `role_id` bigint(20) default NULL COMMENT 'role pengguna',
  `status` int(1) default '0' COMMENT '0:tidak aktif, 1:aktif',
  PRIMARY KEY  (`pengguna_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `login` (`username`,`password`),
  UNIQUE KEY `nama` (`nama`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pengguna`
--


/*!40000 ALTER TABLE `pengguna` DISABLE KEYS */;
LOCK TABLES `pengguna` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `pengguna` ENABLE KEYS */;

--
-- Table structure for table `penjualan`
--

DROP TABLE IF EXISTS `penjualan`;
CREATE TABLE `penjualan` (
  `penjualan_id` bigint(22) NOT NULL default '0',
  `tahun` int(4) default '0',
  `bulan` int(2) default '0',
  `barang_id` bigint(20) default '0',
  `qty` decimal(22,2) default '0.00',
  PRIMARY KEY  (`penjualan_id`),
  UNIQUE KEY `tahunBulanBarang` (`tahun`,`bulan`,`barang_id`),
  KEY `tahun` (`tahun`),
  KEY `bulan` (`bulan`),
  KEY `barang` (`barang_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `penjualan`
--


/*!40000 ALTER TABLE `penjualan` DISABLE KEYS */;
LOCK TABLES `penjualan` WRITE;
INSERT INTO `penjualan` VALUES (283064955462535,2020,1,283064955447491,'20.00'),(283065054098711,2020,2,283064955447491,'7.00'),(283065054133287,2020,3,283064955447491,'6.00'),(283065054142420,2020,4,283064955447491,'11.00'),(283065054149054,2020,5,283064955447491,'5.00'),(283065054167542,2019,1,283064955447491,'9.00'),(283065054179347,2019,2,283064955447491,'2.00'),(283065054188296,2019,3,283064955447491,'9.00'),(283065054197814,2019,4,283064955447491,'6.00'),(283065054208564,2019,5,283064955447491,'4.00'),(283065054217519,2019,6,283064955447491,'6.00'),(283065054229540,2019,7,283064955447491,'8.00'),(283065054238076,2019,8,283064955447491,'9.00'),(283065054246729,2019,9,283064955447491,'5.00'),(283065054269906,2019,10,283064955447491,'6.00'),(283065054281259,2019,11,283064955447491,'8.00'),(283065054293528,2019,12,283064955447491,'9.00'),(283065316783507,2017,1,283064955447491,'3.00'),(283065316783527,2017,2,283064955447491,'5.00'),(283065316783542,2017,3,283064955447491,'2.00'),(283065316783556,2017,4,283064955447491,'7.00'),(283065316783573,2017,5,283064955447491,'4.00'),(283065316783589,2017,6,283064955447491,'3.00'),(283065316783606,2017,7,283064955447491,'6.00'),(283065316783620,2017,8,283064955447491,'7.00'),(283065316783636,2017,9,283064955447491,'5.00'),(283065316783650,2017,10,283064955447491,'7.00'),(283065316783664,2017,11,283064955447491,'9.00'),(283065316783678,2017,12,283064955447491,'4.00'),(283065316783692,2018,1,283064955447491,'7.00'),(283065316783704,2018,2,283064955447491,'9.00'),(283065316783717,2018,3,283064955447491,'3.00'),(283065316783737,2018,4,283064955447491,'2.00'),(283065316783749,2018,5,283064955447491,'8.00'),(283065316783761,2018,6,283064955447491,'5.00'),(283065316783773,2018,7,283064955447491,'9.00'),(283065316783787,2018,8,283064955447491,'4.00'),(283065316783800,2018,9,283064955447491,'6.00'),(283065316783812,2018,10,283064955447491,'8.00'),(283065316783824,2018,11,283064955447491,'4.00'),(283065316783836,2018,12,283064955447491,'6.00');
UNLOCK TABLES;
/*!40000 ALTER TABLE `penjualan` ENABLE KEYS */;

--
-- Table structure for table `peramalan`
--

DROP TABLE IF EXISTS `peramalan`;
CREATE TABLE `peramalan` (
  `peramalan_id` bigint(20) NOT NULL default '0',
  `tahun` int(4) default '0',
  `bulan` int(2) default '0',
  `barang_id` bigint(20) default '0',
  `alpha` decimal(1,1) default '0.0',
  `smoothing_single` decimal(22,2) default '0.00',
  `smoothing_double` decimal(22,2) default '0.00',
  `nilai_a` decimal(22,2) default '0.00',
  `nilai_b` decimal(22,2) default '0.00',
  `nilai_m` decimal(22,2) default '0.00',
  `peramalan` decimal(22,2) default '0.00',
  PRIMARY KEY  (`peramalan_id`),
  UNIQUE KEY `tahunBulanBarangAlpha` (`tahun`,`bulan`,`barang_id`,`alpha`),
  KEY `barang` (`barang_id`),
  KEY `tahun` (`tahun`),
  KEY `bulan` (`bulan`),
  KEY `alpha` (`alpha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `peramalan`
--


/*!40000 ALTER TABLE `peramalan` DISABLE KEYS */;
LOCK TABLES `peramalan` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `peramalan` ENABLE KEYS */;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `role_id` bigint(20) NOT NULL default '0',
  `nama` varchar(25) default NULL,
  PRIMARY KEY  (`role_id`),
  UNIQUE KEY `nama` (`nama`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role`
--


/*!40000 ALTER TABLE `role` DISABLE KEYS */;
LOCK TABLES `role` WRITE;
INSERT INTO `role` VALUES (283064958650428,'Admin'),(283064959792634,'User');
UNLOCK TABLES;
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

--
-- Table structure for table `role_detail`
--

DROP TABLE IF EXISTS `role_detail`;
CREATE TABLE `role_detail` (
  `role_detail_id` bigint(20) NOT NULL default '0',
  `role_id` bigint(20) default NULL,
  `kode_menu` int(4) default NULL,
  `granted` int(1) default NULL,
  PRIMARY KEY  (`role_detail_id`),
  UNIQUE KEY `roleIdKode` (`role_id`,`kode_menu`),
  KEY `roleId` (`role_id`),
  KEY `kode` (`kode_menu`),
  KEY `grant` (`granted`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `role_detail`
--


/*!40000 ALTER TABLE `role_detail` DISABLE KEYS */;
LOCK TABLES `role_detail` WRITE;
INSERT INTO `role_detail` VALUES (283064958650438,283064958650428,0,1),(283064958650444,283064958650428,1,1),(283064958650450,283064958650428,2,1),(283064958650456,283064958650428,3,1),(283064958650462,283064958650428,4,1),(283064958650468,283064958650428,5,1),(283064958650473,283064958650428,6,1),(283064958650479,283064958650428,7,1),(283064959792643,283064959792634,0,0),(283064959792652,283064959792634,1,0),(283064959792661,283064959792634,2,1),(283064959792669,283064959792634,3,1),(283064959792678,283064959792634,4,1),(283064959792687,283064959792634,5,1),(283064959792696,283064959792634,6,0),(283064959792705,283064959792634,7,0);
UNLOCK TABLES;
/*!40000 ALTER TABLE `role_detail` ENABLE KEYS */;

--
-- Table structure for table `system_user`
--

DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `system_user_id` bigint(20) NOT NULL default '0',
  `nama` varchar(50) default NULL,
  `username` varchar(15) default NULL,
  `password` varchar(64) default NULL,
  `role_id` bigint(20) default '0',
  `status` int(1) default '0',
  PRIMARY KEY  (`system_user_id`),
  UNIQUE KEY `nama` (`nama`),
  UNIQUE KEY `username` (`username`),
  KEY `password` (`password`),
  KEY `status` (`status`),
  KEY `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `system_user`
--


/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
LOCK TABLES `system_user` WRITE;
INSERT INTO `system_user` VALUES (283064960236458,'I Nyoman Admin','admin','21232f297a57a5a743894a0e4a801fc3',283064958650428,1),(283064968719358,'I ketut user','user','ee11cbb19052e40b07aac0ca060c23ee',283064959792634,1),(283065049402815,'Rulintia','ulin','256f401c494288d80229f2e039264b1b',283064958650428,1);
UNLOCK TABLES;
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;

--
-- Table structure for table `tmp_peramalan`
--

DROP TABLE IF EXISTS `tmp_peramalan`;
CREATE TABLE `tmp_peramalan` (
  `peramalan_id` bigint(20) NOT NULL default '0',
  `tahun` int(4) default '0',
  `bulan` int(2) default '0',
  `barang_id` bigint(20) default '0',
  `alpha` decimal(1,1) default '0.0',
  `smoothing_single` decimal(22,2) default '0.00',
  `smoothing_double` decimal(22,2) default '0.00',
  `nilai_a` decimal(22,2) default '0.00',
  `nilai_b` decimal(22,2) default '0.00',
  `nilai_m` decimal(22,2) default '0.00',
  `peramalan` decimal(22,2) default '0.00',
  PRIMARY KEY  (`peramalan_id`),
  UNIQUE KEY `tahunBulanBarangAlpha` (`tahun`,`bulan`,`barang_id`,`alpha`),
  KEY `barang` (`barang_id`),
  KEY `tahun` (`tahun`),
  KEY `bulan` (`bulan`),
  KEY `alpha` (`alpha`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tmp_peramalan`
--


/*!40000 ALTER TABLE `tmp_peramalan` DISABLE KEYS */;
LOCK TABLES `tmp_peramalan` WRITE;
UNLOCK TABLES;
/*!40000 ALTER TABLE `tmp_peramalan` ENABLE KEYS */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

