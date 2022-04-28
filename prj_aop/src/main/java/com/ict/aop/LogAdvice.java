package com.ict.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;


@Log4j
@Aspect
@Component
public class LogAdvice {

	@Before("execution( * com.ict.service.SampleService*.*(..) )")
	public void logBefore() {
		log.info("===============");
	}
}
