package cn.ulane.emp.service;

import java.util.List;
import java.util.Map;

import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;

public interface EmpService {
	List<Emp> selectEmp(String empId);
	List<Emp> updateEmp(int oldId, int id, String name, String sex, int age, String post, String department, Double wage);
	List<Emp> insertEmp(String name, String sex, int age, String post, String department);
	List<Map<String, Object>> browseEmp(int page, int pageSize);
	int browseEmpNum();
	int deleteEmp(Integer id);
	int updateEmpPwd(String username, String adminPwd,Integer id, String pwd) ;
	int updateAdminPwd(String username, String adminPwd, String pwd);
	int updateUserPwd(Integer userId, String pwd, String password);
}
