package com.example.downloader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    public CacheService(LettuceConnectionFactory lettuceConnectionFactory) {

        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }

    public void clearAllCaches() {
        lettuceConnectionFactory.getConnection().flushAll();
    }
}
