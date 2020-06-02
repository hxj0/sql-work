package com.hxj.book.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hxj.book.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hxj
 * @since 2020-05-31
 */
public interface UserService extends IService<User> {

    User findUserByUsernameAndPassword( String username, String password);

    Boolean setUserEnabledorDisabled(Integer id, boolean b);

    Boolean updateUserById(User user);

    User selectUserByName(String username);
}
