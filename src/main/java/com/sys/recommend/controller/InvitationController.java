package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Invitation;
import com.sys.recommend.service.InvitationService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 * 帖子controller
 * @author LuoRuiJie
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/invitation")
public class InvitationController extends BaseController{
    @Autowired
    private InvitationService invitationService;


    /**
     * @Author LuoRuiJie
     * @Description 创建帖子
     * @Date
     * @Param Invitation
     * @return Resp
     **/
    @PostMapping("/createInvitation")
    public Resp createInvitation(@RequestBody Invitation invitation){
        int CurrentUserId = Integer.parseInt(getSenderId());
        invitation.setCreaterId(CurrentUserId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date(System.currentTimeMillis());
        String create_time = format.format(time);
        invitation.setCreateTime(create_time);
        if (invitationService.save(invitation)){
            return Resp.ok("发帖成功");
        }
        return Resp.err("发帖失败，因为丑");
    }



    /**
     * @Author LuoRuiJie
     * @Description 分页获取当前小组的所有帖子
     * @Date
     * @Param
     * @return
     **/
    @GetMapping("/pageGetInvitationByGroup")
    public Resp pageGetInvitationByGroup(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int groupId = Integer.parseInt(params.get("groupId"));
        QueryWrapper<Invitation> invitationQueryWrapper = new QueryWrapper<Invitation>().eq("group_id", groupId);
        return Resp.ok(invitationService.page(new Page<>(page,limit),invitationQueryWrapper));
    }



    /**
     * @Author LuoRuiJie
     * @Description 根据id获取帖子信息
     * @Date
     * @Param int
     * @return Resp
     **/
    @GetMapping("/getInvitationById/{id}")
    public Resp getInvitationById(@PathVariable int id){
        return Resp.ok(invitationService.getById(id));
    }



}
