package com.example.logger.aspect;

import com.example.logger.service.LoggingService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.Future;

@RequiredArgsConstructor
@Component
@Aspect
public class LoggingAsyncAspect {

    private final LoggingService loggingService;

    @Pointcut("@annotation(com.example.logger.annotation.TrackAsyncTime)")
    public void trackAsyncTimeAnnotation() {
    }

    @Pointcut("execution(void *(..))")
    public void methodReturnsVoid() {
    }

    @Pointcut("execution(java.util.concurrent.Future *(..))")
    public void methodReturnsFuture() {
    }

    @Async
    @Around("com.example.logger.aspect.CommonPointcuts.isServiceLayer() " +
            "&& trackAsyncTimeAnnotation() " +
            "&& methodReturnsVoid())")
    public void loggingAdviceWithVoid(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        joinPoint.proceed();
        var endTime = System.currentTimeMillis();

        var methodName = joinPoint.getSignature().toLongString();
        var args = Arrays.toString(joinPoint.getArgs());
        loggingService.addLog(methodName, args, endTime - startTime);
    }

    @Async
    @Around("com.example.logger.aspect.CommonPointcuts.isServiceLayer() " +
            "&& trackAsyncTimeAnnotation() " +
            "&& methodReturnsFuture()")
    public Future<?> loggingAdviceWithFuture(ProceedingJoinPoint joinPoint) throws Throwable {
        var startTime = System.currentTimeMillis();
        var result = (Future<?>) joinPoint.proceed();
        var endTime = System.currentTimeMillis();

        var methodName = joinPoint.getSignature().toLongString();
        var args = Arrays.toString(joinPoint.getArgs());
        loggingService.addLog(methodName, args, endTime - startTime);
        return result;
    }
}
