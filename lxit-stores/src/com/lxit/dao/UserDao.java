package com.lxit.dao;




import com.lxit.domain.UserDomain;

public interface UserDao {

	void regist(UserDomain userDomain) throws Exception;

	UserDomain getByCode(String code) throws Exception;

	void update(UserDomain userDomain) throws Exception;

	UserDomain getByUsernameAndPassword(String username, String password) throws Exception;

}
