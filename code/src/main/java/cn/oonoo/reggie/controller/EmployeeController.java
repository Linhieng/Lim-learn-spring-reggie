package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import cn.oonoo.reggie.entity.Employee;
import cn.oonoo.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RequestMapping("/employee")
@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * 新增员工
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {

        // 数据库中存储的是加密后的密码。每新增一个员工时，给他们一个默认密码，后续可以让他们自己修改密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 这一块由 MyBatis Plus 的公共字段自动填充来实现
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        // 获取当前登录的用户 id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("成功新增一名员工");
    }


    /**
     * 员工登录
     *
     * @param request
     * @param employee 只需要 username 和 password 字段. 这里为了方便，就直接使用 employee 类，而不是新建一个类了.
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        // 1. 对密码进行 md5 加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据用户名查询数据库
        // 2.1 封装查询条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        // 由于数据库将 username 配置为了 唯一索引，所以这里使用 getOne
        Employee emp = this.employeeService.getOne(queryWrapper);

        // 3.
        if (emp == null) {
            return R.error("登录失败， 不存在该用户");
        }

        // 4.
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败，密码错误");
        }

        // 5.
        if (emp.getStatus() == 0) {
            return R.error("该账号已被禁用");
        }

        // 6. 登录成功，将用户 id 存入 session 中
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 退出登录，即清理 session
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理 session 中保存的当前登录员工的 id
        request.getSession().removeAttribute("employee");
        return R.success("成功退出登录");
    }

    /**
     * 分页查询员工信息
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {

        // 1. 创建一个分页构造器
        Page<Employee> employeePage = new Page<>(page, pageSize);

        // 2. 创建一个条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotEmpty(name), Employee::getName, name)
                .orderByDesc(Employee::getUpdateTime);

        // 3. 根据条件进行分页
        employeeService.page(employeePage, queryWrapper);

        return R.success(employeePage);
    }

    /**
     * 根据 id 修改员工信息。
     *
     * @param request
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(empId);
        // 案例来说，普通员工能更新的内容是有限的，不过这里先这样吧。
        employeeService.updateById(employee);

        return R.success("员工信息修改成功");

    }


    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据 id:{} 查询员工信息", id);
        Employee emp = employeeService.getById(id);

        if (emp == null) {
            return R.error("没有该员工信息");
        }

        return R.success(emp);
    }
}
