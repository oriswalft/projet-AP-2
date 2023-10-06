-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: projet_ap2
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
  `id_fiche_de_frais` int NOT NULL AUTO_INCREMENT,
  `fk_utilisateurs` varchar(256) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `qteNuitee` int DEFAULT NULL,
  `Kilometre` int DEFAULT NULL,
  `Repas_midi` int DEFAULT NULL,
  `verifiee` tinyint DEFAULT '0',
  PRIMARY KEY (`id_fiche_de_frais`),
  KEY `fk_utilsateurs1_idx` (`fk_utilisateurs`),
  CONSTRAINT `fk_utilsateurs1` FOREIGN KEY (`fk_utilisateurs`) REFERENCES `utilisateur` (`matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fiches_de_frais`
--

LOCK TABLES `fiches_de_frais` WRITE;
/*!40000 ALTER TABLE `fiches_de_frais` DISABLE KEYS */;
INSERT INTO `fiches_de_frais` VALUES (1,'ebb0f6be-a098-11ed-9358-00155dba2bde','2023-02-27 14:42:12',6,4,4,0),(51,'7b0af216-c71c-11ed-9d75-005056c00001','2023-03-20 13:05:34',28,9999,31,0),(52,'ebb0f6be-a098-11ed-9358-00155dba2bde','2023-03-20 13:29:15',22,4,25,1),(53,'efa4fb1c-a098-11ed-9358-00155dba2bde','2023-03-20 13:34:11',15,122,9,1),(54,'ebb0f6be-a098-11ed-9358-00155dba2bde','2023-04-13 06:23:48',10,5,7,0),(55,'7b0af216-c71c-11ed-9d75-005056c00001','2023-04-13 06:24:13',10,100,10,1);
/*!40000 ALTER TABLE `fiches_de_frais` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `frais_forfaitises`
--

DROP TABLE IF EXISTS `frais_forfaitises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frais_forfaitises` (
  `id_frais_forfaitisés` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `cost` double NOT NULL,
  PRIMARY KEY (`id_frais_forfaitisés`)
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
-- Table structure for table `frais_hors_forfaits`
--

DROP TABLE IF EXISTS `frais_hors_forfaits`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `frais_hors_forfaits` (
  `id_fraisHF` int NOT NULL AUTO_INCREMENT,
  `fk_fraisHF` varchar(512) DEFAULT NULL,
  `intitules` varchar(45) DEFAULT NULL,
  `cout` float DEFAULT NULL,
  `Date` date DEFAULT NULL,
  PRIMARY KEY (`id_fraisHF`),
  KEY `fk_utilisateurs1_idx` (`fk_fraisHF`),
  CONSTRAINT `fk_utilisateurs1` FOREIGN KEY (`fk_fraisHF`) REFERENCES `utilisateur` (`matricule`)
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `frais_hors_forfaits`
--

LOCK TABLES `frais_hors_forfaits` WRITE;
/*!40000 ALTER TABLE `frais_hors_forfaits` DISABLE KEYS */;
INSERT INTO `frais_hors_forfaits` VALUES (115,'efa4fb1c-a098-11ed-9358-00155dba2bde','MIAM MIAM LE POULET',30,'2023-03-06'),(116,'efa4fb1c-a098-11ed-9358-00155dba2bde','Poyo',40,'2023-03-08'),(124,'7b0af216-c71c-11ed-9d75-005056c00001','WEEEEEEEEEEEEEEE',50000,'2023-03-22'),(125,'efa4fb1c-a098-11ed-9358-00155dba2bde','Test',51.3,'2023-02-20'),(128,'efa4fb1c-a098-11ed-9358-00155dba2bde','Encore un test bwahahahaha',49.99,'2023-03-10'),(135,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(136,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(137,'ebb0f6be-a098-11ed-9358-00155dba2bde','Test',50,'2023-03-16'),(138,'ebb0f6be-a098-11ed-9358-00155dba2bde','Test',0,'2023-03-15'),(139,'ebb0f6be-a098-11ed-9358-00155dba2bde','Test',150,'2023-03-16'),(140,'ebb0f6be-a098-11ed-9358-00155dba2bde','Test',152,'2023-04-15'),(141,'7b0af216-c71c-11ed-9d75-005056c00001','repas soirée',55,'2023-04-14'),(142,'ebb0f6be-a098-11ed-9358-00155dba2bde','1',10,'2023-04-15'),(144,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(145,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(146,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(147,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(148,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(149,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(150,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(151,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(152,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(153,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(154,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(155,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(156,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(157,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(158,'ebb0f6be-a098-11ed-9358-00155dba2bde','',0,NULL),(159,'ebb0f6be-a098-11ed-9358-00155dba2bde','Oui',0,'2023-04-05'),(161,'7b0af216-c71c-11ed-9d75-005056c00001','ssgfg',555,NULL);
/*!40000 ALTER TABLE `frais_hors_forfaits` ENABLE KEYS */;
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
INSERT INTO `utilisateur` VALUES ('3739b15f-d9d4-11ed-96cf-005056c00001',0,'LESNE','LUCAS','lesne.lucas','26363432903c80aac7b098391ee290d16a988139e2e37ca5cf7a1f8522e146dd',2,2),('7b0af216-c71c-11ed-9d75-005056c00001',0,'BARGE','AYMERIC','aymeric.barge','687930558e78ea4c16e6d65500ab8f46debd564a65a5e5c9f57b84c6e7297f69',1,4),('8a4d14d1-d9d5-11ed-96cf-005056c00001',0,'HENOCQUE','ANTOINE','henocque.antoine','552e0d0bc836247cac79919b0d4a484b83e355635138a207cebf885e0ac58305',2,4),('8ac04b85-d9d4-11ed-96cf-005056c00001',0,'COUVILLERS','MAXENCE','couvillers.maxence','c9ae59dae44740439969615c285526cf9ac99136b3cff5df922c39c1bdd41f65',1,4),('9193da01-c1b2-11ed-8943-005056c00001',0,'DELOT','MATHIAS','mathias.delot','56231c3c7385e40340814ffe0e830b40ec0c7213db170819e3211b5a9eb7af09',2,2),('c0c170ee-a0a3-11ed-9358-00155dba2bde',1,'CABESTAING','MAITE','maite.cabestaing','a3ee9d88c3e0ee59bdc9dfe051e18d2d5d38ac0eda8f804f50226bdef4a78848',2,3),('e4e74e3b-d9d6-11ed-96cf-005056c00001',0,'DROZDZ','MAXENCE','drozdz.maxence','61f17982fd4e66cf8013b5bff8ffb43514fcd7db545c74407f278f62e07a6281',1,4),('ebb0f6be-a098-11ed-9358-00155dba2bde',0,'DINDIN','THOMAS','thomas.dindin','8c9a013ab70c0434313e3e881c310b9ff24aff1075255ceede3f2c239c231623',1,3),('efa4fb1c-a098-11ed-9358-00155dba2bde',0,'CACHEUX','CONSTANT','constant.cacheux','9a82da353db492a8e2892334de51fec091555cb2c4cc996e28bfd02ec4f1a9ad',2,2);
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

-- Dump completed on 2023-10-06 12:15:47
