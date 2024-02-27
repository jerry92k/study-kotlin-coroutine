package com.gigi.studykotlincoroutine.chapter13.cache

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@EnableCaching
@Configuration
class CachingConfig {

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager = ConcurrentMapCacheManager()
        cacheManager.setAllowNullValues(false);
        cacheManager.setCacheNames(listOf("testData"));
        return cacheManager
    }
}
