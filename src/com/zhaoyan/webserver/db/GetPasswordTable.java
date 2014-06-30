package com.zhaoyan.webserver.db;

public class GetPasswordTable {
	public static final String TABLE_NAME = "get_password";
	/** ID */
	public static final String ID = "id";
	/** user name */
	public static final String USER_NAME = "user_name";
	/** email */
	public static final String EMAIL = "email";
	/** get password token */
	public static final String TOKEN = "token";
	/** valid time */
	public static final String VALID_TIME = "valid_time";

	public static String createTable() {
		String sql = "create table get_password(id int auto_increment primary key,user_name varchar(30),email varchar(50),token varchar(20),valid_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP);";
		return sql;
	}
}
