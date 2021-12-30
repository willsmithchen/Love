package com.love.wife.model.websocket;


import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.lujia.model.Outcome;
import org.thymeleaf.util.ArrayUtils;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author lujia chen
 * @version 1.0
 * @date 2020/9/9 23:13
 */

public class ImageEncoder implements Encoder.Text<Image> {

    @Override
    public String encode(Image image) throws EncodeException {
        if (image != null && ArrayUtils.isEmpty(new byte[][]{image.getImageByte()})) {
            String base64Image = Base64.encode(image.getImageByte());
            return JSON.toJSONString(Outcome.success().setMessage("获取视频帧成功").setData(base64Image));
        }
        return JSON.toJSONString(Outcome.success().setMessage("获取视频帧失败"));
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
