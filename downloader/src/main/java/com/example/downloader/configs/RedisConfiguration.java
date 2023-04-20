package com.example.downloader.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableCaching
public class RedisConfiguration {

    private final LettuceConnectionFactory lettuceConnectionFactory;

    @Autowired
    public RedisConfiguration(LettuceConnectionFactory lettuceConnectionFactory) {
        this.lettuceConnectionFactory = lettuceConnectionFactory;
    }

    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager.RedisCacheManagerBuilder builder =
            RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
                lettuceConnectionFactory);
        return builder.build();
    }
}
