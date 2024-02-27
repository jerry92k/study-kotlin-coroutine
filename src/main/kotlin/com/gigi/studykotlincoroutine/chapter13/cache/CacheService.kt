package com.gigi.studykotlincoroutine.chapter13.cache

import kotlinx.coroutines.*
import kotlinx.coroutines.slf4j.MDCContext
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
@CacheConfig(cacheNames = ["testData"])
class CacheService {

    private val logger = LoggerFactory.getLogger(this.javaClass)


    @Cacheable(key = "'all'")
    suspend fun getData() = coroutineScope {
        logger.info("1")
        val coroutineScope = CoroutineScope(Job()+MDCContext())
        coroutineScope.launch {
            logger.info("2")
            logger.info("cache miss")
            delay(10)
        }
        100
    }
}
