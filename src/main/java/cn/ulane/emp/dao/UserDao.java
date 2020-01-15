package cn.ulane.emp.dao;

import cn.ulane.emp.entity.Admin;
import cn.ulane.emp.entity.User;

public interface UserDao {
	Admin findAdminByUsername(String username);
	User findUserById(Integer id);
	int updatePwdById(User user);
	int updatePwdByUsername(Admin admin);
}
