package com.example.aswe.user_microservice.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
@Component
public class SecurityAspect {

    @Autowired
    private HttpSession session;

    // Pointcut for admin-only methods
    @Before("execution(* com.example.aswe.user_microservice.controllers.UserController.deleteUser(..)) || " +
            "execution(* com.example.aswe.user_microservice.controllers.UserController.updateRole(..))")
    public void checkAdminRole(JoinPoint joinPoint) throws Throwable {
        String role = (String) session.getAttribute("role");
        if (role == null || !role.equals("ADMIN")) {
            throw new SecurityException("Access denied: ADMIN only for method " + joinPoint.getSignature());
        }
    }
}