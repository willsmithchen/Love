package com.lujia.event;


import lombok.NonNull;

import java.io.Serializable;

/**
 * 事件基础类：keys与tags不在消息体内，不为作为唯一键校验
 *
 * @author breggor
 */
public abstract class BaseEvent implements Serializable {
    /**
     * key标识：不在消息体内
     */
    protected String keys;

    /**
     * tags标签：不在消息体内
     */
    protected String tags = "*";

    public final String keys() {
        return keys;
    }

    public final String tags() {
        return tags;
    }

    public BaseEvent keys(@NonNull String keys) {
        this.keys = keys;
        return this;
    }

    public BaseEvent tags(@NonNull String tags) {
        this.tags = tags;
        return this;
    }
}