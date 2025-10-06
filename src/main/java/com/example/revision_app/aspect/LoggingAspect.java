package com.example.revision_app.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class LoggingAspect {



    /*
    ProceedingJoinPoint - Contient toutes les infos sur la méthode interceptée :
        joinPoint.getSignature().toShortString() : nom complet de la méthode
        joinPoint.getSignature().getName() : juste le nom
        joinPoint.getArgs() : les paramètres passés à la méthode
        joinPoint.proceed() : CRUCIAL - exécute la vraie méthode
     */

    @Around("execution(* com.example.revision_app.service..*(..))")
    public Object logMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        long startTime = System.currentTimeMillis();
        Object[] args = joinPoint.getArgs();
        log.info("[ENTERING]  method {}.() with args {}", methodName, args);

        try {
            var result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("[EXITING] method {}.() [RETURN] : {} in {} ms", methodName, result != null ? result.getClass().getSimpleName() : null, executionTime);
            return result;
        } catch (Throwable throwable) {
            log.error("[ERROR] Exception in {}.: {}",
                    methodName,
                    throwable.getMessage(),
                    throwable);
            throw throwable;
        }
    }
}
