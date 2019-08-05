/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : frame-permission

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2019-08-05 10:40:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `auth_client_details`
-- ----------------------------
DROP TABLE IF EXISTS `auth_client_details`;
CREATE TABLE `auth_client_details` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(64) DEFAULT NULL,
  `client_secret` varchar(255) DEFAULT NULL,
  `resource_ids` varchar(255) DEFAULT NULL,
  `scopes` varchar(255) DEFAULT NULL,
  `authorized_grant_types` varchar(255) DEFAULT NULL,
  `web_server_redirect_uris` varchar(255) DEFAULT NULL,
  `authorities` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(255) DEFAULT NULL,
  `auto_approve` varchar(255) DEFAULT NULL,
  `valid` tinyint(4) DEFAULT NULL COMMENT '可用性 1是 0否',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pk_client_id` (`client_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='OAuth2自带表';

-- ----------------------------
-- Records of auth_client_details
-- ----------------------------
INSERT INTO `auth_client_details` VALUES ('1', 'client_name', '$2a$10$NaVQghEKxZHQskPC69eyte3iuwYZsLs/rGfSt0Kgt1QQ/k2esoLxu', 'auth,manager', 'auth,manager', 'password,authorization_code,refresh_token,client_credentials', 'http://192.168.3.14:8001/auth/oauth/token', '', '3600', '36000', null, 'false', null);

-- ----------------------------
-- Table structure for `auth_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_permission`;
CREATE TABLE `auth_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `permission_name` varchar(50) DEFAULT NULL COMMENT '权限名称',
  `service_prefix` varchar(25) DEFAULT NULL COMMENT '前缀',
  `method` varchar(10) DEFAULT NULL COMMENT '请求方式 restful 模式 GET查看 POST 新增 PUT更新 DELETE 删除',
  `uri` varchar(50) DEFAULT NULL COMMENT '请求地址  资源',
  `valid` tinyint(4) DEFAULT NULL COMMENT '是否可用1是0否',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `operate_id` int(11) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
-- Records of auth_permission
-- ----------------------------
INSERT INTO `auth_permission` VALUES ('1', '查看auth服务用户权限', 'auth', 'GET', 'auth/getAuthUser', '1', '2019-07-07', '2019-07-07', null);
INSERT INTO `auth_permission` VALUES ('2', '查看auth服务hello权限', 'auth', 'GET', 'auth/hello', '1', '2019-07-07', '2019-07-07', null);
INSERT INTO `auth_permission` VALUES ('3', '查看api服务hello权限', 'api', 'GET', 'hello', '1', '2019-07-07', '2019-07-07', null);

-- ----------------------------
-- Table structure for `auth_resource`
-- ----------------------------
DROP TABLE IF EXISTS `auth_resource`;
CREATE TABLE `auth_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL COMMENT 'serviceName|Method|URI  资源名称|请求方式|URI (资源地址)',
  `need_permission` varchar(255) DEFAULT NULL COMMENT '资源需要权限 多个逗号隔开，且用#隔开 如 ROLE_ADMIN，资源名称|URI|Method  (restFul标识)',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `operate_id` int(11) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限配置表';

-- ----------------------------
-- Records of auth_resource
-- ----------------------------

-- ----------------------------
-- Table structure for `auth_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role`;
CREATE TABLE `auth_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL COMMENT '角色名称',
  `role_ch_name` varchar(50) DEFAULT NULL COMMENT '角色中文名',
  `valid` tinyint(4) DEFAULT NULL COMMENT '是否可用1是0否',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `operate_id` int(11) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rn_unique` (`role_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of auth_role
-- ----------------------------
INSERT INTO `auth_role` VALUES ('1', 'ROLE_ADMIN', '超级管理员', '1', '2019-07-07', '2019-07-07', null);

-- ----------------------------
-- Table structure for `auth_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `auth_role_permission`;
CREATE TABLE `auth_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_role_id` int(11) NOT NULL COMMENT '角色id',
  `auth_permission_id` int(11) NOT NULL COMMENT '用户id',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `valid` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `operate_id` int(11) NOT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='角色-权限关联表';

-- ----------------------------
-- Records of auth_role_permission
-- ----------------------------
INSERT INTO `auth_role_permission` VALUES ('1', '1', '1', '2019-07-07', '2019-07-07', '1', '1');
INSERT INTO `auth_role_permission` VALUES ('2', '1', '2', '2019-07-07', '2019-07-07', '1', '1');
INSERT INTO `auth_role_permission` VALUES ('3', '1', '3', '2019-07-07', '2019-07-07', '1', '1');

-- ----------------------------
-- Table structure for `auth_user`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user`;
CREATE TABLE `auth_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `phone` varchar(11) DEFAULT NULL,
  `ch_name` varchar(50) DEFAULT NULL COMMENT '中文名',
  `valid` tinyint(4) DEFAULT NULL COMMENT '可用性',
  `account_non_expired` tinyint(4) DEFAULT NULL COMMENT '过期性  1没过期0过期',
  `credentials_non_expired` tinyint(4) DEFAULT NULL COMMENT '有效性  1有效0失效',
  `account_non_locked` tinyint(4) DEFAULT NULL COMMENT '锁定性 1未锁定0锁定',
  `create_time` date DEFAULT NULL COMMENT '创建时间',
  `update_time` date DEFAULT NULL COMMENT '更新时间',
  `description` varchar(255) DEFAULT NULL COMMENT '个人说明',
  `operate_id` int(11) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `un_u` (`username`) USING BTREE,
  UNIQUE KEY `un_phone` (`phone`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of auth_user
-- ----------------------------
INSERT INTO `auth_user` VALUES ('1', 'username', '$2a$10$NaVQghEKxZHQskPC69eyte3iuwYZsLs/rGfSt0Kgt1QQ/k2esoLxu', '15000000000', '管理员', '1', '1', '1', '1', '2019-07-07', '2019-07-07', null, null);

-- ----------------------------
-- Table structure for `auth_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `auth_user_role`;
CREATE TABLE `auth_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `auth_user_id` int(11) NOT NULL COMMENT '用户id',
  `auth_role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` date DEFAULT NULL,
  `update_time` date DEFAULT NULL,
  `valid` tinyint(4) DEFAULT NULL COMMENT '是否可用',
  `operate_id` int(11) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户-角色关联表';

-- ----------------------------
-- Records of auth_user_role
-- ----------------------------
INSERT INTO `auth_user_role` VALUES ('1', '1', '1', '2019-07-07', '2019-07-07', '1', null);

-- ----------------------------
-- Table structure for `operate_log`
-- ----------------------------
DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `operate_id` int(11) DEFAULT NULL COMMENT '操作用户id',
  `remote_addr` varchar(100) DEFAULT NULL COMMENT '请求地址',
  `request_uri` varchar(100) DEFAULT NULL COMMENT '请求方法',
  `description` varchar(255) DEFAULT NULL COMMENT '请求描述',
  `method` varchar(25) DEFAULT NULL COMMENT '请求方式',
  `params` varchar(255) DEFAULT NULL COMMENT '请求参数',
  `return_data` varchar(255) DEFAULT NULL COMMENT '返回值',
  `exception_msg` varchar(100) DEFAULT NULL COMMENT '异常信息',
  `create_time` date DEFAULT NULL COMMENT '日志记录时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志';

-- ----------------------------
-- Records of operate_log
-- ----------------------------
