package wen.rui.kang.springDemo01;

import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 功能：Bean容器的一个简单是实现
 * 
 * 1.先说两个方法：URL url = clazz.getResource(path); InputStream is = clazz.getResourceAsStream(path);
 * 	1.第一个方法返回的是所获取资源的URL地址，第二个方法返回的是获得的资源的输入流
 * 	2.path不以'/'开头时，默认是从此类所在的包下取资源；path以'/'开头时，则是从项目的ClassPath根下获取资源。在这里'/'表示ClassPath,即类路径下
 *  3.另外需要说明的是Class.getClassLoader().getResource(String path)后边的那个getResourceAsStream也是如此
 *  	1.path不能以'/'开头时，path是指类加载器的加载范围，在资源加载的过程中，使用的逐级向上委托的形式加载的，'/'表示Boot ClassLoader中的加载范
 *  	      因为这个类加载器是C++实现的，所以加载范围为null。但是path不带'/'时，这个是从项目根目录中开始获取资源的。所以：
 *  	  class.getResource("/") == class.getClassLoader().getResource("")
 *  
 * 2.使用SAXReader解析XML文档：
 * 	1.导入dom4j.jar包
 * 	2.创建SAXReader对象，读取xml文件的输入流对象并返回Document对象，这个Document对象是对整个xml文件的一个抽象。
 *  3.Element element = document.getRootElement()返回xml文件的根节点对象。
 *  4.对root节点下的bean节点进行遍历  ----->>>> for (Iterator i = root.elementIterator("bean"); i.hasNext();)
 *  	Attribute name = foo.attribute("name"); 获得foo节点的name属性
 *  	另外Attribute与Element对象都有getText方法，返回的是对应对象的值。
 *  
 * 3.简单的使用java反射：
 * 	1.反射中获得类对象的三种常见方式：
 * 		1.Class c1 = Class.forName("全类名");
 * 		2.Class c2 = Demo01.getClass(); 通过类名来getClass
 * 		3.Demo01 demo  = new Demo01();
 * 		  Class c3 = demo.class;(即由类对象来获得运行时类)
 * 	      最后可以使用c.newInstance()来调用类的默认构造器,进而创建类的实例
 * 	2.获得类的成员变量：
 * 		1.Field[] field = clazz.getFields();--这个是仅获得某个类的公共属性，受保护的也获得不了
 * 		2.Field field = clazz.getField("fieldName");---获得类中具体的某个成员变量，仅能获得公共的，如果没有会抛出异常
 * 		3.Field[] field = clazz.getDeclaredFields();---获得类中的所有的成员变量
 * 		4.Field field = clazz.getDeclaredField("name");---获得类中的某个成员变量，包括私有的。
 * 		5.另外说明一个Field的方法：public Object get(Object obj)：后边这个参数传入的是要获取具体field值的类对象，如果这个field是static即属于类级别的obj可以为null
 * 	3.获得类的方法：
 * 		1.Method[] method = clazz.getMethods();---仅包含公共的
 * 		2.Method method = clazz.clazz.getMethod(String name,Class... parameterTypes)//name为方法名称，而第二个不定参数为所要返回的方法的形参的类型，按顺序写
 * 		3.Method[] m = clazz.getDeclaredMethods();--包含私有的
 * 		4.Method m = clazz.getDeclaredMethod(String name,Class... parameterTypes);----同上
 * 		5.Method.invoke(Object obj, Object... args)：执行对应对象的method方法，第一个参数为，要执行这个方法的实例对象，第二个不定参数是要向所调用方法中传入的参数的值
 * 
 * 4.BeanInfo使用说明：
 * 	1.什么是BeanInfo：主要是对JavaBean中属性，方法等信息获取的一个类，里边提供对JavaBean属性，方法的反射应用的一些封装。
 * 	2.BeanInfo的获取：使用内省获取java.beans.Introspector.getBeanInfo(Class bean);后边参数为JavaBean的一个类对象
 * 	3.BeanInfo的常用方法：
 * 		1.java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();
 * 		      获取对类的属性的描述--Returns descriptors for all properties of the bean. 
 * 			1.public String getName()----Gets the programmatic name of this feature.获得属性的名称
 * 			2.getReadMethod() ------Gets the method that should be used to read the property value.获得对应属性的getter方法
 * 			3.getWriteMethod()------Gets the method that should be used to write the property value.获得对应属性的setter方法
 * 		2.MethodDescriptor md[] = info.getMethodDescriptors()
 * 		      获取对类的方法的描述---Gets the beans MethodDescriptors.	   
 *    
 * **********************************************************************************************************************************
 * @user krw 2017年3月7日
 */
public class BeanFactory {

	private Map<String, Object> beanMap = new HashMap<String, Object>();

	public void init(String path) throws DocumentException,
			ClassNotFoundException, IntrospectionException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		SAXReader reader = new SAXReader();
		
		ClassLoader clazz = Thread.currentThread().getContextClassLoader();
		InputStream is = clazz.getResourceAsStream(path);

		Document document = reader.read(is);

		Element root = document.getRootElement();
		Element foo;

		for (Iterator i = root.elementIterator("bean"); i.hasNext();) {

			foo = (Element) i.next();

			Attribute id = foo.attribute("id");
			Attribute cls = foo.attribute("class");

			Class bean = Class.forName(cls.getText());
			// 获取对应class的信息
			java.beans.BeanInfo info = java.beans.Introspector.getBeanInfo(bean);
			// 获取其属性描述
			java.beans.PropertyDescriptor pd[] = info.getPropertyDescriptors();

			Object obj = bean.newInstance();

			for (Iterator ite = foo.elementIterator("property"); ite.hasNext();) {
				Element foo2 = (Element) ite.next();
				// 获取该property的name属性
				Attribute name = foo2.attribute("name");
				String value = null;

				for (Iterator ite1 = foo2.elementIterator("value"); ite1.hasNext();) {
					Element node = (Element) ite1.next();
					value = node.getText();
					break;
				}
				// 利用Java的反射机制调用对象的某个set方法，并将值设置进去
				for (int k = 0; k < pd.length; k++) {
					if (pd[k].getName().equalsIgnoreCase(name.getText())) {
						Method mSet = null;
						mSet = pd[k].getWriteMethod();
						mSet.invoke(obj, value);// 这点是调用obj的WRITE方法，赋的值为value
					}
				}
			}
		}
	}
}