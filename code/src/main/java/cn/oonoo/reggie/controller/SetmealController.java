package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import cn.oonoo.reggie.dto.SetmealDto;
import cn.oonoo.reggie.entity.Category;
import cn.oonoo.reggie.entity.Setmeal;
import cn.oonoo.reggie.service.CategoryService;
import cn.oonoo.reggie.service.SetmealDishService;
import cn.oonoo.reggie.service.SetmealService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/setmeal")
@RestController
public class SetmealController {

    private final SetmealService setmealService;
    private final CategoryService categoryService;


    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto) {

        setmealService.saveWithSetmealDish(setmealDto);

        return R.success("成功新增套餐");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(int page, int pageSize, String name) {
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> setmealQW = new LambdaQueryWrapper<Setmeal>()
                .like(name != null, Setmeal::getName, name)
                .orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(setmealPage, setmealQW);


        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");

        // 借助 setmeal 中的 categoryId 查找对应的 categoryName，用于前端的展示
        List<Setmeal> setmealPageRecords = setmealPage.getRecords();
        List<SetmealDto> setmealDtoPageRecords = setmealPageRecords.stream().map(setmeal -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(setmeal, setmealDto);

            Long categoryId = setmeal.getCategoryId();
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                setmealDto.setCategoryName(category.getName());
            }

            return setmealDto;

        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(setmealDtoPageRecords);

        return R.success(setmealDtoPage);

    }

}
