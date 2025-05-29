package com.example.aswe.user_microservice.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    // Apply to all service layer methods
    @Around("execution(* com.example.aswe.user_microservice.services.*.*(..))")
    public Object profileExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long duration = System.currentTimeMillis() - start;

        System.out.println("[PERFORMANCE] " + joinPoint.getSignature() + " took " + duration + " ms");

        if (duration > 500) {
            System.out.println("[WARNING] Execution time exceeded threshold: " + duration + " ms");
        }

        return result;
    }
}