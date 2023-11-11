package cn.oonoo.reggie.service;

import cn.oonoo.reggie.common.R;
import cn.oonoo.reggie.dto.DishDto;
import cn.oonoo.reggie.entity.Category;
import cn.oonoo.reggie.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    @Transactional
    void updateByIdWithFlavor(DishDto dishDto);
}
