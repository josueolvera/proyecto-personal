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
-- Table structure for table `ACCESS_LEVELS_ROLE`
--

DROP TABLE IF EXISTS `ACCESS_LEVELS_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ACCESS_LEVELS_ROLE` (
  `ID_SYSTEM_ROLE` int(11) NOT NULL,
  `ID_ACCESS_LEVEL` int(11) NOT NULL,
  `ID_ACCESS_LEVEL_ROLE` int(11) NOT NULL AUTO_INCREMENT,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_ACCESS_LEVEL_ROLE`),
  KEY `FK_ACCESS_LEVELS_ROLE1` (`ID_ACCESS_LEVEL`),
  KEY `FK_ACCESS_LEVELS_ROLE2` (`ID_SYSTEM_ROLE`),
  CONSTRAINT `FK_ACCESS_LEVELS_ROLE1` FOREIGN KEY (`ID_ACCESS_LEVEL`) REFERENCES `ACCESS_LEVEL` (`ID_ACCESS_LEVEL`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ACCESS_LEVELS_ROLE2` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ACCESS_LEVELS_ROLE`
--

LOCK TABLES `ACCESS_LEVELS_ROLE` WRITE;
/*!40000 ALTER TABLE `ACCESS_LEVELS_ROLE` DISABLE KEYS */;
/*!40000 ALTER TABLE `ACCESS_LEVELS_ROLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-01-04 18:44:01
