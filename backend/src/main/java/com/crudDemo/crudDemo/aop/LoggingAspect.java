package com.crudDemo.crudDemo.aop;

import com.crudDemo.crudDemo.dao.RequestResponseLogRepository;
import com.crudDemo.crudDemo.model.RequestResponseLog;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

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
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestResponseLog logEntry = new RequestResponseLog();
        logEntry.setMethodName(joinPoint.getSignature().toShortString());
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
        logRepository.save(logEntry);

        return result;
    }
}
