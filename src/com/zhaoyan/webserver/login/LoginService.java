package com.zhaoyan.webserver.login;

import java.util.List;

public interface LoginService {

	boolean login(List<Object> params);
	
	boolean isUserExist(List<Object> params);
}
