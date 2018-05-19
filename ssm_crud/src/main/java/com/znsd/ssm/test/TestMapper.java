package com.znsd.ssm.test;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.znsd.ssm.dao.DepartmentMapper;
import com.znsd.ssm.dao.EmployeeMapper;
import com.znsd.ssm.entity.Department;
import com.znsd.ssm.entity.Employee;

/**
 * 测试dao层工作
 * @author Administrator
 *	推荐spring的项目可以使用spring的单元测试，可以自动注入需要的组件
 *	1.导入springTest模块
 *	2.@ContextConfiguration指定spring的配置文件位置
 *	3.直接autowired要使用的组件即可
 */
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestMapper {
	
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
	private EmployeeMapper employeeMapper;
	
	@Autowired
	private SqlSession sqlSession;
	
	/**
	 * 测试部门
	 */
	@Test
	public void testCRUD(){
		/*//1.创建springIOC容器
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		//2.从容器中获取mapper 
		DepartmentMapper bean = context.getBean(DepartmentMapper.class);*/
		System.out.println(departmentMapper);//org.apache.ibatis.binding.MapperProxy@1399441
		
		//1.测试部门插入
		//departmentMapper.insertSelective(new Department(null,"开发部2"));
		//departmentMapper.insertSelective(new Department(null,"测试部2"));
		
		//2.测试员工插入
		//employeeMapper.insertSelective(new Employee(null,"张三","m","aa@qq.com",1));
		
		//3.批量插入员工，
		EmployeeMapper mapper = this.sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String uid = UUID.randomUUID().toString().substring(0, 5) + i;
			String m = i%2==0 ? "m" : "f";
			mapper.insertSelective(new Employee(null,uid,m,uid+"@qq.com",1));
		}
		System.out.println("批量完成");
	}
}
