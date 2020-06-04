package com.hxj.book.controller;


import com.hxj.book.entity.Book;
import com.hxj.book.entity.BookDTO;
import com.hxj.book.service.BookService;
import com.hxj.book.utils.R;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

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
        String cover = book.getCover();
        if(StringUtils.isNotBlank(cover)){
            Book book1 = bookService.getById(book.getId());
            if (StringUtils.isNotBlank(book1.getCover())&&!cover.equals(book1.getCover())){
                File file = new File(new File("").getAbsolutePath() + book1.getCover());
                file.delete();
            }
        }
        boolean update = bookService.updateById(book);
        return R.ok(update).setMsg("更新成功！");
    }


    @DeleteMapping("/delete/{id}")
    public R deleteBookById(@PathVariable("id")Integer id){
        Book book = bookService.getById(id);
        File file = new File(new File("").getAbsolutePath() + book.getCover());
        file.delete();
        System.out.println(book.getCover());
        bookService.removeById(id);
        return R.ok("删除成功！").setMsg("删除成功！");
    }

    @PutMapping("/add")
    public R addBook(@RequestBody Book book){
        System.out.println(book);
        bookService.save(book);
        return R.ok("添加成功！").setMsg("添加成功！");
    }

    @PostMapping("/cover/upload")
    public R addCover(HttpServletRequest request,
                      @RequestParam("file") MultipartFile[] multipartFiles){
        String prefix = "/upload/"+new SimpleDateFormat("yyyy/MM/").format(new Date());
        String absFilePath = new File("").getAbsolutePath();
        String filePath = "";
        if (!new File(absFilePath+prefix).exists()) {
            new File(absFilePath+prefix).mkdirs();
        }
        for (MultipartFile multipartFile : multipartFiles) {
            String fname = multipartFile.getOriginalFilename();
            String ext = fname.substring(fname.lastIndexOf(".")+1);
            if("png".equals(ext) || "jpg".equals(ext) || "jpeg".equals(ext)){
                filePath = prefix+UUID.randomUUID().toString()+fname;
                File file = new File(absFilePath+filePath);
        System.out.println(file.getAbsolutePath());
                try {
                    FileCopyUtils.copy(multipartFile.getInputStream(),new FileOutputStream(file));
                } catch (IOException e) {
                    return R.fail("上传失败!");
                }
            }else{
                return R.fail("上传的不是图片!");
            }
        }
        return R.ok(filePath).setMsg("上传成功！");
    }
}

