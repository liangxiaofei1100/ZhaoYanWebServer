package com.zhaoyan.webserver.userinfomanage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;

public class GetUserInfoDAO implements GetUserInfoService {
	private JDBCUtils mJdbcUtils;

	public GetUserInfoDAO() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public Map<String, Object> getUserInfo(List<Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();

		String sql = "select * from " + UserInfoTable.TABLE_NAME + " where "
				+ UserInfoTable.USER_NAME + "=? or " + UserInfoTable.EMAIL
				+ "=?";
		try {
			mJdbcUtils.getConnection();
			map = mJdbcUtils.findSingleResult(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return map;
	}

}
