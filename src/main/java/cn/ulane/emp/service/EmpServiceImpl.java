package cn.ulane.emp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.ulane.emp.dao.DepartmentDao;
import cn.ulane.emp.dao.EmpDao;
import cn.ulane.emp.dao.UserDao;
import cn.ulane.emp.dao.WageDao;
import cn.ulane.emp.entity.Admin;
import cn.ulane.emp.entity.Emp;
import cn.ulane.emp.entity.Emp_OldId;
import cn.ulane.emp.entity.User;
import cn.ulane.emp.util.Md5;

@Service("empService")
public class EmpServiceImpl implements EmpService {
	@Autowired
	private EmpDao empDao;
	@Autowired
	private WageDao wageDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private DepartmentDao departmentDao;
	
	@Transactional(readOnly=true)
	public List<Emp> selectEmp(String empId) {
		if (empId == null || empId.trim().isEmpty())
			throw new ServiceException("员工编号不能为空");
		try {
			int id = Integer.parseInt(empId);
			List<Emp> list = empDao.findEmpById(id);
			list.get(0);
			return list;
		} catch (Exception e) {
			throw new ServiceException("没有该员工");
		}
	}

	@Transactional
	public List<Emp> updateEmp(int oldId, int id, String name, String sex, int age, String post, String department, Double wage) {
		if(oldId != id && userDao.findUserById(id) != null)
			throw new ServiceException("员工编号被占用");
		if(departmentDao.findDtmtByName(department) == null)
			throw new ServiceException("部门不存在");
		Map map = new HashMap();
		map.put("id", id);
		map.put("oldId", oldId);
		int i = wageDao.updateForeignIdByEmpId(map);
		Emp_OldId emp_OldId = new Emp_OldId(oldId, id, name, sex, age, post, department, wage);
		empDao.updateEmpByOldId(emp_OldId);
		List<Emp> list = new ArrayList<Emp>();
		list.add(emp_OldId);
		return list;
	}

	@Transactional
	public List<Emp> insertEmp(String name, String sex, int age, String post, String department) {
		if(departmentDao.findDtmtByName(department) == null)
			throw new ServiceException("部门不存在");
		Emp emp = new Emp(0, name, sex, age, post, department, 0);
		empDao.insertEmp(emp);
		wageDao.insertWage(emp.getId());
		List<Emp> list = new ArrayList<Emp>();
		list.add(emp);
		return list;
	}

	@Transactional(readOnly=true)
	public List<Map<String, Object>> browseEmp(int page, int pageSize) {
		int start = pageSize * (page - 1);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("start", start);
		map.put("pageSize", pageSize);
		List<Map<String, Object>> list = empDao.browseEmp(map);
		return list;
	}

	@Transactional(readOnly=true)
	public int browseEmpNum() {
		int amount = empDao.browseEmpNum();
		return amount;
	}
	
	@Transactional
	public int deleteEmp(Integer id) {
		int numberWage = wageDao.deleteWage(id);
		int number = empDao.deleteEmp(id);
		return number;
	}
	
	@Transactional
	public int updateEmpPwd(String username, String adminPwd, Integer id, String pwd) {
		Admin admin = userDao.findAdminByUsername(username);
		if(!Md5.saltMd5(adminPwd).equals(admin.getPassword())){
			throw new ServiceException("管理员密码错误");
		}
		User user = new User(id, Md5.saltMd5(pwd));
		int number = userDao.updatePwdById(user);
		return number;
	}
	
	@Transactional
	public int updateAdminPwd(String username, String adminPwd, String pwd) {
		Admin admin = userDao.findAdminByUsername(username);
		if(!Md5.saltMd5(adminPwd).equals(admin.getPassword())){
			throw new ServiceException("管理员密码错误");
		}
		admin.setPassword(Md5.saltMd5(pwd));
		int number = userDao.updatePwdByUsername(admin);
		return number;
	}
	
	@Transactional
	public int updateUserPwd(Integer userId, String pwd, String password) {
		User user = userDao.findUserById(userId);
		if(!Md5.saltMd5(pwd).equals(user.getPassword())){
			throw new ServiceException("原密码错误");
		}
		user.setPassword(Md5.saltMd5(password));
		int number = userDao.updatePwdById(user);
		return number;
	}
}
