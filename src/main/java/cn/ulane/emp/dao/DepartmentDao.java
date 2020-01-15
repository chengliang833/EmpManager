package cn.ulane.emp.dao;

import java.util.List;
import java.util.Map;

import cn.ulane.emp.entity.Department;
import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;
import cn.ulane.emp.entity.Wage;

public interface DepartmentDao {
	Department findDtmtByName(String dtmt_name);
	List<Department> findDeptById(Integer dtmt_id);
	void updateDeptById(Department dept);
	int insertDept(Department dept);
	int deleteDept(int dtmt_id);
	int browseDeptNum();
	
	List<Map<String, Object>> browseDept(Map<String, Integer> map);
	List<String> selectAllDept();
}
