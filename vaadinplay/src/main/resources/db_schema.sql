CREATE DATABASE  IF NOT EXISTS `beatseshDB` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `beatseshDB`;
-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: beatseshDB
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.18.10.1

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
-- Table structure for table `AUTO_PK_SUPPORT`
--

DROP TABLE IF EXISTS `AUTO_PK_SUPPORT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `AUTO_PK_SUPPORT` (
  `TABLE_NAME` char(100) NOT NULL,
  `NEXT_ID` bigint(20) NOT NULL,
  UNIQUE KEY `TABLE_NAME` (`TABLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `JOIN_PARTY_SONG`
--

DROP TABLE IF EXISTS `JOIN_PARTY_SONG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JOIN_PARTY_SONG` (
  `CHILD_ID` int(11) NOT NULL,
  `PARENT_ID` int(11) NOT NULL,
  PRIMARY KEY (`CHILD_ID`,`PARENT_ID`),
  KEY `PARENT_ID` (`PARENT_ID`),
  CONSTRAINT `JOIN_PARTY_SONG_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `PARTY` (`ID`),
  CONSTRAINT `JOIN_PARTY_SONG_ibfk_2` FOREIGN KEY (`CHILD_ID`) REFERENCES `SONG` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `JOIN_PARTY_USER`
--

DROP TABLE IF EXISTS `JOIN_PARTY_USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `JOIN_PARTY_USER` (
  `CHILD_ID` int(11) NOT NULL,
  `PARENT_ID` int(11) NOT NULL,
  PRIMARY KEY (`CHILD_ID`,`PARENT_ID`),
  KEY `PARENT_ID` (`PARENT_ID`),
  CONSTRAINT `JOIN_PARTY_USER_ibfk_1` FOREIGN KEY (`PARENT_ID`) REFERENCES `PARTY` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `JOIN_PARTY_USER_ibfk_2` FOREIGN KEY (`CHILD_ID`) REFERENCES `USER` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PARTY`
--

DROP TABLE IF EXISTS `PARTY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PARTY` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LAST_MODIFIED` datetime NOT NULL,
  `PARTY_NAME` varchar(64) NOT NULL,
  `PARTY_CODE` int(11) NOT NULL,
  `MP3` mediumblob,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SONG`
--

DROP TABLE IF EXISTS `SONG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SONG` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LINK` varchar(64) NOT NULL,
  `SONG_NAME` varchar(64) NOT NULL,
  `SONG_ARTIST` varchar(64) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `EMAIL_ADDRESS` varchar(128) DEFAULT NULL,
  `FIRST_NAME` varchar(48) DEFAULT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `IS_DJ` tinyint(4) NOT NULL,
  `LAST_NAME` varchar(48) DEFAULT NULL,
  `PARTY_ID` int(11) DEFAULT NULL,
  `PASSWORD` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-21 12:13:49
