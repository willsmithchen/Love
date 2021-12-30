package com.love.wife.service;

import com.love.wife.exception.DemoException;
import com.love.wife.model.baidu.TextModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/30 10:57
 */

public interface AudioService {
    /**
     * 文字转音频
     *
     * @param textModel -文字模板参数
     * @return
     */
    Boolean textToAudio(TextModel textModel) throws Exception;

    /**
     * 上传图片
     *
     * @param id   -用户id
     * @param file -要上传的图片文件
     * @return
     */
    Boolean uploadImage(Long id, MultipartFile file);

    /**
     * 预览图片
     *
     * @param id       -图片id
     * @param response
     */
    void viewImage(Long id, HttpServletResponse response);

    /**
     * 随机获取验证码格式
     *
     * @param request
     * @param response
     */
    void getCode(HttpServletRequest request, HttpServletResponse response);

    /**
     * 预览视频功能一
     *
     * @param response
     */
    void getVideo(HttpServletResponse response);

    /**
     * 预览视频功能二
     *
     * @param response
     */
    void getPreview(HttpServletResponse response);

    /**
     * 预览视频功能三
     *
     * @param response
     */
    void previewVideo(String bucketName, String objectName, HttpServletResponse response);

    /**
     * 音频预览
     *
     * @param response
     */
    void previewAudio(HttpServletResponse response);

    /**
     * 预览PDF
     *
     * @param response
     */
    void previewPdf( HttpServletResponse response);
}
