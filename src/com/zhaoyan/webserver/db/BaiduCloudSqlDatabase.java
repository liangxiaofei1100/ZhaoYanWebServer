package com.zhaoyan.webserver.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zhaoyan.webserver.common.BaiduCloudConfig;

public class BaiduCloudSqlDatabase {
	String DRIVER_NAME = "com.mysql.jdbc.Driver";

	public BaiduCloudSqlDatabase() {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		Connection connection = null;

		String databaseName = BaiduCloudConfig.MYSQLNAME;
		String host = BaiduCloudConfig.MYSQLHOST;
		String port = BaiduCloudConfig.MYSQLPORT;
		String username = BaiduCloudConfig.USER;
		String password = BaiduCloudConfig.PWD;

		String dbUrl = "jdbc:mysql://";
		String serverName = host + ":" + port + "/";
		String connName = dbUrl + serverName + databaseName;

		connection = DriverManager.getConnection(connName, username, password);
		return connection;
	}
}
