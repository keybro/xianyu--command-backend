package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Groupinfo;
import com.sys.recommend.entity.Movie;
import com.sys.recommend.entity.Music;
import com.sys.recommend.service.GroupService;
import com.sys.recommend.service.MinioService;
import com.sys.recommend.tool.Resp;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
@RequestMapping("/group")
public class GroupController extends BaseController{

    @Autowired
    private MinioService minioService;

    @Autowired
    private GroupService groupService;



    /**
     * @Author LuoRuiJie
     * @Description 小组根据对象的名字获取图片的URL
     * @Date
     * @Param String
     * @return Resp
     * 已经测通
     **/
    @GetMapping("/getGroupHeadImgURL")
    public Resp getGroupHeadImgURL(@RequestParam("name") String imgName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        String objectLocation = "/group/";
        String target = objectLocation+imgName;
        String presignedObjectUrl = minioService.getPresignedObjectUrl("recommend", target);
        return Resp.ok(presignedObjectUrl);
    }


    /**
     * @Author LuoRuiJie
     * @Description 创建小组接口
     * @Date
     * @Param Group
     * @return Resp
     *
     **/
    @PostMapping("/createGroup")
    public Resp createGroup(@RequestBody Groupinfo groupinfo){
        int currentUserId = Integer.parseInt(getSenderId());
        groupinfo.setCreaterId(currentUserId);
        groupinfo.setState(1);
        if (groupService.save(groupinfo)){
            return Resp.ok("创建成功");
        }
        return Resp.err("创建失败");
    }



    /**
     * @Author LuoRuiJie
     * @Description 首页获取12个小组列表
     * @Date
     * @Param null
     * @return Resp
     **/
    @GetMapping("/getMainPageGroupList")
    public Resp getMainPageGroupList(){
        QueryWrapper<Groupinfo> groupinfoQueryWrapper = new QueryWrapper<Groupinfo>().last("limit 12");
        List<Groupinfo> groupinfoList = groupService.list(groupinfoQueryWrapper);
        return Resp.ok(groupinfoList);
    }



    /**
     * @Author LuoRuiJie
     * @Description 根据id获取小组信息
     * @Date
     * @Param int
     * @return Resp
     **/
    @GetMapping("/getGroupInfoById/{id}")
    public Resp getGroupInfoById(@PathVariable int id){
        Groupinfo groupinfo = groupService.getById(id);
        return Resp.ok(groupinfo);
    }



    /**
     * @Author LuoRuiJie
     * @Description 根据小组id推荐5个小组
     * @Date
     * @Param int
     * @return Resp
     **/
    @GetMapping("/getRecommendGroup/{id}")
    public Resp getRecommendGroup(@PathVariable int id){
        Groupinfo targetGroup = groupService.getById(id);
        String label = targetGroup.getLabel();
        QueryWrapper<Groupinfo> groupinfoQueryWrapper = new QueryWrapper<Groupinfo>().like("label", label).ne("group_id",id).last("limit 5");
        List<Groupinfo> groupinfoList = groupService.list(groupinfoQueryWrapper);
        return Resp.ok(groupinfoList);
    }



    /**
     * @Author LuoRuiJie
     * @Description 根据用户选择的类型标签查询电影,分页获取
     * @Date
     * @Param Map
     * @return Resp
     * 已测通，已接通
     **/
    @GetMapping("/getGroupListByType")
    public Resp getGroupListByType(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String groupType = params.get("label");
        if (groupType.equals("全部")){
            Page<Groupinfo> groupinfoPagePage = groupService.page(new Page<>(page, limit));
            return Resp.ok(groupinfoPagePage);
        }
        QueryWrapper<Groupinfo> movieQueryWrapper = new QueryWrapper<Groupinfo>().like("label", groupType);
        Page<Groupinfo> moviePage = groupService.page(new Page<>(page, limit), movieQueryWrapper);
        return Resp.ok(moviePage);
    }



    /**
     * @Author LuoRuiJie
     * @Description 搜索用，当搜索作品类型选择为音乐的时候调用
     * @Date
     * @Param Map
     * @return Resp
     **/
    @GetMapping("/getSearchGroup")
    public Resp getSearchGroup(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String keyword = params.get("keyword");
        QueryWrapper<Groupinfo> groupQueryWrapper = new QueryWrapper<Groupinfo>().like("group_name", keyword);
        return Resp.ok(groupService.page(new Page<>(page,limit),groupQueryWrapper));
    }
}
