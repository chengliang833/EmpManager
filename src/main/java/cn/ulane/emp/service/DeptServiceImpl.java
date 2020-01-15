package cn.ulane.emp.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ulane.emp.dao.DepartmentDao;
import cn.ulane.emp.dao.UserDao;
import cn.ulane.emp.entity.Department;
import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Wage;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DepartmentDao deptDao;
	@Autowired
	private UserDao userDao;
	
	@Transactional(readOnly=true)
	public List<Department> selectDept(String deptId) {
		if (deptId == null || deptId.trim().isEmpty())
			throw new ServiceException("部门编号不能为空");
		try {
			int id = Integer.parseInt(deptId);
			List<Department> list = deptDao.findDeptById(id);
			list.get(0);
			return list;
		} catch (Exception e) {
			throw new ServiceException("没有该部门");
		}
	}

	@Transactional
	public List<Department> updateDept(int dtmt_id,String dtmt_name, int emp_num) {
		if(deptDao.findDeptById(dtmt_id) == null)
			throw new ServiceException("部门不存在");
		Department deptobj = new Department(dtmt_id, dtmt_name, emp_num);
		deptDao.updateDeptById(deptobj);
		List<Department> list = new ArrayList<Department>();
		list.add(deptobj);
		return list;
	}

	@Transactional
	public List<Department> insertDept(String dtmt_name, int emp_num) {
		if(deptDao.findDtmtByName(dtmt_name) != null)
			throw new ServiceException("部门已存在");
		Department dept = new Department(null, dtmt_name, emp_num);
		deptDao.insertDept(dept);
		List<Department> list = new ArrayList<Department>();
		list.add(dept);
		return list;
	}

	@Transactional
	public int deleteDept(int dtmt_id) {
		try{
			int amount = deptDao.deleteDept(dtmt_id);
			return amount;
		}catch(Exception e){
			throw new ServiceException("部门中存在相关人员");
		}
	}
	
	
	@Transactional(readOnly=true)
	public List<Map<String, Object>> browseDept(int page, int pageSize) {
		int start = pageSize * (page - 1);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> list = deptDao.browseDept(map);
		return list;
	}

	@Transactional(readOnly=true)
	public int browseDeptNum() {
		int amount = deptDao.browseDeptNum();
		return amount;
	}

	public List<String> selectAllDept() {
		List<String> list = deptDao.selectAllDept();
		return list;
	}
	
}
