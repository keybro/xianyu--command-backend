package com.sys.recommend.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.User;
import com.sys.recommend.service.UserService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController{

    @Autowired
    UserService userService;

    @PostMapping("/test")
    public String test(){
        return "部署成功!";
    }


    /**
     * @Author LuoRuiJie
     * @Description //TODO 用户注册
     * @Date
     * @Param User
     * @return Resp
     * 测通
     **/
    @PostMapping("/create")
    public Resp create(@RequestBody User user) {
        //根据需求，账号和昵称都不允许重复
        if (userService.checkAccountUnique(user.getAccount()) && userService.checkNickNameUnique(user.getNickname())) {
            User targetUser = userService.saveWithHashPassword(user);
            String token = userService.createToken(targetUser);
            return Resp.ok(token);
        } else {
            return Resp.err("此用户已经存在");
        }
    }


    /**
     * @Author LuoRuiJie
     * @Description //TODO 用户登录
     * @Date
     * @Param User
     * @return Resp
     * 测通
     **/
    @PostMapping("/login")
    public Resp login(@RequestBody User user) {
        Assert.notNull(user.getAccount(), "账户为空");
        Assert.notNull(user.getPassword(), "密码为空");
        User targetUser = userService.findUser(user);
        if (targetUser == null) {
            return Resp.err("用户名不存在");
        }
        if (userService.checkPassword(targetUser, user.getPassword())) {
            String token = userService.createToken(targetUser);
            return Resp.ok(token);
        } else {
            return Resp.err("密码错误");
        }
    }


    @PostMapping("/modify")
    public Resp modifyUserInfo(@RequestBody User user) {
        if (userService.updateById(user)) {
            return Resp.ok("success");
        } else {
            return Resp.err("modify failed");
        }
    }

    @GetMapping("/getInfoById/{id}")
    public Resp getInfoById(@PathVariable int id) {
        User targetUser = userService.getById(id);
        Assert.notNull(targetUser, "用户不存在");
        return Resp.ok(targetUser);
    }


    /**
     * 管理员获取所有用户信息
     */
    @GetMapping("/getUserInfos")
    public Resp getUserInfos(@RequestParam Map<String, String> Param) {
        int userId = Integer.parseInt(getSenderId());
        User targetUser = userService.getById(userId);
        if (targetUser.getType() != 2) {
            return Resp.err("没有权限");
        }
        int limit = Integer.parseInt(Param.get("limit"));
        int page = Integer.parseInt(Param.get("currentPage"));
        Page<User> userInfoPage = new Page<>(page, limit);
        return Resp.ok(userService.findAllUser(userInfoPage, userId));
    }



}
