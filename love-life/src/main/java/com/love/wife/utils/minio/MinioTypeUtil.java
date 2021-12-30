package com.love.wife.utils.minio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.UUID;

/**
 * @author chenlujia
 */

@Slf4j
public class MinioTypeUtil {

    public static String getMediaType(String suffix) throws IOException {
        switch (suffix) {
            case ".png":
                return MediaType.IMAGE_PNG_VALUE;
            case ".jpeg":
                return MediaType.IMAGE_JPEG_VALUE;
            case ".docx":
            case ".doc":
                return MediaType.TEXT_PLAIN_VALUE;
            case ".pdf":
                return MediaType.APPLICATION_PDF_VALUE;
            case ".ppt":
            case ".pptx":
            case ".avi":
            case ".mp4":
            case ".mp3":
                return MediaType.MULTIPART_FORM_DATA_VALUE;


        }
        log.info("您上传资源类型暂不支持");
        throw new IOException("您上传资源类型暂不支持");
    }

    public static String getBucketName(String suffix) throws IOException {
        switch (suffix) {
            case ".png":
            case ".jpeg":
            case ".jpg":
                return "registered-image";
            case ".docx":
            case ".doc":
            case ".pdf":
                return "word";
            case ".ppt":
            case ".pptx":
                return "ppt";
            case ".avi":
            case ".mp4":
                return "video";
            case ".mp3":
                return "audio";
        }
        log.info("您上传资源类型暂不支持");
        throw new IOException("您上传资源类型暂不支持");
    }

    /**
     * 获取文件名
     *
     * @param suffix 后缀
     * @return 返回上传文件名
     */
    public static String getFileName(String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid + suffix;
    }
}

