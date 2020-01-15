package cn.ulane.emp.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.service.EmpService;

@Controller
@RequestMapping("/emp")
public class EmpController {
	@Autowired
	private EmpService empService;
	
	@RequestMapping("/select.do")
	@ResponseBody
	public JsonResult select(String empId){
//		User user = empService.selectEmp(empId);
		List<Emp> list = empService.selectEmp(empId);
		return new JsonResult(list);
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public JsonResult update(int oldId, int id, String name, String sex, int age, String post, String department, Double wage){
		List<Emp> list = empService.updateEmp(oldId, id, name, sex, age, post, department, wage);
		return new JsonResult(list);
	}
	
	@RequestMapping("/insert.do")
	@ResponseBody
	public JsonResult insert(String name, String sex, int age, String post, String department){
		List<Emp> list = empService.insertEmp(name, sex, age, post, department);
		return new JsonResult(list);
	}
	
	@RequestMapping("/browse.do")
	@ResponseBody
	public JsonResult browse(int page, int pageSize){
		List<Map<String, Object>> list = empService.browseEmp(page, pageSize);
		return new JsonResult(list);
	}
	
	@RequestMapping("/browseEmpNum.do")
	@ResponseBody
	public JsonResult browseEmpNum(){
		int amount = empService.browseEmpNum();
		return new JsonResult(amount);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult deleteEmp(int empId){
		int amount = empService.deleteEmp(empId);
		return new JsonResult(amount);
	}
	
	@RequestMapping("/pwdemp.do")
	@ResponseBody
	public JsonResult updateEmpPwd(String adminPwd, int id,String password,HttpServletRequest req){
//		System.out.println(adminPwd);
		String username = null;
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies){
			if("username".equals(cookie.getName())){
				username = cookie.getValue();
				break;
			}
		}
//		System.out.println(username);
		int amount = empService.updateEmpPwd(username, adminPwd, id, password);
		return new JsonResult(amount);
	}
	
	@RequestMapping("/pwdadmin.do")
	@ResponseBody
	public JsonResult updateAdminPwd(String adminPwd,String password,HttpServletRequest req){
//		System.out.println(adminPwd);
		String username = null;
		Cookie[] cookies = req.getCookies();
		for(Cookie cookie : cookies){
			if("username".equals(cookie.getName())){
				username = cookie.getValue();
				break;
			}
		}
//		System.out.println(username);
		int amount = empService.updateAdminPwd(username, adminPwd, password);
		return new JsonResult(amount);
	}
}













