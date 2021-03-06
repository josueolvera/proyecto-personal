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
-- Table structure for table `AUTHORIZATIONS`
--

DROP TABLE IF EXISTS `AUTHORIZATIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTHORIZATIONS` (
  `ID_AUTHORIZATION` int(11) NOT NULL AUTO_INCREMENT,
  `FOLIO` varchar(40) NOT NULL,
  `ID_USER` int(11) NOT NULL,
  `ID_AUTHORIZATION_STATUS` int(11) DEFAULT NULL,
  `DETAILS` text,
  `AUTHORIZATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `ID_ACCESS_LEVEL` int(11) DEFAULT '1',
  PRIMARY KEY (`ID_AUTHORIZATION`),
  KEY `FK_AUTHORIZATIONS2` (`FOLIO`),
  KEY `FK769rn1bpxyxbcpn1cbqqtecii` (`ID_AUTHORIZATION_STATUS`),
  KEY `FKbo5kg9ugkdo9sxmcockly7nqf` (`ID_USER`),
  CONSTRAINT `FKbo5kg9ugkdo9sxmcockly7nqf` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`),
  CONSTRAINT `FK769rn1bpxyxbcpn1cbqqtecii` FOREIGN KEY (`ID_AUTHORIZATION_STATUS`) REFERENCES `C_AUTHORIZATION_STATUS` (`ID_AUTHORIZATION_STATUS`),
  CONSTRAINT `FK_AUTHORIZATIONS1` FOREIGN KEY (`ID_AUTHORIZATION_STATUS`) REFERENCES `C_AUTHORIZATION_STATUS` (`ID_AUTHORIZATION_STATUS`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_AUTHORIZATIONS2` FOREIGN KEY (`FOLIO`) REFERENCES `C_FOLIOS` (`FOLIO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_AUTHORIZATIONS3` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `AUTHORIZATIONS`
--

LOCK TABLES `AUTHORIZATIONS` WRITE;
/*!40000 ALTER TABLE `AUTHORIZATIONS` DISABLE KEYS */;
INSERT INTO `AUTHORIZATIONS` VALUES (2,'efh24751',2,2,NULL,'2015-12-07 21:50:07',1),(3,'efh24751',1,1,NULL,'2015-12-05 00:16:13',1),(4,'efh24751',2,3,NULL,'2015-12-07 21:55:23',1),(5,'efh24751',2,1,NULL,'2015-12-07 18:36:26',1),(6,'efh24751',2,1,NULL,'2015-12-07 18:39:18',1),(7,'efh24751',2,1,NULL,'2015-12-07 18:41:44',1);
/*!40000 ALTER TABLE `AUTHORIZATIONS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:47
