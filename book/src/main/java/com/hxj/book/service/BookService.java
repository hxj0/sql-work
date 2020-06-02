package com.hxj.book.service;

import com.hxj.book.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxj.book.entity.BookDTO;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
public interface BookService extends IService<Book> {

    ArrayList<BookDTO> getAllBooks();

}
