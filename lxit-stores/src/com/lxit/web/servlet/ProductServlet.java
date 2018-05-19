package com.lxit.web.servlet;

import com.lxit.domain.PageBean;
import com.lxit.domain.ProductDomain;
import com.lxit.service.ProductService;
import com.lxit.service.impl.ProductServiceImpl;
import com.lxit.web.servlet.base.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 前台商品模块
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 分类商品展示
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取pagenumber cid 设置pagesize
//		String pagenNumber = request.getParameter("pageNumber");
		int pageNumber = 1;
		try {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		} catch (NumberFormatException e) {//只try不处理，如果parseInt报错，默认pageNumber=1
		}
		int pageSize = 12;
		String cid = request.getParameter("cid");
		//2.调用service 分页查询商品 返回pagebean
		ProductService productService = new ProductServiceImpl(); 
		try {
			PageBean<ProductDomain> pageBean = productService.findByPage(pageNumber,pageSize,cid);
			//3.将pagebean放入到request域中
			request.setAttribute("pageBean",pageBean);
		} catch (Exception e) {
			//e.printStackTrace();
			request.setAttribute("msg", "分页查询失败");
			return "jsp/msg.jsp";
		}
		return "/jsp/product_list.jsp";
	}
	
	/**
	 * 查询详细商品详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
    public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//1.获取pid
    	String pid = request.getParameter("pid");
    	//2.调用service 通过pid获取详细的商品详情
    	ProductService productService = new ProductServiceImpl();
    	try {
			ProductDomain productDomain = productService.getById(pid);
			//3.将product放入到request域中
			request.setAttribute("bean", productDomain);
		} catch (Exception e) {
			request.setAttribute("msg", "详细商品查询失败");
			return "jsp/msg.jsp";
		}
    	return "/jsp/product_info.jsp";
    }
    
}
