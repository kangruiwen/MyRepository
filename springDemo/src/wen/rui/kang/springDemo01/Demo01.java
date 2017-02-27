package wen.rui.kang.springDemo01;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 从头开始搭建Spring框架
 * 第一节：容器的创建
 * @author kang
 * 1.导入相关jar包 core、content、beans，expression四个jar包，创建Spring容器。（最基本的几个jar包，Spring容器的创建必须需要）
 * 	并且由于spring框架依赖common-logging日志接口，所以需要导入common-logging包
 * 2.建立spring的配置文件，beans.xml
 * 3.建立测试环境导入junit与hamcrest-core包
 */
public class Demo01 {
	@Test
	public void testIoc() {
		System.out.println("开始读取配置文件！");
		@SuppressWarnings({ "resource", "unused" })
		ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println("读取配置文件结束！");
	}
}
