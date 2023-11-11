/*

这里的数据库没有做任何修改

 */
SET FOREIGN_KEY_CHECKS = 0;



-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee`
(
    `id`          bigint(20)                   NOT NULL COMMENT '主键',
    `name`        varchar(32) COLLATE utf8_bin NOT NULL COMMENT '姓名',
    `username`    varchar(32) COLLATE utf8_bin NOT NULL COMMENT '用户名',
    `password`    varchar(64) COLLATE utf8_bin NOT NULL COMMENT '密码',
    `phone`       varchar(11) COLLATE utf8_bin NOT NULL COMMENT '手机号',
    `sex`         varchar(2) COLLATE utf8_bin  NOT NULL COMMENT '性别',
    `id_number`   varchar(18) COLLATE utf8_bin NOT NULL COMMENT '身份证号',
    `status`      int(11)                      NOT NULL DEFAULT '1' COMMENT '状态 0:禁用，1:正常',
    `create_time` datetime                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                     NOT NULL COMMENT '更新时间',
    `create_user` bigint(20)                   NOT NULL COMMENT '创建人',
    `update_user` bigint(20)                   NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_username` (`username`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='员工信息';

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee`
VALUES ('1', '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13812312312', '1', '110101199001010047', '1',
        '2021-05-06 17:20:07', '2021-05-10 02:24:09', '1', '1');



-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`
(
    `id`          bigint(20)                   NOT NULL COMMENT '主键',
    `type`        int(11)                               DEFAULT NULL COMMENT '类型   1 菜品分类 2 套餐分类',
    `name`        varchar(64) COLLATE utf8_bin NOT NULL COMMENT '分类名称',
    `sort`        int(11)                      NOT NULL DEFAULT '0' COMMENT '顺序',
    `create_time` datetime                     NOT NULL COMMENT '创建时间',
    `update_time` datetime                     NOT NULL COMMENT '更新时间',
    `create_user` bigint(20)                   NOT NULL COMMENT '创建人',
    `update_user` bigint(20)                   NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `idx_category_name` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COLLATE = utf8_bin COMMENT ='菜品及套餐分类';

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category`
VALUES ('1397844263642378242', '1', '湘菜', '1', '2021-05-27 09:16:58', '2021-07-15 20:25:23', '1', '1');
INSERT INTO `category`
VALUES ('1397844303408574465', '1', '川菜', '2', '2021-05-27 09:17:07', '2021-06-02 14:27:22', '1', '1');
INSERT INTO `category`
VALUES ('1397844391040167938', '1', '粤菜', '3', '2021-05-27 09:17:28', '2021-07-09 14:37:13', '1', '1');
INSERT INTO `category`
VALUES ('1413341197421846529', '1', '饮品', '11', '2021-07-09 11:36:15', '2021-07-09 14:39:15', '1', '1');
INSERT INTO `category`
VALUES ('1413342269393674242', '2', '商务套餐', '5', '2021-07-09 11:40:30', '2021-07-09 14:43:45', '1', '1');
INSERT INTO `category`
VALUES ('1413384954989060097', '1', '主食', '12', '2021-07-09 14:30:07', '2021-07-09 14:39:19', '1', '1');
INSERT INTO `category`
VALUES ('1413386191767674881', '2', '儿童套餐', '6', '2021-07-09 14:35:02', '2021-07-09 14:39:05', '1', '1');
