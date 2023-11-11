# 练习 - 瑞吉外卖

来自[黑马教程](https://www.bilibili.com/video/BV13a411q753)

## 我认为可以继续完成的地方

1. 数据库建表

    每张表都的创建时间和修改时间应该由数据库自行处理

    ```sql
    `create_time`DATETIMENOTNULLCOMMENT'创建时间'DEFAULTCURRENT_TIMESTAMP,
    `update_time`DATETIMENOTNULLCOMMENT'更新时间'DEFAULTCURRENT_TIMESTAMPONUPDATECURRENT_TIMESTAMP,
    ```

    id 类型推荐使用 INT UNSIGNED AUTO_INCREMENT

    一些字段（比如 gender, is_deleted）的类型应该使用 TINYINT 或 TINYINT  UNSIGNED

1. 统一响应体结构可以优化

    统一响应体的 code 可以使用枚举类型，而不是直接使用一个没有含义的数字加注释来说明.

1. 登录过滤器中的某些代码可以提取到 utils 中

    比如路径匹配这一块逻辑可以提取到工具类中，对应 `AntPathMatcher` 的创建也不需要放在过滤器中.
    还有对未鉴权时的响应字段 msg 也应该封装为常量，而不是直接写死一个字符串
