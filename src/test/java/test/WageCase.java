package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ulane.emp.service.EmpService;
import cn.ulane.emp.service.WageService;

public class WageCase {
	ApplicationContext ac;
	@Before
	public void init(){
		ac = new ClassPathXmlApplicationContext("spring-mybatis.xml","spring-service.xml");
		//System.out.println(ac);
	}
	
	//	数据源
	@Test
	public void dataSource(){
		BasicDataSource ds = ac.getBean("dataSource", BasicDataSource.class);
		System.out.println(ds);
	}
	 
	//wageService
	@Test
	public void wageService(){
		WageService ws = ac.getBean("wageService", WageService.class);
		System.out.println(ws);
		List list = ws.selectWage("102");
		System.out.println(list);
		System.out.println(list.get(0));
	}
	
	//updatewageService
	@Test
	public void updateWageService() throws ParseException{
		WageService ws = ac.getBean("wageService", WageService.class);
		System.out.println(ws);
		List list = ws.updateWage(113, "sad",1223 ,12 , 123, 123, 123, new SimpleDateFormat("yyyy-MM-dd").parse("1998-08-07"));
		System.out.println(list);
		System.out.println(list.get(0));
	}
	
	@Test
	public void browseWage(){
		WageService as = ac.getBean("wageService", WageService.class);
		System.out.println(as);
		List<Map<String, Object>> list = as.browseWage(2,5);
		System.out.println(list);
	}
	
	
}










