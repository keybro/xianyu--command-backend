package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.InvitationReply;
import com.sys.recommend.entity.User;
import com.sys.recommend.service.InvitationReplyService;
import com.sys.recommend.service.UserService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
@RequestMapping("/invitation-reply")
public class InvitationReplyController extends BaseController {

    @Autowired
    private InvitationReplyService invitationReplyService;

    @Autowired
    private UserService userService;


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 当前用户创建回应
     * @Date
     * @Param InvitationReply
     **/
    @PostMapping("/createinvitationReply")
    public Resp createinvitationReply(@RequestBody InvitationReply invitationReply) {
        int currentUserId = Integer.parseInt(getSenderId());
        invitationReply.setCreaterId(currentUserId);
        User targetUser = userService.getById(currentUserId);
        invitationReply.setCreaterName(targetUser.getNickname());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date(System.currentTimeMillis());
        String create_time = format.format(time);
        invitationReply.setReplyTime(create_time);
        if (invitationReplyService.save(invitationReply)) {
            return Resp.ok("回应成功");
        }
        return Resp.err("回应失败");
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 分页
     * @Date
     * @Param Map
     **/
    @GetMapping("/pageGetInvitationReply")
    public Resp pageGetInvitationReplyById(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int invitationId = Integer.parseInt(params.get("invitationId"));
        QueryWrapper<InvitationReply> invitationQueryWrapper = new QueryWrapper<InvitationReply>().eq("invitation_belong_id", invitationId).orderByDesc("reply_time");
        return Resp.ok(invitationReplyService.page(new Page<>(page, limit), invitationQueryWrapper));
    }


    /**
     * @return Resp
     * 需要传入token
     * @Author LuoRuiJie
     * @Description 个人主页获取帖子回复
     * @Date
     * @Param Map
     **/
    @GetMapping("/pageGetMyReply")
    public Resp pageGetMyReply(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int CurrentUserId = Integer.parseInt(getSenderId());
        QueryWrapper<InvitationReply> invitationReplyQueryWrapper = new QueryWrapper<InvitationReply>().eq("creater_id", CurrentUserId);
        Page<InvitationReply> invitationReplyPage = invitationReplyService.page(new Page<>(page, limit), invitationReplyQueryWrapper);
        return Resp.ok(invitationReplyPage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 修改帖子的回复
     * @Date
     * @Param InvitationReply
     **/
    @PostMapping("/updateReply")
    public Resp updateReply(@RequestBody InvitationReply invitationReply) {
        if (invitationReplyService.updateById(invitationReply)) {
            return Resp.ok("修改成功");
        }
        return Resp.err("修改失败");
    }


    @PostMapping("/removeReplyById/{id}")
    public Resp removeReplyById(@PathVariable int id) {
        if (invitationReplyService.removeById(id)) {
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }

}
