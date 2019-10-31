-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: coupons
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,'comp1@gmail.com','comp1','pass1'),(2,'comp2@gmail.com','comp2','pass2'),(3,'comp3@gmail.com','comp3','pass3'),(4,'comp4@gmail.com','comp4','pass4'),(5,'comp5@gmail.com','comp5','pass5');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `amount` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `end_date` date NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `price` double NOT NULL,
  `start_date` date NOT NULL,
  `title` varchar(255) NOT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKe2v6qnb3w90rekqrae28iiqhm` (`company_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (1,4,'HEALTH','2019-09-30','1dollaroff.png',NULL,13,'2019-09-11','1 Dollar Off',1),(2,0,'FOOD','2019-10-22','1dollaroffB.png','Message1',34.78,'2019-04-12','Another dollar off',2),(3,0,'FASHION','2019-10-26','1dollaroffC.png','Message2',34.78,'2019-04-16','Get a dollar off',3),(4,0,'STUDIES','2019-10-21','10dollarsoff.png','Message3',55,'2019-05-17','Big discount',4),(5,2,'HEALTH','2019-11-09','10percentoff.png','Message4',109,'2019-06-17','10 percent off',5),(6,1,'STUDIES','2019-11-08','15percentoff.png','Message2',115,'2019-07-17','15 percent off',1),(7,7,'SPORT','2019-11-25','25off.png','Message7',125,'2019-07-25','25 DOLLARS OFF',2),(8,3,'HEALTH','2019-11-04','250dollarsoff.png','Message8',660,'2019-07-25','250 DOLLARS OFF',3),(9,6,'HEALTH','2019-12-04','coupon.png','Message9',19.99,'2019-07-25','Great coupon',3),(10,2,'FASHION','2019-11-16','coupons.png','Message10',25.99,'2019-03-25','Good deal',3);
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'cust1','1111'),(2,'cust2','2222'),(3,'cust3','3333'),(4,'cust4','4444'),(5,'cust5','5555'),(6,'cust6','6666');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_coupons`
--

DROP TABLE IF EXISTS `customer_coupons`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `customer_coupons` (
  `customers_id` bigint(20) NOT NULL,
  `coupons_id` bigint(20) NOT NULL,
  KEY `FK3ra7y4e2fu00kui0lby4mj0w1` (`coupons_id`),
  KEY `FKq8ich3016fmlbyrhig4p7eyqg` (`customers_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_coupons`
--

LOCK TABLES `customer_coupons` WRITE;
/*!40000 ALTER TABLE `customer_coupons` DISABLE KEYS */;
INSERT INTO `customer_coupons` VALUES (1,8),(1,3),(1,10),(2,2),(2,4),(2,3),(2,9),(3,4),(3,2),(3,7),(4,9),(4,6),(4,10);
/*!40000 ALTER TABLE `customer_coupons` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-12 13:04:29