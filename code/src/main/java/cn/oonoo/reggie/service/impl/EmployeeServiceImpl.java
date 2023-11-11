package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.entity.Employee;
import cn.oonoo.reggie.mapper.EmployeeMapper;
import cn.oonoo.reggie.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
