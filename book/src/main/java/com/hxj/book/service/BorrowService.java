package com.hxj.book.service;

import com.hxj.book.entity.Borrow;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hxj.book.entity.Log;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
public interface BorrowService extends IService<Borrow> {

    List<Log> getAllLogs(String username, String role);

    Boolean deleteLog(Integer bookId, Integer userId, String borrowDate);

    Boolean addBorrowLog(Integer bookId, String username);

    Boolean recive(Borrow borrow);

    Integer getLogByBookIdAndUserName(Integer bookId, String username);
}
