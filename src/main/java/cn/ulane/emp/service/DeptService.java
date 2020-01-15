package cn.ulane.emp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ulane.emp.entity.Department;
import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;
import cn.ulane.emp.entity.Wage;

public interface DeptService {
	List<Department> selectDept(String deptId);
	List<Department> updateDept(int dtmt_id,String dtmt_name, int emp_num);
	List<Department> insertDept(String dtmt_name, int emp_num);
	int deleteDept(int dtmt_id);
	
	List<Map<String, Object>> browseDept(int page, int pageSize);
	int browseDeptNum();
	List<String> selectAllDept();
}
