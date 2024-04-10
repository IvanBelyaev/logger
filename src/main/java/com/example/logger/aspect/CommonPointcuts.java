package com.example.logger.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CommonPointcuts {

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void isServiceLayer() {
    }
}
