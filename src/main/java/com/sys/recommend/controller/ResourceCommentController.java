package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Book;
import com.sys.recommend.entity.Movie;
import com.sys.recommend.entity.Music;
import com.sys.recommend.entity.ResourceComment;
import com.sys.recommend.service.BookService;
import com.sys.recommend.service.MovieService;
import com.sys.recommend.service.MusicService;
import com.sys.recommend.service.ResourceCommentService;
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
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
@RestController
@RequestMapping("/resource-comment")
public class ResourceCommentController extends BaseController {

    @Autowired
    private ResourceCommentService resourceCommentService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private MusicService musicService;


    /**
     * @return Resp
     * 已测通，已接通
     * @Author LuoRuiJie
     * @Description 用户对书籍进行评论
     * @Date
     * @Param ResourceComment
     **/
    @PostMapping("/writeBookComment")
    public Resp writeBookComment(@RequestBody ResourceComment resourceComment) {
        int currentUserId = Integer.parseInt(getSenderId());
        resourceComment.setPublicUserId(currentUserId);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date time = new Date(System.currentTimeMillis());
        String public_time = format.format(time);
        resourceComment.setPublicTime(public_time);
        resourceComment.setProveNumber(0);
        if (resourceCommentService.save(resourceComment)) {
            return Resp.ok("评论成功");
        }
        return Resp.err("评论失败");
    }


    /**
     * @return
     * @Author LuoRuiJie
     * @Description 根据id和类型，分页获取用户评论
     * @Date
     * @Param
     **/
    @GetMapping("/pageGetAllCommentByTypeAndId")
    public Resp getAllCommentByType(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int resourceId = Integer.parseInt(params.get("id"));
        String type = params.get("type");
        QueryWrapper<ResourceComment> resourceCommentQueryWrapper = new QueryWrapper<ResourceComment>().eq("comment_belong_id", resourceId).eq("belong_type", type);
        return Resp.ok(resourceCommentService.page(new Page<>(page, limit), resourceCommentQueryWrapper));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 用户首页根据类型点击分页获取用户评话
     * @Date
     * @Param Map
     **/
    @GetMapping("/pageGetAllCommentPersonCenter")
    public Resp pageGetAllCommentPersonCenter(@RequestParam Map<String, String> params) {
        int CurrentUserId = Integer.parseInt(getSenderId());
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        int type = Integer.parseInt(params.get("type"));
        QueryWrapper<ResourceComment> commentQueryWrapper = new QueryWrapper<ResourceComment>().eq("public_user_id", CurrentUserId).eq("belong_type", type);
        Page<ResourceComment> resourceCommentPage = resourceCommentService.page(new Page<>(page, limit), commentQueryWrapper);
        List<ResourceComment> CommentRecords = resourceCommentPage.getRecords();
        if (type == 1) {
            for (ResourceComment resourceComment :
                    CommentRecords) {
                Book targetBook = bookService.getById(resourceComment.getCommentBelongId());
                resourceComment.setResourceName(targetBook.getBookName());
            }
            return Resp.ok(resourceCommentPage);
        }
        if (type == 2) {
            for (ResourceComment resourceComment :
                    CommentRecords) {
                Movie targetMovie = movieService.getById(resourceComment.getCommentBelongId());
                resourceComment.setResourceName(targetMovie.getMovieName());
            }
            return Resp.ok(resourceCommentPage);
        }
        if (type == 3) {
            for (ResourceComment resourceComment :
                    CommentRecords) {
                Music targetMusic = musicService.getById(resourceComment.getCommentBelongId());
                resourceComment.setResourceName(targetMusic.getMusicName());
            }
            return Resp.ok(resourceCommentPage);
        }
        return Resp.err("获取失败");
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据传入的平话修改评话
     * @Date
     * @Param ResourceComment
     **/
    @PostMapping("/updateMyComment")
    public Resp updateMyComment(@RequestBody ResourceComment resourceComment) {
        if (resourceCommentService.updateById(resourceComment)) {
            return Resp.ok("修改成功");
        }
        return Resp.err("修改失败");
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id删除对应的评话，书影音通用
     * @Date
     * @Param int
     **/
    @PostMapping("/removeCommentById/{id}")
    public Resp removeCommentById(@PathVariable int id) {
        if (resourceCommentService.removeById(id)) {
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }


}
