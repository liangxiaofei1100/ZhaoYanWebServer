package com.zhaoyan.webserver.db;

public class DBData {

	public static class UserInfoTable {
		public static final String TABLE_NAME = "userinfo";
		/** ID */
		public static final String ID = "id";
		/** login user name */
		public static final String USER_NAME = "user_name";
		/** nickname */
		public static final String NICKNAME = "nickname";
		/** login password */
		public static final String PASSWORD = "password";
		/** email */
		public static final String EMAIL = "email";
		/** phone number */
		public static final String PHONE = "phone";
		/** gold coin */
		public static final String JIFEN = "jifen";

		public static String createTable() {
			String sql = "create table userinfo(id int auto_increment primary key,user_name varchar(30),nickname varchar(30),password varchar(30),email varchar(50),phone varchar(30),jifen int DEFAULT 0,UNIQUE KEY (user_name),UNIQUE KEY (email),UNIQUE KEY (phone));";
			return sql;
		}
	}
}
