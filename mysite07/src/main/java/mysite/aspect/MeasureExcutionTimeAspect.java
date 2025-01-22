package mysite.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class MeasureExcutionTimeAspect {

//	@Around("execution(* mysite.repository..*.*(..))")
	@Around("execution(* *.repository.*.*(..)) || execution(* *.service.*.*(..)) || execution(* *.controller.*.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable{
		/* before */
		StopWatch sw = new StopWatch();
		sw.start();
		
		Object result = pjp.proceed();
		
		/* After */
		sw.stop();
		long totalTime = sw.getTotalTimeMillis();
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String taskName = className + "." + methodName;
//		System.out.println("[Execution Time][" + taskName + "]" + totalTime + "millis");
		log.info("[Execution Time][" + taskName + "]" + totalTime + "millis");
		return result;
	}
}
