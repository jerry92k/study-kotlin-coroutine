package com.gigi.studykotlincoroutine.chapter13.cache

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CacheController(
    private val cacheService: CacheService
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/v1/cache")
    suspend fun calculate(): Int {
        logger.info("1")
       return cacheService.getData()
    }
}
