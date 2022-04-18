package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Invitation;
import com.sys.recommend.entity.Joins;
import com.sys.recommend.entity.ResourceComment;
import com.sys.recommend.entity.User;
import com.sys.recommend.service.InvitationService;
import com.sys.recommend.service.JoinService;
import com.sys.recommend.service.ResourceCommentService;
import com.sys.recommend.service.UserService;
import com.sys.recommend.tool.Resp;
import com.sys.recommend.vo.RightTagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    private ResourceCommentService resourceCommentService;

    @Autowired
    private JoinService joinService;

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/test")
    public String test() {
        return "部署成功!";
    }


    /**
     * @return Resp
     * 测通
     * @Author LuoRuiJie
     * @Description 用户注册
     * @Date
     * @Param User
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
     * @return Resp
     * 测通
     * @Author LuoRuiJie
     * @Description 用户登录
     * @Date
     * @Param User
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


    /**
     * @return Resp
     * 需要传入token
     * @Author LuoRuiJie
     * @Description 获取当前用户的基本信息
     * @Date
     * @Param null
     **/
    @GetMapping("/getCurrentUserInfo")
    public Resp getCurrentUserInfo() {
        int CurrentUserId = Integer.parseInt(getSenderId());
        return Resp.ok(userService.getById(CurrentUserId));
    }


    /**
     * @return Resp
     * 需要传入token
     * @Author LuoRuiJie
     * @Description 根据当前用户id获取右边悬浮窗口的信息
     * @Date
     * @Param int
     **/
    @GetMapping("/getRightTagInfo")
    public Resp getRightTagInfo() {
        int CurrentUserId = Integer.parseInt(getSenderId());
        RightTagInfo rightTagInfo = new RightTagInfo();
        QueryWrapper<ResourceComment> bookQueryWrapper = new QueryWrapper<ResourceComment>().eq("belong_type", 1).eq("public_user_id", CurrentUserId);
        int bookCommentCount = resourceCommentService.count(bookQueryWrapper);
        rightTagInfo.setMyBookCommentNumber(bookCommentCount);
        QueryWrapper<ResourceComment> movieQueryWrapper = new QueryWrapper<ResourceComment>().eq("belong_type", 2).eq("public_user_id", CurrentUserId);
        int movieCommentCount = resourceCommentService.count(movieQueryWrapper);
        rightTagInfo.setMyMovieCommentNumber(movieCommentCount);
        QueryWrapper<ResourceComment> musicQueryWrapper = new QueryWrapper<ResourceComment>().eq("belong_type", 3).eq("public_user_id", CurrentUserId);
        int musicCommentCount = resourceCommentService.count(musicQueryWrapper);
        rightTagInfo.setMyMusicCommentNumber(musicCommentCount);
        QueryWrapper<Joins> GroupNumberQuery = new QueryWrapper<Joins>().eq("user_id", CurrentUserId);
        int joinGroupNumbers = joinService.count(GroupNumberQuery);
        rightTagInfo.setMyGroupNumber(joinGroupNumbers);
        QueryWrapper<Invitation> invitationQueryWrapper = new QueryWrapper<Invitation>().eq("creater_id", CurrentUserId);
        int invitationNumber = invitationService.count(invitationQueryWrapper);
        rightTagInfo.setMyInvitationNumber(invitationNumber);
        return Resp.ok(rightTagInfo);
    }


    /**
     * @return
     * @Author LuoRuiJie
     * @Description 根据传入的用户对象修改信息
     * @Date
     * @Param
     **/
    @PostMapping("/updateMyInfo")
    public Resp updateMyInfoById(@RequestBody User user) {
        if (userService.updateById(user)) {
            return Resp.ok("修改成功");
        }
        return Resp.err("修改失败");
    }

    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 获取当前用户信息
     * @Date
     * @Param null
     **/
    @GetMapping("/getUserInfo")
    public Resp getType() {
        int CurrentId = Integer.parseInt(getSenderId());
        User targetUser = userService.getById(CurrentId);
        return Resp.ok(targetUser);
    }


}
