package com.hxj.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxj.book.entity.Borrow;
import com.hxj.book.entity.Log;
import com.hxj.book.mapper.BorrowMapper;
import com.hxj.book.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
@Service
@Transactional
public class BorrowServiceImpl extends ServiceImpl<BorrowMapper, Borrow> implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public List<Log> getAllLogs(String username, String role) {
        return borrowMapper.getAllLogs(username, role);
    }

    @Override
    public Boolean deleteLog(Integer bookId, Integer userId, String borrowDate) {
        return borrowMapper.deleteLog(bookId, userId, borrowDate);
    }

    @Override
    public Boolean addBorrowLog(Integer bookId, String username) {
        return borrowMapper.addBorrowLog(bookId, username);
    }

    @Override
    public Boolean recive(Borrow borrow) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(borrow.getBorrowDate());
    System.out.println(date);
        return borrowMapper.recive(borrow,date);
    }

    @Override
    public Integer getLogByBookIdAndUserName(Integer bookId, String username) {
        return borrowMapper.getLogByBookIdAndUserName(bookId, username);
    }
}
