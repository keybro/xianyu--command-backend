package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Music;
import com.sys.recommend.service.MinioService;
import com.sys.recommend.service.MusicService;
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
 * 前端控制器
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/music")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @Autowired
    private MinioService minioService;

    /**
     * @return Resp
     * 已测通
     * @Author LuoRuiJie
     * @Description 首页根据score来推荐5本书籍
     * @Date
     * @Param null
     **/
    @GetMapping("/getMainPageMusic")
    public Resp getMainPageBook() {
        QueryWrapper<Music> MusicMainPageQueryWrapper = new QueryWrapper<Music>().orderByDesc("music_score").last("limit 5");
        List<Music> musicMainPageList = musicService.list(MusicMainPageQueryWrapper);
        return Resp.ok(musicMainPageList);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id获取音乐详情
     * @Date
     * @Param int
     **/
    @GetMapping("/getMusicById/{id}")
    public Resp getMusicById(@PathVariable int id) {
        Music music = musicService.getById(id);
        return Resp.ok(music);
    }


    /**
     * @return Resp
     * 已测通，已接通
     * @Author LuoRuiJie
     * @Description 根据音乐id推荐类型相似的音乐，5个
     * @Date
     * @Param int
     **/
    @GetMapping("/getMusicRecommendList/{id}")
    public Resp getMusicRecommendList(@PathVariable int id) {
        Music targetMusic = musicService.getById(id);
        String musicType = targetMusic.getMusicType();
        QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<Music>().like("music_type", musicType).ne("music_id", id).last("limit 5");
        List<Music> list = musicService.list(musicQueryWrapper);
        return Resp.ok(list);
    }

    /**
     * @return Resp
     * 已测通
     * @Author LuoRuiJie
     * @Description 根据用户选择的类型标签查询音乐, 分页获取
     * @Date
     * @Param Map
     **/
    @GetMapping("/getMusicListByType")
    public Resp getMusicListByType(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        System.out.println(limit);
        int page = Integer.parseInt(params.get("currentPage"));
        System.out.println(page);
        String bookType = params.get("music_type");
        System.out.println(bookType);
        if (bookType.equals("全部")) {
            QueryWrapper<Music> allMusicQueryWrapper = new QueryWrapper<Music>().orderByDesc("publish_time");
            Page<Music> moviePage = musicService.page(new Page<>(page, limit), allMusicQueryWrapper);
            return Resp.ok(moviePage);
        }
        QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<Music>().like("music_type", bookType).orderByDesc("publish_time");
        Page<Music> musicPage = musicService.page(new Page<>(page, limit), musicQueryWrapper);
        return Resp.ok(musicPage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 搜索用，当搜索作品类型选择为音乐的时候调用
     * @Date
     * @Param Map
     **/
    @GetMapping("/getSearchMusic")
    public Resp getSearchMusic(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String keyword = params.get("keyword");
        QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<Music>().like("music_name", keyword).orderByDesc("publish_time");
        return Resp.ok(musicService.page(new Page<>(page, limit), musicQueryWrapper));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 音乐的top100，分页获取
     * @Date
     * @Param Map
     **/
    @GetMapping("/getMusicTop")
    public Resp getMusicTop(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Music> musicQuery = new QueryWrapper<Music>().orderByDesc("music_score");
        return Resp.ok(musicService.page(new Page<>(page, limit), musicQuery));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 分页获取所有的音乐，管理员界面用
     * @Date
     * @Param Map
     **/
    @GetMapping("/pageGetAllMusic")
    public Resp pageGetAllMusic(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<Music>().orderByDesc("publish_time");
        return Resp.ok(musicService.page(new Page<>(page, limit), musicQueryWrapper));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 创建一个音乐
     * @Date
     * @Param Movie
     **/
    @PostMapping("/createMusic")
    public Resp createMusic(@RequestBody Music music) {
        if (musicService.save(music)) {
            return Resp.ok("保存成功");
        }
        return Resp.err("保存失败");
    }


    /**
     * @return Resp
     * 已经测通
     * @Author LuoRuiJie
     * @Description 小组根据对象的名字获取图片的URL
     * @Date
     * @Param String
     **/
    @GetMapping("/getMusicHeadImgURL")
    public Resp getMusicHeadImgURL(@RequestParam("name") String imgName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        String objectLocation = "/music/";
        String target = objectLocation + imgName;
        String presignedObjectUrl = minioService.getPresignedObjectUrl("recommend", target);
        return Resp.ok(presignedObjectUrl);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id删除音乐
     * @Date
     * @Param int
     **/
    @PostMapping("/removeMusicById/{id}")
    public Resp removeMusicById(@PathVariable int id) {
        if (musicService.removeById(id)) {
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }

}
