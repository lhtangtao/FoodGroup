package com.uxian.foodgroup.util;

import java.io.IOException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author iversoncl
 * @Date 2015年4月16日
 * @Project HiHealthTest
 */
public class MyBatis {
	
	/**
	 * @Description: 获得MyBatis SqlSessionFactory
	 *               SqlSessionFactory负责创建SqlSession，一旦创建成功，
	 *               就可以用SqlSession实例来执行映射语句，commit，rollback，close等方法。
	 * @return SqlSessionFactory
	 * @author: iversoncl
	 * @time:2015年4月16日 下午2:49:36
	 */
	public static SqlSessionFactory getSessionFactory(String environment) {
		SqlSessionFactory sessionFactory = null;
		String resource = "Configuration.xml";
		try {
			sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(resource), environment);
			
		}
		catch(IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sessionFactory;
	}
	/*
		public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			SqlSession sqlSession = getSessionFactory().openSession();
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			User user = userMapper.selectByMobile("13429666805");
			System.out.println(user.getNickName());
		
			Class<?> cl = user.getClass();  
			Method nickname = cl.getMethod("getNickName", null);  
			String s="getNickName";
			String name = (String) nickname.invoke(user, null);
		}
	*/
}
