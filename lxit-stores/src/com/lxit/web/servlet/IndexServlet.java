package com.lxit.web.servlet;

import com.lxit.domain.ProductDomain;
import com.lxit.service.ProductService;
import com.lxit.service.impl.ProductServiceImpl;
import com.lxit.web.servlet.base.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 首页模块
 * Servlet implementation class IndexServlet
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.调用productservice查询最新商品和热门 	商品
		ProductService productService = new ProductServiceImpl();
		try {
			List<ProductDomain> hotList = productService.findHot();
			List<ProductDomain> newList = productService.findNew();
			//2.将查询出来的数据list保存到request域中
			request.setAttribute("hotList", hotList);
			request.setAttribute("newList", newList);
		} catch (Exception e) {//异常不处理
			e.printStackTrace();
		}
		return "/jsp/index.jsp";
	}
}
