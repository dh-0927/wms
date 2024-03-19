/*
 Navicat MySQL Data Transfer

 Source Server         : dh
 Source Server Type    : MySQL
 Source Server Version : 80019 (8.0.19)
 Source Host           : localhost:3306
 Source Schema         : wms

 Target Server Type    : MySQL
 Target Server Version : 80019 (8.0.19)
 File Encoding         : 65001

 Date: 19/03/2024 14:52:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint NOT NULL COMMENT '商品唯一标识',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `specification` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品规格',
  `price` decimal(10, 2) NOT NULL COMMENT '商品单价',
  `create_time` datetime NULL DEFAULT NULL COMMENT '商品录入时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '商品更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, '手机', NULL, 1000.00, '2024-03-18 12:28:39', '2024-03-18 12:28:44');
INSERT INTO `goods` VALUES (2, '平板', '6.5寸，mini', 6666.00, '2024-03-17 08:17:56', '2024-03-17 12:45:42');
INSERT INTO `goods` VALUES (3, '电视', NULL, 10000.00, '2024-03-19 14:49:36', '2024-03-19 14:49:42');
INSERT INTO `goods` VALUES (5, '鼠标', NULL, 1231.00, '2024-03-19 14:49:45', '2024-03-19 14:49:49');
INSERT INTO `goods` VALUES (6, '伊利牛奶', NULL, 35.00, '2024-03-19 14:50:18', '2024-03-19 14:50:21');

-- ----------------------------
-- Table structure for ledger
-- ----------------------------
DROP TABLE IF EXISTS `ledger`;
CREATE TABLE `ledger`  (
  `id` bigint NOT NULL COMMENT '台账唯一标识',
  `operator` bigint NOT NULL COMMENT '操作人唯一标识',
  `goods_id` bigint NOT NULL COMMENT '商品唯一标识（关联商品表）',
  `store_id` bigint NOT NULL COMMENT '门店唯一标识（关联门店表）',
  `type` tinyint NOT NULL COMMENT '台账类型（1：入库  0：出库）',
  `quantity` int NOT NULL COMMENT '数量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '台账创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '台账修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of ledger
-- ----------------------------
INSERT INTO `ledger` VALUES (1, 1, 1, 2, 1, 10, '2024-03-16 21:01:45', '2024-03-17 13:36:39');
INSERT INTO `ledger` VALUES (2, 1, 1, 2, 0, 2, '2024-03-15 13:36:55', '2024-03-16 13:37:00');
INSERT INTO `ledger` VALUES (3, 1, 1, 2, 1, 12, '2024-03-10 13:37:06', '2024-03-18 13:37:13');
INSERT INTO `ledger` VALUES (1769378595085619200, 1, 1, 2, 1, 5, '2024-03-17 23:01:45', '2024-03-17 23:01:45');
INSERT INTO `ledger` VALUES (1769722714202968064, 1, 1, 2, 1, 1, '2024-03-18 21:49:10', '2024-03-18 21:49:10');
INSERT INTO `ledger` VALUES (1769742336629280768, 2, 1, 2, 0, 1, '2024-03-18 23:07:08', '2024-03-18 23:07:08');
INSERT INTO `ledger` VALUES (1769760200744636416, 2, 1, 2, 0, 1, '2024-03-19 00:18:07', '2024-03-19 00:18:07');
INSERT INTO `ledger` VALUES (1769762932402556928, 2, 1, 2, 0, 1, '2024-03-19 00:28:58', '2024-03-19 00:28:58');
INSERT INTO `ledger` VALUES (1769771499704684544, 2, 1, 2, 0, 5, '2024-03-19 01:03:01', '2024-03-19 01:03:01');

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` bigint NOT NULL COMMENT '消息唯一标识',
  `goods_id` bigint NOT NULL COMMENT '商品唯一标识',
  `outbound_time` datetime NOT NULL COMMENT '出库时间',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息正文',
  `read_status` tinyint NOT NULL COMMENT '阅读状态（1：已读  0：未读）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1769742336805441536, 1, '2024-03-18 23:07:08', '张三（15179490101）于2024-03-18 23:07:07将 1数量的手机出库到了安定坊（南昌）', 0);
INSERT INTO `message` VALUES (1769760200912408576, 1, '2024-03-19 00:18:07', '张三（15179490101）于 2024-03-19 00:18:07 将 1数量的手机出库到了安定坊（南昌）', 0);
INSERT INTO `message` VALUES (1769762932545163264, 1, '2024-03-19 00:28:58', '张三（15179490101）于 2024-03-19 00:28:58 将 1数量的手机出库到了安定坊（南昌）', 0);
INSERT INTO `message` VALUES (1769771499767599104, 1, '2024-03-19 01:03:01', '张三（15179490101）于 2024-03-19 01:03:00 将 5数量的手机出库到了安定坊（南昌）', 1);

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store`  (
  `id` bigint NOT NULL COMMENT '门店唯一标识',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '门店名称',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '门店位置',
  `create_time` datetime NULL DEFAULT NULL COMMENT '门店创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '门店修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of store
-- ----------------------------
INSERT INTO `store` VALUES (2, '安定坊', '南昌', NULL, NULL);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint NOT NULL COMMENT '用户唯一标识',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `account` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `role` tinyint NOT NULL COMMENT '权限（0：admin / 1 ：普通用户.....）',
  `del_flag` tinyint NOT NULL COMMENT '禁用表示（1：可用 / 0：禁用）',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '18379479110', '邓辉', '校长', '2923503420@qq.com', '2923503420', 0, 1, '2024-03-18 21:40:53', '2024-03-18 21:40:57');
INSERT INTO `user` VALUES (2, '15179490101', '张三', '一修', '3326698881@qq.com', '3326698884', 1, 1, '2024-03-18 21:42:42', '2024-03-18 21:42:48');

SET FOREIGN_KEY_CHECKS = 1;
