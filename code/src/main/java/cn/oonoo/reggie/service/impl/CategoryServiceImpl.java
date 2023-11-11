package cn.oonoo.reggie.service.impl;

import cn.oonoo.reggie.entity.Category;
import cn.oonoo.reggie.mapper.CategoryMapper;
import cn.oonoo.reggie.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
}
