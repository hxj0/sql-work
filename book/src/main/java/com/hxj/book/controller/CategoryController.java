package com.hxj.book.controller;


import com.hxj.book.entity.Category;
import com.hxj.book.service.CategoryService;
import com.hxj.book.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
@RestController
@RequestMapping("/book/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/getall")
    public R getAllCategorys(){
        List<Category> list = categoryService.list();
        return R.ok(list);
    }

}

