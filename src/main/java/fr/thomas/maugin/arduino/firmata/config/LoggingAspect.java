package fr.thomas.maugin.arduino.firmata.config;

/**
 * Created by thoma on 08/11/2015.
 */

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {

    @Value("${metrics.aspect}")
    private Boolean enableAspect;

    @Around("execution(* *(..)) && @annotation(fr.thomas.maugin.arduino.firmata.annotation.Loggable)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if(enableAspect) {
            final Logger LOGGER = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
            LOGGER.info("{} : {} => {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()), result);
        }
        return result;
    }

}