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
-- Table structure for table `STOCKS`
--

DROP TABLE IF EXISTS `STOCKS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STOCKS` (
  `ID_STOCK` int(11) NOT NULL AUTO_INCREMENT,
  `ID_ARTICLE` int(11) NOT NULL,
  `ID_DW_ENTERPRISE` int(11) DEFAULT NULL,
  `SERIAL_NUMBER` varchar(255) DEFAULT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `FOLIO` varchar(40) DEFAULT NULL,
  `STOCK_FOLIO` varchar(2048) DEFAULT NULL,
  `PURCHASE_PRICE` decimal(12,2) DEFAULT '0.00',
  `ID_ARTICLE_STATUS` int(11) DEFAULT NULL,
  `ID_CURRENCY` int(11) NOT NULL DEFAULT '1',
  `ID_ACCESS_LEVEL` int(11) DEFAULT '1',
  PRIMARY KEY (`ID_STOCK`),
  KEY `FK_STOCKS2_idx` (`ID_CURRENCY`),
  KEY `FKotv2d0a92u8kd2rmebuhm9wdl` (`ID_ARTICLE`),
  KEY `FKch52ey2g9x1nasypyj8jddcly` (`ID_ARTICLE_STATUS`),
  KEY `FKab29vccsug7dhrhc3ipy8kbca` (`ID_DW_ENTERPRISE`),
  CONSTRAINT `FKab29vccsug7dhrhc3ipy8kbca` FOREIGN KEY (`ID_DW_ENTERPRISE`) REFERENCES `DW_ENTERPRISES` (`ID_DW_ENTERPRISE`),
  CONSTRAINT `FKch52ey2g9x1nasypyj8jddcly` FOREIGN KEY (`ID_ARTICLE_STATUS`) REFERENCES `C_ARTICLE_STATUS` (`ID_ARTICLE_STATUS`),
  CONSTRAINT `FKotv2d0a92u8kd2rmebuhm9wdl` FOREIGN KEY (`ID_ARTICLE`) REFERENCES `C_ARTICLES` (`ID_ARTICLE`),
  CONSTRAINT `FK_STOCKS1` FOREIGN KEY (`ID_ARTICLE`) REFERENCES `C_ARTICLES` (`ID_ARTICLE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_STOCKS2` FOREIGN KEY (`ID_CURRENCY`) REFERENCES `C_CURRENCIES` (`ID_CURRENCY`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_STOCKS_C_ARTICLE_STATUS` FOREIGN KEY (`ID_ARTICLE_STATUS`) REFERENCES `C_ARTICLE_STATUS` (`ID_ARTICLE_STATUS`),
  CONSTRAINT `STOCKS_DW_ENTERPRISES_ID_DW_ENTERPRISE_fk` FOREIGN KEY (`ID_DW_ENTERPRISE`) REFERENCES `DW_ENTERPRISES` (`ID_DW_ENTERPRISE`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `STOCKS`
--

LOCK TABLES `STOCKS` WRITE;
/*!40000 ALTER TABLE `STOCKS` DISABLE KEYS */;
INSERT INTO `STOCKS` VALUES (1,1,88,'a123451','2015-12-08 00:23:30','efh24751','afgsh120',10901.00,3,1,1),(2,2,87,'987y','2015-12-09 16:52:32','efh24751','afgsh7654',3560.66,1,1,1),(3,2,86,'74747jjd38udjrf','2015-12-09 16:58:52','efh24751','afgsh577',3120.45,1,1,1),(4,1,86,'jgj5jdjd','2015-12-28 20:03:35','efh24751','afgsh876',10000.00,2,1,1);
/*!40000 ALTER TABLE `STOCKS` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:50
