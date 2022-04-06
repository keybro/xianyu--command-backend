package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sys.recommend.entity.Book;
import com.sys.recommend.entity.Movie;
import com.sys.recommend.service.MovieService;
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
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;


    /**
     * @Author LuoRuiJie
     * @Description 首页根据score来推荐5本书籍
     * @Date
     * @Param null
     * @return Resp
     * 已测通
     **/
    @GetMapping("/getMainPageMovie")
    public Resp getMainPageBook(){
        QueryWrapper<Movie> MovieMainPageQueryWrapper = new QueryWrapper<Movie>().orderByDesc("movie_score").last("limit 5");
        List<Movie> movieMainPageList = movieService.list(MovieMainPageQueryWrapper);
        return Resp.ok(movieMainPageList);
    }


}
