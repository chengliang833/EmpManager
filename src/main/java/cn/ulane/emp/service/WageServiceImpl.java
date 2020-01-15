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
import cn.ulane.emp.dao.WageDao;
import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;
import cn.ulane.emp.entity.Wage;

@Service("wageService")
public class WageServiceImpl implements WageService {
	@Autowired
	private WageDao wageDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DepartmentDao departmentDao;
	
	@Transactional(readOnly=true)
	public List<Wage> selectWage(String empId) {
		if (empId == null || empId.trim().isEmpty())
			throw new ServiceException("员工编号不能为空");
		try {
			int id = Integer.parseInt(empId);
			List<Wage> list = wageDao.findWageById(id);
			list.get(0);
			return list;
		} catch (Exception e) {
			throw new ServiceException("没有该员工");
		}
	}

	@Transactional
	public List<Wage> updateWage(int id,String name,double wage,int wagetype, double basewage, double bonus, double deductwage, Date grantdate) {
		if(userDao.findUserById(id) == null)
			throw new ServiceException("员工不存在");
		Wage wageobj = new Wage(id, name, wage, wagetype, basewage, bonus, deductwage, grantdate);
		wageDao.updateWageById(wageobj);
		List<Wage> list = new ArrayList<Wage>();
		list.add(wageobj);
		return list;
	}

	@Transactional
	public List<Emp> insertEmp(String name, String sex, int age, String post, String department) {
		if(departmentDao.findDtmtByName(department) == null)
			throw new ServiceException("部门不存在");
		Emp emp = new Emp(0, name, sex, age, post, department, 0);
		wageDao.insertEmp(emp);
		List<Emp> list = new ArrayList<Emp>();
		list.add(emp);
		return list;
	}

	@Transactional(readOnly=true)
	public List<Map<String, Object>> browseWage(int page, int pageSize) {
		int start = pageSize * (page - 1);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> list = wageDao.browseWage(map);
		return list;
	}

	@Transactional(readOnly=true)
	public int browseEmpNum() {
		int amount = wageDao.browseEmpNum();
		return amount;
	}

}
