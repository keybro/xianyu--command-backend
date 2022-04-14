package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Movie;
import com.sys.recommend.entity.Music;
import com.sys.recommend.service.MusicService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
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

    /**
     * @Author LuoRuiJie
     * @Description 首页根据score来推荐5本书籍
     * @Date
     * @Param null
     * @return Resp
     * 已测通
     **/
    @GetMapping("/getMainPageMusic")
    public Resp getMainPageBook(){
        QueryWrapper<Music> MusicMainPageQueryWrapper = new QueryWrapper<Music>().orderByDesc("music_score").last("limit 5");
        List<Music> musicMainPageList = musicService.list(MusicMainPageQueryWrapper);
        return Resp.ok(musicMainPageList);
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据id获取音乐详情
     * @Date
     * @Param int
     * @return Resp
     *
     **/
    @GetMapping("/getMusicById/{id}")
    public Resp getMusicById(@PathVariable int id){
        Music music = musicService.getById(id);
        return Resp.ok(music);
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据音乐id推荐类型相似的音乐，5个
     * @Date
     * @Param int
     * @return Resp
     * 已测通，已接通
     **/
    @GetMapping("/getMusicRecommendList/{id}")
    public Resp getMusicRecommendList(@PathVariable int id){
        Music targetMusic = musicService.getById(id);
        String musicType = targetMusic.getMusicType();
        QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<Music>().like("music_type", musicType).ne("music_id", id).last("limit 5");
        List<Music> list = musicService.list(musicQueryWrapper);
        return Resp.ok(list);
    }

    /**
     * @Author LuoRuiJie
     * @Description 根据用户选择的类型标签查询音乐,分页获取
     * @Date
     * @Param Map
     * @return Resp
     * 已测通
     **/
    @GetMapping("/getMusicListByType")
    public Resp getMusicListByType(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        System.out.println(limit);
        int page = Integer.parseInt(params.get("currentPage"));
        System.out.println(page);
        String bookType = params.get("music_type");
        System.out.println(bookType);
        if (bookType.equals("全部")){
            QueryWrapper<Music> allMusicQueryWrapper = new QueryWrapper<Music>().orderByDesc("publish_time");
            Page<Music> moviePage = musicService.page(new Page<>(page, limit), allMusicQueryWrapper);
            return Resp.ok(moviePage);
        }
        QueryWrapper<Music> musicQueryWrapper = new QueryWrapper<Music>().like("music_type", bookType).orderByDesc("publish_time");
        Page<Music> musicPage = musicService.page(new Page<>(page, limit), musicQueryWrapper);
        return Resp.ok(musicPage);
    }

}
