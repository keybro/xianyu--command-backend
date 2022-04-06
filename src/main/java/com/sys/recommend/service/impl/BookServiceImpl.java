package com.sys.recommend.service.impl;

import com.sys.recommend.entity.Book;
import com.sys.recommend.mapper.BookMapper;
import com.sys.recommend.service.BookService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-04-06
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

}
