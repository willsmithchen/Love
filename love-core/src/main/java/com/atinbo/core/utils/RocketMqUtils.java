package com.atinbo.core.utils;

import com.lujia.event.BaseEvent;
import lombok.experimental.UtilityClass;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * rocketmq工具类
 *
 * @author breggor
 */
@UtilityClass
public class RocketMqUtils {

    private static final String KEYS = "KEYS";
    private static final String TAGS = "TAGS";


    public <T extends BaseEvent> Message<T> buildMessage(T event) {
        if (StringUtil.isBlank(event.keys()) || StringUtil.isBlank(event.tags())) {
            throw new RuntimeException("keys或tags是必填项");
        }

        return MessageBuilder.withPayload(event).setHeader(TAGS, event.tags()).setHeader(KEYS, event.keys()).build();
    }
}
