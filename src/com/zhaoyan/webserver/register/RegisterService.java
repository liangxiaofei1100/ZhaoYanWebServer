package com.zhaoyan.webserver.register;

import java.util.List;

public interface RegisterService {

	/**
	 * Register User
	 * 
	 * @param params
	 * @return
	 */
	boolean registerUser(List<Object> params);
	
	boolean isUserNameExist(String username);
}
