package com.hesen.web.aspect;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by hesen on 2017-10-31
 */

@Aspect
@Order(5)
@Component
public class ControllerLogAspect {
	
	private Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);
	
	ThreadLocal<Long> startTime = new ThreadLocal<>();
	
	@Pointcut("execution(public * com.hesen.controller..*.*(..))")//所有Controller包及其子包中的任意方法
	public void controllerLog(){}
	
	@Before("controllerLog()")
	public void doBefore(JoinPoint point){
		startTime.set(System.currentTimeMillis());
		
		//获取请求内容
		ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attr.getRequest();
		
		//记录请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("ARGS :　" + Arrays.toString(point.getArgs()));
	}
	
	@AfterReturning(pointcut = "controllerLog()", returning = "obj")
	public void doReturnAfter(Object obj){
		logger.info("RESPONSE : " + obj);
		logger.info("SPEND TIME : " + (System.currentTimeMillis()-startTime.get()) + "MS");
	}
}
