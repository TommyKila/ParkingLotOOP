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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `EMP_NUM` varchar(45) NOT NULL,
  `EMP_FNAME` varchar(45) NOT NULL,
  `EMP_LNAME` varchar(45) NOT NULL,
  `EMP_EMAIL` varchar(45) DEFAULT NULL,
  `EMP_PHONENUM` varchar(10) NOT NULL,
  `EMP_POS` enum('ATTENDANT','ADMIN') NOT NULL,
  `EMP_USERNAME` varchar(45) NOT NULL,
  `EMP_PASSWORD` varchar(45) NOT NULL,
  PRIMARY KEY (`EMP_NUM`),
  UNIQUE KEY `EMP_NUM_UNIQUE` (`EMP_NUM`),
  UNIQUE KEY `EMP_PHONENUM_UNIQUE` (`EMP_PHONENUM`),
  UNIQUE KEY `EMP_USERNAME_UNIQUE` (`EMP_USERNAME`),
  UNIQUE KEY `EMP_EMAIL_UNIQUE` (`EMP_EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES ('002','Thomas','Kat','ihatejerry@gmail.com','0692575473','ADMIN','pussinboot','meow'),('004','Bruce','Wayne','richaf@gmail.com','0998835477','ATTENDANT','iowntheplace','batty'),('005','Patrick','Bateman','disectgirls@gmail.com','0589735401','ADMIN','paul','allen'),('006','Ambatukam','Omaygott','dreamy@gmail.com','038165927','ATTENDANT','buss','nutting'),('009','Madd','Mikkelsen','lector@gmail.com','0918276451','ATTENDANT','hanny','canny'),('010','Kumala','Sarvesta','mrwaduh@gmail.com','0698172642','ATTENDANT','mrwaduh','thugshaker'),('011','Joe','Mama','joem@gmail.com','019482715','ATTENDANT','joe','mthfrs');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
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
