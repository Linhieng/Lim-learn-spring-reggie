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
