package com.sys.recommend.service.impl;

import com.sys.recommend.entity.Movie;
import com.sys.recommend.mapper.MovieMapper;
import com.sys.recommend.service.MovieService;
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
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {

}
