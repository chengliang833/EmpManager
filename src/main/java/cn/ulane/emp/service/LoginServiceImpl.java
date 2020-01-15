package cn.ulane.emp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.ulane.emp.dao.UserDao;
import cn.ulane.emp.entity.Admin;
import cn.ulane.emp.entity.User;
import cn.ulane.emp.util.Md5;

@Service("adminService")
public class LoginServiceImpl implements LoginService {
	@Autowired
	private UserDao userDao;

	public Object login(String username, String password) {
		if (username == null || username.trim().isEmpty())
			throw new ServiceException("用户名不能为空");
		if (password == null || password.trim().isEmpty())
			throw new ServiceException("密码不能为空");
		if(username.startsWith("emp")){
			User user;
			try {
				int id = Integer.parseInt(username.substring(3));
				user = userDao.findUserById(id);
				user.getId();
			} catch (Exception e) {
				throw new ServiceException("用户名错误");
			}
			if(!Md5.saltMd5(password).equals(user.getPassword()))
				throw new ServiceException("密码错误");
			return user;
		}
		Admin admin = userDao.findAdminByUsername(username);
		if(admin == null)
			throw new ServiceException("用户名错误或数据库未开启");
		if(!Md5.saltMd5(password).equals(admin.getPassword()))
			throw new ServiceException("密码错误");
		return admin;
	}
	
}
