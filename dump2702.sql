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
-- Table structure for table `fiches_de_frais`
--

DROP TABLE IF EXISTS `fiches_de_frais`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fiches_de_frais` (
  `id_fk_utilisateur` int NOT NULL AUTO_INCREMENT,
  `fk_utilisateurs` varchar(256) NOT NULL,
  `Date` int NOT NULL,
  `Nuitée` int DEFAULT NULL,
  `Kilometre` int DEFAULT NULL,
  PRIMARY KEY (`id_fk_utilisateur`),
  KEY `fk_utilsateurs1_idx` (`fk_utilisateurs`),
  CONSTRAINT `fk_utilsateurs1` FOREIGN KEY (`fk_utilisateurs`) REFERENCES `utilisateur` (`matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fiches_de_frais`
--

LOCK TABLES `fiches_de_frais` WRITE;
/*!40000 ALTER TABLE `fiches_de_frais` DISABLE KEYS */;
INSERT INTO `fiches_de_frais` VALUES (1,'c0c170ee-a0a3-11ed-9358-00155dba2bde',1,1,1),(2,'cdbcf45f-a098-11ed-9358-00155dba2bde',1,1,1),(3,'ebb0f6be-a098-11ed-9358-00155dba2bde',1,1,1),(4,'efa4fb1c-a098-11ed-9358-00155dba2bde',1,1,1);
/*!40000 ALTER TABLE `fiches_de_frais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frais_forfaitises`
--

DROP TABLE IF EXISTS `frais_forfaitises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frais_forfaitises` (
  `id_frais forfaitisés` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `cost` double NOT NULL,
  PRIMARY KEY (`id_frais forfaitisés`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frais_forfaitises`
--

LOCK TABLES `frais_forfaitises` WRITE;
/*!40000 ALTER TABLE `frais_forfaitises` DISABLE KEYS */;
INSERT INTO `frais_forfaitises` VALUES (1,'nuitee',80),(2,'midi',29);
/*!40000 ALTER TABLE `frais_forfaitises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_agent`
--

DROP TABLE IF EXISTS `type_agent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_agent` (
  `id_type_agent` int unsigned NOT NULL AUTO_INCREMENT,
  `nom_agent` varchar(256) NOT NULL,
  PRIMARY KEY (`id_type_agent`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_agent`
--

LOCK TABLES `type_agent` WRITE;
/*!40000 ALTER TABLE `type_agent` DISABLE KEYS */;
INSERT INTO `type_agent` VALUES (1,'visiteur'),(2,'comptable');
/*!40000 ALTER TABLE `type_agent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_vehicule`
--

DROP TABLE IF EXISTS `type_vehicule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `type_vehicule` (
  `id_type_vehicule` int unsigned NOT NULL AUTO_INCREMENT,
  `Nom_vehicule` varchar(256) NOT NULL,
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
  `matricule` varchar(36) NOT NULL,
  `genre` int NOT NULL DEFAULT '0',
  `nom` varchar(256) NOT NULL,
  `prenom` varchar(256) NOT NULL,
  `username` varchar(256) NOT NULL,
  `mot_de_passe` varchar(256) NOT NULL,
  `fk_type_agent` int unsigned DEFAULT NULL,
  `fk_type_vehicule` int unsigned DEFAULT NULL,
  PRIMARY KEY (`matricule`),
  KEY `fk_véhicule_idx` (`fk_type_vehicule`),
  KEY `fk_agent_idx` (`fk_type_agent`),
  CONSTRAINT `fk_agent` FOREIGN KEY (`fk_type_agent`) REFERENCES `type_agent` (`id_type_agent`),
  CONSTRAINT `fk_véhicule` FOREIGN KEY (`fk_type_vehicule`) REFERENCES `type_vehicule` (`id_type_vehicule`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `utilisateur`
--

LOCK TABLES `utilisateur` WRITE;
/*!40000 ALTER TABLE `utilisateur` DISABLE KEYS */;
INSERT INTO `utilisateur` VALUES ('c0c170ee-a0a3-11ed-9358-00155dba2bde',1,'CABESTAING','MAITE','maite.cabestaing','a3ee9d88c3e0ee59bdc9dfe051e18d2d5d38ac0eda8f804f50226bdef4a78848',2,3),('cdbcf45f-a098-11ed-9358-00155dba2bde',0,'BARGE','AYMERIC','aymeric.barge','687930558e78ea4c16e6d65500ab8f46debd564a65a5e5c9f57b84c6e7297f69',1,4),('ebb0f6be-a098-11ed-9358-00155dba2bde',0,'DINDIN','THOMAS','thomas.dindin','8c9a013ab70c0434313e3e881c310b9ff24aff1075255ceede3f2c239c231623',1,3),('efa4fb1c-a098-11ed-9358-00155dba2bde',0,'CACHEUX','CONSTANT','constant.cacheux','9a82da353db492a8e2892334de51fec091555cb2c4cc996e28bfd02ec4f1a9ad',2,2);
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

-- Dump completed on 2023-02-27 13:17:10
