package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.service.SetmealDishService;
import cn.oonoo.reggie.service.SetmealService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/setmeal")
@RestController
public class SetmealController {

    private final SetmealService setmealService;
    private final SetmealDishService setmealDishService;
}
