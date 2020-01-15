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
import cn.ulane.emp.entity.Wage;
import cn.ulane.emp.service.EmpService;
import cn.ulane.emp.service.WageService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private EmpService empService;
	
	@Autowired
	private WageService wageService;
	
	
	@RequestMapping("/select.do")
	@ResponseBody
	public JsonResult select(HttpServletRequest request){
		String userId = getCookie("userId", request);
		List<Emp> list = empService.selectEmp(userId);
		return new JsonResult(list);
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public JsonResult update(String name, String sex, int age, String post, String department, Double wage,HttpServletRequest request){
		int userId = Integer.parseInt(getCookie("userId", request));
		System.out.println(userId);
		List<Emp> list = empService.updateEmp(userId, userId, name, sex, age, post, department, wage);
		return new JsonResult(list);
	}

	@RequestMapping("/selectWage.do")
	@ResponseBody
	public JsonResult selectWage(HttpServletRequest request){
		String userId = getCookie("userId", request);
		List<Wage> list = wageService.selectWage(userId);
		return new JsonResult(list);
	}

	@RequestMapping("/pwd.do")
	@ResponseBody
	public JsonResult updateUserPwd(String pwd,String password,HttpServletRequest request){
		int userId = Integer.parseInt(getCookie("userId", request));
		int amount = empService.updateUserPwd(userId, pwd, password);
		return new JsonResult(amount);
	}

	
	
	
	public String getCookie(String key, HttpServletRequest request){
		Cookie[] cookies = request.getCookies();
		for(Cookie cookie : cookies){
			if("userId".equals(cookie.getName())){
				return cookie.getValue();
			}
		}
		return null;
	}
	
}













