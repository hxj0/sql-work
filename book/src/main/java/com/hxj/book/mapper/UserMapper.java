package com.hxj.book.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hxj.book.entity.User;
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
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过用户密码查询用户
     * @param username 用户名
     * @param password 密码
     * @return 用户
     */
    User findUserByUsernameAndPassword(@Param("username") String username, @Param("password")String password);

    List<User> findAll();

    User findUserById(@Param("id") Integer id);

    Boolean addUser(User user);

    Boolean setUserEnabledorDisabled(@Param("id") Integer id,@Param("b") boolean b);

    Boolean updateUserById(User user);

    User selectUserByName(@Param("username") String username);
}
