package wen.rui.kang.service;

import wen.rui.kang.dao.DemoDao;

/**
 * 服务层Demo
 * @author Administrator
 */
public class DemoService {
	
	private DemoDao demoDao;
	
	public DemoService() {
		System.out.println("demoService初始化");
	}
	
	public void demoMethod() {
		demoDao.demoMethod();
	}
	
	//setter注入时这个set方法必须写
	public void setDemoDao(DemoDao demoDao) {
		System.out.println("demoDao开始注入了！");
		this.demoDao = demoDao;
	}
	
}
