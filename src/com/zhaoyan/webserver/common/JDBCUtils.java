package com.zhaoyan.webserver.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhaoyan.webserver.db.BaiduCloudSqlDatabase;

/**
 * Database operations.
 * 
 */
public class JDBCUtils {
	private BaiduCloudSqlDatabase mDatabase;
	private ResultSet mResultSet;
	private PreparedStatement mPreparedStatement;
	private Connection mConnection;

	public JDBCUtils() {
		mDatabase = new BaiduCloudSqlDatabase();
	}

	public Connection getConnection() {
		try {
			mConnection = mDatabase.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mConnection;
	}

	/**
	 * Get single result.
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public Map<String, Object> findSingleResult(String sql, List<Object> params)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		mPreparedStatement = getPreparedStatement(sql, params);

		mResultSet = mPreparedStatement.executeQuery();
		ResultSetMetaData metaData = mResultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		if (mResultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = mResultSet.getObject(i + 1);
				if (col_value == null) {
					col_value = "";
				}
				map.put(col_name, col_value);
			}
		}

		return map;
	}

	/**
	 * Get multiple result.
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> findMultiResult(String sql,
			List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		mPreparedStatement = getPreparedStatement(sql, params);

		mResultSet = mPreparedStatement.executeQuery();
		ResultSetMetaData metaData = mResultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (mResultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < col_len; i++) {
				String col_name = metaData.getColumnName(i + 1);
				Object col_value = mResultSet.getObject(i + 1);
				if (col_value == null) {
					col_value = "";
				}
				map.put(col_name, col_value);
			}
			list.add(map);
		}

		return list;
	}

	/**
	 * Update, insert, delete operations.
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public boolean updateByPreparedStatement(String sql, List<Object> params)
			throws SQLException {
		boolean isUpdateSuccess = false;
		mPreparedStatement = getPreparedStatement(sql, params);
		int result = mPreparedStatement.executeUpdate();
		isUpdateSuccess = result > 0 ? true : false;
		return isUpdateSuccess;
	}

	/**
	 * Execute SQL.
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public boolean executeSQL(String sql) throws SQLException {
		boolean result = false;
		Statement statement = mConnection.createStatement();
		result = statement.execute(sql);
		return result;
	}

	private PreparedStatement getPreparedStatement(String sql,
			List<Object> params) throws SQLException {
		PreparedStatement preparedStatement = mConnection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				// PreparedStatement index is from 1 to n.
				preparedStatement.setObject(i + 1, params.get(i));
			}
		}
		return preparedStatement;
	}

	public void releaseConnection() {
		if (mResultSet != null) {
			try {
				mResultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (mPreparedStatement != null) {
			try {
				mPreparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (mConnection != null) {
			try {
				mConnection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
