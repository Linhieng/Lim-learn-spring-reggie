package cn.oonoo.reggie.dto;

import cn.oonoo.reggie.entity.Setmeal;
import cn.oonoo.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}