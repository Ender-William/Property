/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : property

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 05/10/2022 20:36:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cargodetailTB
-- ----------------------------
DROP TABLE IF EXISTS `cargodetailTB`;
CREATE TABLE `cargodetailTB` (
  `sn` varchar(255) NOT NULL,
  `itemName` varchar(255) NOT NULL,
  `id` int NOT NULL,
  `storageAddress` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `introImagePath` varchar(255) DEFAULT NULL,
  `createDate` varchar(255) DEFAULT NULL,
  `creator` int DEFAULT NULL,
  PRIMARY KEY (`sn`,`itemName`,`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for cargoinfoTB
-- ----------------------------
DROP TABLE IF EXISTS `cargoinfoTB`;
CREATE TABLE `cargoinfoTB` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(255) NOT NULL,
  `sonItem` int DEFAULT NULL,
  `sonTotality` int DEFAULT NULL,
  `categoryState` int DEFAULT NULL,
  PRIMARY KEY (`id`,`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for lendTB
-- ----------------------------
DROP TABLE IF EXISTS `lendTB`;
CREATE TABLE `lendTB` (
  `lendOperateCode` int NOT NULL,
  `stuID` int DEFAULT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `sn` varchar(255) NOT NULL,
  `loanQuantity` int DEFAULT NULL,
  `operateDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`lendOperateCode`,`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for returnTB
-- ----------------------------
DROP TABLE IF EXISTS `returnTB`;
CREATE TABLE `returnTB` (
  `returnOperateCode` int NOT NULL,
  `stuID` int DEFAULT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `sn` varchar(255) NOT NULL,
  `returnQuantity` int DEFAULT NULL,
  `lendOperateCOde` int DEFAULT NULL,
  `operateDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`returnOperateCode`,`sn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for tokenTB
-- ----------------------------
DROP TABLE IF EXISTS `tokenTB`;
CREATE TABLE `tokenTB` (
  `token` varchar(255) NOT NULL,
  `stuID` int NOT NULL,
  `invalidDate` varchar(255) NOT NULL,
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for userinfoTB
-- ----------------------------
DROP TABLE IF EXISTS `userinfoTB`;
CREATE TABLE `userinfoTB` (
  `stuID` int NOT NULL,
  `passwd` varchar(255) NOT NULL,
  `stuname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(255) NOT NULL,
  `authority` varchar(255) NOT NULL,
  `state` int DEFAULT NULL,
  PRIMARY KEY (`stuID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
