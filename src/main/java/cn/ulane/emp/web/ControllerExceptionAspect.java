package cn.ulane.emp.web;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.stereotype.Controller;

@Controller
@Aspect
public class ControllerExceptionAspect {
	@Around("bean(*Controller)")
	public Object process(ProceedingJoinPoint joinPoint){
		try {
			Object obj = joinPoint.proceed();
			return obj;
		} catch (MyBatisSystemException e){
			return new JsonResult(JsonResult.ERROR, "数据库异常");
		} catch (Throwable e) {
//			e.printStackTrace();
			System.out.println("----" + e.getMessage());
			return new JsonResult(JsonResult.ERROR, e.getMessage());
		}
	}
}
