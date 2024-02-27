package com.gigi.studykotlincoroutine.chapter13.cache

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.util.*

@Component
class TraceIdFilter : OncePerRequestFilter() {

    private val logger = LoggerFactory.getLogger(TraceIdFilter::class.java)
    private val TRACE_ID_HEADER = "X-Trace-Id"
    private val TRACE_ID_MDC_KEY = "trace.id"

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            var traceId = request.getHeader(TRACE_ID_HEADER)
            if (traceId.isNullOrBlank()) {
                traceId = UUID.randomUUID().toString()
                logger.debug("Generated TraceID: $traceId")
            }

            MDC.put(TRACE_ID_MDC_KEY, traceId)
            filterChain.doFilter(request, response)
        } finally {
            MDC.remove(TRACE_ID_MDC_KEY)
        }
    }
}
