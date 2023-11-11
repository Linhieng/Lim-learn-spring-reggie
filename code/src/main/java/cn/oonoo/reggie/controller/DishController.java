package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import cn.oonoo.reggie.dto.DishDto;
import cn.oonoo.reggie.service.DishFlavorService;
import cn.oonoo.reggie.service.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/dish")
@RestController
public class DishController {

    private final DishService dishService;
    private final DishFlavorService dishFlavorService;

    /**
     * 新建菜品，同时会保存对应菜品口味
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);

        return R.success("成功新增菜品");
    }
}
