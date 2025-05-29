package com.example.aswe.user_microservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Pointcuts for each method explicitly from your UserController

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.login(..))")
    public void login() {}

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.logout(..))")
    public void logout() {}

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.deleteUser(..))")
    public void deleteUser() {}

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.getAllUsers(..))")
    public void getAllUsers() {}

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.getById(..))")
    public void getById() {}

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.createOrUpdate(..))")
    public void createOrUpdate() {}

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.searchByName(..))")
    public void searchByName() {}

    @Pointcut("execution(* com.example.aswe.user_microservice.controllers.UserController.updateRole(..))")
    public void updateRole() {}

    // Combined pointcut for all listed methods
    @Pointcut("login() || logout() || deleteUser() || getAllUsers() || getById() || createOrUpdate() || searchByName() || updateRole()")
    public void userControllerMethods() {}

    // Advice for logging before method execution
    @Before("userControllerMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("[AOP Before] Calling: " + joinPoint.getSignature());
    }

    // Advice for logging after method execution
    @After("userControllerMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[AOP After] Completed: " + joinPoint.getSignature());
    }

    // Advice for logging returned values
    @AfterReturning(pointcut = "userControllerMethods()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("[AOP Return] Method: " + joinPoint.getSignature() + " returned: " + result);
    }

    // Advice for logging exceptions thrown
    @AfterThrowing(pointcut = "userControllerMethods()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Exception ex) {
        System.out.println("[AOP Exception] Method: " + joinPoint.getSignature() + " threw: " + ex.getMessage());
    }

    // Advice for timing method execution
    @Around("userControllerMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - start;
        System.out.println("[AOP Timing] " + joinPoint.getSignature() + " took " + timeTaken + " ms");
        return result;
    }
}