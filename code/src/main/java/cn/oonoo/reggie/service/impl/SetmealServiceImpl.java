package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.common.CustomException;
import cn.oonoo.reggie.dto.SetmealDto;
import cn.oonoo.reggie.entity.Setmeal;
import cn.oonoo.reggie.entity.SetmealDish;
import cn.oonoo.reggie.mapper.SetmealMapper;
import cn.oonoo.reggie.service.SetmealDishService;
import cn.oonoo.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    private final SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时保存套餐与菜品的关联关系
     *
     * @param setmealDto
     */
    @Override
    public void saveWithSetmealDish(SetmealDto setmealDto) {

        // 保存 setmeal 信息
        this.save(setmealDto);

        // 保存 setmealDish 信息，同样的需要自己添加 setmealid
        Long setmealId = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes = setmealDishes.stream()
                .peek(item -> item.setSetmealId(setmealId))
                .collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithSetmealDish(List<Long> setmealIds) {

        // 查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> setmealQW = new LambdaQueryWrapper<Setmeal>()
                .in(Setmeal::getId, setmealIds)
                .eq(Setmeal::getStatus, 1);

        if (this.count(setmealQW) > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }

        // 先删除 setmeal
        this.removeByIds(setmealIds);

        // 再删除 setmeal_dish
        LambdaQueryWrapper<SetmealDish> setmealDishQW = new LambdaQueryWrapper<SetmealDish>()
                .in(SetmealDish::getSetmealId, setmealIds);
        setmealDishService.remove(setmealDishQW);

    }
}
