package com.crudDemo.crudDemo.aop;

import com.crudDemo.crudDemo.dao.RequestResponseLogRepository;
import com.crudDemo.crudDemo.model.RequestResponseLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

@Aspect
@Component
public class LoggingAspect {

    private final RequestResponseLogRepository logRepository;
    private final ObjectMapper objectMapper;

    public LoggingAspect(RequestResponseLogRepository logRepository, ObjectMapper objectMapper) {
        this.logRepository = logRepository;
        this.objectMapper = objectMapper;
    }

    @Around("execution(* com.crudDemo.crudDemo.controller..*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        return logRequestAndResponse(joinPoint, "CONTROLLER");
    }


    @Around("execution(* com.crudDemo.crudDemo.service..*(..))")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        return logRequestAndResponse(joinPoint, "SERVICE");
    }

    private Object logRequestAndResponse(ProceedingJoinPoint joinPoint, String layer) throws Throwable {
        RequestResponseLog logEntry = new RequestResponseLog();
        logEntry.setMethodName(layer + " -> " + joinPoint.getSignature().toShortString());
        logEntry.setCreatedTime(LocalDateTime.now());

        Object[] args = joinPoint.getArgs();
        try {
            String requestJson = objectMapper.writeValueAsString(args);
            logEntry.setRequestBody(requestJson);
        } catch (Exception e) {
            logEntry.setRequestBody(Arrays.toString(args));
        }

        Object result;
        result = joinPoint.proceed();

        try {
            String responseJson = objectMapper.writeValueAsString(result);
            logEntry.setResponseBody(responseJson);
        } catch (Exception e) {
            logEntry.setResponseBody(String.valueOf(result));
        }

        logEntry.setCompletedTime(LocalDateTime.now());
        long duration = ChronoUnit.MILLIS.between(logEntry.getCreatedTime(), logEntry.getCompletedTime());
        logEntry.setDurationMs(duration);
        logEntry.setLayerName(layer);
        logRepository.save(logEntry);

        return result;
    }
}
