package com.lxit.web.servlet;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxit.domain.CategoryDomain;
import com.lxit.service.CategoryService;
import com.lxit.utils.BeanFactory;
import com.lxit.utils.UUIDUtils;
import com.lxit.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;
       
	/*
	 *添加分类 
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.封装categoryDomain对象
			CategoryDomain categoryDomain = new CategoryDomain();
			categoryDomain.setCid(UUIDUtils.getId());
			categoryDomain.setCname(request.getParameter("cname"));
			//2.调用service完成添加
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
			categoryService.save(categoryDomain);
			//3.重定向
			response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	/**
	 * 跳转到添加页面
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}
	/**
	 * 后台展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service 获取所有分类
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
			List<CategoryDomain> list = categoryService.findList();
			//2.将返回的list保存到request域中
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/category/list.jsp";
	}

}
