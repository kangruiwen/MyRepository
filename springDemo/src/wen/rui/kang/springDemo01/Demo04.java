package wen.rui.kang.springDemo01;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 * 日志框架部分的搭建
 * 1、使用log4j2日志框架：
 * 	1.导入log4j-core-xx与log4j-apijar包
 * 	2.配置其配置文件命名为log4j2.xml
 *		在类路径下系统会默认读取名为log4j2.xml的配置文件
 * 	3.log4j2的获取
 * 		private static final Logger log = LogManager.getLogger();
 * 	    说明：static final 因为日志类型最好不变所以声明为static final
 * 	4.日志配置文件的简单说明（具体的配置说明可以看笔记）：
 * 		log4j2规定了默认的几个级别：all<trace<debug<info<warn<error<fatal<off，关于这个日志级别有下面几点需要注意：
 * 			1.如果不进行日志级别的设置，log4j2的默认日志级别为error
 * 			2.如果设置的日志级别为info，则只有大于或等于这个级别的日志才会输出
 * 			3.all：最低日志级别，会输出所有日志；trace：是追踪，就是程序一下推进；off：最高日志级别，会关闭所有日志打印
 * 
 * 2、使用slf4j来隐藏日志的具体实现，即实现客户端应用的解耦：
 * 	1.导入log4j-slf4j-impl-2.2.jar、slf4j-api-1.7.21.jar包
 * 	2.slf4j的获取
 * 		 private static final logger = LoggerFactory.getLogger(Demo04.class);
 * 	一般情况下我们就可以使用slf4j即简单日志门面，这样从设计模式的角度考虑，它是用来在log和代码层之间起到门面的作用。
 * 	对用户来说只要使用slf4j提供的接口，即可隐藏日志的具体实现。他与jdbc的实现有些类似。
 * 
 * 到此位置一般的日志框架的搭建就完成了。
 * 
 * *******************************************************************************************************************************
 * @author kang
 *
 */
public class Demo04 {
	
	 private static final Logger log = LogManager.getLogger();
	 
	 private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Demo04.class);	 
	 @Test
	 public void testDemo() {
		 log.info("测试Log");
		 logger.info("再次测试！");
	 }
	 
}
