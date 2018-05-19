package com.lxit.service.impl;

import com.lxit.constant.Constant;
import com.lxit.dao.UserDao;
import com.lxit.dao.impl.UserDaoImpl;
import com.lxit.domain.UserDomain;
import com.lxit.service.UserService;
import com.lxit.utils.MailUtils;

public class UserServiceImpl implements UserService {
	UserDao userDao = new UserDaoImpl();
	
	/**
	 * 用户登录
	 * @throws Exception 
	 */
	@Override
	public UserDomain login(String username, String password) throws Exception {
		//调用dao层  通过用户名和密码 获取userDoamin对象
		return  userDao.getByUsernameAndPassword(username,password);
	}
	
	/**
	 * 用户激活 
	 * @throws Exception 
	 */
	@Override
	public UserDomain active(String code) throws Exception {
		//通过code获取用户信息
		UserDomain userDomain = userDao.getByCode(code);
		if(userDomain == null){//通过激活码没有找到用户，返回null
			return null;
		}
		//修改用户的激活状态码
		userDomain.setState(Constant.USER_IS_ACTICE);
		userDomain.setCode(null);//将code置空
		//调用dao层完成修改
		userDao.update(userDomain);
		return userDomain;
	}
	
	/**
	 * 用户注册
	 * @throws Exception 
	 */
	@Override
	public void regist(UserDomain userDomain) throws Exception {
		//1.调用userDao的regist方法完成注册
		userDao.regist(userDomain);
		/*//2发送邮件完成激活
		String emailMsg = "恭喜"+userDomain.getUsername()+"注册成功，<a href = 'http://localhost/lxit-store/user?method=active&code="+userDomain.getCode()+"'>点此激活</a>";
		MailUtils.sendMail(userDomain.getEmail(), emailMsg);*/
	}

	
	

}
