/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50727
Source Host           : localhost:3306
Source Database       : book

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-06-28 16:48:29
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `author` varchar(20) NOT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `count` int(11) NOT NULL,
  `price` double DEFAULT NULL,
  `introduction` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `category` (`category_id`),
  CONSTRAINT `book_category` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('1', '三体', '刘慈欣', '/upload/2020/07/37513636-eb64-4093-93e0-156151eb6779s2768378.jpg', '4', '12', '32', '讲述了地球人类文明和三体文明的信息交流、生死搏杀及两个文明在宇宙中的兴衰历程。');
INSERT INTO `book` VALUES ('3', '放学后', '东野圭吾', '/upload/2020/07/3170e8e9-2b70-4993-bf10-0b5ab5cd3d9e86d6277f9e2f07088a2b781ee624b899a901f286.jpg', '5', '4', '34', '主人公前岛老师为了生存，在女子高中教书，但他本人对教书无一点兴趣，私下被学生称作“机器”，但由于前岛三次在放学后遭人暗算，内心十分紧张。');
INSERT INTO `book` VALUES ('4', '球状闪电', '刘慈欣', '/upload/2020/07/e7ab22a5-72ba-4077-8c4f-dfe44b5c5d0eu=1357134987,1622788786&fm=26&gp=0.jpg', '2', '11', '25', '历经球状闪电的男主角对其历尽艰辛的研究历程，向我们展现了一个独特、神秘而离奇的世界');
INSERT INTO `book` VALUES ('5', '红手指', '东野圭吾', '/upload/2020/07/f4f425bf-385e-41ef-b7b3-6dceca7c87c2u=2792370217,1699450143&fm=26&gp=0.jpg', '6', '1', '20', '以一个女童的离奇身亡展开情节，逐渐走入诸多社会问题的背后，当抽丝剥茧拨开案件迷雾的最后，只剩下一个令人心碎的悲凉救赎……');
INSERT INTO `book` VALUES ('6', '天下第九', '鹅是老五', '/upload/2020/07/85e9d51a-c419-4917-99e2-24437a9f749cu=2951577341,1903855811&fm=26&gp=0.jpg', '2', '3', '40', '无尽宇宙之中有八道鸿蒙道则，这八道道则每一道都被一个无上强者融合。没有人知道，宇宙之中还有第九道道则，这一道道则破开鸿蒙，无人可触。');
INSERT INTO `book` VALUES ('13', '白夜行', '东野圭吾', '/upload/2020/07/96aca227-9c8c-4dfe-8ddc-f987729e0c9cdownload.jpg', '1', '3', '30', '世界上有两种东西不能直视，一是太阳，一是人心。');
INSERT INTO `book` VALUES ('14', '如何打造你的最优工作节奏', '闾佳', '/upload/2020/07/9f832be7-f068-4ed2-a84b-87a49668e4c910001306.jpg', '1', '20', '30', '为什么你一做事就分心，一思考就走神？\n为什么努力进行时间管理，却搞得自己精疲力尽？\n为什么用到创意的时候就跳不出惯性思维的怪圈？');

-- ----------------------------
-- Table structure for borrow
-- ----------------------------
DROP TABLE IF EXISTS `borrow`;
CREATE TABLE `borrow` (
  `user_id` int(11) NOT NULL,
  `book_id` int(11) NOT NULL,
  `borrow_date` date NOT NULL,
  `return_date` date DEFAULT NULL,
  PRIMARY KEY (`user_id`,`book_id`,`borrow_date`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `borrow_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `borrow_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of borrow
-- ----------------------------
INSERT INTO `borrow` VALUES ('1', '5', '2020-06-04', '2020-06-04');
INSERT INTO `borrow` VALUES ('2', '4', '2020-06-04', '2020-06-04');
INSERT INTO `borrow` VALUES ('2', '4', '2020-06-23', '2020-06-23');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('1', '文学');
INSERT INTO `category` VALUES ('2', '小说');
INSERT INTO `category` VALUES ('3', '散文');
INSERT INTO `category` VALUES ('4', '科技');
INSERT INTO `category` VALUES ('5', '历史');
INSERT INTO `category` VALUES ('6', '新闻');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(32) NOT NULL,
  `email` varchar(30) NOT NULL,
  `role` enum('root','admin','user') DEFAULT 'user',
  `is_enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uname` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'root', '202cb962ac59075b964b07152d234b70', '110290@qq.com', 'root', '1');
INSERT INTO `user` VALUES ('2', 'user', '202cb962ac59075b964b07152d234b70', '1235@qq.com', 'user', '1');
INSERT INTO `user` VALUES ('4', 'admin', '202cb962ac59075b964b07152d234b70', '1102907885@qq.com', 'admin', '1');
INSERT INTO `user` VALUES ('5', 'a', '92eb5ffee6ae2fec3ad71c777531578f', '3@q', 'user', '1');
DROP TRIGGER IF EXISTS `delete_trigger`;
DELIMITER ;;
CREATE TRIGGER `delete_trigger` BEFORE DELETE ON `book` FOR EACH ROW BEGIN
 	delete from borrow where book_id=old.id;
 END
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delete_trigger_user`;
DELIMITER ;;
CREATE TRIGGER `delete_trigger_user` BEFORE DELETE ON `user` FOR EACH ROW BEGIN
 	delete from borrow where user_id=old.id;
 END
;;
DELIMITER ;
