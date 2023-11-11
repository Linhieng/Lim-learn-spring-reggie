package cn.oonoo.reggie.controller;

import cn.oonoo.reggie.common.R;
import cn.oonoo.reggie.entity.Category;
import cn.oonoo.reggie.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 新增分类（可以是菜品，也可以是套餐）
     *
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * 更新分类
     *
     * @param category
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("更新分类成功");
    }

    /**
     * 分页获取所有菜品和套餐类别
     * @param curPage
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> page(@RequestParam("page") int curPage, int pageSize) {
        Page<Category> categoryPage = new Page<>(curPage, pageSize);

        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<Category>()
                .orderByAsc(Category::getSort);

        categoryService.page(categoryPage, queryWrapper);

        return R.success(categoryPage);
    }


    @DeleteMapping
    public R<String> deleteById(@RequestParam("ids") Long id) {
        categoryService.removeById(id);

        return R.success("成功删除该分类");
    }
}
