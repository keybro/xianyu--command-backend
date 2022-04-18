package com.sys.recommend.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.Book;
import com.sys.recommend.service.BookService;
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
 * 前端控制器
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-06
 */
@RestController
@RequestMapping("/book")
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

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
    @GetMapping("/getMainPageBook")
    public Resp getMainPageBook() {
        QueryWrapper<Book> bookMainPageQueryWrapper = new QueryWrapper<Book>().orderByDesc("score").last("limit 5");
        List<Book> bookMainPageList = bookService.list(bookMainPageQueryWrapper);
        return Resp.ok(bookMainPageList);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据bookId获取详情
     * @Date
     * @Param int
     **/
    @GetMapping("/getBookById/{id}")
    public Resp getBookById(@PathVariable int id) {
        Book book = bookService.getById(id);
        return Resp.ok(book);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据当前页面书籍id，找到对应类型，进行推荐，5个
     * @Date
     * @Param int
     **/
    @GetMapping("/getBookRecommendList/{id}")
    public Resp getBookRecommendList(@PathVariable int id) {
        Book targetBook = bookService.getById(id);
        String bookType = targetBook.getBookType();
        QueryWrapper<Book> recommendBookQueryWrapper = new QueryWrapper<Book>().like("book_type", bookType).ne("book_id", id).last("limit 5");
        List<Book> recommendBookList = bookService.list(recommendBookQueryWrapper);
        return Resp.ok(recommendBookList);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据用户选择的类型标签查询书籍, 分页获取
     * @Date
     * @Param Map
     **/
    @GetMapping("/getBookListByType")
    public Resp getBookListByType(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String bookType = params.get("book_type");
        if (bookType.equals("全部")) {
            QueryWrapper<Book> allBookQueryWrapper = new QueryWrapper<Book>().orderByDesc("publish_time");
            Page<Book> bookPage = bookService.page(new Page<>(page, limit), allBookQueryWrapper);
            return Resp.ok(bookPage);
        }
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<Book>().like("book_type", bookType).orderByDesc("publish_time");
        Page<Book> bookPage = bookService.page(new Page<>(page, limit), bookQueryWrapper);
        return Resp.ok(bookPage);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 搜索用，当搜索作品类型选择为电影的时候调用
     * @Date
     * @Param Map
     **/
    @GetMapping("/getSearchBook")
    public Resp getSearchBook(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        String keyword = params.get("keyword");
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<Book>().like("book_name", keyword).orderByDesc("publish_time");
        return Resp.ok(bookService.page(new Page<>(page, limit), bookQueryWrapper));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 书籍的top100，分页获取
     * @Date
     * @Param Map
     **/
    @GetMapping("/getBookTop")
    public Resp getBookTop(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Book> bookQuery = new QueryWrapper<Book>().orderByDesc("score");
        return Resp.ok(bookService.page(new Page<>(page, limit), bookQuery));
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 分页获取所有的书籍，管理员界面用
     * @Date
     * @Param Map
     **/
    @GetMapping("/pageGetAllBook")
    public Resp pageGetAllBook(@RequestParam Map<String, String> params) {
        int limit = Integer.parseInt(params.get("limit"));
        int page = Integer.parseInt(params.get("currentPage"));
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<Book>().orderByDesc("publish_time");
        return Resp.ok(bookService.page(new Page<>(page, limit), bookQueryWrapper));
    }


    /**
     * @return Book
     * @Author LuoRuiJie
     * @Description 管理员上传图书
     * @Date
     * @Param Book
     **/
    @PostMapping("/createBook")
    public Resp createBook(@RequestBody Book book) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        if (bookService.save(book)) {
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
    @GetMapping("/getBookHeadImgURL")
    public Resp getGroupHeadImgURL(@RequestParam("name") String imgName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        String objectLocation = "/book/";
        String target = objectLocation + imgName;
        String presignedObjectUrl = minioService.getPresignedObjectUrl("recommend", target);
        return Resp.ok(presignedObjectUrl);
    }


    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据id删除书籍
     * @Date
     * @Param int
     **/
    @PostMapping("/removeBookById/{id}")
    public Resp removeBookById(@PathVariable int id) {
        if (bookService.removeById(id)) {
            return Resp.ok("删除成功");
        }
        return Resp.err("删除失败");
    }


}
