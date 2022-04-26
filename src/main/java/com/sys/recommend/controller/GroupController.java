package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Groupinfo;
import com.sys.recommend.entity.Joins;
import com.sys.recommend.entity.User;
import com.sys.recommend.service.GroupService;
import com.sys.recommend.service.JoinService;
import com.sys.recommend.service.MinioService;
import com.sys.recommend.service.UserService;
import com.sys.recommend.tool.Resp;
import io.minio.errors.*;
import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
@RequestMapping("/group")
public class GroupController extends BaseController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private JoinService joinService;

    @Autowired
    private UserService userService;


    /**
     * @return Resp
     * 已经测通
     * @Author LuoRuiJie
     * @Description 小组根据对象的名字获取图片的URL
     * @Date
     * @Param String
     **/
    @GetMapping("/getGroupHeadImgURL")
    public Resp getGroupHeadImgURL(@RequestParam("name") String imgName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        String objectLocation = "/group/";
        String target = objectLocation + imgName;
        String presignedObjectUrl = minioService.getPresignedObjectUrl("recommend", target);
        return Resp.ok(presignedObjectUrl);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 创建小组接口
     * @Date
     * @Param Group
     **/
    @PostMapping("/createGroup")
    public Resp createGroup(@RequestBody Groupinfo groupinfo) {
        int currentUserId = Integer.parseInt(getSenderId());
        groupinfo.setCreaterId(currentUserId);
        groupinfo.setState(1);
        if (groupService.save(groupinfo)) {
            Groupinfo groupinfo1 = groupService.getById(groupinfo.getGroupId());
            Joins joins = new Joins();
            joins.setGroupId(groupinfo1.getGroupId());
            joins.setUserId(currentUserId);
            joinService.save(joins);
            return Resp.ok("创建成功");
        }
        return Resp.err("创建失败");
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 首页获取12个小组列表
     * @Date
     * @Param null
     **/
    @GetMapping("/getMainPageGroupList")
    public Resp getMainPageGroupList() {
        QueryWrapper<Groupinfo> groupinfoQueryWrapper = new QueryWrapper<Groupinfo>().last("limit 12");
        List<Groupinfo> groupinfoList = groupService.list(groupinfoQueryWrapper);
        for (Groupinfo groupInfo :
                groupinfoList) {
            QueryWrapper<Joins> joinsQueryWrapper = new QueryWrapper<Joins>().eq("group_id", groupInfo.getGroupId());
            int count = joinService.count(joinsQueryWrapper);
            groupInfo.setPersonNumber(count);

        }
        return Resp.ok(groupinfoList);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id获取小组信息
     * @Date
     * @Param int
     **/
    @GetMapping("/getGroupInfoById/{id}")
    public Resp getGroupInfoById(@PathVariable int id) {
        Groupinfo groupinfo = groupService.getById(id);
        return Resp.ok(groupinfo);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据小组id推荐5个小组
     * @Date
     * @Param int
     **/
    @GetMapping("/getRecommendGroup/{id}")
    public Resp getRecommendGroup(@PathVariable int id) {
        Groupinfo targetGroup = groupService.getById(id);
        String label = targetGroup.getLabel();
        QueryWrapper<Groupinfo> groupinfoQueryWrapper = new QueryWrapper<Groupinfo>().like("label", label).ne("group_id", id).last("limit 5");
        List<Groupinfo> groupinfoList = groupService.list(groupinfoQueryWrapper);
        for (Groupinfo group :
                groupinfoList) {
            QueryWrapper<Joins> joinsQueryWrapper = new QueryWrapper<Joins>().eq("group_id", group.getGroupId());
            int count = joinService.count(joinsQueryWrapper);
            group.setPersonNumber(count);
        }
        return Resp.ok(groupinfoList);
    }


    /**
     * @return Resp
     * 已测通，已接通
     * @Author LuoRuiJie
     * @Description 根据用户选择的类型标签查询电影, 分页获取
     * @Date
     * @Param Map
     **/
    @GetMapping("/getGroupListByType")
    public Resp getGroupListByType(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String groupType = params.get("label");
        if (groupType.equals("全部")) {
            Page<Groupinfo> groupinfoPagePage = groupService.page(new Page<>(page, limit));
            List<Groupinfo> records = groupinfoPagePage.getRecords();
            for (Groupinfo GroupInfo :
                    records) {
                QueryWrapper<Joins> joinsQueryWrapper = new QueryWrapper<Joins>().eq("group_id", GroupInfo.getGroupId());
                int count = joinService.count(joinsQueryWrapper);
                GroupInfo.setPersonNumber(count);
            }
            return Resp.ok(groupinfoPagePage);
        }
        QueryWrapper<Groupinfo> movieQueryWrapper = new QueryWrapper<Groupinfo>().like("label", groupType);
        Page<Groupinfo> moviePage = groupService.page(new Page<>(page, limit), movieQueryWrapper);
        List<Groupinfo> records = moviePage.getRecords();
        for (Groupinfo GroupInfo :
                records) {
            QueryWrapper<Joins> joinsQueryWrapper = new QueryWrapper<Joins>().eq("group_id", GroupInfo.getGroupId());
            int count = joinService.count(joinsQueryWrapper);
            GroupInfo.setPersonNumber(count);
        }
        return Resp.ok(moviePage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 搜索用，当搜索作品类型选择为音乐的时候调用
     * @Date
     * @Param Map
     **/
    @GetMapping("/getSearchGroup")
    public Resp getSearchGroup(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String keyword = params.get("keyword");
        QueryWrapper<Groupinfo> groupQueryWrapper = new QueryWrapper<Groupinfo>().like("group_name", keyword);
        return Resp.ok(groupService.page(new Page<>(page, limit), groupQueryWrapper));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 修改小组信息
     * @Date
     * @Param Groupinfo
     **/
    @PostMapping("/updateGroupInfo")
    public Resp updateGroupInfo(@RequestBody Groupinfo groupinfo) {
        if (groupService.updateById(groupinfo)) {
            return Resp.ok("修改小组信息成功");
        }
        return Resp.err("修改小组信息失败");
    }


    /**
     * @return Resp
     * 不用传入token做权限控制，因为前端只有是创建者才会有解散按钮
     * @Author LuoRuiJie
     * @Description 创建者解散群聊
     * @Date
     * @Param Groupinfo
     **/
    @PostMapping("/removeThisGroup")
    public Resp removeThisGroup(@RequestBody Groupinfo groupinfo) {
        if (groupService.removeById(groupinfo.getGroupId())) {
            return Resp.ok("解散成功");
        }
        return Resp.err("解散失败");

    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 返回小组的信息
     * @Date
     * @Param Map
     **/
    @GetMapping("/pageGetAllGroup")
    public Resp pageGetAllGroup(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Groupinfo> groupQuery = new QueryWrapper<Groupinfo>().orderByDesc();
        Page<Groupinfo> groupinfoPage = groupService.page(new Page<>(page, limit), groupQuery);
        List<Groupinfo> records = groupinfoPage.getRecords();
        for (Groupinfo groupInfo :
                records) {
            QueryWrapper<Joins> joinsQueryWrapper = new QueryWrapper<Joins>().eq("group_id", groupInfo.getGroupId());
            int count = joinService.count(joinsQueryWrapper);
            User targetUser = userService.getById(groupInfo.getCreaterId());
            groupInfo.setCreaterName(targetUser.getNickname());
            groupInfo.setPersonNumber(count);
        }
        return Resp.ok(groupinfoPage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id删除电影
     * @Date
     * @Param int
     **/
    @PostMapping("/removeGroupById/{id}")
    public Resp removeGroupById(@PathVariable int id) {
        if (groupService.removeById(id)) {
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }


    /**
     * @Author LuoRuiJie
     * @Description 判断当前用户是否创建过小组
     * @Date
     * @Param null
     * @return Resp
     **/
    @GetMapping("/isCreater")
    public Resp isCreater(){
        int CurrentUserId = Integer.parseInt(getSenderId());
        System.out.println(CurrentUserId);
        QueryWrapper<Groupinfo> groupinfoQueryWrapper = new QueryWrapper<Groupinfo>().eq("creater_id", CurrentUserId).last("limit 1");
        Groupinfo groupinfo = groupService.getOne(groupinfoQueryWrapper);
        return Resp.ok(groupinfo == null);
    }


}
