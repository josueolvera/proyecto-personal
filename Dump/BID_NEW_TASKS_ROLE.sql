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
-- Table structure for table `TASKS_ROLE`
--

DROP TABLE IF EXISTS `TASKS_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `TASKS_ROLE` (
  `ID_SYSTEM_ROLE` int(11) NOT NULL,
  `ID_TASK` int(11) NOT NULL,
  `ID_TASK_ROLE` int(11) NOT NULL AUTO_INCREMENT,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_TASK_ROLE`),
  KEY `FK_TASKS_ROLE1` (`ID_TASK`),
  KEY `FK_TASKS_ROLE2` (`ID_SYSTEM_ROLE`),
  CONSTRAINT `FK_TASKS_ROLE1` FOREIGN KEY (`ID_TASK`) REFERENCES `C_TASKS` (`ID_TASK`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TASKS_ROLE2` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TASKS_ROLE`
--

LOCK TABLES `TASKS_ROLE` WRITE;
/*!40000 ALTER TABLE `TASKS_ROLE` DISABLE KEYS */;
INSERT INTO `TASKS_ROLE` VALUES (1,1,1,'2015-11-04 22:56:26'),(2,1,2,'2015-11-06 20:10:15'),(3,1,3,'2015-11-06 20:18:14'),(3,2,4,'2015-11-13 23:23:43'),(1,4,6,'2015-11-17 23:32:30'),(1,5,7,'2015-11-18 00:53:40'),(1,6,8,'2015-11-18 00:53:40'),(1,8,10,'2015-11-18 23:27:49'),(1,10,13,'2015-11-19 18:11:36'),(1,12,15,'2015-11-20 17:02:55'),(1,13,16,'2015-11-20 17:02:55'),(1,14,17,'2015-11-20 17:02:55'),(1,15,18,'2015-11-20 23:47:04'),(1,16,19,'2015-11-20 23:47:04'),(1,17,20,'2015-11-23 15:31:47'),(1,18,21,'2015-11-23 23:21:34'),(1,19,22,'2015-11-25 19:02:01'),(1,20,23,'2015-11-25 19:02:01'),(1,21,24,'2015-11-27 16:13:31'),(1,22,25,'2015-11-27 16:47:03'),(1,23,26,'2015-11-27 18:00:30'),(1,24,27,'2015-11-27 23:38:32'),(1,25,28,'2015-11-27 23:38:32'),(1,26,29,'2015-11-28 00:16:48'),(1,30,30,'2015-12-01 16:21:50'),(1,32,31,'2015-12-02 23:38:17'),(1,33,32,'2015-12-03 18:57:44'),(1,34,34,'2015-12-03 19:01:12'),(1,35,35,'2015-12-03 23:18:07'),(1,36,36,'2015-12-04 18:45:36'),(1,37,37,'2015-12-04 23:36:40');
/*!40000 ALTER TABLE `TASKS_ROLE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-04 18:45:56
