package com.lxit.web.servlet.base;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的servlet，其他servlet的父类
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取方法名称
		String methodName = request.getParameter("method");
		try {
			//判断参数是否为空，如果为空，让其执行默认方法
			if(methodName == null || methodName.trim().isEmpty()){
				methodName = "index";
			}
			//2.获取方法对象
			Method method = this.getClass().getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			//3.让方法执行,接受返回值
			String path = (String) method.invoke(this,request,response);
			if(path!=null){//如果path！=null 执行请求转发
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			//throw new RuntimeException();
		} 
	}
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print("你没有传递参数");
		return null;
	}
}
