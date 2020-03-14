-- MySQL dump 10.13  Distrib 8.0.16, for macos10.14 (x86_64)
--
-- Host: localhost    Database: sell
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `sell`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `sell` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `sell`;

--
-- Table structure for table `order_detail`
--

DROP TABLE IF EXISTS `order_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `order_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '当前价格,单位分',
  `product_quantity` int(11) NOT NULL COMMENT '数量',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_detail`
--

LOCK TABLES `order_detail` WRITE;
/*!40000 ALTER TABLE `order_detail` DISABLE KEYS */;
INSERT INTO `order_detail` VALUES ('001','1345','1320','玉米粥',9.50,30,'http://3.jpg','2020-02-24 08:42:27','2020-02-24 08:42:27'),('002','1348','1355','皮蛋瘦肉粥',8.50,10,'http://1.jpg','2020-02-24 08:44:47','2020-03-10 14:54:53'),('1582604089000766980','1582604088949887404','1320','玉米粥',9.50,708,'http://3.jpg','2020-02-25 04:14:49','2020-02-25 04:14:49'),('1582604089025259584','1582604088949887404','1355','皮蛋瘦肉粥',8.50,2353,'http://1.jpg','2020-02-25 04:14:49','2020-02-25 04:14:49'),('1582604089029496939','1582604088949887404','1357','八宝粥',9.00,52,'http://2.jpg','2020-02-25 04:14:49','2020-02-25 04:14:49'),('1582634643042662868','1582634642965378371','1320','玉米粥',9.50,708,'http://3.jpg','2020-02-25 12:44:03','2020-02-25 12:44:03'),('1582634643085621855','1582634642965378371','1355','皮蛋瘦肉粥',8.50,2353,'http://1.jpg','2020-02-25 12:44:03','2020-02-25 12:44:03'),('1582634643089200483','1582634642965378371','1357','八宝粥',9.00,52,'http://2.jpg','2020-02-25 12:44:03','2020-02-25 12:44:03');
/*!40000 ALTER TABLE `order_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_master`
--

DROP TABLE IF EXISTS `order_master`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_master` (
  `order_id` varchar(32) NOT NULL,
  `buyer_name` varchar(32) NOT NULL COMMENT '买家名字',
  `buyer_phone` varchar(32) NOT NULL COMMENT '买家电话',
  `buyer_address` varchar(128) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(64) NOT NULL COMMENT '买家微信openid',
  `order_amount` decimal(8,2) NOT NULL COMMENT '订单总金额',
  `order_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '订单状态, 默认为新下单',
  `pay_status` tinyint(3) NOT NULL DEFAULT '0' COMMENT '支付状态, 默认未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_buyer_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_master`
--

LOCK TABLES `order_master` WRITE;
/*!40000 ALTER TABLE `order_master` DISABLE KEYS */;
INSERT INTO `order_master` VALUES ('1345','hujun','15294515333','浙江省温州市','111',780.79,1,1,'2020-02-24 08:31:30','2020-03-12 10:53:45'),('1348','hujun','15294515333','浙江省温州市','111',669.00,2,0,'2020-02-24 08:47:07','2020-03-12 10:53:50'),('1582604088949887404','qianqian','15398723453','北京','112',27194.50,0,0,'2020-02-25 04:14:49','2020-02-25 04:14:49'),('1582634642965378371','qianqian','15398723453','北京','112',27194.50,0,0,'2020-02-25 12:44:03','2020-02-25 12:44:03');
/*!40000 ALTER TABLE `order_master` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_category`
--

DROP TABLE IF EXISTS `product_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(64) NOT NULL COMMENT '类目名字',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uqe_category_type` (`category_type`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_category`
--

LOCK TABLES `product_category` WRITE;
/*!40000 ALTER TABLE `product_category` DISABLE KEYS */;
INSERT INTO `product_category` VALUES (5,'rubbishshsh',23,'2020-02-05 09:36:35','2020-03-12 16:14:17'),(7,'erasererer',11,'2020-02-05 09:41:54','2020-03-12 16:14:40'),(8,'food',10,'2020-02-25 03:54:54','2020-02-25 03:54:54'),(9,'水果 ',521,'2020-03-05 07:53:37','2020-03-05 07:53:37'),(10,'水果 ',3524,'2020-03-05 07:53:37','2020-03-05 07:53:37'),(11,'nomango',354,'2020-03-05 07:56:15','2020-03-05 09:28:49'),(12,'bread',51,'2020-03-05 07:56:15','2020-03-05 09:23:50'),(13,'strawberry',52,'2020-03-05 08:49:10','2020-03-05 08:49:10'),(14,'菜肴',27,'2020-03-12 16:56:56','2020-03-12 17:06:06');
/*!40000 ALTER TABLE `product_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_info`
--

DROP TABLE IF EXISTS `product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_description` varchar(64) DEFAULT NULL COMMENT '描述',
  `product_icon` varchar(512) DEFAULT NULL COMMENT '小图',
  `product_status` tinyint(3) DEFAULT '0' COMMENT '商品状态,0正常1下架',
  `category_type` int(11) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_info`
--

LOCK TABLES `product_info` WRITE;
/*!40000 ALTER TABLE `product_info` DISABLE KEYS */;
INSERT INTO `product_info` VALUES ('1320','玉米粥',9.50,1000,'美味营养','https://bkimg.cdn.bcebos.com/pic/e824b899a9014c08df4e43cd057b02087bf4f465?x-bce-process=image/watermark,g_7,image_d2F0ZXIvYmFpa2U5Mg==,xp_5,yp_5',1,10,'2020-02-05 11:55:58','2020-03-12 15:56:53'),('1353','鲜虾粥',18.00,100,'海的味道','https://bkimg.cdn.bcebos.com/pic/9f2f070828381f307e673bd5a9014c086e06f054?x-bce-process=image/watermark,g_7,image_d2F0ZXIvYmFpa2U4MA==,xp_5,yp_5',1,24532,'2020-03-12 16:40:58','2020-03-12 16:49:45'),('1355','皮蛋瘦肉粥',8.50,999,'美味营养nice','https://bkimg.cdn.bcebos.com/pic/2fdda3cc7cd98d106a783e2c293fb80e7bec907a?x-bce-process=image/crop,x_0,y_3,w_1000,h_660/watermark,g_7,image_d2F0ZXIvYmFpa2UxMTY=,xp_5,yp_5',0,11,'2020-02-05 10:08:02','2020-03-12 15:56:22'),('1357','八宝粥',9.00,998,'美味营养','https://bkimg.cdn.bcebos.com/pic/a8014c086e061d95c061543b7bf40ad162d9ca8a?x-bce-process=image/watermark,g_7,image_d2F0ZXIvYmFpa2U3Mg==,xp_5,yp_5',0,11,'2020-02-05 10:09:04','2020-03-12 14:36:47'),('1584031938258480067','山药粥',23.00,2325,'美味营养','https://bkimg.cdn.bcebos.com/pic/8ad4b31c8701a18b9a04cf37912f07082938fedf?x-bce-process=image/watermark,g_7,image_d2F0ZXIvYmFpa2U5Mg==,xp_5,yp_5',0,8,'2020-03-12 16:52:18','2020-03-12 16:53:27');
/*!40000 ALTER TABLE `product_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seller_info`
--

DROP TABLE IF EXISTS `seller_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `seller_info` (
  `seller_id` varchar(32) NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `openid` varchar(64) NOT NULL COMMENT '微信openid',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`seller_id`),
  UNIQUE KEY `openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='卖家信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seller_info`
--

LOCK TABLES `seller_info` WRITE;
/*!40000 ALTER TABLE `seller_info` DISABLE KEYS */;
INSERT INTO `seller_info` VALUES ('1583763652011653054','hujun','123456','45245','2020-03-09 14:20:52','2020-03-09 14:20:52'),('1583763870533289415','lilan','1234','78910','2020-03-09 14:24:30','2020-03-09 14:24:30');
/*!40000 ALTER TABLE `seller_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-03-13 15:59:31
