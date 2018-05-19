package com.lxit.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.lxit.constant.Constant;
import com.lxit.domain.UserDomain;
import com.lxit.service.UserService;
import com.lxit.service.impl.UserServiceImpl;
import com.lxit.utils.UUIDUtils;
import com.lxit.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
   /**
    * 跳转到register页面
    * @param request
    * @param response
    * @return
    * @throws ServletException
    * @throws IOException
    */
	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//当用户退出时销毁session
		request.getSession().invalidate();
		//重定向到index.jsp页面
		response.sendRedirect(request.getContextPath());
		return null;
	}
	
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	
	/**
	    * 跳转到login页面
	    * @param request
	    * @param response
	    * @return
	    * @throws ServletException
	    * @throws IOException
	    */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取用户名和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//2.调用service 完成登录校验：放回userDomain对象
		UserService userService = new UserServiceImpl();
		try {
			UserDomain userDomain = userService.login(username,password);
			
			//3.根据userDomain对象，生成相应的提示信息
			if(userDomain == null){//用户名和密码错误生成提示信息
				request.setAttribute("msg", "用户名或密码错误");
				return "jsp/login.jsp";
			}
			
			if(userDomain.getState() == Constant.USER_IS_ACTICE){
				request.setAttribute("msg", "用户未激活");
				return "jsp/msg.jsp";
			}
			//用户验证成功，登录成功，保存用户名到session 跳转到index.jsp
			request.getSession().setAttribute("user", userDomain);
			
			///////////记住用户名////////////////
			//判断是否勾选记住用户名
			String savename = request.getParameter("savename");
			if(Constant.SAVE_NAME.equalsIgnoreCase(savename)){
				Cookie cookie = new Cookie("saveName", URLEncoder.encode(username,"utf-8"));
				cookie.setMaxAge(Constant.MAXTIME);
				cookie.setPath(request.getContextPath()+"/");
				response.addCookie(cookie);
			}
			///////////记住用户名////////////////
			
			//跳转到index.jsp
			response.sendRedirect(request.getContextPath());
		} catch (Exception e) {
			request.setAttribute("msg", "用户登录失败");
			return "jsp/msg.jsp";
		}
		
		
		return null;
	}
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.封装对象
		UserDomain userDomain = new UserDomain();
		try {
			BeanUtils.populate(userDomain, request.getParameterMap());
			//1.1手动封装，状态吗，和code验证码
			userDomain.setUid(UUIDUtils.getId());
			userDomain.setState(Constant.USER_IS_NOT_ACTICE);
			userDomain.setCode(UUIDUtils.getCode());
			System.out.println(userDomain);
			//2调用service完成注册
			UserService userService = new UserServiceImpl();
			userService.regist(userDomain);
			//3.注册成功，生成提示信息，转发到msg.jsp
			request.setAttribute("msg","注册成功，激活后请登录");
		} catch (Exception e) {
			e.printStackTrace();
			//转发到msg.jsp
			request.setAttribute("msg", "用户注册失败");
			return "jsp/msg.jsp";
		}
		return "jsp/msg.jsp";
	}
	/**
	 * 用户激活
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.接受code
		String code = request.getParameter("code");
		//2.调用service完成激活 返回userDomain对象
		UserService userService = new UserServiceImpl();
		try {
			UserDomain userDomain = userService.active(code);
			//3判断userDomain,生成不同的提示信息
			if(userDomain == null ){//没有找到要激活的这个用户，激活失败
				request.setAttribute("msg", "用户激活失败，请重新激活或注册");
				return "jsp/msg.jsp";
			}
			//激活成功，生成 提示信息
			request.setAttribute("msg", "恭喜您，激活成功，可以登录");
		} catch (Exception e) {
			request.setAttribute("msg", "激活失败，请重新注册");
			return "jsp/msg.jsp";
		}
		return "jsp/msg.jsp";
	}
}
