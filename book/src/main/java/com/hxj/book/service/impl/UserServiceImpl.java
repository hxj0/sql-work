package com.hxj.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hxj.book.entity.User;
import com.hxj.book.mapper.UserMapper;
import com.hxj.book.service.UserService;
import com.hxj.book.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return userMapper.findUserByUsernameAndPassword(username, password);
    }

    @Override
    public Boolean setUserEnabledorDisabled(Integer id, boolean b) {
        return userMapper.setUserEnabledorDisabled(id, b);
    }

    @Override
    public Boolean updateUserById(User user) {
        if (!StringUtils.isBlank(user.getPassword())){
            user.setPassword(MD5Util.code(user.getPassword()));
        }
        if(StringUtils.isBlank(user.getUsername())){
            user.setUsername(null);
        }
        if(StringUtils.isBlank(user.getEmail())){
            user.setEmail(null);
        }
        if(StringUtils.isBlank(user.getRole())){
            user.setRole(null);
        }
        return userMapper.updateUserById(user);
    }

    @Override
    public User selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }
}
