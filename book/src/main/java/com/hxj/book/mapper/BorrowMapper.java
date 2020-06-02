package com.hxj.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxj.book.entity.Borrow;
import com.hxj.book.entity.Log;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
public interface BorrowMapper extends BaseMapper<Borrow> {

    List<Log> getAllLogs();

    Boolean deleteLog(@Param("bookId") Integer bookId, @Param("userId")Integer userId);

    Boolean addBorrowLog(@Param("bookId") Integer bookId, @Param("username") String username);

    Boolean recive(Borrow borrow);

    Integer getLogByBookIdAndUserName(@Param("bookId")Integer bookId, @Param("username") String username);
}
