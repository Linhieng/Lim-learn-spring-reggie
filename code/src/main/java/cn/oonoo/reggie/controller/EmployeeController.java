package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import cn.oonoo.reggie.entity.Employee;
import cn.oonoo.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequestMapping("/employee")
@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;


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
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        // 清理 session 中保存的当前登录员工的 id
        request.getSession().removeAttribute("employee");
        return R.success("成功退出登录");
    }
}
