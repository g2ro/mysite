package mysite.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class ExcutionTimeAspect {

	@Around("execution(* mysite.repository..*.*(..))")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable{
		StopWatch sw = new StopWatch();
		sw.start();
		Object result = pjp.proceed();
		sw.stop();
		long totalTime = sw.getTotalTimeMillis();
		System.out.println("[Execution Time] " + totalTime + "millisecond");
		return result;
	}
}
