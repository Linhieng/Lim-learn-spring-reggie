package cn.oonoo.reggie.service;

import cn.oonoo.reggie.dto.SetmealDto;
import cn.oonoo.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    void saveOrUpdateWithSetmealDish(SetmealDto setmealDto);

    @Transactional
    void removeWithSetmealDish(List<Long> setmealIds);

    SetmealDto getByIdWithSetmealDish(Long id);
}
