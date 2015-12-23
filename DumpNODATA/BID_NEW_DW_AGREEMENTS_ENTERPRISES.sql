CREATE DATABASE  IF NOT EXISTS `BID_NEW` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `BID_NEW`;
-- MySQL dump 10.13  Distrib 5.6.27, for debian-linux-gnu (x86_64)
--
-- Host: 192.168.1.149    Database: BID_NEW
-- ------------------------------------------------------
-- Server version	5.5.41-MariaDB

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
-- Table structure for table `DW_AGREEMENTS_ENTERPRISES`
--

DROP TABLE IF EXISTS `DW_AGREEMENTS_ENTERPRISES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `DW_AGREEMENTS_ENTERPRISES` (
  `ID_AGREEMENT_ENTERPRISE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_BRANCH` int(11) NOT NULL DEFAULT '0',
  `ID_COMPANY` int(11) NOT NULL DEFAULT '0',
  `ID_AGREEMENT` int(11) NOT NULL DEFAULT '0',
  `ID_GCOMM` int(11) NOT NULL DEFAULT '2',
  `JANUARY_C` decimal(12,2) DEFAULT '0.00',
  `FEBRUARY_C` decimal(12,2) DEFAULT '0.00',
  `MARCH_C` decimal(12,2) DEFAULT '0.00',
  `APRIL_C` decimal(12,2) DEFAULT '0.00',
  `MAY_C` decimal(12,2) DEFAULT '0.00',
  `JUNE_C` decimal(12,2) DEFAULT '0.00',
  `JULY_C` decimal(12,2) DEFAULT '0.00',
  `AUGUST_C` decimal(12,2) DEFAULT '0.00',
  `SEPTEMBER_C` decimal(12,2) DEFAULT '0.00',
  `OCTOBER_C` decimal(12,2) DEFAULT '0.00',
  `NOVEMBER_C` decimal(12,2) DEFAULT '0.00',
  `DECEMBER_C` decimal(12,2) DEFAULT '0.00',
  `TOTAL_BUDGETS_C` decimal(12,2) DEFAULT '0.00',
  `JANUARY_A` decimal(12,2) DEFAULT '0.00',
  `FEBRUARY_A` decimal(12,2) DEFAULT '0.00',
  `MARCH_A` decimal(12,2) DEFAULT '0.00',
  `APRIL_A` decimal(12,2) DEFAULT '0.00',
  `MAY_A` decimal(12,2) DEFAULT '0.00',
  `JUNE_A` decimal(12,2) DEFAULT '0.00',
  `JULY_A` decimal(12,2) DEFAULT '0.00',
  `AUGUST_A` decimal(14,2) DEFAULT '0.00',
  `SEPTEMBER_A` decimal(12,2) DEFAULT '0.00',
  `OCTOBER_A` decimal(12,2) DEFAULT '0.00',
  `NOVEMBER_A` decimal(12,2) DEFAULT '0.00',
  `DECEMBER_A` decimal(12,2) DEFAULT '0.00',
  `TOTAL_BUDGETS_A` decimal(12,2) DEFAULT '0.00',
  `YEAR` int(11) NOT NULL DEFAULT '2015',
  `UPLOADED_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `STATUS` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_AGREEMENT_ENTERPRISE`)
) ENGINE=InnoDB AUTO_INCREMENT=396 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-21 18:41:59