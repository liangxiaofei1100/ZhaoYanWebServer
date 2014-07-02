package com.zhaoyan.webserver.userinfomanage;

public interface ModifyUserInfoService {

	boolean modifyPassword(String username, String newPassword);

	boolean modifyEmail(String username, String email);

	boolean modifyPhone(String username, String phone);

}
