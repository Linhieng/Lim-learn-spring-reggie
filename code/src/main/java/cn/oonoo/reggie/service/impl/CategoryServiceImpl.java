package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.common.CustomException;
import cn.oonoo.reggie.entity.Category;
import cn.oonoo.reggie.entity.Dish;
import cn.oonoo.reggie.entity.Setmeal;
import cn.oonoo.reggie.mapper.CategoryMapper;
import cn.oonoo.reggie.service.CategoryService;
import cn.oonoo.reggie.service.DishService;
import cn.oonoo.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final DishService dishService;
    private final SetmealService setmealService;

    /**
     * 根据 id 删除对应分类，但删除时需要判断该分类是否已关联某些菜品和套餐，如果已关联，则不能删除
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {

        LambdaQueryWrapper<Dish> dishQW = new LambdaQueryWrapper<>();
        dishQW.eq(Dish::getCategoryId, id);
        if (dishService.count(dishQW) > 0) {
            throw new CustomException("当前分类仍关联有菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealQW = new LambdaQueryWrapper<>();
        setmealQW.eq(Setmeal::getCategoryId, id);
        if (setmealService.count(setmealQW) > 0) {
            throw new CustomException("当前分类仍关联有套餐，不能删除");
        }

        return super.removeById(id);
    }
}
