package test;

import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.ulane.emp.dao.EmpDao;
import cn.ulane.emp.dao.UserDao;
import cn.ulane.emp.entity.Admin;
import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.User;
import cn.ulane.emp.service.EmpService;
import cn.ulane.emp.service.LoginService;
import cn.ulane.emp.util.Md5;

public class TestCase {
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
	
	//findAdmin
	@Test
	public void findAdminByUsername(){
		UserDao dao = ac.getBean("userDao", UserDao.class);
		System.out.println(dao);
		Admin admin = dao.findAdminByUsername("admin");
		System.out.println(admin);
	}
	 
	
	//findUser
	@Test
	public void findUserById(){
		UserDao dao = ac.getBean("userDao", UserDao.class);
		System.out.println(dao);
		User user = dao.findUserById(105);
		System.out.println(user);
	}
	
	//loginService
	@Test
	public void loginService(){
		LoginService as = ac.getBean("adminService", LoginService.class);
		System.out.println(as);
		Admin admin = (Admin) as.login("abc", "123456");
		System.out.println(admin);
		Emp user = (Emp)as.login("emp102", "123456");
		System.out.println(user);
	}
	
	//MD5
	@Test
	public void md5(){
		System.out.println(Md5.saltMd5("admin"));
	}
	
	//empService
	@Test
	public void empService(){
		EmpService as = ac.getBean("empService", EmpService.class);
		System.out.println(as);
		Emp emp = (Emp)as.selectEmp("102");
		System.out.println(emp);
	}
	
	//insertEmp
	@Test
	public void insertEmp(){
		EmpDao ed = ac.getBean("empDao", EmpDao.class);
		Emp emp = new Emp(0, "dsfd", "sdfds", 34, "df", "人力资源部", 0);
		System.out.println(emp);
		ed.insertEmp(emp);
		System.out.println(emp);
	}

	//browseEmp
	@Test
	public void browseEmp(){
		EmpService as = ac.getBean("empService", EmpService.class);
		System.out.println(as);
		List<Map<String, Object>> list = as.browseEmp(1,5);
		System.out.println(list);
	}
	
	@Test
	public void deleteEmp(){
		EmpDao ed = ac.getBean("empDao", EmpDao.class);
		int i = ed.deleteEmp(114);
		System.out.println(i);
	}
	
	@Test
	public void deleteEmpService(){
		EmpService as = ac.getBean("empService", EmpService.class);
		int i = as.deleteEmp(123);
		System.out.println(i);
	}
	
	@Test
	public void updateEmpPwd(){
		EmpService ed = ac.getBean("empService", EmpService.class);
		int i = ed.updateEmpPwd("admin","admin",112,"12321423edd");
		System.out.println(i);
	}
	
	@Test
	public void updateAdminPwd(){
		EmpService ed = ac.getBean("empService", EmpService.class);
//		int i = ed.updateEmpPwd("admin","admin",112,"12321423edd");
		int i = ed.updateAdminPwd("abc", "123123", "123456");
		System.out.println(i);
	}
}










