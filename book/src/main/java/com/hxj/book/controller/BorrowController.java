package com.hxj.book.controller;


import com.hxj.book.entity.Book;
import com.hxj.book.entity.Borrow;
import com.hxj.book.entity.Log;
import com.hxj.book.entity.User;
import com.hxj.book.service.BookService;
import com.hxj.book.service.BorrowService;
import com.hxj.book.service.UserService;
import com.hxj.book.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
@RestController
@RequestMapping("/book/borrow")
@CrossOrigin
public class BorrowController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @GetMapping("all")
    public R getAllLogs(HttpSession session, @RequestParam String username){
        String role = (String)session.getAttribute("user_role");
        List<Log> logs = borrowService.getAllLogs(username,role);
        return R.ok(logs);
    }

    @DeleteMapping("/delete/{bookId}/{userId}")
    public R deleteLog(@PathVariable("bookId") Integer bookId,
                       @PathVariable("userId") Integer userId,
                       @RequestParam String borrowDate){
        Boolean delete = borrowService.deleteLog(bookId, userId, borrowDate);
        return R.ok(delete).setMsg("删除日志成功！");
    }

    @PutMapping("/addBorrowLog")
    public R addBorrowLog(@RequestBody Map<String, Object> map){
        Integer bookId = (Integer) map.get("bookId");
        String username = (String) map.get("username");
        Book book = bookService.getById(bookId);
        User user = userService.selectUserByName(username);
        if(user == null){
            return R.fail("用户"+username+"不存在！");
        }
        if(!user.getIsEnabled()){
            return R.fail("该用户已被禁用！");
        }
        if(book.getCount() <= 0) {
            return R.fail("这本书借完了！");
        }
        if (borrowService.getLogByBookIdAndUserName(bookId, username) != null){
            return R.fail("用户"+username+"已经借阅了《"+book.getName()+"》");
        }
        Boolean add = borrowService.addBorrowLog(bookId, username);
        return R.ok(add).setMsg("用户"+username+"借阅《"+book.getName()+"》成功！");
    }

    @PostMapping("recive")
    public R recive(@RequestBody Borrow borrow){
        System.out.println(borrow);
        borrowService.recive(borrow);
        return R.ok("").setMsg("归还成功！");
    }

}

