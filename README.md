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

1. controller 中的请求体范围太大了

    目前代码中的请求体都是直接使用 Employee 类，但实际发送的请求并不需要传递一个完整的 Employee 类。
    只不过由于传递的内容都会是 Employee 类中的某个字段，所以这里直接就通过 Employee 类了，这样初次编写代码时确实比较方便。
    但带来的是后期维护的麻烦，而且作为一个前端人员，看到后端代码时也会疑惑这个接口到底需要传递什么对象。

    所以，一般创建一个 req 类，来表示接口中接收的前端请求体的结构。但这有时候又会带来另一个问题，如果每一个接口都要写一个对象，岂不是很麻烦？
    这个的话就具体问题具体分析了。（似乎后端会使用 dto 来表示接口接收的请求体？）

    既然有 req，那肯定就有 resp 类了。有时候一张表中的某些字段是不会返回给前端的，比如逻辑删除字段和密码字段。又比如有时候我们会多表联查，
    然后将数据整合起来返回给前端，这种时候就可以使用 resp 类来表示返回给前端的对象是什么样的了。（似乎后端会使用 VO 来表示 resp 类？）

    此外，后端还有一些 PO, BO, POJO, DO 等等，这些我就不太了解了，而且我觉得作用不大，因为目前没遇到需要使用这么多类的场景。

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

1. 修改员工信息模块

    该项目中仅仅只是前端将“禁用员工”的按钮对非管理员进行简单地隐藏了，但后端完全没有限制人员对 employee 进行修改。
    普通员工只需要简单的登录，然后直接发送请求就可以禁用某个员工了。

    这一块的内容和前面我说的情况是一样的，对于权限功能应该通过某个字段，或者新的表来实现。但目前就先这样吧，知道有这个问题存在就行。

1. session 中的字段名称不应该写死成字符串

    目前开发了几个接口，可以发现获取 session 是经常用到的，但目前都是直接通过字符串 `"employee"` 进行获取，这种方式我觉得不太行。
    应该通过一个常量来封装该字符串，这样后续想要修改名称的时候就比较方便，而且在查看代码时，也可以更方便地知道 session 有多少种类型。

1. 接口的路径不推荐以 `/` 结尾

    虽然可以借助 method 来区分某些功能，但我觉得除了每一个接口最后都提供一个简单的单词来说明一下该接口的作用是什么。
    这样前端开发时也不容易混淆。

## 值得记录的地方

js 对数字的精度是 16 位，但后端的 id 是 19 位的，所以当后端将 id 封装为 json 时，如果封装为数字，
则前端将使用 number 存储 id，结果就是无法获取正确的 id。

现在学习了一些后端知识后，我认为前端应该只注重视图相关的逻辑，
对于通过接口获取到的数据，大部分情况下前端是不应该再做任何封装的，这些封装都应该由后端进行处理，比如什么数字 1 表示男还是女；
或者某个套餐 1 所对应的具体套餐名称呀，这些都应该由后端处理，而不是在前端再创建一个映射表，然后查看某个数字对应的是什么内容。

---

## 值得记录的知识点

- MyBatis Plus 设置公共字段自动填充
- 借助 ThreadLocal 设置当前请求的全局上下文
