package com.lxit.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxit.domain.CartDomain;
import com.lxit.domain.CartItemDomain;
import com.lxit.domain.ProductDomain;
import com.lxit.service.ProductService;
import com.lxit.service.impl.ProductServiceImpl;
import com.lxit.utils.BeanFactory;
import com.lxit.web.servlet.base.BaseServlet;


/**
 * 购物车模块
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取购物车，并清空该购物车
		getCart(request).clearCart();
		//2.重定向到cart.jsp
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	/**
	 * 从购物车中移除购物项
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取pid
		String pid = request.getParameter("pid");
		System.out.println("rr"+pid);
		//2.获取购物车，并移除
		getCart(request).removeCart(pid);
		//3.重定向到cart.jsp
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}

	/**
	 * 添加购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add2cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取表单传递过来的商品编号pid,商品数量count
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		//2.封装cartitem
		//2.1调用service获取product
		//ProductService productService = (ProductService) BeanFactory.getBean("ProductService");
		ProductService productService = new ProductServiceImpl();
		try {
			ProductDomain product = productService.getById(pid);
			CartItemDomain cartItemDomain = new CartItemDomain(product,count);
			//3.加入购物车
			//得到购物车
			CartDomain cartDomain = getCart(request);
			cartDomain.add2cart(cartItemDomain);//添加购物项
			//4.重定向到jsp/cart.jsp页面
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加购物车失败");
			return "/jsp/msg.jsp";
		}
		return null;
	}

	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
	private CartDomain getCart(HttpServletRequest request) {
		CartDomain cartDomain = (CartDomain) request.getSession().getAttribute("cart");
		//对cartDomain 进行判断 如果没有==null则创建一个购物车，并放到session域中
		if(cartDomain == null){
			cartDomain = new CartDomain();
			request.getSession().setAttribute("cart", cartDomain);
		}
		return cartDomain;
	}

}
