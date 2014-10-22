package com.zhaoyan.webserver.db;

public class FriendTable {
	/** ID */
	public static final String ID = "id";
	/** friend user id */
	public static final String FRIEND_ID = "friend_id";

	public static String getTableName(int userId) {
		return "friend_" + userId;
	}

	public static String createTable(int userId) {
		String sql = "create table if not exists " + getTableName(userId)
				+ "(id int auto_increment primary key,friend_id int);";
		return sql;
	}

}
