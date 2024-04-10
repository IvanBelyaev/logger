package com.example.logger.aspect;

import com.example.logger.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@RequiredArgsConstructor
@Component
@Aspect
public class LoggingAspect {

    private final LoggingService loggingService;

    @Pointcut("@annotation(com.example.logger.annotation.TrackTime)")
    public void trackTimeAnnotation() {
    }

    @Around("com.example.logger.aspect.CommonPointcuts.isServiceLayer() && trackTimeAnnotation())")
    public Object loggingAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        var result = joinPoint.proceed();
        var endTime = System.currentTimeMillis();

        var methodName = joinPoint.getSignature().toLongString();
        var args = Arrays.toString(joinPoint.getArgs());
        loggingService.addLog(methodName, args, endTime - startTime);

        return result;
    }
}
