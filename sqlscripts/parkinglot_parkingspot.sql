-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: parkinglot
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `parkingspot`
--

DROP TABLE IF EXISTS `parkingspot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `parkingspot` (
  `SPOT_NUM` varchar(4) NOT NULL,
  `SPOT_TYPE` enum('Car','Motorcycle','Electric') NOT NULL,
  `VEH_LIS_NUM` varchar(12) DEFAULT NULL,
  `FLOOR_NUM` enum('Floor 1','Floor 2','Floor 3') NOT NULL,
  `PARKED_AT` datetime DEFAULT NULL,
  `TICKET_ID` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`SPOT_NUM`),
  UNIQUE KEY `SPOT_NUM_UNIQUE` (`SPOT_NUM`),
  UNIQUE KEY `VEH_LIS_NUM_UNIQUE` (`VEH_LIS_NUM`),
  UNIQUE KEY `TICKET_ID_UNIQUE` (`TICKET_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parkingspot`
--

LOCK TABLES `parkingspot` WRITE;
/*!40000 ALTER TABLE `parkingspot` DISABLE KEYS */;
INSERT INTO `parkingspot` VALUES ('001','Car',NULL,'Floor 3',NULL,NULL),('002','Car','35A-192.681','Floor 1','2023-07-01 17:17:04','002'),('003','Electric','20MD-2144','Floor 2','2023-06-10 10:01:26','003'),('004','Car',NULL,'Floor 3',NULL,NULL),('005','Motorcycle','29H2-5599','Floor 3','2023-06-05 18:02:50','005'),('007','Motorcycle',NULL,'Floor 1',NULL,NULL),('008','Motorcycle',NULL,'Floor 2',NULL,NULL),('010','Car',NULL,'Floor 3',NULL,NULL),('012','Car',NULL,'Floor 1',NULL,NULL),('014','Motorcycle',NULL,'Floor 2',NULL,NULL),('015','Electric',NULL,'Floor 1',NULL,NULL),('017','Electric',NULL,'Floor 2',NULL,NULL);
/*!40000 ALTER TABLE `parkingspot` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-01 22:12:27
