-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: projetap
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `frais forfaitisés`
--

DROP TABLE IF EXISTS `frais forfaitisés`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frais forfaitisés` (
  `id_frais forfaitisés` int NOT NULL AUTO_INCREMENT,
  `Nom du frais` varchar(45) NOT NULL,
  `Date` date NOT NULL,
  `Prix` double NOT NULL,
  PRIMARY KEY (`id_frais forfaitisés`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frais forfaitisés`
--

LOCK TABLES `frais forfaitisés` WRITE;
/*!40000 ALTER TABLE `frais forfaitisés` DISABLE KEYS */;
/*!40000 ALTER TABLE `frais forfaitisés` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type-agent`
--

DROP TABLE IF EXISTS `type-agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type-agent` (
  `id_type-agent` int unsigned NOT NULL AUTO_INCREMENT,
  `nom` varchar(256) NOT NULL,
  PRIMARY KEY (`id_type-agent`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type-agent`
--

LOCK TABLES `type-agent` WRITE;
/*!40000 ALTER TABLE `type-agent` DISABLE KEYS */;
INSERT INTO `type-agent` VALUES (1,'visiteur'),(2,'comptable');
/*!40000 ALTER TABLE `type-agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_vehicule`
--

DROP TABLE IF EXISTS `type_vehicule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_vehicule` (
  `id_type_vehicule` int unsigned NOT NULL AUTO_INCREMENT,
  `Nom` varchar(256) NOT NULL,
  `Cout` double NOT NULL,
  PRIMARY KEY (`id_type_vehicule`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_vehicule`
--

LOCK TABLES `type_vehicule` WRITE;
/*!40000 ALTER TABLE `type_vehicule` DISABLE KEYS */;
INSERT INTO `type_vehicule` VALUES (1,'4CV diesel',0.52),(2,'5/6CV diesel',0.58),(3,'4CV essence',0.68),(4,'5/6CV essence',0.67);
/*!40000 ALTER TABLE `type_vehicule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utilisateur` (
  `matricule` varchar(45) NOT NULL,
  `genre` int NOT NULL DEFAULT '0',
  `nom` varchar(256) NOT NULL,
  `prenom` varchar(256) NOT NULL,
  `username` varchar(256) NOT NULL,
  `password` varchar(256) NOT NULL,
  `fk_type_agent` int unsigned DEFAULT NULL,
  `fk_type_vehicule` int unsigned DEFAULT NULL,
  PRIMARY KEY (`matricule`),
  KEY `fk_véhicule_idx` (`fk_type_vehicule`),
  KEY `fk_agent_idx` (`fk_type_agent`),
  CONSTRAINT `fk_agent` FOREIGN KEY (`fk_type_agent`) REFERENCES `type-agent` (`id_type-agent`),
  CONSTRAINT `fk_véhicule` FOREIGN KEY (`fk_type_vehicule`) REFERENCES `type_vehicule` (`id_type_vehicule`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` VALUES ('100150569598976001',0,'BARGE','AYMERIC','aymeric.barge','687930558e78ea4c16e6d65500ab8f46debd564a65a5e5c9f57b84c6e7297f69',1,4),('100150569598976002',0,'DINDIN','THOMAS','thomas.dindin','8c9a013ab70c0434313e3e881c310b9ff24aff1075255ceede3f2c239c231623',1,3);
/*!40000 ALTER TABLE `utilisateur` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-23 15:20:39
