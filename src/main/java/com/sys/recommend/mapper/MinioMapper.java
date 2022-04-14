package com.sys.recommend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.minio.MinioClient;
import org.springframework.stereotype.Service;

/**
 * @Author: LuoRuiJie
 * @Date: 2021/7/13 9:58
 * @Version 1.0
 */
@Service
public interface MinioMapper extends BaseMapper<MinioClient> {
}
