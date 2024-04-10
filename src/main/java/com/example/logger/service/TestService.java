package com.example.logger.service;

import com.example.logger.annotation.TrackAsyncTime;
import com.example.logger.annotation.TrackTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Slf4j
@Service
public class TestService {

    @TrackAsyncTime
    public Future<String> testAsyncWithFuture() {
        log.info("testAsyncWithFuture()");
        return CompletableFuture.completedFuture("Hello");
    }

    @TrackAsyncTime
    public void testAsyncWithVoid() {
        log.info("testAsyncWithVoid()");
    }

    @TrackTime
    public String testTrackTime() {
        log.info("testTrackTime()");
        return "hello";
    }
}
