package com.zhaoyan.webserver.apps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.AppInfoTable;

public class Get3rdAppInfoDao implements Get3rdAppInfoService {
	private JDBCUtils mJdbcUtils;

	public Get3rdAppInfoDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public List<Map<String, Object>> getAll3rdAppInfo() {
		List<Map<String, Object>> appInfos = new ArrayList<Map<String, Object>>();

		String sql = "select * from " + AppInfoTable.TABLE_NAME;
		try {
			mJdbcUtils.getConnection();
			appInfos = mJdbcUtils.findMultiResult(sql, null);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return appInfos;
	}

}
