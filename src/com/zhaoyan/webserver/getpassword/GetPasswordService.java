package com.zhaoyan.webserver.getpassword;

import java.util.List;
import java.util.Map;

public interface GetPasswordService {

	/**
	 * Whether the user is exist or not.
	 * 
	 * @param params
	 * @return
	 */
	String getUserName(List<Object> params);

	/**
	 * Get user id;
	 * 
	 * @param params
	 * @return
	 */
	int getUserId(List<Object> params);

	/**
	 * Whether the user has email or not.
	 * 
	 * @param params
	 * @return
	 */
	String getEmail(List<Object> params);

	/**
	 * Whether the user has send a email before.
	 * 
	 * @param params
	 * @return
	 */
	boolean isEmailAlreadySent(List<Object> params);

	/**
	 * When send a email, add the record into database.
	 * 
	 * @param params
	 */
	boolean addSendEmailRecordIntoDB(List<Object> params);

	/**
	 * When a email has already sent,update the record in database.
	 * 
	 * @param params
	 */
	boolean updateSendEmailRecordDB(String username, String email);

	/**
	 * When receive a reset password request. check the user name, token and
	 * valid time.
	 * 
	 * @param params
	 * @return
	 */
	boolean isResetPasswordRequestLeagal(List<Object> params);

	/**
	 * When reset password, delete the record to avoid reset again.
	 * 
	 * @param params
	 * @return
	 */
	boolean deleteSendEmailRecordIntoDB(List<Object> params);

	String getToken(String userName, String email);

	String getValidTime(String userName, String email, String token);

}
