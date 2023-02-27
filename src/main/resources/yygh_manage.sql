/*
Navicat MySQL Data Transfer

Source Server         : 2001JavaA
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : yygh_manage

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2021-05-28 11:02:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hospital_set`
-- ----------------------------
DROP TABLE IF EXISTS `hospital_set`;
CREATE TABLE `hospital_set` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `hoscode` varchar(30) DEFAULT NULL COMMENT '医院编号',
  `sign_key` varchar(50) DEFAULT NULL COMMENT '签名秘钥',
  `api_url` varchar(100) DEFAULT NULL COMMENT '统一挂号平台api地址',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='医院设置表';

-- ----------------------------
-- Records of hospital_set
-- ----------------------------
INSERT INTO `hospital_set` VALUES ('1', '10000_1', 'e52f22948b010efa28e0dc345eaba6a8', 'http://localhost:8201/', '2021-05-28 10:59:39', '2021-05-04 10:03:23', '0');

-- ----------------------------
-- Table structure for `order_info`
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `schedule_id` bigint(20) DEFAULT NULL COMMENT '排班id',
  `patient_id` bigint(20) DEFAULT NULL COMMENT '就诊人id',
  `number` int(11) DEFAULT NULL COMMENT '预约号序',
  `fetch_time` varchar(50) DEFAULT NULL COMMENT '建议取号时间',
  `fetch_address` varchar(255) DEFAULT NULL COMMENT '取号地点',
  `amount` decimal(10,0) DEFAULT NULL COMMENT '医事服务费',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `quit_time` datetime DEFAULT NULL COMMENT '退号时间',
  `order_status` tinyint(3) DEFAULT NULL COMMENT '订单状态',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for `schedule`
-- ----------------------------
DROP TABLE IF EXISTS `schedule`;
CREATE TABLE `schedule` (
  `id` bigint(20) NOT NULL DEFAULT '0' COMMENT '编号',
  `hoscode` varchar(30) DEFAULT NULL COMMENT '医院编号',
  `depcode` varchar(30) DEFAULT NULL COMMENT '科室编号',
  `title` varchar(20) DEFAULT NULL COMMENT '职称',
  `docname` varchar(20) DEFAULT NULL COMMENT '医生名称',
  `skill` text COMMENT '擅长技能',
  `work_date` date DEFAULT NULL COMMENT '安排日期',
  `work_time` tinyint(3) DEFAULT '0' COMMENT '安排时间（0：上午 1：下午）',
  `reserved_number` int(11) DEFAULT '0' COMMENT '可预约数',
  `available_number` int(11) DEFAULT '0' COMMENT '剩余预约数',
  `amount` decimal(10,0) DEFAULT NULL COMMENT '挂号费',
  `status` tinyint(3) DEFAULT NULL COMMENT '排班状态（-1：停诊 0：停约 1：可约）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '更新时间',
  `is_deleted` tinyint(3) NOT NULL DEFAULT '0' COMMENT '逻辑删除(1:已删除，0:未删除)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医生日程安排表';

-- ----------------------------
-- Records of schedule
-- ----------------------------
INSERT INTO `schedule` VALUES ('1', '1000_0', '200040878', '医师', '邵迎红', '内分泌科常见病', '2020-12-13', '0', '33', '22', '100', '1', '2021-05-28 08:50:27', '0000-00-00 00:00:00', '0');
