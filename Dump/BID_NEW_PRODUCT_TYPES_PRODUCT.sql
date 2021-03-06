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
-- Table structure for table `PRODUCT_TYPES_PRODUCT`
--

DROP TABLE IF EXISTS `PRODUCT_TYPES_PRODUCT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRODUCT_TYPES_PRODUCT` (
  `ID_PRODUCT_TYPE_PRODUCT` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PRODUCT_TYPE` int(11) NOT NULL,
  `ID_PRODUCT` int(11) NOT NULL,
  `ID_ACCESS_LEVEL` int(11) NOT NULL,
  PRIMARY KEY (`ID_PRODUCT_TYPE_PRODUCT`),
  KEY `FK_PRODUCT_TYPES_PRODUCT_1_idx` (`ID_PRODUCT_TYPE`),
  KEY `FK_PRODUCT_TYPES_PRODUCT_2_idx` (`ID_PRODUCT`),
  CONSTRAINT `FKs5b8c7nv3r9y5cnlm8wq81np2` FOREIGN KEY (`ID_PRODUCT_TYPE`) REFERENCES `C_PRODUCT_TYPES` (`ID_PRODUCT_TYPE`),
  CONSTRAINT `FK2njj6ix154fsoa7bw3e99x9ek` FOREIGN KEY (`ID_PRODUCT`) REFERENCES `C_PRODUCTS` (`ID_PRODUCT`),
  CONSTRAINT `FK_PRODUCT_TYPES_PRODUCT_1` FOREIGN KEY (`ID_PRODUCT_TYPE`) REFERENCES `C_PRODUCT_TYPES` (`ID_PRODUCT_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PRODUCT_TYPES_PRODUCT_2` FOREIGN KEY (`ID_PRODUCT`) REFERENCES `C_PRODUCTS` (`ID_PRODUCT`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRODUCT_TYPES_PRODUCT`
--

LOCK TABLES `PRODUCT_TYPES_PRODUCT` WRITE;
/*!40000 ALTER TABLE `PRODUCT_TYPES_PRODUCT` DISABLE KEYS */;
INSERT INTO `PRODUCT_TYPES_PRODUCT` VALUES (1,1,1,1),(2,1,2,1),(3,8,14,1);
/*!40000 ALTER TABLE `PRODUCT_TYPES_PRODUCT` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:49
