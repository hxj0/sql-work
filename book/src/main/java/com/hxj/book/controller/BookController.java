package com.hxj.book.controller;


import com.hxj.book.entity.Book;
import com.hxj.book.entity.BookDTO;
import com.hxj.book.service.BookService;
import com.hxj.book.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("booklist/{page}/{limit}")
    public R getAll(@RequestParam(required = false, defaultValue = "") String search,
                    @RequestParam(required = false) Integer typeId,
                    @PathVariable("page") Integer page,
                    @PathVariable("limit") Integer limit){
        search = "%"+search+"%";
        ArrayList<BookDTO> bookDTOSs = bookService.getAllBooks(search, typeId, page, limit);
        Integer pageCount = bookService.getTotal(search, typeId);
        HashMap<String, Object> map = new HashMap<>();
        map.put("data", bookDTOSs);
        map.put("total", pageCount);
        return R.ok(map);
    }

    @GetMapping("/{id}")
    public R get(@PathVariable("id") Integer id){
        Book book = bookService.getById(id);
        return R.ok(book);
    }

    @PostMapping("/update")
    public R update(@RequestBody Book book){
        boolean update = bookService.updateById(book);
        return R.ok(update).setMsg("更新成功！");
    }


    @DeleteMapping("/delete/{id}")
    public R deleteBookById(@PathVariable("id")Integer id){
        bookService.removeById(id);
        return R.ok("删除成功！").setMsg("删除成功！");
    }

    @PutMapping("/add")
    public R addBook(@RequestBody Book book){
        System.out.println(book);
        bookService.save(book);
        return R.ok("添加成功！").setMsg("添加成功！");
    }
}

