package wen.rui.kang.springDemo01;

import java.net.URL;

import org.junit.Test;

import wen.rui.kang.entity.UserEntity;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehcache缓存框架的使用
 * 
 * 1、Ehcache的类层次模型主要为三层，最上层的是CacheManager，他是操作Ehcache的入口。
 * 	    我们可以通过CacheManager.getInstance()获得一个单个的CacheManager，或者通过CacheManager的构造函数创建一个新的CacheManager。
 * 	    每个CacheManager都管理着多个Cache。而每个Cache都以一种类Hash的方式，关联着多个Element。而Element则是我们用于存放要缓存内容的地方。
 * 
 * 2、导入相关jar包：ehcache-2.10.3.jar并且说明，ehcache是一个很强大的轻量级框架，不依赖除了slf4j以外的任何包
 * 
 * 3、进行配置文件的配置
 * 	    一个ehcache.xml对应一个CacheManager
 * 	    对配置文件的说明：
 * 		1.maxElementsInMemory = "100" overflowToDisk = "true"
 * 		当对某个缓存添加的element超过100个的时候，开始向磁盘中写入数据。如果在添加Elemtent时，缓存中的Element个数达到了最大缓存数并且overflowToDisk配置的属性为true，
 * 		Ehcache会更具配置项MemoryStoreEvictionPolicy的失效策略将Element输出到磁盘。如果后边设置为false，则超过100个以后，后边的会按FIFO的策略进行清空。
 * 		并且如果需要向磁盘中写，缓存对象必须实现Serializable序列化接口，这点很好理解，因为Java对象IO流中对象必须序列化
 * 		2.在使用完Ehcache后，必须要shutdown缓存。Ehcache中有自己的关闭机制，不过最好在你的代码中显示调用CacheManager.getInstance().shutdown();
 * 		3.另外在刚开始进行缓存的时候，需要先对获得的缓存进行一次清空，然后在开始压入，这样的更有“纯洁性”：cache.removeAll();
 * @author kang
 *
 */
public class Demo05 {

	@Test
	public void testEhcache() {
		//URL是指配置文件所在路径 的URL,后边括号内以/开头，表示从ClassPath根下获取
		URL url = Demo05.class.getClass().getResource("/ehcache.xml");
		//配置缓存管理器
	    CacheManager cacheManager = CacheManager.create(url);
	    
	    //创建完成之后就可以添加缓存 
	    UserEntity userEntity1 = new UserEntity();
	    UserEntity userEntity2 = new UserEntity();
	    UserEntity userEntity3 = new UserEntity();
	    Element element1 = new Element("kang1",userEntity1);  
	    Element element2 = new Element("kang2",userEntity2);
	    Element element3 = new Element("kang3",userEntity3);
	    //cacheName:指ehcache-test.xml配置文件中的缓存名称 name="xxx"中的值  
	    Cache cache = cacheManager.getCache("cache_test");  
	    cache.put(element1);  
	    cache.put(element2);  
	    cache.put(element3);  
	    cache.flush();
	    //缓存的获取
	    Cache cache1 = cacheManager.getCache("cache_test");  
	    Element ele = cache1.get("kang1");  
	    Object data = ele.getObjectValue();//获取到的缓存数据  
	    System.out.println(data);
	    CacheManager.getInstance().shutdown();
	}
	
}
