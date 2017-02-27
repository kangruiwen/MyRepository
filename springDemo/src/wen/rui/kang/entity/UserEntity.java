package wen.rui.kang.entity;

import java.io.Serializable;

public class UserEntity implements Serializable{
	
	private static final long serialVersionUID = 6986843178306901699L;

	public UserEntity() {
		System.out.println("UserEntity无参构造器");
	}
	
	public UserEntity(String userName, String passWord) {
		this.userName = userName;
		this.passWord = passWord;
	}
	
	private String userName;
	private String passWord;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
