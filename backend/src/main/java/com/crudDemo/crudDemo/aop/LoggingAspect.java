package com.crudDemo.crudDemo.aop;

import com.crudDemo.crudDemo.dao.RequestResponseLogRepository;
import com.crudDemo.crudDemo.model.RequestResponseLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    private final RequestResponseLogRepository logRepository;
    private final ObjectMapper objectMapper;

    public LoggingAspect(RequestResponseLogRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    @Before("execution(* com.crudDemo.crudDemo.controller..*(..))")
    public void logBeforeController(JoinPoint joinPoint) throws Throwable {
        RequestResponseLog logEntry = new RequestResponseLog();
        logEntry.setMethodName("CONTROLLER" + " -> " + joinPoint.getSignature().toShortString());
        logEntry.setLayerName("CONTROLLER");
        logEntry.setCreatedTime(LocalDateTime.now());
        Object[] args = joinPoint.getArgs();
        try {
            String requestJson = objectMapper.writeValueAsString(args);
            logEntry.setRequestBody(requestJson);
        } catch (Exception e) {
            logEntry.setRequestBody(Arrays.toString(args));
        }
    }

    @AfterReturning(pointcut = "execution(* com.crudDemo.crudDemo.controller..*(..))", returning = "result")
    public void logAfterController(JoinPoint joinPoint, Object result) throws Throwable {
        RequestResponseLog logEntry = new RequestResponseLog();
        logEntry.setResponseBody(objectMapper.writeValueAsString(result));
        logEntry.setCompletedTime(LocalDateTime.now());
        logEntry.setDurationMs(ChronoUnit.MILLIS.between(logEntry.getCreatedTime(), logEntry.getCompletedTime()));
        logRepository.save(logEntry);
    }

    @AfterThrowing(pointcut = "execution(* com.crudDemo.crudDemo.controller..*(..))", throwing = "ex")
    public void logAfterThrowingController(JoinPoint joinPoint, Throwable ex) {
        RequestResponseLog logEntry = new RequestResponseLog();
        logEntry.setResponseBody("Exception: " + ex.getMessage());
        logEntry.setCompletedTime(LocalDateTime.now());
        long duration = ChronoUnit.MILLIS.between(logEntry.getCreatedTime(), logEntry.getCompletedTime());
        logEntry.setDurationMs(duration);
        logEntry.setLayerName("CONTROLLER");
        logRepository.save(logEntry);
    }


    @Around("execution(* com.crudDemo.crudDemo.service..*(..))")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestResponseLog logEntry = new RequestResponseLog();
        logEntry.setMethodName("SERVICE" + " -> " + joinPoint.getSignature().toShortString());
        logEntry.setLayerName("SERVICE");
        logEntry.setCreatedTime(LocalDateTime.now());

        Object[] args = joinPoint.getArgs();
        try {
            String requestJson = objectMapper.writeValueAsString(args);
            logEntry.setRequestBody(requestJson);
        } catch (Exception e) {
            logEntry.setRequestBody(Arrays.toString(args));
        }

        Object result;
        try {
            result = joinPoint.proceed();
            try {
                String responseJson = objectMapper.writeValueAsString(result);
                logEntry.setResponseBody(responseJson);
            } catch (Exception e) {
                logEntry.setResponseBody(String.valueOf(result));
            }
        } catch (Throwable ex) {
            logEntry.setResponseBody("Exception: " + ex.getMessage());
            throw ex;
        } finally {
            logEntry.setCompletedTime(LocalDateTime.now());
            long duration = ChronoUnit.MILLIS.between(logEntry.getCreatedTime(), logEntry.getCompletedTime());
            logEntry.setDurationMs(duration);
            logRepository.save(logEntry);
        }

        return result;
    }
}
