package huan.yan.myproject.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;

@SuppressWarnings("all")
@Aspect
@Component
public class AopTest {

    @Pointcut("execution(public * huan.yan.myproject.controller.*.*(..))")
    public void apocut(){

    }

    @Around(value = "apocut()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("satrt:" +new Date());
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult result = (BindingResult) arg;
                if (result.hasErrors()) {
                    FieldError fieldError = result.getFieldError();
                    if(fieldError!=null){
                        return fieldError.getDefaultMessage();
                    }else{
                        return "";
                    }
                }
            }
        }
        return joinPoint.proceed();
    }

}
