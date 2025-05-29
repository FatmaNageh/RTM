package com.example.aswe.user_microservice.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
@Component
public class TransactionManagementAspect {

    private final PlatformTransactionManager transactionManager;

    public TransactionManagementAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around("@annotation(org.springframework.transaction.annotation.Transactional)")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(status);
            System.out.println("[TRANSACTION] Committed transaction for: " + joinPoint.getSignature());
            return result;
        } catch (Throwable ex) {
            transactionManager.rollback(status);
            System.out.println("[TRANSACTION] Rolled back transaction for: " + joinPoint.getSignature());
            throw ex;
        }
    }
}