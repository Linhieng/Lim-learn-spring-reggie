package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.service.DishFlavorService;
import cn.oonoo.reggie.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/dish")
@RestController
public class DishController {

    private final DishService dishService;
    private final DishFlavorService dishFlavorService;
    
}
