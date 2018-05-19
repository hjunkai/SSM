package com.lxit.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxit.service.CategoryService;
import com.lxit.service.impl.CategoryServiceImpl;
import com.lxit.web.servlet.base.BaseServlet;

/**
 * 分类模块
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 查询所有的分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//0.设置响应的编码
		response.setContentType("text/html;charset=utf-8");
		//1.调用service方法，查询所有的分类，放回json类型的String字符串
		CategoryService categoryService = new CategoryServiceImpl();
		try {
			//这是从MySQL数据库中获取
			String value = categoryService.findAll();
			//这是从redis中获取
			//String value = categoryService.findAllFormRedis();
			//2.将字符串写回到jsp页面
			response.getWriter().print(value);
		} catch (Exception e) {//异常不处理
		}
		return null;
	}

}
