package cn.ulane.emp.dao;

import java.util.List;
import java.util.Map;

import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;
import cn.ulane.emp.entity.Wage;

public interface WageDao {
	List<Wage> findWageById(Integer id);
	void updateWageById(Wage wage);
	int insertEmp(Emp emp);
	List<Map<String, Object>> browseWage(Map<String, Integer> map);
	int browseEmpNum();
	int updateForeignIdByEmpId(Map<String, Integer> map);
	int insertWage(Integer id);
	int deleteWage(Integer id);
}
