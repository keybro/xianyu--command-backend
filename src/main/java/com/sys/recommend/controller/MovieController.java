package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Movie;
import com.sys.recommend.service.MinioService;
import com.sys.recommend.service.MovieService;
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
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

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
    @GetMapping("/getMainPageMovie")
    public Resp getMainPageBook() {
        QueryWrapper<Movie> MovieMainPageQueryWrapper = new QueryWrapper<Movie>().orderByDesc("movie_score").last("limit 5");
        List<Movie> movieMainPageList = movieService.list(MovieMainPageQueryWrapper);
        return Resp.ok(movieMainPageList);
    }


    /**
     * @return Resp
     * 已测通
     * @Author LuoRuiJie
     * @Description 根据id获取电影
     * @Date
     * @Param int
     **/
    @GetMapping("/getMovieById/{id}")
    public Resp getMovieById(@PathVariable int id) {
        Movie movie = movieService.getById(id);
        return Resp.ok(movie);
    }


    /**
     * @return Resp
     * 已测通，已接通
     * @Author LuoRuiJie
     * @Description 根据当前页面电影id，找到对应类型，进行推荐，5个
     * @Date
     * @Param int
     **/
    @GetMapping("/getMovieRecommendList/{id}")
    public Resp getBookRecommendList(@PathVariable int id) {
        Movie targetMovie = movieService.getById(id);
        String bookType = targetMovie.getMovieType();
        QueryWrapper<Movie> recommendMovieQueryWrapper = new QueryWrapper<Movie>().like("movie_type", bookType).ne("movie_id", id).last("limit 5");
        List<Movie> recommendMovieList = movieService.list(recommendMovieQueryWrapper);
        if (recommendMovieList.isEmpty()) {
            String[] split = bookType.split("/");
            QueryWrapper<Movie> queryWrapper = new QueryWrapper<Movie>().like("movie_type", split[0]).ne("movie_id", id).last("limit 5");
            return Resp.ok(movieService.list(queryWrapper));
        }
        return Resp.ok(recommendMovieList);
    }

    /**
     * @return Resp
     * 已测通，已接通
     * @Author LuoRuiJie
     * @Description 根据用户选择的类型标签查询电影, 分页获取
     * @Date
     * @Param Map
     **/
    @GetMapping("/getMovieListByType")
    public Resp getBookListByType(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String bookType = params.get("movie_type");
        if (bookType.equals("全部")) {
            QueryWrapper<Movie> allMovieQueryWrapper = new QueryWrapper<Movie>().orderByDesc("show_time");
            Page<Movie> moviePage = movieService.page(new Page<>(page, limit), allMovieQueryWrapper);
            return Resp.ok(moviePage);
        }
        QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<Movie>().like("movie_type", bookType).orderByDesc("show_time");
        Page<Movie> moviePage = movieService.page(new Page<>(page, limit), movieQueryWrapper);
        return Resp.ok(moviePage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 搜索用，当搜索作品类型选择为电影的时候调用
     * @Date
     * @Param Map
     **/
    @GetMapping("/getSearchMovie")
    public Resp getSearchMovie(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String keyword = params.get("keyword");
        QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<Movie>().like("movie_name", keyword).orderByDesc("show_time");
        return Resp.ok(movieService.page(new Page<>(page, limit), movieQueryWrapper));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 电影的top100，分页获取
     * @Date
     * @Param Map
     **/
    @GetMapping("/getMovieTop")
    public Resp getMovieTop(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Movie> movieQuery = new QueryWrapper<Movie>().orderByDesc("movie_score");
        return Resp.ok(movieService.page(new Page<>(page, limit), movieQuery));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 分页获取所有的电影，管理员界面用
     * @Date
     * @Param Map
     **/
    @GetMapping("/pageGetAllMovie")
    public Resp pageGetAllMovie(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Movie> movieQueryWrapper = new QueryWrapper<Movie>().orderByDesc("show_time");
        return Resp.ok(movieService.page(new Page<>(page, limit), movieQueryWrapper));
    }


    /**
     * @return Resp
     * 已经测通
     * @Author LuoRuiJie
     * @Description 小组根据对象的名字获取图片的URL
     * @Date
     * @Param String
     **/
    @GetMapping("/getMovieHeadImgURL")
    public Resp getGroupHeadImgURL(@RequestParam("name") String imgName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        String objectLocation = "/movie/";
        String target = objectLocation + imgName;
        String presignedObjectUrl = minioService.getPresignedObjectUrl("recommend", target);
        return Resp.ok(presignedObjectUrl);
    }

    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 创建一个电影
     * @Date
     * @Param Movie
     **/
    @PostMapping("/createMovie")
    public Resp createMovie(@RequestBody Movie movie) {
        if (movieService.save(movie)) {
            return Resp.ok("保存成功");
        }
        return Resp.err("保存失败");
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id删除电影
     * @Date
     * @Param int
     **/
    @PostMapping("/removeMovieById/{id}")
    public Resp removeMovieById(@PathVariable int id) {
        if (movieService.removeById(id)) {
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }


}
