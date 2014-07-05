package com.zhaoyan.webserver.db;

public class QuickRegisterTable {
	public static final String TABLE_NAME = "quick_register";
	public static final String USER_NAME = "user_name";

	public static String createTable() {
		String sql = "CREATE TABLE quick_register(user_name INT PRIMARY KEY); insert into quick_register(user_name) values(100000);";
		return sql;
	}
}