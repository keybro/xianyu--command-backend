package com.sys.recommend.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: LuoRuiJie
 * @Date: 2021/7/6 14:42
 * @Version 1.0
 */
@Data
@Configuration
public class MinioConfig {
    /**
     * ip
     */
    @Value("${minio.endpoint}")
    private String endPoint;

    /**
     * port
     */
    @Value("${minio.port}")
    private int port;

    /**
     * accessKey:登录账号
     */
    @Value("${minio.accessKey}")
    private String accessKey;

    /**
     * secretKey:登录密码
     */
    @Value("${minio.secretKey}")
    private String secretKey;

    /**
     * bucketName:存储桶
     */
    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * secure:如果是true，则用的是https而不是http,默认值是true
     */
    @Value("${minio.secure}")
    private Boolean secure;


    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public Boolean getSecure() {
        return secure;
    }

    public void setSecure(Boolean secure) {
        this.secure = secure;
    }

    @Bean
    public MinioClient getMinioClient() {
        MinioClient minioClient = MinioClient.builder().endpoint(endPoint, port, secure)
                .credentials(accessKey, secretKey)
                .build();
        return minioClient;
    }


}
