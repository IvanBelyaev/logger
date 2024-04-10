package com.example.logger;

import com.example.logger.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class LoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoggerApplication.class, args);
    }

    @Autowired
    private TestService testService;

    @EventListener(ApplicationReadyEvent.class)
    public void testLogging() {
        for (int i = 0; i < 5; i++) {
            testService.testAsyncWithFuture();
            testService.testAsyncWithVoid();
        }

        for (int i = 0; i < 5; i++) {
            testService.testTrackTime();
        }
    }
}
