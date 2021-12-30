package com.atinbo.core.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 本地缓存操作接口
 *
 * @author breggor
 */
@Slf4j
public class CaffeineCacheOps {
    private static final int DEFAULT_EXPIRY_IN_SECONDS = 1800;

    private final Cache<Object, Object> cache;

    public CaffeineCacheOps() {
        cache = Caffeine.newBuilder()
                // 设置cache中的数据在写入之后的存活时间
                .expireAfterWrite(DEFAULT_EXPIRY_IN_SECONDS, TimeUnit.SECONDS)
                // 构建cache实例
                .build();
        log.debug("caffeiene cache expiryInSeconds={}", DEFAULT_EXPIRY_IN_SECONDS);
    }

    public boolean contains(Object key) {
        try {
            log.debug("contains key={}", key);
            return cache.getIfPresent(key) != null;
        } catch (Exception ignored) {
            log.warn("Fail to exists key. key={}", key, ignored);
            return false;
        }
    }

    public Object getFromCache(Object key) {
        try {
            return cache.getIfPresent(key);
        } catch (Exception ignored) {
            log.warn("Fail to get cache item... key={}", key, ignored);
            return null;
        }
    }

    public void putIntoCache(Object key, Object value) {
        try {
            cache.put(key, value);
        } catch (Exception ignored) {
            log.warn("Fail to put cache item... key={},", key, ignored);
        }
    }

    public void evictData() {
        try {
            cache.invalidateAll();
        } catch (Exception ignored) {
            log.warn("Fail to clear..ex={}", ignored);
        }
    }

    public void evictData(Object key) {
        try {
            cache.invalidate(key);
        } catch (Exception ignored) {
            log.warn("Fail to remove cache item... key={}", key, ignored);
        }
    }

    public void release() {
        cache.cleanUp();
        cache.invalidateAll();
    }
}