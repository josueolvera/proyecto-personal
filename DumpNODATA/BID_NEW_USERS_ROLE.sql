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
-- Table structure for table `USERS_ROLE`
--

DROP TABLE IF EXISTS `USERS_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USERS_ROLE` (
  `ID_USER_ROLE` int(11) NOT NULL AUTO_INCREMENT,
  `ID_USER` int(11) NOT NULL,
  `ID_SYSTEM_ROLE` int(11) NOT NULL,
  `CREATION_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_USER_ROLE`),
  KEY `FK2w5c2aulk0lx1sp25o8f9jmdd` (`ID_SYSTEM_ROLE`),
  KEY `FKlgjo5odvjowk3lqb6r4tgio0` (`ID_USER`),
  CONSTRAINT `FKlgjo5odvjowk3lqb6r4tgio0` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`),
  CONSTRAINT `FK2w5c2aulk0lx1sp25o8f9jmdd` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`),
  CONSTRAINT `FK_USERS_ROLE1` FOREIGN KEY (`ID_USER`) REFERENCES `USERS` (`ID_USER`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USERS_ROLE2` FOREIGN KEY (`ID_SYSTEM_ROLE`) REFERENCES `SYSTEM_ROLES` (`ID_SYSTEM_ROLE`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-04-20 18:57:04
