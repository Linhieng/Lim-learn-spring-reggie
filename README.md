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

    不，应该是 code 可以有多种类型，msg 应该只用于“描述信息”，而不是用于判断。当前的前端是通过 code + msg 来判断是否未鉴权的，
    但我觉得应该通过定义特定的 code 来标识未鉴权。

1. 新增员工模块

    新增员工时，后台应该提供一个接口给前端判断当前员工名是否已重复，此外，后台添加员工时是否也应该先判断一下呢？
    但这会多一条 sql 语句的执行。直接通过执行 save，当名称重复时直接抛出错误好像也是一种解决方案。

1. 只对管理员显示 “可禁用或启用”员工账户

    目前的代码中前端只是简单粗暴的判断用户名是否为 admin 从而判断是否是管理员，这种实现我认为不好。
    应该在数据库中添加一个字段，或者新建一张表来表示某位员工是否是管理员，或者他的权限级别是多少。
    但目前来说，能实现功能就行。
