package com.sys.recommend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.minio.MinioClient;
import io.minio.errors.*;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: LuoRuiJie
 * @Date: 2021/7/13 9:42
 * @Version 1.0
 */
public interface MinioService extends IService<MinioClient> {


    /**
     * 通过InputStream上传对象
     *
     * @param bucketName  存储桶名称
     * @param objectName  存储桶里的对象名称
     * @param inputStream 要上传的流
     * @param contentType 上传的文件类型 例如 video/mp4  image/jpg
     * @return
     */
    boolean putObject(String bucketName, String objectName, InputStream inputStream, String contentType) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException;


    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName 存储桶名称
     * @param objectName 存储桶里的对象名称
     * @return
     */
    String getPresignedObjectUrl(String bucketName, String objectName) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, ErrorResponseException, XmlParserException, InsufficientDataException, InternalException;
}
