package cn.oonoo.reggie.dto;

import cn.oonoo.reggie.entity.Dish;
import cn.oonoo.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
