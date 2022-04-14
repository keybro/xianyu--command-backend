package com.sys.recommend.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sys.recommend.service.MinioService;
import com.sys.recommend.service.MinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @Author: LuoRuiJie
 * @Date: 2021/7/13 9:42
 * @Version 1.0
 */

@Service
public class MinioServiceImpl extends ServiceImpl<BaseMapper<MinioClient>, MinioClient> implements MinioService {

    @Autowired
    private MinioClient minioClient;

    /**
     * 检查存储桶是否存在
     *
     * @param bucketName 存储桶名称
     * @return
     */
    public boolean bucketExists(String bucketName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, ErrorResponseException {
        boolean flag = false;
        flag = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (flag) {
            return true;
        }
        return false;
    }


    /**
     * 通过InputStream上传对象
     *
     * @param bucketName  存储桶名称
     * @param objectName  存储桶里的对象名称
     * @param inputStream 要上传的流
     * @param contentType 上传的文件类型 例如 video/mp4  image/jpg
     * @return
     */
    @Override
    public boolean putObject(String bucketName, String objectName, InputStream inputStream, String contentType) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                    //不清楚文件的大小时，可以传-1，10485760。如果知道大小也可以传入size，partsize。
                    inputStream, -1, 10485760)
                    .contentType(contentType)
                    .build());
            StatObjectResponse statObject = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
            if (statObject != null && statObject.size() > 0) {
                return true;
            }
        }
        return false;

    }


    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    @Override
    public String getPresignedObjectUrl(String bucketName, String objectName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException {
        boolean flag = bucketExists(bucketName);
        String url = "";
        if (flag) {
            url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(objectName)

                    //                       .expiry(24 * 60 * 60)//用秒来计算一天时间有效期
//                        .expiry(1, TimeUnit.DAYS)//按天传参
//                        .expiry(1, TimeUnit.HOURS)//按小时传参数
                    .build());
        }
        return url;
    }



}
