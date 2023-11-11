package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.entity.DishFlavor;
import cn.oonoo.reggie.mapper.DishFlavorMapper;
import cn.oonoo.reggie.service.DishFlavorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
