package wen.rui.kang.springDemo01;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;


/**
 * 第二节：数据库连接池的配置
 * 说明：1.数据库使用mysql数据库
 * 	   2.连接池使用dbcp连接池
 * 搭建：1.导入相关jar包：数据库驱动包mysql-connector-java、dbcp实现包commons-dbcp、dbcp依赖包common-pool
 * 	   2.使用配置文件方式对连接池进行配置，配置文件为dbconfig.properties
 * 
 * ************************************************************************************************************************
 * 
 * 再复习一下JDBC的API
 * 一、在不使用数据库连接池的情况下：
 * 1：加载数据库驱动
 * 	Class.forName("com.mysql.jdbc.Driver");
 * 2:创建数据库连接对象
 * 	Connection conn =  DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/application","userName","passWord");
 * 	Connection常用方法
 *		1.创建语句对象Statement ： conn.createStatement()
 *		2.创建预处理对象PreparedStatement :
 *			conn.prepareStatement(sql)：使用给定对象创建预处理对象
 * 3.Statement接口
 * 	1、查询：public ResultSet executeQuery(String sql)
 * 	2、非查询语句：public int executeUpdate(String sql) : 返回值是更新的行数
 *  3、执行语句：public boolean execute(String sql) : 允许执行查询语句、更新语句、DDL语句
 *  	对执行语句的说明：返回值为true时，表示执行的是查询语句，可以通过state.getResultSet方法获取结果；
 *  	返回值为false时，执行的是更新语句或DDL语句，过state.getUpdateCount方法获取更新的记录数量。
 * 4.PreparedStatement接口：
 * 	1、设置占位符：
 * 		pstmt.setInt(int parameterIndex,int x);第一个参数为序号（从1开始），第二个参数为要传入后台的值
 * 		基本上每个类型都有对应的setXXX方法
 * 	查询与非查询语句与上边相同，不过里边没有sql
 * 5、ResultSet接口：
 * 	1.检索字段值(以String为例，别的基本类型也有getXXX方法)：
 * 		public String getString(int columnIndex);//查询结果集当前条目的某个字段值，后边这个参数是当前结果集中的第几个，序号从1开始，但是不建议使用
 * 		public String getString(int columnName); //同上，参数为字段名，即数据库中列名
 * 	2.移动游标
 * 		public boolean next() throws SQLException;//移动游标，若新行有有效数据返回true
 * 
 * *******************************************************************************************************************
 * 
 * 二、使用dbcp数据源：
 * 1.对数据源进行配置
 * 2.获取数据源
 * 3.导入相关Jar包：dbcp实现包commons-dbcp、dbcp依赖包common-pool
 * @author kang
 *
 */
public class Demo02 {
	
	//数据源
	private static DataSource ds;  
	
	//数据源配置
	static{  
        try {
            InputStream in = Demo02.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
            Properties props = new Properties(); 
            props.load(in);  
            ds = BasicDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
           System.out.println("数据库配置错误！");
        }
    }
	
	//获得数据库连接
	public static Connection getConnection(){  
        try {  
            return ds.getConnection();  
        } catch (SQLException e) {  
            throw new RuntimeException(e);  
        }  
    }
	
	/**
	 * 数据库调用结束之后，释放资源
	 * @param rs
	 * @param stmt
	 * @param conn
	 */
	public static void release(ResultSet rs,Statement stmt,Connection conn){  
        if(rs!=null){  
            try {  
                rs.close();  
            } catch (SQLException e) {  
            	System.out.println("ResultSet关闭失败！");
            }  
            rs = null;  
        }  
        if(stmt!=null){  
            try {  
                stmt.close();  
            } catch (SQLException e) {  
            	System.out.println("Statement关闭失败！");
            }  
            stmt = null;  
        }  
        if(conn!=null){  
            try {  
                conn.close();  
            } catch (SQLException e) {  
            	System.out.println("Connection关闭失败！");
            }  
            conn = null;  
        }  
    }
	
	
    public void testStatementQuery() throws SQLException {
    	Connection conn = Demo02.getConnection();
		Statement stat = conn.createStatement();
		ResultSet rs = stat.executeQuery("SELECT * FROM USER");
		while(rs.next()){
			String userName = rs.getString("user_name");
			String passWord = rs.getString("user_id");
			System.out.println(userName);
			System.out.println(passWord);
		}
    }
    
    public void testStatementUpdate() throws SQLException {
    	Connection conn = Demo02.getConnection();
		Statement stat = conn.createStatement();
		int boo = stat.executeUpdate("UPDATE USER SET USER_NAME = 'rui' WHERE USER_ID = '10000'");
		System.out.println(boo);
    }
    
   
    public void testStatementInsert() throws SQLException {
    	Connection conn = Demo02.getConnection();
		Statement stat = conn.createStatement();
		int boo = stat.executeUpdate("INSERT INTO USER (USER_ID,USER_NAME,PASS_WORD) VALUES ('30000','WEN','33333')");
		System.out.println(boo);
    }
    
    @Test
    public void testPreState() throws SQLException {
    	Connection conn = Demo02.getConnection();
    	String sql = "DELETE FROM USER WHERE USER_ID = ?";
    	PreparedStatement ps = conn.prepareStatement(sql);
    	ps.setString(1, "30000");
    	int boo = ps.executeUpdate();
    	System.out.println(boo);
    }
	
}
