package cn.ulane.emp.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Wage;
import cn.ulane.emp.service.WageService;

@Controller
@RequestMapping("/wage")
public class WageController {
	@Autowired
	private WageService wageService;
	
	@RequestMapping("/select.do")
	@ResponseBody
	public JsonResult select(String empId){
//		User user = empService.selectEmp(empId);
		List<Wage> list = wageService.selectWage(empId);
		return new JsonResult(list);
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public JsonResult update(int id,String name,double wage,int wagetype, double basewage, double bonus, double deductwage){
//		System.out.println(id);
		List<Wage> list = wageService.updateWage(id, name, wage, wagetype, basewage, bonus, deductwage, new Date());
		return new JsonResult(list);
	}
	
	@RequestMapping("/insert.do")
	@ResponseBody
	public JsonResult insert(String name, String sex, int age, String post, String department){
		List<Emp> list = wageService.insertEmp(name, sex, age, post, department);
		return new JsonResult(list);
	}
	
	@RequestMapping("/browse.do")
	@ResponseBody
	public JsonResult browse(int page, int pageSize){
		List<Map<String, Object>> list = wageService.browseWage(page, pageSize);
		return new JsonResult(list);
	}
	
	@RequestMapping("/browseEmpNum.do")
	@ResponseBody
	public JsonResult browseEmpNum(){
		int amount = wageService.browseEmpNum();
		return new JsonResult(amount);
	}
	
}













