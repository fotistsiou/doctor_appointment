-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: doctor_appointment
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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `appointment_date` varchar(255) DEFAULT NULL,
  `appointment_time` varchar(255) DEFAULT NULL,
  `booked` int NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `doctor_id` bigint NOT NULL,
  `patient_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1l62fhlqqc08wmgrvn7hjtfom` (`doctor_id`),
  KEY `FKg90ck1kd0p4rbamwc22jd2oql` (`patient_id`),
  CONSTRAINT `FK1l62fhlqqc08wmgrvn7hjtfom` FOREIGN KEY (`doctor_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKg90ck1kd0p4rbamwc22jd2oql` FOREIGN KEY (`patient_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (1,'2023-09-21','12:00',0,NULL,1,NULL),(2,'2023-09-21','12:30',1,'Έλεγχος αιματολογικών εξετάσεων.',1,2),(3,'2023-09-21','13:00',0,NULL,1,NULL),(4,'2023-09-21','13:30',0,NULL,1,NULL),(5,'2023-09-21','14:00',0,NULL,1,NULL),(6,'2023-09-21','14:30',1,'Πόνος στο στομάχι.',1,6),(7,'2023-09-21','15:00',1,'Πόνο στην κοιλιά.',1,3),(8,'2023-09-21','15:30',0,NULL,1,NULL),(9,'2023-09-21','16:00',0,NULL,1,NULL),(10,'2023-09-21','16:30',0,NULL,1,NULL),(11,'2023-09-21','17:00',0,NULL,1,NULL),(12,'2023-09-21','17:30',0,NULL,1,NULL),(13,'2023-09-21','18:00',0,NULL,1,NULL),(14,'2023-09-22','14:00',1,'Πιθανή μόλυνση στο μάτι.',4,2),(15,'2023-09-22','14:30',0,NULL,4,NULL),(16,'2023-09-22','15:00',0,NULL,4,NULL),(17,'2023-09-22','15:30',1,'Ερεθισμός στο μάτι.',4,3),(18,'2023-09-22','16:00',0,NULL,4,NULL),(19,'2023-09-22','16:30',0,NULL,4,NULL),(20,'2023-09-23','12:00',1,'Εμβόλιο ανεμοβλογίας.',5,6),(21,'2023-09-23','12:30',0,NULL,5,NULL),(22,'2023-09-23','13:00',0,NULL,5,NULL);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_DOCTOR'),(2,'ROLE_PATIENT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `specialization` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Πλάτωνος 134, 17674 Καλλιθέα','f.tsioumas@domain.com','Φώτης','Τσιούμας','$2a$10$JY2CX9WZ8TYsCszkocCD1eblzXapeQuVnsdd5Ri5UUzB82.4B0rFC','Παθολόγος','2101245789'),(2,NULL,'a.sideri@domain.com','Άσπα','Σιδέρη','$2a$10$JY2CX9WZ8TYsCszkocCD1eblzXapeQuVnsdd5Ri5UUzB82.4B0rFC',NULL,'2101248963'),(3,NULL,'g.papatsikhs@domain.com','Γιάννης','Παπατσίκης','$2a$10$JY2CX9WZ8TYsCszkocCD1eblzXapeQuVnsdd5Ri5UUzB82.4B0rFC',NULL,'6984470955'),(4,'Ακροπόλεως 4, 14121 Ηράκλειο','p.takanhs@domain.com','Πέτρος','Τακάνης','$2a$10$JY2CX9WZ8TYsCszkocCD1eblzXapeQuVnsdd5Ri5UUzB82.4B0rFC','Οφθαλμίατρος','6985632147'),(5,'Σωκράτους 38, 16673 Βούλα','h.panagiotou@domain.com','Ηλίας','Παναγιώτου','$2a$10$ZgaolHjsLrhkL5mgUAZHZOYpyQ10EfacY6CztHK2Q5QDBDPH2bnCm','Παιδίατρος','2104578963'),(6,NULL,'m.batakh@domain.com','Μαρία','Μπατάκη','$2a$10$NsWiNYwg7zRMXuMxYcOqWODH2yXTUc1IHg/fs9xIL56OcMTQ/nYQO',NULL,'6985623147');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `FKrhfovtciq1l558cw6udg0h0d3` (`role_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKrhfovtciq1l558cw6udg0h0d3` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (1,1),(4,1),(5,1),(2,2),(3,2),(6,2);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-22  3:18:40
