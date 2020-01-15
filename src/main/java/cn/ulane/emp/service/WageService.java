package cn.ulane.emp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;
import cn.ulane.emp.entity.Wage;

public interface WageService {
	List<Wage> selectWage(String empId);
	List<Wage> updateWage(int id,String name,double wage,int wagetype, double basewage, double bonus, double deductwage, Date grantdate);
	List<Emp> insertEmp(String name, String sex, int age, String post, String department);
	List<Map<String, Object>> browseWage(int page, int pageSize);
	int browseEmpNum();
}
