package com.love.wife.service;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author chenlujia
 * minio接口
 */

public interface MinIoService {

    /**
     * 上传文件
     *
     * @param bucketName  -存储桶名称
     * @param inputStream -文件流
     * @param fileName    -存储桶里的对象名称
     * @return 储存文件地址 bucketName/fileName;
     * @throws IOException
     */
    String upload(String bucketName, InputStream inputStream, String fileName) throws IOException;

    /**
     * 上传文件的前缀
     *
     * @param inputStream -文件流
     * @param suffix      -文件名前缀
     * @return 储存文件地址 bucketName/fileName;
     * @throws IOException
     */
    String uploadSuffix(InputStream inputStream, String suffix) throws IOException;

    /**
     * 上传文件的前缀
     *
     * @param data   -字节流
     * @param suffix -文件名前缀
     * @return 储存文件地址 bucketName/fileName;
     * @throws IOException
     */
    String uploadSuffix(byte[] data, String suffix) throws IOException;

    /**
     * 文件访问路径
     *
     * @param bucketName -存储桶名称
     * @param fileName   -存储桶里的对象名称
     * @return -返回文件的访问路径
     * @throws IOException
     */
    String getPath(String bucketName, String fileName) throws IOException;

    /**
     * 删除文件
     *
     * @param bucketName -存储桶名称
     * @param fileName   -存储文件名称
     * @return -是否
     */
    boolean deleteFile(String bucketName, String fileName);

    /**
     * 生成一个给HTTP GET请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行下载，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName -存储桶名称
     * @param objectName -存储桶里的对象名称
     * @param expires    -失效时间（以秒为单位），默认是7天，不得大于七天
     * @return -返回一个有时间期限的图片浏览路径
     */
    String browseImgPath(String bucketName, String objectName, Integer expires);

    /**
     * 生成一个给HTTP PUT请求用的presigned URL。
     * 浏览器/移动端的客户端可以用这个URL进行上传，即使其所在的存储桶是私有的。这个presigned URL可以设置一个失效时间，默认值是7天。
     *
     * @param bucketName -存储桶名称
     * @param objectName -存储桶里的对象名称
     * @param expires    -失效时间（以秒为单位），默认是7天，不得大于七天
     * @return -生成一个上传的URL地址
     */
    String preSignedPutObject(String bucketName, String objectName, Integer expires);

}
