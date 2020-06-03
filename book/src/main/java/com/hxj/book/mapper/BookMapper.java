package com.hxj.book.mapper;

import com.hxj.book.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxj.book.entity.BookDTO;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
public interface BookMapper extends BaseMapper<Book> {

    ArrayList<BookDTO> getAllBooks(@Param("search") String search, @Param("typeId") Integer typeId,
                                   @Param("page") Integer page, @Param("limit")Integer limit);

    Integer getTotal(@Param("search") String search, @Param("typeId") Integer typeId);

}
