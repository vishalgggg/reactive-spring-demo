package ca.neilwhite.hrservice.controllers;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    @Around("execution(public * ca.neilwhite.hrservice.controllers.*.*(..))")
    public Object logInputAndExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long t = System.currentTimeMillis();
        log.info("=> {} :: args: {}", pjp.getSignature().toShortString(), pjp.getArgs());

        Object retVal = pjp.proceed();
        
        log.info("<= {} :: Execution Time: {}ms", pjp.getSignature().toShortString(), System.currentTimeMillis() - t);
        return retVal;
        // ObjectMapper mapper = new ObjectMapper();
		// String methodName = pjp.getSignature().getName();
		// String className = pjp.getTarget().getClass().toString();
		// Object[] array = pjp.getArgs();
		// log.info("method invoked " + className + " : " + methodName + "()" + "arguments : "
		// 		+ mapper.writeValueAsString(array));
		// Object object = pjp.proceed();
		// log.info(className + " : " + methodName + "()" + "Response : "
		// 		+ mapper.writeValueAsString(object));
		// return object;
    }
}