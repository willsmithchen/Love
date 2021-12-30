//package com.atinbo.common;
//
//import lombok.experimental.UtilityClass;
//import lombok.extern.slf4j.Slf4j;
//import ws.schild.jave.MultimediaInfo;
//import ws.schild.jave.MultimediaObject;
//
//import java.io.File;
//import java.net.URL;
//
//@Slf4j
//@UtilityClass
//public class VideoUtil {
//
//    /**
//     * 获取视频时长
//     * 单位：毫秒
//     *
//     * @param path
//     * @return long
//     */
//    long getVideoTime(String path) {
//        return getVideoTime(new File(path));
//    }
//
//    /**
//     * 获取视频时长
//     * 单位：毫秒
//     *
//     * @param file
//     * @return long
//     */
//    long getVideoTime(File file) {
//        return getDuration(new MultimediaObject(file));
//    }
//
//    /**
//     * 获取视频时长
//     * 单位：毫秒
//     *
//     * @param url
//     * @return long
//     */
//    long getVideoTime(URL url) {
//        return getDuration(new MultimediaObject(url));
//    }
//
//    private static long getDuration(MultimediaObject instance) {
//        long drt = 0;
//        try {
//            MultimediaInfo result = instance.getInfo();
//            if (result.getDuration() > 0) {
//                drt = result.getDuration();
//                int hour = (int) (drt / 3600);
//                int minute = (int) (drt % 3600) / 60;
//                int second = (int) (drt - hour * 3600 - minute * 60);
//                log.info("视频时长: hour={}, minute={}, second={}", hour, minute, second);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return drt;
//    }
//}