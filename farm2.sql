/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : farm2

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 24/02/2025 21:32:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for environment
-- ----------------------------
DROP TABLE IF EXISTS `environment`;
CREATE TABLE `environment`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `place_id` int(10) NULL DEFAULT NULL COMMENT '农场',
  `tem_value` double NULL DEFAULT NULL COMMENT '温度/°C',
  `hum_value` double NULL DEFAULT NULL COMMENT '湿度/%',
  `light_value` double NULL DEFAULT NULL COMMENT '光强/lux',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  `alarm_info` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '报警信息',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '环境' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of environment
-- ----------------------------
INSERT INTO `environment` VALUES (7, 6, 20, 10, 66, 2, '温度较高光强较高', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (8, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (9, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (10, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (11, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (12, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (13, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (14, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (15, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (16, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (17, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (18, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (19, 6, 20, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (20, 6, 201, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (21, 6, 211, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (22, 6, 2111, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `environment` VALUES (23, 6, 2111, 10, 66, 2, '温度较高/光强较高/', '2025-02-24 20:52:37', '2025-02-24 20:52:37');
INSERT INTO `environment` VALUES (24, 6, 2, 10, 34, 2, '光强较高/', '2025-02-24 20:53:31', '2025-02-24 20:53:31');
INSERT INTO `environment` VALUES (25, 6, 2, 10, 30, 1, '', '2025-02-24 20:53:43', '2025-02-24 20:53:43');
INSERT INTO `environment` VALUES (26, 6, 2, 10, 130, 1, '', '2025-02-24 20:54:05', '2025-02-24 20:54:05');
INSERT INTO `environment` VALUES (27, 6, 2, 10, 1130, 2, '光强较高/', '2025-02-24 20:54:09', '2025-02-24 20:54:09');

-- ----------------------------
-- Table structure for place
-- ----------------------------
DROP TABLE IF EXISTS `place`;
CREATE TABLE `place`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `place_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '农场名',
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地点',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '农场' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of place
-- ----------------------------
INSERT INTO `place` VALUES (6, '农场1', '11', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `place` VALUES (7, '农场2', '111', '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `place` VALUES (8, '农产3', '111', '2025-02-24 20:51:59', '2025-02-24 20:51:59');

-- ----------------------------
-- Table structure for setting
-- ----------------------------
DROP TABLE IF EXISTS `setting`;
CREATE TABLE `setting`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `min_tem_value` double NULL DEFAULT NULL COMMENT '温度最小值',
  `max_tem_value` double NULL DEFAULT NULL COMMENT '温度最大值',
  `min_hum_value` double NULL DEFAULT NULL COMMENT '湿度最小值',
  `max_hum_value` double NULL DEFAULT NULL COMMENT '湿度最大值',
  `min_light_value` double NULL DEFAULT NULL COMMENT '最小光强',
  `max_light_value` double NULL DEFAULT NULL COMMENT '最大光强',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of setting
-- ----------------------------
INSERT INTO `setting` VALUES (6, 1, 4, 1, 78, 1, 331, '2025-02-24 20:51:28', '2025-02-24 20:53:56');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `image_url` longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片',
  `email` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电子邮件',
  `user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `sex` int(10) NULL DEFAULT NULL COMMENT '性别',
  `user_type` int(10) NULL DEFAULT NULL COMMENT '用户类型',
  `create_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建时间',
  `update_time` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (6, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2025-02-11//55cc5bb1-e915-445e-8d40-373a2c941a15_u=1306948478,4275113549&fm=253&fmt=auto&app=120&f=PNG.webp', '3483@qq.com', 'admin', '12345', '超管', '19989897856', '安徽省', 1, 1, '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `user` VALUES (7, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2025-02-11//35963018-af9e-4f8b-824d-3dd630053790_u=1703339567,1758039541&fm=253&fmt=auto&app=138&f=JPEG.webp', '63437@qq.com', 'zhangsan', '12345', '张三', '19938483431', '11', 2, 2, '2025-02-24 20:51:28', '2025-02-24 20:51:28');
INSERT INTO `user` VALUES (8, 'https://gulimall-psw.oss-cn-hangzhou.aliyuncs.com/2025-02-24//eb1c6394-83ae-4f35-8ee5-a01508c87c39_u=1002611527,4133047660&fm=253&fmt=auto&app=138&f=JPEG.webp', '34772@qq.com', 'wudi', '12345', '无敌', '18848548544', '11', 1, 2, '2025-02-24 20:54:38', '2025-02-24 20:54:38');

SET FOREIGN_KEY_CHECKS = 1;
