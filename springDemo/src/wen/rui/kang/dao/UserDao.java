package wen.rui.kang.dao;

import wen.rui.kang.entity.UserEntity;

public class UserDao {
	
	private UserEntity userEntity;

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}
	
	public void sayDemo() {
		System.out.println("用户名为：" + userEntity.getUserName());
		System.out.println("用户密码为：" + userEntity.getPassWord());
	}
	
}
