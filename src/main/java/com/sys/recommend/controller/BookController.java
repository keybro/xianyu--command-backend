package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Book;
import com.sys.recommend.entity.Movie;
import com.sys.recommend.service.BookService;
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
@RequestMapping("/book")
public class BookController extends BaseController{

    @Autowired
    private BookService bookService;


    /**
     * @Author LuoRuiJie
     * @Description 首页根据score来推荐5本书籍
     * @Date
     * @Param null
     * @return Resp
     * 已测通
     **/
    @GetMapping("/getMainPageBook")
    public Resp getMainPageBook(){
        QueryWrapper<Book> bookMainPageQueryWrapper = new QueryWrapper<Book>().orderByDesc("score").last("limit 5");
        List<Book> bookMainPageList = bookService.list(bookMainPageQueryWrapper);
        return Resp.ok(bookMainPageList);
    }



    /**
     * @Author LuoRuiJie
     * @Description 根据bookId获取详情
     * @Date
     * @Param int
     * @return Resp
     **/
    @GetMapping("/getBookById/{id}")
    public Resp getBookById(@PathVariable int id){
        Book book = bookService.getById(id);
        return Resp.ok(book);
    }



    /**
     * @Author LuoRuiJie
     * @Description 根据当前页面书籍id，找到对应类型，进行推荐，5个
     * @Date
     * @Param int
     * @return Resp
     *
     **/
    @GetMapping("/getBookRecommendList/{id}")
    public Resp getBookRecommendList(@PathVariable int id){
        Book targetBook = bookService.getById(id);
        String bookType = targetBook.getBookType();
        QueryWrapper<Book> recommendBookQueryWrapper = new QueryWrapper<Book>().like("book_type", bookType).ne("book_id",id).last("limit 5");
        List<Book> recommendBookList = bookService.list(recommendBookQueryWrapper);
        return Resp.ok(recommendBookList);
    }


    /**
     * @Author LuoRuiJie
     * @Description 根据用户选择的类型标签查询书籍,分页获取
     * @Date
     * @Param Map
     * @return Resp
     **/
    @GetMapping("/getBookListByType")
    public Resp getBookListByType(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String bookType = params.get("book_type");
        if (bookType.equals("全部")){
            QueryWrapper<Book> allBookQueryWrapper = new QueryWrapper<Book>().orderByDesc("publish_time");
            Page<Book> bookPage = bookService.page(new Page<>(page, limit), allBookQueryWrapper);
            return Resp.ok(bookPage);
        }
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<Book>().like("book_type", bookType).orderByDesc("publish_time");
        Page<Book> bookPage = bookService.page(new Page<>(page, limit), bookQueryWrapper);
        return Resp.ok(bookPage);
    }


    /**
     * @Author LuoRuiJie
     * @Description 搜索用，当搜索作品类型选择为电影的时候调用
     * @Date
     * @Param Map
     * @return Resp
     **/
    @GetMapping("/getSearchBook")
    public Resp getSearchBook(@RequestParam Map<String,String> params){
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String keyword = params.get("keyword");
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<Book>().like("book_name", keyword).orderByDesc("publish_time");
        return Resp.ok(bookService.page(new Page<>(page,limit),bookQueryWrapper));
    }

}
