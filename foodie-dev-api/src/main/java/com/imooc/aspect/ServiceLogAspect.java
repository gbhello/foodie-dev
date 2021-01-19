package com.imooc.aspect;

import com.imooc.consts.BusinessConsts;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author gengbin
 * @date 2021/1/19
 */
@Aspect
@Component
public class ServiceLogAspect {
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Around("execution(* com.imooc.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("==== 开始执行{},{} ====", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
        //记录开始时间
        long begin = System.currentTimeMillis();
        //执行目标service
        Object result = joinPoint.proceed();
        //记录结束时间
        long end = System.currentTimeMillis();
        long takeTime = end - begin;

        if (takeTime > BusinessConsts.ERROR_TAKE_TIME) {
            logger.error("==== 执行结束，耗时：{}毫秒 ====", takeTime);
        } else if (takeTime > BusinessConsts.WARN_TAKE_TIME) {
            logger.warn("==== 执行结束，耗时：{}毫秒 ====", takeTime);
        } else {
            logger.info("==== 执行结束，耗时：{}毫秒 ====", takeTime);
        }
        return result;
    }
}
