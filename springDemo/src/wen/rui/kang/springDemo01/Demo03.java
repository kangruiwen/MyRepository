package wen.rui.kang.springDemo01;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wen.rui.kang.entity.UserEntity;
import wen.rui.kang.service.DemoService;
import wen.rui.kang.service.UserService;

/**
 * 获得容器中的bean
 * 
 * 1、控制反转与依赖注入的不同：
 * 	控制反转：bean由谁来创建，即控制权的转移。
 * 	依赖注入：bean之间依赖关系的控制。
 * 
 * 2、容器的启动（启动的过程中会有bean的创建）
 * 	1.当容器中配置bean的一个属性scope="prototype"（原型模式）即为每次访问这个bean都会创建一个实例，
 * 	      默认SpringBean是单例的当配置bean的属性scope="singleton"(单例模式) 在创建容器的时候就会初始化这个bean
 * 	2.如果Bean设置为单例，会在加载配置文件时，直接初始化bean（默认情况下）
 *    如果Bean为原型，加载配置文件后并不会直接初始化这个bean，只有在访问这个bean的时候创建bean
 *  3.bean还有个属性为lazy-init="default" 单例模式下会在加载配置文件时，直接初始化bean
 *    lazy-init="false" 同上，即延迟加载为false时是默认情况
 *    lazy-init="true" 时单例模式下会在第一次访问Bean是初始化bean
 *    而如果时scope="prototype"即原型模式下，不管lazy-init怎么设置都是延迟加载的，即访问bean的时候进行初始化
 *  4.如果bean中重载了构造方法，则必须给这个bean一个没有参数的构造方法
 *  5.bean中还有两个属性，一个时init_method,一个是destory_method这是两个监听bean被创建时调用的方法和bean被销毁时调用的方法
 *    而destory_method的调用会在容器被销魂时调用。
 *    
 * 3、setter注入(demoMethod01有演示)：
 * 	  <bean id = "demoService" class = "wen.rui.kang.service.DemoService">
 * 	  	<property name="demoDao" ref="demoDao"></property>
 * 	  </bean>
 *    说明：如果成员变量是基本数据类型的话，后边就不用ref了，直接用value就行，但是在DemoService类中对应成员变量的set方法必须写
 *    	      其中上边property节点中的name属性的值为成员变量的名称，ref引用的为相应的bean的id
 *    
 * 4、构造注入：(demoMethod02有演示)：
 *    <bean id = "userEntity" class = "wen.rui.kang.entity.UserEntity">
 *    	<constructor-arg index = "0" type = "java.lang.String" value = "kangruiwen"></constructor-arg>
 *    	<constructor-arg index = "1" type = "java.lang.String" value = "kangruiwen"></constructor-arg>
 *    </bean>
 * 	  说明：上面那么写时IoC容器调用有参的构造函数，上边的index时指构造函数中的第几个参数，type是指参数类型，并且如果在没有歧义的情况下，没有index也时可以的，仅仅有index也时可以的
 * 	     index是从0开始的，如果value是对象，则type给出对象的全类型，value改为ref，引用已存在Bean的id
 * 	               并且如果以上边得方式定义bean的话，调用的一定是含两个参数的构造器，仅有这样的UserEntity的bean的话，可以不写无参构造器，从这儿也可以看出，平时调用的是无参构造器
 * 
 * 5、自动装配（demoMethod03有演示）：bean节点的属性autowire，但是并不鼓励使用自动装配
 * 	1、no:默认值，不适用自动装配，bean的依赖必须使用ref或value来显示指定
 * 	2、byType：按类型进行自动装配
 * 		<bean id = "userService" class = "wen.rui.kang.service.UserService" autowire = "byType" ></bean>
 * 	  说明：byType指UserService中的依赖成员的类型，如果在bean容器中找到如此类型的bean则自动装配，但找不到或找到多个则会抛出异常。
 * 	3、byName：按名称自动装配
 * 		<bean id = "userDao" class = "wen.rui.kang.dao.UserDao" autowire="byName"></bean>
 * 	  说明：按UserService中依赖成员的名称，如果在bean容器中找到bean的id与这个依赖的bean的名称一致，则制动装配，否则抛出异常
 * 
 * ***********************************************************************************************************************************
 * 
 * @author Administrator
 *
 */
public class Demo03 {
	
	public void demoMethod01() {
		System.out.println("开始读取配置文件！");
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
	    System.out.println("读取配置文件结束！");
	    DemoService demoService = (DemoService) app.getBean("demoService");
		demoService.demoMethod();
	}
	
	public void demoMethod02() {
		System.out.println("开始读取配置文件！");
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
	    System.out.println("读取配置文件结束！");
	    //注意这个与上边的不同，这样子不用强转
	    UserEntity userEntity = app.getBean("userEntity",UserEntity.class);
	    System.out.println("用户名为：" + userEntity.getUserName());
	    System.out.println("用户密码为：" + userEntity.getPassWord());
	}
	
	@Test
	public void demoMethod03() {
		System.out.println("开始读取配置文件！");
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext app = new ClassPathXmlApplicationContext("beans.xml");
	    System.out.println("读取配置文件结束！");
	    UserService userService = app.getBean("userService",UserService.class);
	    userService.sayDemo();
	}
	
}
