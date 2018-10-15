
package cz.muni.fi.pa165.currency;

import javax.inject.Named;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 *
 * @author xrajivv
 */

@Named
@Aspect
public class LoggingAspect {
    @Around("execution(public * *(..))")
    public Object LogMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        
        System.err.println("Calling For Method: "
                + joinPoint.getSignature());

        Object result = joinPoint.proceed();

        System.err.println("Method finished: "
                + joinPoint.getSignature());
        
        return result;
    }
   
}
