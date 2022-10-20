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

 Date: 21/10/2022 01:04:34
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
  `cateid` int NOT NULL,
  `storageAddress` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `introImagePath` mediumblob,
  `createDate` varchar(255) DEFAULT NULL,
  `creator` int DEFAULT NULL,
  PRIMARY KEY (`sn`,`itemName`,`cateid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of cargodetailTB
-- ----------------------------
BEGIN;
INSERT INTO `cargodetailTB` VALUES ('1', 'T1', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('10', 'T10', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('11', 'T11', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('12', 'T12', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('13', 'T13', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('14', 'T14', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('15', 'T15', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('16', 'T16', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('17', 'T17', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('18', 'T18', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('19', 'T19', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('2', 'T2', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('20', 'T20', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('21', 'T21', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('22', 'T22', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('23', 'T23', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('24', 'T24', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('25', 'T25', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('26', 'T26', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('27', 'T27', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('28', 'T28', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('29', 'T29', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('3', 'T3', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('4', 'T4', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('5', 'T5', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('6', 'T6', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('6923902216000', '金华清感', 1, NULL, 1030, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('7', 'T7', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('8', 'T8', 1, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('9', 'T9', 2, NULL, 2000, NULL, NULL, NULL);
INSERT INTO `cargodetailTB` VALUES ('9787302441717', 'Java_Web', 1, NULL, 14, NULL, NULL, NULL);
COMMIT;

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
-- Records of cargoinfoTB
-- ----------------------------
BEGIN;
INSERT INTO `cargoinfoTB` VALUES (1, '教科书', 30, 10049, 0);
INSERT INTO `cargoinfoTB` VALUES (2, '工具书', 12, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (3, '字典', 3, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (4, '词典', 47, 10000, 1);
INSERT INTO `cargoinfoTB` VALUES (5, '辞典', 96, 10000, 1);
INSERT INTO `cargoinfoTB` VALUES (6, 'CPU', 45, 10000, 1);
INSERT INTO `cargoinfoTB` VALUES (7, '手机', 23, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (8, '平板电脑', 31, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (9, '显示屏', 10, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (10, '电池', 50, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (11, '电容', 100, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (12, '电阻', 90, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (13, '芯片', 50, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (14, '螺丝刀', 200, 10000, 1);
INSERT INTO `cargoinfoTB` VALUES (15, '电灯', 3, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (16, '转接卡', 5, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (17, '螺丝', 123, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (18, '扳手', 40, 10000, 0);
INSERT INTO `cargoinfoTB` VALUES (19, 'SD卡', 2, 10000, 0);
COMMIT;

-- ----------------------------
-- Table structure for lendTB
-- ----------------------------
DROP TABLE IF EXISTS `lendTB`;
CREATE TABLE `lendTB` (
  `lendOperateCode` int NOT NULL AUTO_INCREMENT,
  `stuID` int DEFAULT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `sn` varchar(255) NOT NULL,
  `loanQuantity` int DEFAULT NULL,
  `operateDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`lendOperateCode`,`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of lendTB
-- ----------------------------
BEGIN;
INSERT INTO `lendTB` VALUES (1, 1111, 'JavaWeb', '9787302441717', 1, '1666076974605');
INSERT INTO `lendTB` VALUES (2, 1111, 'JavaWeb', '9787302441717', 2, '1666077106494');
INSERT INTO `lendTB` VALUES (3, 1111, 'Java_Web', '9787302441717', 2, '1666079582448');
INSERT INTO `lendTB` VALUES (4, 1111, 'Java_Web', '9787302441717', 1, '1666081400376');
INSERT INTO `lendTB` VALUES (5, 1111, '金华清感', '6923902216000', 15, '1666181537306');
INSERT INTO `lendTB` VALUES (6, 1111, 'Java_Web', '9787302441717', 1, '1666284842512');
INSERT INTO `lendTB` VALUES (7, 1111, 'Java_Web', '9787302441717', 2, '1666284852714');
COMMIT;

-- ----------------------------
-- Table structure for returnTB
-- ----------------------------
DROP TABLE IF EXISTS `returnTB`;
CREATE TABLE `returnTB` (
  `returnOperateCode` int NOT NULL AUTO_INCREMENT,
  `stuID` int DEFAULT NULL,
  `itemName` varchar(255) DEFAULT NULL,
  `sn` varchar(255) NOT NULL,
  `returnQuantity` int DEFAULT NULL,
  `lendOperateCode` int DEFAULT NULL,
  `operateDate` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`returnOperateCode`,`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of returnTB
-- ----------------------------
BEGIN;
INSERT INTO `returnTB` VALUES (1, 1111, '金华清感', '6923902216000', 10, 1, '1666182035504');
INSERT INTO `returnTB` VALUES (2, 1111, '金华清感', '6923902216000', 10, 1, '1666182091845');
INSERT INTO `returnTB` VALUES (3, 1111, '金华清感', '6923902216000', 10, 1, '1666182127239');
INSERT INTO `returnTB` VALUES (4, 1111, '金华清感', '6923902216000', 20, 1, '1666182536030');
INSERT INTO `returnTB` VALUES (5, 1111, '金华清感', '6923902216000', 20, 1, '1666182552163');
INSERT INTO `returnTB` VALUES (6, 1111, 'Java_Web', '9787302441717', 3, 1, '1666284868106');
COMMIT;

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
-- Records of tokenTB
-- ----------------------------
BEGIN;
COMMIT;

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

-- ----------------------------
-- Records of userinfoTB
-- ----------------------------
BEGIN;
INSERT INTO `userinfoTB` VALUES (1111, 'admin', 'admin', 'fuwa165@126.com', 'admin', 0);
INSERT INTO `userinfoTB` VALUES (200401, '123456', 'dhjdb', 'jdjkdjhd', 'nomal', 0);
INSERT INTO `userinfoTB` VALUES (1111111, '123456', 'kd', '123@123.com', 'nomal', 0);
INSERT INTO `userinfoTB` VALUES (12345687, '1234567', '张三', '1234@123.com', 'nomal', 0);
INSERT INTO `userinfoTB` VALUES (123456789, '123456789', 'dgjund', 'ihkhhhd', 'nomal', 0);
INSERT INTO `userinfoTB` VALUES (200401011, '12345', 'dgjd', 'jbjhjd', 'nomal', 0);
INSERT INTO `userinfoTB` VALUES (2004010212, '123', 'cjc', 'aaaa', 'nomal', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
