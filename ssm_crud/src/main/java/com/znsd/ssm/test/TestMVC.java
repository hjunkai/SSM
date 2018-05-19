package com.znsd.ssm.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;

/*
 * 使用spring测试模块提供的请求功能
 */
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpath:springmvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestMVC {
	
	//传入springmvc的ioc
	@Autowired
	WebApplicationContext context;

	//虚拟mvc请求，获取请求结果
	MockMvc mockMvc;
	
	/*@Before
	public void initWockMvc(){
		
	}*/
	
	@Test
	public void testPage() throws Exception{
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employee/emps").param("pg", "1")).andReturn();
		System.out.println(result);
		MockHttpServletRequest request = result.getRequest();
		PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
		System.out.println(pageInfo);
		//PageInfo{pageNum=1, pageSize=5, size=5, startRow=1, endRow=5, total=1001, pages=201, list=Page{count=true, pageNum=1, pageSize=5, startRow=0, endRow=5, total=1001, pages=201, reasonable=false, pageSizeZero=false}, prePage=0, nextPage=2, isFirstPage=true, isLastPage=false, hasPreviousPage=false, hasNextPage=true, navigatePages=10, navigateFirstPage=1, navigateLastPage=10, navigatepageNums=[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]}
	}
}
