package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Book;
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

}
