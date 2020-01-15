package cn.ulane.emp.dao;

import java.util.List;
import java.util.Map;

import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;

public interface EmpDao {
	List<Emp> findEmpById(Integer id);
	void updateEmpByOldId(Emp_OldId emp_OldId);
	int insertEmp(Emp emp);
	List<Map<String, Object>> browseEmp(Map<String, Integer> map);
	int browseEmpNum();
	int deleteEmp(Integer id);
}
