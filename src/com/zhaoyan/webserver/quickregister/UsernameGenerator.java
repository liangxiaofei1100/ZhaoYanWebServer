package com.zhaoyan.webserver.quickregister;

import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.QuickRegisterTable;

public class UsernameGenerator {
	public static String generateUsername() {
		String username = "";

		JDBCUtils jdbcUtils = new JDBCUtils();
		String sql = "select * from " + QuickRegisterTable.TABLE_NAME;

		try {
			jdbcUtils.getConnection();
			Map<String, Object> map = jdbcUtils.findSingleResult(sql, null);
			username = String.valueOf(map.get(QuickRegisterTable.USER_NAME));
			// update username;
			sql = "update " + QuickRegisterTable.TABLE_NAME + " set "
					+ QuickRegisterTable.USER_NAME + "="
					+ QuickRegisterTable.USER_NAME + "+1";
			jdbcUtils.updateByPreparedStatement(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConnection();
		}
		return username;
	}
}
