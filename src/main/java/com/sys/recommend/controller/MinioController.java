package com.sys.recommend.controller;

import com.sys.recommend.service.MinioService;
import com.sys.recommend.tool.Resp;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * @Author: LuoRuiJie
 * @Date: 2021/7/12 23:52
 * @Version 1.0
 */
@RestController
@RequestMapping("/io")
public class MinioController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private MinioClient minioClient;

    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 以流的方式上传文件
     * @Date 2021/7/13 9:52
     * @Param String fileName文件的名称，默认上传到minio pdf文件夹下
     **/
//    @PostMapping("/uploadFileByStream")
//    public Resp uploadFileByInputStream() throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
//        String filePath = "C:\\Users\\ZHB\\Desktop\\pdf\\test.pdf";
//        //FileInputStream继承了InputStream，所以可以直接多态
//        InputStream inputStream = FileUtils.readFile(filePath);
//        if (minioService.putObject("test", "pdf/hhh3Pdf.pdf", inputStream, "file/pdf")) {
//            return Resp.ok("上传成功");
//        } else {
//            return Resp.err("上传失败");
//        }
//    }

    /**
     * @return Resp
     * @Author LuoRuiJie
     * @Description 根据桶名，对象名获取URL
     * @Date 2021/7/13 11:15
     * @Param String bucketName,String objectName
     **/
    @GetMapping("/getFileUrl")
    public Resp getFileUrl(@RequestParam Map<String, String> Params) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InsufficientDataException, ErrorResponseException {
        String bucketName = Params.get("bucketName");
        String objectName = Params.get("objectName");
        String url = minioService.getPresignedObjectUrl(bucketName, objectName);
        return Resp.ok(url);
    }


    /**
     * 通过MultipartFile，上传文件
     *
     * @param bucketname  存储桶
     * @param file        文件
     * @param objectName  对象名
     * @param contentType 如 image/png
     */
    @PostMapping("/uploadFileByAllParams")
    public Resp putObject(String bucketname, MultipartFile file,
                          String objectName, String contentType) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        InputStream inputStream = file.getInputStream();
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder().bucket(bucketname).object(objectName).contentType(contentType)
                .stream(inputStream, inputStream.available(), -1)
                .build());
        return Resp.ok(objectWriteResponse);
    }


    /**
     * @return Resp
     * 已测通
     * @Author LuoRuiJie
     * @Description 小组头像选取后上传到minio的recommend/group/文件名下
     * @Date
     * @Param MultipartFile, String
     **/
    @PostMapping("/uploadFile")
    public Resp putObjectToGroupHead(@RequestParam("file") MultipartFile file, @RequestParam("name") String objectName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        String objectLocation = "/group/";
        String targetObjectName = objectLocation + objectName;
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        minioService.putObject("recommend", targetObjectName, inputStream, contentType);
        return Resp.ok("上传成功");
    }


    /**
     * @return Resp
     * 已测通
     * @Author LuoRuiJie
     * @Description 小组头像选取后上传到minio的recommend/group/文件名下
     * @Date
     * @Param MultipartFile, String
     **/
    @PostMapping("/uploadFileBook")
    public Resp putObjectToBookHead(@RequestParam("file") MultipartFile file, @RequestParam("name") String objectName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        String objectLocation = "/book/";
        String targetObjectName = objectLocation + objectName;
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        minioService.putObject("recommend", targetObjectName, inputStream, contentType);
        return Resp.ok("上传成功");
    }


    /**
     * @return Resp
     * 已测通
     * @Author LuoRuiJie
     * @Description 小组头像选取后上传到minio的recommend/movie/文件名下
     * @Date
     * @Param MultipartFile, String
     **/
    @PostMapping("/uploadFileMovie")
    public Resp putObjectToMovieHead(@RequestParam("file") MultipartFile file, @RequestParam("name") String objectName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        String objectLocation = "/movie/";
        String targetObjectName = objectLocation + objectName;
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        minioService.putObject("recommend", targetObjectName, inputStream, contentType);
        return Resp.ok("上传成功");
    }


    /**
     * @return Resp
     * 已测通
     * @Author LuoRuiJie
     * @Description 小组头像选取后上传到minio的recommend/music/文件名下
     * @Date
     * @Param MultipartFile, String
     **/
    @PostMapping("/uploadFileMusic")
    public Resp putObjectToMusicHead(@RequestParam("file") MultipartFile file, @RequestParam("name") String objectName) throws IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException {
        String objectLocation = "/music/";
        String targetObjectName = objectLocation + objectName;
        InputStream inputStream = file.getInputStream();
        String contentType = file.getContentType();
        minioService.putObject("recommend", targetObjectName, inputStream, contentType);
        return Resp.ok("上传成功");
    }


}

