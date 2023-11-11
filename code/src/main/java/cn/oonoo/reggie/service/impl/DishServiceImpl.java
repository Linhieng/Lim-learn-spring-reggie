package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.dto.DishDto;
import cn.oonoo.reggie.entity.Dish;
import cn.oonoo.reggie.entity.DishFlavor;
import cn.oonoo.reggie.mapper.DishMapper;
import cn.oonoo.reggie.service.DishFlavorService;
import cn.oonoo.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    private final DishFlavorService dishFlavorService;

    /**
     * 由于涉及到了两张表的操作，所以这里添加了事务操作
     *
     * @param dishDto
     */
    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        // 保存菜品的基本信息到 dish 表中
        this.save(dishDto);

        // 保存 flavor 是需要 dishId 字段
        Long dishId = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().peek(item -> item.setDishId(dishId)).collect(Collectors.toList());

        // 保存菜品口味数据到 dish_flavor 表中
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据 id 查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        Dish dish = this.getById(id);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        LambdaQueryWrapper<DishFlavor> eq = new LambdaQueryWrapper<DishFlavor>()
                .eq(DishFlavor::getDishId, dish.getId());

        dishDto.setFlavors(dishFlavorService.list(eq));

        return dishDto;
    }
}
