package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.dto.SetmealDto;
import cn.oonoo.reggie.entity.Setmeal;
import cn.oonoo.reggie.entity.SetmealDish;
import cn.oonoo.reggie.mapper.SetmealMapper;
import cn.oonoo.reggie.service.SetmealDishService;
import cn.oonoo.reggie.service.SetmealService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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


}
