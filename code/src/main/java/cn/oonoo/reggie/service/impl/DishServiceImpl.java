package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.entity.Dish;
import cn.oonoo.reggie.mapper.DishMapper;
import cn.oonoo.reggie.service.DishService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
