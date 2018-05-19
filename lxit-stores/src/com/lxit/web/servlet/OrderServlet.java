package com.lxit.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lxit.constant.Constant;
import com.lxit.domain.CartDomain;
import com.lxit.domain.CartItemDomain;
import com.lxit.domain.OrderDomain;
import com.lxit.domain.OrderItemDomain;
import com.lxit.domain.PageBean;
import com.lxit.domain.UserDomain;
import com.lxit.service.OrderService;
import com.lxit.utils.BeanFactory;
import com.lxit.utils.PaymentUtil;
import com.lxit.utils.UUIDUtils;
import com.lxit.web.servlet.base.BaseServlet;

/**
 * 订单模块
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 在线支付
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取收货信息，获取oid 获取银行
			//接受参数
			String address=request.getParameter("address");
			String name=request.getParameter("name");
			String telephone=request.getParameter("telephone");
			String oid=request.getParameter("oid");
			
			
			//通过id获取order
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			OrderDomain order = s.getById(oid);
			
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			
			//更新order
			s.update(order);
			//2.调用service获取订单修改个人信息，更新订单
			//3.拼接给第三方的url
			//4.重定向
			// 组织发送支付公司需要哪些数据
					String pd_FrpId = request.getParameter("pd_FrpId");
					String p0_Cmd = "Buy";
					String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
					String p2_Order = oid;
					String p3_Amt = "0.01";
					String p4_Cur = "CNY";
					String p5_Pid = "";
					String p6_Pcat = "";
					String p7_Pdesc = "";
					// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
					// 第三方支付可以访问网址
					String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
					String p9_SAF = "";
					String pa_MP = "";
					String pr_NeedResponse = "1";
					// 加密hmac 需要密钥
					String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
					String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
							p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
							pd_FrpId, pr_NeedResponse, keyValue);
				
					
					//发送给第三方
					StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
					sb.append("p0_Cmd=").append(p0_Cmd).append("&");
					sb.append("p1_MerId=").append(p1_MerId).append("&");
					sb.append("p2_Order=").append(p2_Order).append("&");
					sb.append("p3_Amt=").append(p3_Amt).append("&");
					sb.append("p4_Cur=").append(p4_Cur).append("&");
					sb.append("p5_Pid=").append(p5_Pid).append("&");
					sb.append("p6_Pcat=").append(p6_Pcat).append("&");
					sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
					sb.append("p8_Url=").append(p8_Url).append("&");
					sb.append("p9_SAF=").append(p9_SAF).append("&");
					sb.append("pa_MP=").append(pa_MP).append("&");
					sb.append("pd_FrpId=").append(pd_FrpId).append("&");
					sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
					sb.append("hmac=").append(hmac);
					
					response.sendRedirect(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
			return "/jsp/msg.jsp";
		}
		return null;
	}
	
	/**
	 * 获取订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid= request.getParameter("oid");
			//2.调用service查询当个订单
			OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
			OrderDomain orderDomain = orderService.getById(oid);
			//3.保存查询出来的订单详情对象，请求转发到order_info.jsp
			request.setAttribute("bean", orderDomain);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查看订单详情失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取pageNumber 设置pagesize 获取userid
		int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		int pagesize = 3;
		UserDomain userDomain = (UserDomain) request.getSession().getAttribute("user");
		if(userDomain==null){
			request.setAttribute("msg", "请先登录");
			return "/jsp/msg.jsp";
		}
		//2.调用service获取当前页面数据pagebean
		OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
		try {
			PageBean<OrderDomain> pageBean = orderService.findMyOrderByPage(pageNumber,pagesize,userDomain.getUid());
			//3.将pagebean放入到request域中，请求转发到order_list.jsp
			request.setAttribute("pageBean", pageBean);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "订单获取失败");
			return "jsp/msg.jsp";
		}
		return "/jsp/order_list.jsp";
	}
    
	/**
	 * 保存订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//0.-1从session中获取用户user
			UserDomain userDomain = (UserDomain) request.getSession().getAttribute("user");
			if(userDomain==null){
				//未登录
				request.setAttribute("msg", "您还没登录，不能生成订单");
				return "/jsp/msg.jsp";
			}
			//0.0得到购物车对象
			CartDomain cartDomain = (CartDomain) request.getSession().getAttribute("cart");
			//1封装订单对象
			
			//1.1创建对象
			OrderDomain orderDomain = new OrderDomain();
			
			//1.1设置oid
			orderDomain.setOid(UUIDUtils.getId());
			//1.2设置ordertime
			orderDomain.setOrdertime(new Date());
			//1.3设置total
			//从购物车中获取
			orderDomain.setTotal(cartDomain.getTotal());
			//1.4设置state
			orderDomain.setState(Constant.ORDER_WEIFUKUAN);
			//1.5设置user
			orderDomain.setUser(userDomain);
			//1.6设置orderItems(订单列表)，需要遍历购物列表
			for (CartItemDomain ci : cartDomain.getCartItems()) {
				//从购物项中遍历出来的数据封装到orderItem（订单项）
				//1.6.1 封装orderItem对象
				//a.创建orderItem对象
				OrderItemDomain itemDomain = new OrderItemDomain();
				//b.设置itemid
				itemDomain.setItemId(UUIDUtils.getId());
				//c.设置count
				itemDomain.setCount(ci.getCount());
				//d.设置subtotal
				itemDomain.setSubtotal(ci.getSubtotal());
				//e.设置product
				itemDomain.setProduct(ci.getProduct());
				//e.设置order 
				itemDomain.setOrder(orderDomain);
				//1.6.2将orderItem加入到order的items中
				orderDomain.getOrderItems().add(itemDomain);
			}
			//2调用service完成保存操作
			OrderService orderService = (OrderService) BeanFactory.getBean("OrderService");
			orderService.save(orderDomain);
			//生成订单成功后要清空购物车
			cartDomain.clearCart();
			//3请求转发到order_info.jsp页面
			request.setAttribute("bean", orderDomain);
		} catch (Exception e) {//异常只try不处理
		}
		return "/jsp/order_info.jsp";
	}
}
