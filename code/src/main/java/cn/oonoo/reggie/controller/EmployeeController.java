package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/employee")
@RestController
@AllArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

}
