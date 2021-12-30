package com.love.wife.service.impl;

import com.alibaba.cloud.nacos.client.NacosPropertySourceBuilder;
import com.love.wife.entity.Wife;
import com.love.wife.mapper.LoveMapper;
import com.love.wife.model.baidu.BaiduYaml;
import com.love.wife.model.baidu.TextModel;
import com.love.wife.service.AudioService;
import com.love.wife.service.MinIoService;
import com.love.wife.utils.baidu.ConnUtil;
import com.love.wife.utils.baidu.TokenHolder;
import com.love.wife.utils.minio.MinioUtil;
import com.wf.captcha.*;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/8/30 10:59
 */
@Slf4j
@Service
public class AudioServiceImpl implements AudioService {
    @Resource
    private BaiduYaml baiduYaml;
    @Resource
    private MinIoService minioService;
    @Resource
    private LoveMapper loveMapper;
    @Resource
    private MinioUtil minioUtil;
    /**
     * 音频前缀
     */
    private static String AUDIO_SUFFIX = "audio/";

    /**
     * 文字转音频
     *
     * @param textModel -文字模板参数
     * @return
     */
    @Override
    public Boolean textToAudio(TextModel textModel) throws Exception {
        Boolean result = false;
        TokenHolder tokenHolder = baiduYaml.getTokenHolder();
        String token = tokenHolder.getToken();
        String params = "tex=" + ConnUtil.urlEncode(textModel.getText());
        params += "&per=" + textModel.getPer();
        params += "&spd=" + textModel.getSpd();
        params += "&pit=" + textModel.getPit();
        params += "&vol=" + textModel.getVol();
        params += "&cuid=" + "123456java";
        params += "&tok=" + token;
        params += "&aue=" + textModel.getAue();
        params += "&lan=zh&ctp=1";
        //反馈带上此URL，浏览器上可以测试
        System.out.println(baiduYaml.getUrl() + "?" + params);
        HttpURLConnection connection = (HttpURLConnection) new URL(baiduYaml.getUrl()).openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setConnectTimeout(5000);
        PrintWriter printWriter = new PrintWriter(connection.getOutputStream());
        printWriter.write(params);
        printWriter.close();
        String contentType = connection.getContentType();
        if (contentType.contains(AUDIO_SUFFIX)) {
            byte[] bytes = ConnUtil.getResponseBytes(connection);
            String format = getFormat(textModel.getAue());
            String url = minioService.uploadSuffix(bytes, "." + format);
            result = true;
        } else {
            log.error("error:content-type=" + contentType);
            String res = ConnUtil.getResponseString(connection);
            log.error(res);
        }

        return result;
    }

    /**
     * 下载文件格式，3：mp3(default) 4:pcm-16k 5:pcm-8k 6:wav
     *
     * @param aue -文件格式
     * @return
     */
    private String getFormat(int aue) {
        String[] formats = {"mp3", "pcm", "pcm", "wav"};
        return formats[aue - 3];
    }

    /**
     * 上传图片
     *
     * @param id   -用户id
     * @param file -要上传的图片文件
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean uploadImage(Long id, MultipartFile file) {
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        Boolean result = true;
        try {
            String url = minioService.uploadSuffix(file.getBytes(), suffix);
            Wife wife = loveMapper.selectById(id);
            wife.setImg(url);
            loveMapper.updateById(wife);
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    /**
     * 预览图片
     *
     * @param id       -图片id
     * @param response
     */
    @Override
    public void viewImage(Long id, HttpServletResponse response) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        Wife wife = loveMapper.selectById(id);
        String img = wife.getImg();
        String bucketName = "";
        String fileName = "";
        response.setContentType("image/*,charset=utf-8");
        if (StringUtils.isNotEmpty(img)) {
            bucketName = img.substring(0, img.indexOf("/"));
            fileName = img.substring(bucketName.length() + 1);
        }
        try {
            inputStream = minioUtil.getObject(bucketName, fileName);
            outputStream = response.getOutputStream();
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, len);
            }
            response.flushBuffer();

        } catch (Exception e) {
            e.printStackTrace();
            log.error("预览图片失败" + e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e.getMessage(), e);
                }
            }
        }

    }

    /**
     * 随机获取验证码格式
     *
     * @param request
     * @param response
     */
    @Override
    public void getCode(HttpServletRequest request, HttpServletResponse response) {
        try {
            int result = (int) (Math.random() * 5 + 1);
            switch (result) {
                //算术验证码 数字加减乘除 建议2位运算就行 captcha.setlen(2)
                case 1:
                    ArithmeticCaptcha captcha1 = new ArithmeticCaptcha(120, 40);
                    CaptchaUtil.out(captcha1, request, response);
                    break;
                //中文验证码
                case 2:
                    ChineseCaptcha captcha2 = new ChineseCaptcha(120, 40);
                    CaptchaUtil.out(captcha2, request, response);
                    break;
                //英文雨数字验证码
                case 3:
                    SpecCaptcha captcha3 = new SpecCaptcha(120, 40);
                    CaptchaUtil.out(captcha3, request, response);
                    break;
                //英文与数字动态验证码
                case 4:
                    GifCaptcha captcha4 = new GifCaptcha(120, 40);
                    CaptchaUtil.out(captcha4, request, response);
                    break;
                //中文动态验证码
                default:
                    ChineseGifCaptcha captcha5 = new ChineseGifCaptcha(120, 40);
                    CaptchaUtil.out(captcha5, request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览视频方法一
     *
     * @param response
     */
    @Override
    public void getVideo(HttpServletResponse response) {
        try {
            InputStream inputStream = minioUtil.getObject("video", "202009181724.mp4");
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            String diskfileName = "final.mp4";
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "attachment;filename=\"" + diskfileName + "\"");
            System.out.println("data.length " + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364");
            OutputStream os = response.getOutputStream();
            os.write(data);
            //先声明的流后关闭
            os.flush();
            os.close();
            inputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 预览视频功能二
     *
     * @param response
     */
    @Override
    public void getPreview(HttpServletResponse response) {
        try {
            InputStream inputStream = minioUtil.getObject("video", "202009181724.mp4");
            IOUtils.copy(inputStream, response.getOutputStream());
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Accept-Ranges", "bytes");
            response.flushBuffer();
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * 预览视频功能三
     *
     * @param response
     */
    @Override
    public void previewVideo(String bucketName, String objectName, HttpServletResponse response) {
        InputStream inputStream = minioUtil.getObject("video", "202009181724.mp4");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        try {
            while ((n = inputStream.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            inputStream.close();
            bos.close();
            byte[] buffer = bos.toByteArray();
            response.setContentType("application/octet-stream");
            response.setHeader("Accept-Ranges", "bytes");
            response.setContentLength(buffer.length);
            response.getOutputStream().write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 音频预览
     *
     * @param response
     */
    @Override
    public void previewAudio(HttpServletResponse response) {
        InputStream inputStream = minioUtil.getObject("audio", "2f559956b9b046b68df6a3f091dc9491.mp3");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        try {
            while ((n = inputStream.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            inputStream.close();
            bos.close();
            byte[] buffer = bos.toByteArray();
            response.setContentType("application/octet-stream");
            response.setHeader("Accept-Ranges", "bytes");
            response.setContentLength(buffer.length);
            response.getOutputStream().write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 预览PDF
     *
     * @param response
     */
    @SneakyThrows
    @Override
    public void previewPdf(HttpServletResponse response) {
        response.reset();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "line;filename=file");
        //获得PDF文件流
        InputStream inputStream = minioUtil.getObject("document", "GB∕T 12706.2-2008.pdf");
        OutputStream outputStream = response.getOutputStream();
        IOUtils.write(IOUtils.toByteArray(inputStream), outputStream);
        outputStream.flush();
        inputStream.close();
        outputStream.close();
    }

}
