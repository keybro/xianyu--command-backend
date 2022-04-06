package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sys.recommend.entity.Movie;
import com.sys.recommend.entity.Music;
import com.sys.recommend.service.MusicService;
import com.sys.recommend.tool.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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




}
