package com.crudDemo.crudDemo.aop;

import com.crudDemo.crudDemo.dao.ErrorLogRepository;
import com.crudDemo.crudDemo.model.ErrorLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private final ErrorLogRepository errorLogRepository;

    public ExceptionLoggingAspect(ErrorLogRepository errorLogRepository) {
        this.errorLogRepository = errorLogRepository;
    }

    @Pointcut("execution(* com.crudDemo.crudDemo.controller..*(..))")
    public void controllerPublicMethods() {}

    @Pointcut("execution(* com.crudDemo.crudDemo.service..*(..))")
    public void serviceMethods() {}

    @AfterThrowing(pointcut = "controllerPublicMethods()", throwing = "ex")
    public void logControllerException(JoinPoint joinPoint, Throwable ex) {
        saveErrorLog(joinPoint, ex, "CONTROLLER");
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logServiceException(JoinPoint joinPoint, Throwable ex) {
        saveErrorLog(joinPoint, ex, "SERVICE");
    }

    private void saveErrorLog(JoinPoint joinPoint, Throwable ex, String layer) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setLayerName(layer);
        errorLog.setMethodName(joinPoint.getSignature().toShortString());
        errorLog.setExceptionMessage(ex.getMessage());
        errorLog.setCreatedTime(LocalDateTime.now());
        errorLogRepository.save(errorLog);
    }
}