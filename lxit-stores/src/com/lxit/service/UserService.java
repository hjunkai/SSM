package com.lxit.service;

import com.lxit.domain.UserDomain;

public interface UserService {

	void regist(UserDomain userDomain) throws Exception;

	UserDomain active(String code) throws Exception;

	UserDomain login(String username, String password) throws Exception;

}
