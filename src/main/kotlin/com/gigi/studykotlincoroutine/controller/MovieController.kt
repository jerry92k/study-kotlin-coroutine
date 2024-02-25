package com.gigi.studykotlincoroutine.controller

import com.gigi.studykotlincoroutine.service.MovieService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController(
    private val movieService: MovieService
) {

    @GetMapping("/v1/movies/count")
    suspend fun movieCount(): String {
        // MonoCoroutine
        movieService.getMoviesWithSuspend()
        return "success"
    }

    @GetMapping("/v2/movies")
    suspend fun moviesWithSuspend(): List<String> {
        return listOf()
    }
}
