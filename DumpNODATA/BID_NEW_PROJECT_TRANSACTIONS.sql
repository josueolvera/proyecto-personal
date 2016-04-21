-- MySQL dump 10.16  Distrib 10.1.13-MariaDB, for Linux (x86_64)
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
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `PROJECT_TRANSACTIONS`
--

DROP TABLE IF EXISTS `PROJECT_TRANSACTIONS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PROJECT_TRANSACTIONS` (
  `ID_PROJECT_TRANSACTIONS` int(11) NOT NULL AUTO_INCREMENT,
  `ID_PROJECT` int(11) NOT NULL,
  `ID_TRANSACTION` int(11) NOT NULL,
  `PERCENT` decimal(10,4) NOT NULL,
  `ID_ACCESS_LEVEL` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID_PROJECT_TRANSACTIONS`),
  KEY `FK_PROJECT_TRANSACTIONS_1_idx` (`ID_PROJECT`),
  KEY `FK_PROJECT_TRANSACTIONS_2_idx` (`ID_TRANSACTION`),
  CONSTRAINT `FK_PROJECT_TRANSACTIONS_1` FOREIGN KEY (`ID_PROJECT`) REFERENCES `PROJECTS` (`ID_PROJECT`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_PROJECT_TRANSACTIONS_2` FOREIGN KEY (`ID_TRANSACTION`) REFERENCES `TRANSACTIONS` (`ID_TRANSACTION`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-20 18:57:04
