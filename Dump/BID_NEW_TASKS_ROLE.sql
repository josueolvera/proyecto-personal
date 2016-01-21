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
  UNIQUE KEY `UNIQUE_TASK_ROL` (`ID_SYSTEM_ROLE`,`ID_TASK`),
  KEY `FK_TASKS_ROLE1` (`ID_TASK`),
  CONSTRAINT `FK_TASKS_ROLE1` FOREIGN KEY (`ID_TASK`) REFERENCES `C_TASKS` (`ID_TASK`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TASKS_ROLE2` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `TASKS_ROLE`
--

LOCK TABLES `TASKS_ROLE` WRITE;
/*!40000 ALTER TABLE `TASKS_ROLE` DISABLE KEYS */;
INSERT INTO `TASKS_ROLE` VALUES (1,1,1,'2015-11-04 22:56:26'),(2,1,2,'2015-11-06 20:10:15'),(3,1,3,'2015-11-06 20:18:14'),(3,2,4,'2015-11-13 23:23:43'),(1,4,6,'2015-11-17 23:32:30'),(1,5,7,'2015-11-18 00:53:40'),(1,6,8,'2015-11-18 00:53:40'),(1,8,10,'2015-11-18 23:27:49'),(1,10,13,'2015-11-19 18:11:36'),(1,12,15,'2015-11-20 17:02:55'),(1,13,16,'2015-11-20 17:02:55'),(1,14,17,'2015-11-20 17:02:55'),(1,15,18,'2015-11-20 23:47:04'),(1,16,19,'2015-11-20 23:47:04'),(1,17,20,'2015-11-23 15:31:47'),(1,18,21,'2015-11-23 23:21:34'),(1,19,22,'2015-11-25 19:02:01'),(1,20,23,'2015-11-25 19:02:01'),(1,21,24,'2015-11-27 16:13:31'),(1,22,25,'2015-11-27 16:47:03'),(1,23,26,'2015-11-27 18:00:30'),(1,24,27,'2015-11-27 23:38:32'),(1,25,28,'2015-11-27 23:38:32'),(1,26,29,'2015-11-28 00:16:48'),(1,30,30,'2015-12-01 16:21:50'),(1,32,31,'2015-12-02 23:38:17'),(1,33,32,'2015-12-03 18:57:44'),(1,34,34,'2015-12-03 19:01:12'),(1,35,35,'2015-12-03 23:18:07'),(1,36,36,'2015-12-04 18:45:36'),(1,37,37,'2015-12-04 23:36:40'),(3,38,38,'2015-12-07 18:30:02'),(3,39,39,'2015-12-07 19:18:17'),(3,40,40,'2015-12-07 19:18:17'),(1,42,41,'2015-12-09 16:29:47'),(1,43,42,'2015-12-09 23:15:03'),(1,27,43,'2015-12-09 23:19:02'),(1,45,44,'2015-12-10 22:04:09'),(1,46,45,'2015-12-11 18:39:17'),(1,47,46,'2015-12-11 19:28:08'),(1,48,47,'2015-12-16 18:36:55'),(1,49,48,'2015-12-17 19:51:08'),(1,50,49,'2015-12-21 18:25:36'),(1,51,50,'2015-12-22 17:07:09'),(1,52,51,'2015-12-22 23:41:20'),(1,53,53,'2015-12-23 19:53:19'),(1,54,54,'2015-12-28 22:44:30'),(1,55,55,'2015-12-29 21:57:37'),(1,56,56,'2015-12-29 23:47:34'),(1,57,57,'2015-12-30 22:50:02'),(1,58,58,'2015-12-30 23:47:01'),(1,59,59,'2015-12-31 01:00:38'),(1,60,60,'2016-01-04 20:14:53'),(1,61,61,'2016-01-05 23:11:14'),(1,62,62,'2016-01-06 00:43:52'),(1,63,63,'2016-01-06 22:44:44'),(1,64,65,'2016-01-06 23:57:42'),(1,65,66,'2016-01-06 23:57:42'),(1,66,67,'2016-01-06 23:57:42'),(1,67,68,'2016-01-07 18:44:01'),(1,68,69,'2016-01-07 19:18:01'),(1,69,70,'2016-01-07 19:48:19'),(1,70,71,'2016-01-08 00:16:00'),(1,71,72,'2016-01-11 19:33:05'),(1,72,73,'2016-01-11 19:40:18'),(1,73,74,'2016-01-11 22:06:55'),(1,74,75,'2016-01-15 23:42:14'),(1,75,76,'2016-01-19 22:57:21'),(1,76,77,'2016-01-20 00:15:14'),(1,77,78,'2016-01-20 17:03:55'),(1,78,79,'2016-01-20 22:55:18');
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

-- Dump completed on 2016-01-21 17:37:05
