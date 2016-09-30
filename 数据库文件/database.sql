/*
Navicat MySQL Data Transfer

Source Server         : TEST_MYSQL
Source Server Version : 50519
Source Host           : localhost:3306
Source Database       : database

Target Server Type    : MYSQL
Target Server Version : 50519
File Encoding         : 65001

Date: 2016-09-28 21:11:34
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `friend`
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend` (
  `QQNUM` bigint(20) NOT NULL,
  `FRIEND` text,
  PRIMARY KEY (`QQNUM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES ('793159785', '王793159786_109.jpg_2&向793159787_112.jpg_3');
INSERT INTO `friend` VALUES ('793159786', '申793159785_108.jpg_Shensn\r\n');
INSERT INTO `friend` VALUES ('793159787', '王793159786_109.jpg_2&申793159785_108.jpg_Shensn\r\n');
INSERT INTO `friend` VALUES ('793159788', '申793159785_108.jpg_Shensn\r\n');
INSERT INTO `friend` VALUES ('793159789', '申793159785_108.jpg_Shensn\r\n');
INSERT INTO `friend` VALUES ('793159790', null);

-- ----------------------------
-- Table structure for `qqnum`
-- ----------------------------
DROP TABLE IF EXISTS `qqnum`;
CREATE TABLE `qqnum` (
  `ID` bigint(20) NOT NULL,
  `QQNUM` bigint(20) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of qqnum
-- ----------------------------
INSERT INTO `qqnum` VALUES ('1', '793159790');

-- ----------------------------
-- Table structure for `user_info`
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `NUM` bigint(20) NOT NULL,
  `PIC` text,
  `NAME` text,
  `PASSWORD` text,
  `SEX` text,
  `BIRTHDAY` text,
  `PLACE` text,
  `INFO` text,
  `STATUS` int(11) DEFAULT NULL,
  `IP` varchar(40) DEFAULT NULL,
  `PORT` int(11) DEFAULT NULL,
  PRIMARY KEY (`NUM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('793159785', '108.jpg', '申', '1', '女', '1997   年1  月6   日', '内蒙古', 'Shensn\r\n', '0', 'null', '0');
INSERT INTO `user_info` VALUES ('793159786', '109.jpg', '王', '2', '男', '1996   年4  月11   日', '山西', '2', '0', 'null', '0');
INSERT INTO `user_info` VALUES ('793159787', '112.jpg', '向', '3', '男', '2016   年1  月1   日', '湖南', '3', '0', 'null', '0');
INSERT INTO `user_info` VALUES ('793159788', '101.jpg', '狗狗', '6', '女', '2016   年1  月1   日', '北京', '6', '0', 'null', '0');
INSERT INTO `user_info` VALUES ('793159789', '123.jpg', '虎牙', '7', '男', '2016   年1  月1   日', '北京市', '7', '0', 'null', '0');
INSERT INTO `user_info` VALUES ('793159790', '120.jpg', '狗狗', '066026', '女', '1997   年1  月5   日', '北京', '欢迎使用本聊天系统~', '1', '127.0.0.1', '8866');
