package com.sys.recommend.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sys.recommend.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LuoRuiJie
 * @since 2022-03-30
 */
public interface UserMapper extends BaseMapper<User> {
    Page<User> findAllUser(Page<User> page, @Param("id") int id);

}
