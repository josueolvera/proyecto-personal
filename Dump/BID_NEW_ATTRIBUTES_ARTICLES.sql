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
-- Table structure for table `ATTRIBUTES_ARTICLES`
--

DROP TABLE IF EXISTS `ATTRIBUTES_ARTICLES`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ATTRIBUTES_ARTICLES` (
  `ID_ATTRIBUTE_ARTICLE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_ARTICLE` int(11) NOT NULL,
  `ID_ATTRIBUTE` int(11) NOT NULL,
  `ID_DATA_TYPE` int(11) NOT NULL,
  `ID_ACCESS_LEVEL` int(11) DEFAULT '1',
  PRIMARY KEY (`ID_ATTRIBUTE_ARTICLE`),
  KEY `FKqp7bbr6cmyqh6befocx7fy8hl` (`ID_ARTICLE`),
  KEY `FKq737bilnn39fn0u4b7eiqgyf9` (`ID_ATTRIBUTE`),
  KEY `FK427xv5hk2j8en1a90x2m2wqgj` (`ID_DATA_TYPE`),
  CONSTRAINT `FK427xv5hk2j8en1a90x2m2wqgj` FOREIGN KEY (`ID_DATA_TYPE`) REFERENCES `C_DATA_TYPES` (`ID_DATA_TYPE`),
  CONSTRAINT `FKq737bilnn39fn0u4b7eiqgyf9` FOREIGN KEY (`ID_ATTRIBUTE`) REFERENCES `C_ATTRIBUTES` (`ID_ATTRIBUTE`),
  CONSTRAINT `FKqp7bbr6cmyqh6befocx7fy8hl` FOREIGN KEY (`ID_ARTICLE`) REFERENCES `C_ARTICLES` (`ID_ARTICLE`),
  CONSTRAINT `FK_ATTRIBUTES_ARTICLES1` FOREIGN KEY (`ID_ARTICLE`) REFERENCES `C_ARTICLES` (`ID_ARTICLE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ATTRIBUTES_ARTICLES2` FOREIGN KEY (`ID_ATTRIBUTE`) REFERENCES `C_ATTRIBUTES` (`ID_ATTRIBUTE`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ATTRIBUTES_ARTICLES3` FOREIGN KEY (`ID_DATA_TYPE`) REFERENCES `C_DATA_TYPES` (`ID_DATA_TYPE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ATTRIBUTES_ARTICLES`
--

LOCK TABLES `ATTRIBUTES_ARTICLES` WRITE;
/*!40000 ALTER TABLE `ATTRIBUTES_ARTICLES` DISABLE KEYS */;
INSERT INTO `ATTRIBUTES_ARTICLES` VALUES (1,1,5,1,1),(2,2,6,2,1),(3,2,5,1,1),(4,1,7,1,1),(5,2,7,1,1);
/*!40000 ALTER TABLE `ATTRIBUTES_ARTICLES` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-02-17 17:36:46
