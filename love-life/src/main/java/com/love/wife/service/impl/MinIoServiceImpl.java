package com.love.wife.service.impl;


import com.love.wife.service.MinIoService;
import com.love.wife.utils.minio.MinioTypeUtil;
import com.love.wife.utils.minio.MinioUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author chenlujia
 */
@Service
@Slf4j
public class MinIoServiceImpl implements MinIoService {

    @Resource
    private MinioUtil minioUtil;

    @Override
    public String upload(String bucketName, InputStream inputStream, String fileName) throws IOException {
        try {
            minioUtil.putObject(bucketName, fileName, inputStream);
        } catch (Exception e) {
            log.info("minio上传文件失败，请检查配置信息");
            throw new IOException("minio上传文件失败，请检查配置信息");
        }
        return bucketName + "/" + fileName;
    }

    @SneakyThrows
    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        String fileName = new SimpleDateFormat("yyyy/MM/dd/").format(new Date()) + MinioTypeUtil.getFileName(suffix);
        String bucketName = MinioTypeUtil.getBucketName(suffix);
        boolean result = minioUtil.bucketExists(bucketName);
        if (!result) {
            minioUtil.makeBucket(bucketName);
        }
        return upload(bucketName, inputStream, fileName);
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return uploadSuffix(new ByteArrayInputStream(data), suffix);
    }

    @Override
    public String getPath(String bucketName, String fileName) throws IOException {
        String url;
        try {
            url = minioUtil.getObjectUrl(bucketName, fileName);
        } catch (Exception e) {
            log.info("minio获取资源url失败");
            throw new IOException("minio获取资源url失败");
        }
        return url;
    }

    @Override
    public boolean deleteFile(String bucketName, String fileName) {
        try {
            minioUtil.removeObject(bucketName, fileName);
        } catch (Exception e) {
            log.info("minio删除文件失败");
            return false;
        }
        return true;
    }

    @Override
    public String browseImgPath(String bucketName, String objectName, Integer expires) {
        String browseImgPath = "";
        try {
            browseImgPath = minioUtil.preSignedGetObject(bucketName, objectName, expires);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return browseImgPath;
    }

    @Override
    public String preSignedPutObject(String bucketName, String objectName, Integer expires) {
        String preSignedPutObject = "";
        preSignedPutObject = minioUtil.preSignedPutObject(bucketName, objectName, expires);
        return preSignedPutObject;
    }
}
