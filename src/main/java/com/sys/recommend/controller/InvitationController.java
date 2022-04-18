package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Groupinfo;
import com.sys.recommend.entity.Invitation;
import com.sys.recommend.entity.InvitationReply;
import com.sys.recommend.entity.User;
import com.sys.recommend.service.GroupService;
import com.sys.recommend.service.InvitationReplyService;
import com.sys.recommend.service.InvitationService;
import com.sys.recommend.service.UserService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 * 帖子controller
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/invitation")
public class InvitationController extends BaseController {
    @Autowired
    private InvitationService invitationService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private InvitationReplyService invitationReplyService;

    @Autowired
    private UserService userService;


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 创建帖子
     * @Date
     * @Param Invitation
     **/
    @PostMapping("/createInvitation")
    public Resp createInvitation(@RequestBody Invitation invitation) {
        int CurrentUserId = Integer.parseInt(getSenderId());
        invitation.setCreaterId(CurrentUserId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date(System.currentTimeMillis());
        String create_time = format.format(time);
        invitation.setCreateTime(create_time);
        if (invitationService.save(invitation)) {
            return Resp.ok("发帖成功");
        }
        return Resp.err("发帖失败，因为丑");
    }


    /**
     * @return
     * @Author LuoRuiJie
     * @Description 分页获取当前小组的所有帖子
     * @Date
     * @Param
     **/
    @GetMapping("/pageGetInvitationByGroup")
    public Resp pageGetInvitationByGroup(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int groupId = Integer.parseInt(params.get("groupId"));
        QueryWrapper<Invitation> invitationQueryWrapper = new QueryWrapper<Invitation>().eq("group_id", groupId);
        Page<Invitation> invitationPage = invitationService.page(new Page<>(page, limit), invitationQueryWrapper);
        List<Invitation> records = invitationPage.getRecords();
        for (Invitation invitaion :
                records) {
            User targetUser = userService.getById(invitaion.getCreaterId());
            invitaion.setCreaterName(targetUser.getNickname());
            QueryWrapper<InvitationReply> invitationReplyQueryWrapper = new QueryWrapper<InvitationReply>().eq("invitation_belong_id", invitaion.getInvitationId());
            int count = invitationReplyService.count(invitationReplyQueryWrapper);
            invitaion.setReplyNumber(count);
        }
        return Resp.ok(invitationPage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id获取帖子信息
     * @Date
     * @Param int
     **/
    @GetMapping("/getInvitationById/{id}")
    public Resp getInvitationById(@PathVariable int id) {
        return Resp.ok(invitationService.getById(id));
    }


    /**
     * @return Resp
     * 需要传入token
     * @Author LuoRuiJie
     * @Description 用户首页分页获取个人的帖子
     * @Date
     * @Param Map
     **/
    @GetMapping("/getMyInvitation")
    public Resp getMyInvitation(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int CurrentUserId = Integer.parseInt(getSenderId());
        QueryWrapper<Invitation> invitationQueryWrapper = new QueryWrapper<Invitation>().eq("creater_id", CurrentUserId);
        Page<Invitation> invitationPage = invitationService.page(new Page<>(page, limit), invitationQueryWrapper);
        List<Invitation> records = invitationPage.getRecords();
        for (Invitation invitaion :
                records) {
            Groupinfo targetGroup = groupService.getById(invitaion.getGroupId());
            invitaion.setBelongGroupName(targetGroup.getGroupName());
        }
        return Resp.ok(invitationPage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据传入参数修改帖子
     * @Date
     * @Param Invitation
     **/
    @PostMapping("/updateInvitation")
    public Resp updateInvitation(@RequestBody Invitation invitation) {
        if (invitationService.updateById(invitation)) {
            return Resp.ok("修改帖子成功");
        }
        return Resp.err("修改帖子失败");
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id删除帖子
     * @Date
     * @Param int
     **/
    @PostMapping("/removeInvitationById/{id}")
    public Resp removeInvitationById(@PathVariable int id) {
        if (invitationService.removeById(id)) {
            return Resp.ok("删除帖子成功");
        }
        return Resp.err("删除帖子失败");
    }


}
