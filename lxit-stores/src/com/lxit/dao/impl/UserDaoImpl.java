package com.lxit.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.lxit.dao.UserDao;
import com.lxit.domain.UserDomain;
import com.lxit.utils.DataSourceUtils;

public class UserDaoImpl implements UserDao {
	/**
	 * dao层把注册信息持久化到数据库中
	 * @throws SQLException 
	 */
	@Override
	public void regist(UserDomain userDomain) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql,userDomain.getUid(),userDomain.getUsername(),
				userDomain.getPassword(),userDomain.getName(),userDomain.getEmail()
				,userDomain.getTelephone(),userDomain.getBirthday()
				,userDomain.getSex(),userDomain.getState(),userDomain.getCode());
	}
	/**
	 * 通过激活码获取用户信息
	 * @throws SQLException 
	 */
	@Override
	public UserDomain getByCode(String code) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
		qr.query(sql, new BeanHandler<UserDomain>(UserDomain.class));
		return null;
	}
	/**
	 * 更新用户的信息
	 * @throws SQLException 
	 */
	@Override
	public void update(UserDomain userDomain) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update user set password = ?,sex = ?,state = ?,code = ? where uid = ?";
		qr.update(sql,userDomain.getPassword(),userDomain.getSex(),userDomain.getState(),userDomain.getCode());
	}
	@Override
	public UserDomain getByUsernameAndPassword(String username, String password) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ?";
		return qr.query(sql,new BeanHandler<UserDomain>(UserDomain.class),
				username,password);
	}

}
