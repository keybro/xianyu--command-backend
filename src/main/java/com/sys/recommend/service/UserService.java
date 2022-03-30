package com.sys.recommend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
public interface UserService extends IService<User> {

    boolean checkAccountUnique(String account);

    boolean checkNickNameUnique(String nickname);

    User saveWithHashPassword(User record);

    String createToken(User userInfo);

    User findUser(User userInfo);

    boolean checkPassword(User record, String password);

    Page<User> findAllUser(Page<User> page, int id);

}
