package com.hxj.book.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hxj.book.entity.User;
import com.hxj.book.service.UserService;
import com.hxj.book.utils.MD5Util;
import com.hxj.book.utils.R;
import com.hxj.book.utils.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
@RequestMapping("/book/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public R getAllUsers(HttpSession session){
        String role = (String) session.getAttribute("user_role");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        if ("admin".equals(role)){
            wrapper.ne("role","root");
            wrapper.ne("role","admin");
            wrapper.or().eq("username",(String) session.getAttribute("username"));
        }else if ("user".equals(role)){
            wrapper.ne("role","root");
            wrapper.ne("role","admin");
            wrapper.ne("role","user");
        }
        List<User> users = userService.list(wrapper);
        return R.ok(users);
    }

    @PostMapping("/enabledorDisabled/{id}")
    public R enabledorDisabled(@PathVariable("id") Integer id,
                              @RequestParam("isEnabled") Boolean isEnabled){
        Boolean enabledorDisabled = userService.setUserEnabledorDisabled(id, !isEnabled);
        if (!enabledorDisabled) {
            if(isEnabled) {
                return R.fail("禁用失败！");
            }else {
                return R.fail("启用失败！");
            }
        }
        if(isEnabled) {
            return R.ok("成功").setMsg("禁用成功！");
        }else{
            return R.ok("成功").setMsg("启用成功！");
        }
    }

    @PutMapping("/add")
    public R addUser(@RequestBody User user){
        user.setPassword(MD5Util.code(user.getPassword()));
        if (!user.getEmail().contains("@")){
            return R.fail("邮箱格式不正确！");
        }
        boolean save = userService.save(user);
        return R.ok(save).setMsg("添加用户成功！");
    }

    @PostMapping("/update")
    public R updateUser(@RequestBody User user){
    System.out.println(user);
        Boolean update = userService.updateUserById(user);
        return R.ok(update).setMsg("更新用户成功！");
    }

    @GetMapping("/{id}")
    public R getUserById(@PathVariable("id")Integer id){
        User user = userService.getById(id);
        user.setPassword(null);
        return R.ok(user);
    }

    @PostMapping("login")
    public R login(HttpServletRequest request, @RequestBody Map<String, String> map){
        String verifyCode = (String) request.getSession().getAttribute("verify_code");
        if(!verifyCode.equalsIgnoreCase(map.get("code"))){
            return R.fail("验证码错误！");
        }
        User user = userService.findUserByUsernameAndPassword(
                map.get("username"), MD5Util.code(map.get("password")));
        if(user == null){
            return R.fail("用户名或密码错误！");
        }else if(!user.getIsEnabled()){
            return R.fail("该用户被禁用！");
        }
        request.getSession().setAttribute("user_role", user.getRole());
        request.getSession().setAttribute("username", user.getUsername());
        user.setPassword(null);
        return R.ok(user).setMsg("登录成功！");
    }

    @GetMapping("/verifyCode")
    public void verifyCode(HttpServletRequest request, HttpServletResponse resp) throws IOException {
        VerificationCode code = new VerificationCode();
        BufferedImage image = code.getImage();
        String text = code.getText();
        HttpSession session = request.getSession(true);
        session.setAttribute("verify_code", text);
        VerificationCode.output(image,resp.getOutputStream());
    }

    @DeleteMapping("/delete/{userId}")
    public R deleteUserById(HttpSession session, @PathVariable("userId") Integer userId){
        String role = (String) session.getAttribute("user_role");
        if("user".equals(role)){
            return R.fail("没有删除权限！");
        }
        userService.removeById(userId);
        return R.ok("").setMsg("删除成功！");
    }

}

