package com.example.downloader.services;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final RedisCacheManager cacheManager;

    @Autowired
    public CacheService(RedisCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public void clearAllCaches() {
        cacheManager.getCacheNames().forEach(cacheName -> Objects.requireNonNull(
            cacheManager.getCache(cacheName)).clear());
    }
}
