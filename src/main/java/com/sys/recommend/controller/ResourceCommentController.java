package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.ResourceComment;
import com.sys.recommend.entity.User;
import com.sys.recommend.service.ResourceCommentService;
import com.sys.recommend.service.UserService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
@RequestMapping("/resource-comment")
public class ResourceCommentController extends BaseController{

    @Autowired
    private ResourceCommentService resourceCommentService;


    /**
     * @Author LuoRuiJie
     * @Description 用户对书籍进行评论
     * @Date
     * @Param ResourceComment
     * @return Resp
     * 已测通，已接通
     **/
    @PostMapping("/writeBookComment")
    public Resp writeBookComment(@RequestBody ResourceComment resourceComment){
        int currentUserId = Integer.parseInt(getSenderId());
        resourceComment.setPublicUserId(currentUserId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date(System.currentTimeMillis());
        String public_time = format.format(time);
        resourceComment.setPublicTime(public_time);
        resourceComment.setProveNumber(0);
        if (resourceCommentService.save(resourceComment)){
            return Resp.ok("评论成功");
        }
        return Resp.err("评论失败");
    }



    /**
     * @Author LuoRuiJie
     * @Description 根据id和类型，分页获取用户评论
     * @Date
     * @Param
     * @return
     **/
    @GetMapping("/pageGetAllCommentByTypeAndId")
    public Resp getAllCommentByType(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int resourceId = Integer.parseInt(params.get("id"));
        String type = params.get("type");
        QueryWrapper<ResourceComment> resourceCommentQueryWrapper = new QueryWrapper<ResourceComment>().eq("comment_belong_id", resourceId).eq("belong_type", type);
        return Resp.ok(resourceCommentService.page(new Page<>(page,limit),resourceCommentQueryWrapper));
    }




}
