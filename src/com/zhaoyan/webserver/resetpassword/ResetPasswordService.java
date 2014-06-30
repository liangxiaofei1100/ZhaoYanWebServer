package com.zhaoyan.webserver.resetpassword;

public interface ResetPasswordService {

	/**
	 * user name and token must exist and current time must before valid time.
	 * 
	 * @param username
	 * @param token
	 * @return
	 */
	boolean isRequestPasswordValid(String username, String token);

	boolean resetPassword(String userName, String password);

	/**
	 * When reset password, delete the record to avoid reset again.
	 * 
	 * @param params
	 * @return
	 */
	boolean removeResetPasswordRequestFromDB(String username, String token);
}
