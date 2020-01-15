package cn.ulane.emp.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ulane.emp.entity.Department;
import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Wage;
import cn.ulane.emp.service.DeptService;

@Controller
@RequestMapping("/dept")
public class DeptController {
	@Autowired
	private DeptService deptService;
	
	@RequestMapping("/select.do")
	@ResponseBody
	public JsonResult selectDept(String deptId){
//		User user = empService.selectEmp(empId);
		List<Department> list = deptService.selectDept(deptId);
		return new JsonResult(list);
	}
	
	@RequestMapping("/update.do")
	@ResponseBody
	public JsonResult update(int dtmt_id,String dtmt_name, int emp_num){
//		System.out.println(id);
		List<Department> list = deptService.updateDept(dtmt_id, dtmt_name, emp_num);
		return new JsonResult(list);
	}
	
	@RequestMapping("/insert.do")
	@ResponseBody
	public JsonResult insert(String dtmt_name, int emp_num){
		List<Department> list = deptService.insertDept(dtmt_name, emp_num);
		return new JsonResult(list);
	}
	
	@RequestMapping("/delete.do")
	@ResponseBody
	public JsonResult delete(int deptId){
		System.out.println(123);
		int amount = deptService.deleteDept(deptId);
		return new JsonResult(amount);
	}
	
	@RequestMapping("/browse.do")
	@ResponseBody
	public JsonResult browse(int page, int pageSize){
		List<Map<String, Object>> list = deptService.browseDept(page, pageSize);
		return new JsonResult(list);
	}
	
	@RequestMapping("/browseDeptNum.do")
	@ResponseBody
	public JsonResult browseDeptNum(){
		int amount = deptService.browseDeptNum();
		return new JsonResult(amount);
	}
	
	@RequestMapping("/selectAllDept.do")
	@ResponseBody
	public JsonResult selectAllDept(){
		List<String> list = deptService.selectAllDept();
		return new JsonResult(list);
	}
}













