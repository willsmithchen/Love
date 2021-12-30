package com.love.wife.model.websocket;

import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/9 22:24
 */

@Slf4j
@Component
@EnableAsync
public class MediaTransfer {
    private String rtspUrl;
    private String rtspTransportType;
    private static FFmpegFrameGrabber grabber;
    private static boolean isStart = false;

    /**
     * 视频频率
     */
    private static int frameRate = 30;
    /**
     * 视频宽度
     */
    private static int frameWidth = 1080;
    /**
     * 视频高度
     */
    private static int frameHeight = 720;

    /**
     * 开启获取rtsp流，通过websocket传输数据
     */
    @Async
    public void live() {
        log.info("连接rtsp：" + rtspUrl + "，开始创建grabber");

    }

    /**
     * 构建视频抓取器
     *
     * @param rtsp -摄像头rtsp地址
     * @return
     */
    public FFmpegFrameGrabber createGrabber(String rtsp) {
        //获取视频源
        FFmpegFrameGrabber grabber = null;
        try {
            grabber = FFmpegFrameGrabber.createDefault(rtsp);
            grabber.setOption("rtsp_transport", rtspTransportType);
            //设置帧率
            grabber.setFrameRate(frameRate);
            //设置获取的视频宽度
            grabber.setVideoBitrate(2000000);
            return grabber;
        } catch (FrameGrabber.Exception e) {
            log.error("创建解析rtsp FFmpegFrameFrameGrabber 失败");
            log.error("create rtsp FFmpegFrameGrabber exception:", e);
            return null;
        }
    }

    /**
     * 推送图片
     */
    public void startCameraPush() {
        Java2DFrameConverter java2DFrameConverter = new Java2DFrameConverter();
        while (true) {
            if (grabber == null) {
                log.info("重试连接rtsp：" + rtspUrl + "开始创建grabber");
                grabber = createGrabber(rtspUrl);
                log.info("创建grabber成功");
            }
            try {
                if (grabber != null && isStart) {
                    grabber.start();
                    isStart = true;
                    log.info("启动grabber成功");
                }
                if (grabber != null) {
                    Frame frame = grabber.grabImage();
                    if (null == frame) {
                        continue;
                    }
                    BufferedImage bufferedImage = java2DFrameConverter.getBufferedImage(frame);
                    byte[] bytes = imageToBytes(bufferedImage, "jpg");
                    //使用websocket发送视频帧数据

                }
            } catch (FrameGrabber.Exception | RuntimeException e) {
                log.error("因为异常，grabber关闭，rtsp连接断开，尝试重新连接");
                log.error("exception：",e);
                if(grabber!=null){
                    try {
                        grabber.stop();

                    } catch (FrameGrabber.Exception exception) {
                        log.error("grabber stop exception:",exception);
                    }finally {
                        grabber=null;
                        isStart = false;
                    }
                }
            }
        }

    }

    /**
     * 图片转字节数组
     *
     * @param bImage -图片数据
     * @param format -格式
     * @return 图片字节码
     */
    private byte[] imageToBytes(BufferedImage bImage, String format) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, format, out);
        } catch (IOException e) {
            log.error("bufferImage转byte数组异常");
            log.error("bufferImage transfer byte[] exception:", e);
            return null;
        }
        return out.toByteArray();
    }
}
