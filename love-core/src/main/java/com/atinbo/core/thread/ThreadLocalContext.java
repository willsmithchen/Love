package com.atinbo.core.thread;

import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * 线程上下文数据
 */
@UtilityClass
public class ThreadLocalContext {
    private static final ThreadLocal<Map<String, Object>> GLOBAL_THREAD_LOCAL = new InheritableThreadLocal();

    /**
     * 设置数据
     *
     * @param key
     * @param value
     */
    public static void set(String key, Object value) {
        if (StringUtils.isEmpty(key) || Objects.isNull(value)) {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for set , key : {%s}, value : {%s}", key, value));
        }
        Map<String, Object> entry = GLOBAL_THREAD_LOCAL.get();
        if (entry == null) {
            entry = new HashMap();
        }
        entry.put(key, value);
        GLOBAL_THREAD_LOCAL.set(entry);
    }


    /**
     * 清空
     */
    public static void clean() {
        GLOBAL_THREAD_LOCAL.remove();
    }

    /**
     * 删除数据
     *
     * @param key
     */
    public static void remove(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for remove method , key : {%s}", key));
        }
        Map<String, Object> entry = GLOBAL_THREAD_LOCAL.get();
        if (entry != null) {
            entry.remove(key);
            if (entry.size() == 0) {
                clean();
            } else {
                GLOBAL_THREAD_LOCAL.set(entry);
            }
        }
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static Object get(String key) {
        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException(String.format("ThreadLocal argument empty for remove method , key : {%s}", key));
        }

        Object result = null;
        Map<String, Object> entry = (Map) GLOBAL_THREAD_LOCAL.get();
        if (entry != null && entry.containsKey(key)) {
            result = entry.get(key);
        }
        return result;
    }

    /**
     * 获取数据
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T get(String key, T clazz) {
        return (T) get(key);
    }
}