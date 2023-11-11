package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import cn.oonoo.reggie.dto.DishDto;
import cn.oonoo.reggie.entity.Category;
import cn.oonoo.reggie.entity.Dish;
import cn.oonoo.reggie.service.CategoryService;
import cn.oonoo.reggie.service.DishFlavorService;
import cn.oonoo.reggie.service.DishService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/dish")
@RestController
public class DishController {

    private final DishService dishService;
    private final DishFlavorService dishFlavorService;
    private final CategoryService categoryService;

    /**
     * 新建菜品，同时会保存对应菜品口味
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);

        return R.success("成功新增菜品");
    }

    /**
     * 分页获取 dish 内容。但其中的 categoryId 涉及到 category 表的查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(int page, int pageSize, String name) {
        Page<Dish> dishPage = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> dishQW = new LambdaQueryWrapper<Dish>()
                .like(name != null, Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime);

        dishService.page(dishPage, dishQW);

        // 将 dishPage 中的内容都拷贝到 dishDtoPage 上，但 records 不要拷贝，因为我们需要对其进行处理
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");


        // 将 records 中的 categoryId 替换为对应的 name，这设计到多表查询
        List<Dish> dishPageRecords = dishPage.getRecords();
        List<DishDto> dishDtoRecords = dishPageRecords.stream().map(dish -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(dish, dishDto);

            // 我们需要将 分类id 转换为 分类name，用于前端页面的展示
            Long categoryId = dish.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) { // 正常情况下是对应的 category 的，但要是不存在时，也不应该让整个程序报错。
                dishDto.setCategoryName(category.getName());
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(dishDtoRecords);

        return R.success(dishDtoPage);
    }

    /**
     * 根据 id 获取 dish + dish_flavor 内容
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getByIdWithFlavor(@PathVariable Long id) {
        return R.success(dishService.getByIdWithFlavor(id));
    }

    /**
     * 更新 dish 和 dishFlavor
     * @return
     */
    @PutMapping
    public R<String> updateByIdWithFlavor(@RequestBody DishDto dishDto) {
        dishService.updateByIdWithFlavor(dishDto);
        return R.success("成功更新菜单");
    }

    /**
     * 虽然这里只用到 categoryId，但还是选择接收一个 Dish 对象
     * 目前来看似乎有点浪费了，但未来如果需要根据其他字段进行查询，
     * 我们可以直接复用该接口，而不需要新建接口，所以是好是坏见仁见智。
     * @param dish
     * @return
     */
    @GetMapping("list")
    public R<List<Dish>> list(Dish dish) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<Dish>()
                .eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
                .eq(Dish::getStatus, 1) // 这一块不一定需要，但由于前端没有对停售进行处理，所以这里直接禁用了。
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);

        return R.success(dishService.list(queryWrapper));
    }
}
