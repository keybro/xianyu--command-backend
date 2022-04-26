package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Groupinfo;
import com.sys.recommend.entity.Joins;
import com.sys.recommend.service.GroupService;
import com.sys.recommend.service.JoinService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("/join")
public class JoinController extends BaseController {

    @Autowired
    private JoinService joinService;

    @Autowired
    private GroupService groupService;


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 创建加入小组的申请
     * @Date
     * @Param Join
     **/
    @PostMapping("/createJoinApplication/{id}")
    public Resp createJoinApplication(@PathVariable int id) {
        int CurrentUserId = Integer.parseInt(getSenderId());
        Joins joins = new Joins();
        joins.setUserId(CurrentUserId);
        joins.setGroupId(id);
        if (joinService.save(joins)) {
            return Resp.ok("加入小组成功");
        }
        return Resp.err("加入小组失败");
    }


    /**
     * @return Resp
     * 此接口还判断了当前用户是否是对应小组的创建者，是的话显示和功能不一样
     * @Author LuoRuiJie
     * @Description 个人中心查看我加入的小组
     * @Date
     * @Param Map
     **/
    @GetMapping("/getMyGroup")
    public Resp getMyGroup(@RequestParam Map<String, String> params) {
        int CurrentUserId = Integer.parseInt(getSenderId());
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Joins> joinsQueryWrapper = new QueryWrapper<Joins>().eq("user_id", CurrentUserId);
        Page<Joins> joinsPage = joinService.page(new Page<>(page, limit), joinsQueryWrapper);
        List<Joins> records = joinsPage.getRecords();
        for (Joins joins :
                records) {
            Groupinfo targetGroup = groupService.getById(joins.getGroupId());
            //如果当前用户是创建者，设置isCreater=1
            if (targetGroup.getCreaterId() == CurrentUserId) {
                joins.setIsCreater(1);
            } else {
                //如果当前用户不是创建者，设置isCreater=2
                joins.setIsCreater(2);
            }
            QueryWrapper<Joins> groupCountQuery = new QueryWrapper<Joins>().eq("group_id", joins.getGroupId());
            int count = joinService.count(groupCountQuery);
            joins.setPersonNumber(count);
            joins.setGroupName(targetGroup.getGroupName());
            joins.setGroupHead(targetGroup.getGroupHead());
        }
        return Resp.ok(joinsPage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 用户退出指定群
     * @Date
     * @Param Groupinfo
     **/
    @PostMapping("/quitThisGroup/{id}")
    public Resp quitThisGroup(@PathVariable int id) {
        int CurrentUserId = Integer.parseInt(getSenderId());
        QueryWrapper<Joins> queryWrapper = new QueryWrapper<Joins>().eq("user_id", CurrentUserId).eq("group_id", id);
        if (joinService.remove(queryWrapper)) {
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }




    /**
     * @Author LuoRuiJie
     * @Description 判断当前小组当前用户是否已经加入过
     * @Date
     * @Param int
     * @return Resp
     **/
    @GetMapping("/isHaveJoin/{id}")
    public Resp isHaveJoin(@PathVariable int id){
        int CurrentUserId = Integer.parseInt(getSenderId());
        QueryWrapper<Joins> joinsQueryWrapper = new QueryWrapper<Joins>().eq("user_id", CurrentUserId).eq("group_id", id);
        Joins joins = joinService.getOne(joinsQueryWrapper);
        return Resp.ok(joins == null);
    }


}
