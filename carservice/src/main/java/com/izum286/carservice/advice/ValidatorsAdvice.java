package com.izum286.carservice.advice;


import com.izum286.carservice.annotaion.NotNull;
import com.izum286.carservice.exceptions.ServiceException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

@Aspect
@Component
public class ValidatorsAdvice {

    @Around("@annotation(com.izum286.carservice.annotaion.CheckForNull)")
    public Object nullValueParameterCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] params = joinPoint.getArgs();
        for (Object param : params) {
            if (param == null) {
                throw new ServiceException(String.format("Parameters can't be NULL at ", joinPoint.getSignature()));
            }
            Field[] paramFields = param.getClass().getDeclaredFields();
            for (Field paramField : paramFields) {
                Annotation[] annotations = paramField.getDeclaredAnnotations();
                paramField.setAccessible(true);
                for (Annotation annotation : annotations) {
                    if (annotation instanceof NotNull) {
                        Object obj = paramField.get(param);
                        if (obj == null) {
                            throw new ServiceException(String.format("Field %s can't be NULL", paramField.getName()));
                        }
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}