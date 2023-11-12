package cn.oonoo.reggie.service;

import cn.oonoo.reggie.dto.SetmealDto;
import cn.oonoo.reggie.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SetmealService extends IService<Setmeal> {
    void saveWithSetmealDish(SetmealDto setmealDto);

}
