package test;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ulane.emp.dao.DepartmentDao;
import cn.ulane.emp.service.DeptService;

public class DeptCase {
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
	 
	@Test
	public void deptService(){
		DeptService ws = ac.getBean("deptService", DeptService.class);
		System.out.println(ws);
		List list = ws.selectDept("801");
		System.out.println(list);
		System.out.println(list.get(0));
	}
	
	@Test
	public void updateWDeptService() throws ParseException{
		DeptService ws = ac.getBean("deptService", DeptService.class);
		System.out.println(ws);
		List list = ws.updateDept(804, "生产部", 5);
		System.out.println(list);
		System.out.println(list.get(0));
	}
	
	@Test
	public void insertDept(){
		DeptService ed = ac.getBean("deptService", DeptService.class);
		List list = ed.insertDept("什么部", 22);
		System.out.println(list);
		System.out.println(list.get(0));
	}
	
	@Test
	public void deleteDept(){
		DeptService ed = ac.getBean("deptService", DeptService.class);
		int i = ed.deleteDept(855);
		System.out.println(i);
	}
	
	@Test
	public void browseDept(){
		DeptService as = ac.getBean("deptService", DeptService.class);
		System.out.println(as);
		List<Map<String, Object>> list = as.browseDept(2,5);
		System.out.println(list);
	}
	
	@Test
	public void selectAllDept(){
		DepartmentDao as = ac.getBean("departmentDao", DepartmentDao.class);
		System.out.println(as);
		List<String> list = as.selectAllDept();
		System.out.println(list.toString());
	}
	
	
}










