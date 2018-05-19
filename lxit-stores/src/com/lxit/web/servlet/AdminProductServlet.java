package com.lxit.web.servlet;

import com.lxit.domain.ProductDomain;
import com.lxit.service.ProductService;
import com.lxit.utils.BeanFactory;
import com.lxit.web.servlet.base.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/*
	 *查询已上架的商品 
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service 查询上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<ProductDomain> list = ps.findAll();
			//2将返回值保存到request域中
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/product/list.jsp";
		
	}

}
