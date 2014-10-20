package com.zhaoyan.webserver.chengyudahui.gold;

import java.util.ArrayList;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class GoldDao implements GoldService {
	private JDBCUtils mJdbcUtils;

	public GoldDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean addGold(String username, String gold) {
		boolean result = false;
		String sql = "update " + UserInfoTable.TABLE_NAME + " set "
				+ UserInfoTable.JIFEN + "=" + UserInfoTable.JIFEN + "+? where "
				+ UserInfoTable.USER_NAME + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(gold);
		params.add(username);

		try {
			mJdbcUtils.getConnection();
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;
	}

	@Override
	public boolean subGold(String username, String gold) {
		boolean result = false;
		String sql = "update " + UserInfoTable.TABLE_NAME + " set "
				+ UserInfoTable.JIFEN + "=" + UserInfoTable.JIFEN + "-? where "
				+ UserInfoTable.USER_NAME + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(gold);
		params.add(username);

		try {
			mJdbcUtils.getConnection();
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;
	}

	@Override
	public boolean setGold(String username, String gold) {
		boolean result = false;
		String sql = "update " + UserInfoTable.TABLE_NAME + " set "
				+ UserInfoTable.JIFEN + "=? where " + UserInfoTable.USER_NAME
				+ "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(gold);
		params.add(username);

		try {
			mJdbcUtils.getConnection();
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;
	}
}
