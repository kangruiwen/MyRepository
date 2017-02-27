package wen.rui.kang.service;

import wen.rui.kang.dao.UserDao;

public class UserService {
	
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void sayDemo() {
		userDao.sayDemo();
	}
	
}
