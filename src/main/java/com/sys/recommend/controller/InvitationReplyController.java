package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.sys.recommend.entity.InvitationReply;
import com.sys.recommend.entity.User;
import com.sys.recommend.service.InvitationReplyService;
import com.sys.recommend.service.InvitationService;
import com.sys.recommend.service.UserService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
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
     * @Author LuoRuiJie
     * @Description 当前用户创建回应
     * @Date
     * @Param InvitationReply
     * @return Resp
     **/
    @PostMapping("/createinvitationReply")
    public Resp createinvitationReply(@RequestBody InvitationReply invitationReply){
        int currentUserId = Integer.parseInt(getSenderId());
        invitationReply.setCreaterId(currentUserId);
        User targetUser = userService.getById(currentUserId);
        invitationReply.setCreaterName(targetUser.getNickname());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date(System.currentTimeMillis());
        String create_time= format.format(time);
        invitationReply.setReplyTime(create_time);
        if (invitationReplyService.save(invitationReply)){
            return Resp.ok("回应成功");
        }
        return Resp.err("回应失败");
    }


    /**
     * @Author LuoRuiJie
     * @Description 分页
     * @Date
     * @Param Map
     * @return Resp
     **/
    @GetMapping("/pageGetInvitationReply")
    public Resp pageGetInvitationReplyById(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int invitationId= Integer.parseInt(params.get("invitationId"));
        QueryWrapper<InvitationReply> invitationQueryWrapper = new QueryWrapper<InvitationReply>().eq("invitation_belong_id", invitationId).orderByDesc("reply_time");
        return Resp.ok(invitationReplyService.page(new Page<>(page,limit),invitationQueryWrapper));
    }

}
