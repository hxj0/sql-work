package com.hxj.book.service.impl;

import com.hxj.book.entity.Book;
import com.hxj.book.entity.BookDTO;
import com.hxj.book.mapper.BookMapper;
import com.hxj.book.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public ArrayList<BookDTO> getAllBooks() {
        return bookMapper.getAllBooks();
    }
}
