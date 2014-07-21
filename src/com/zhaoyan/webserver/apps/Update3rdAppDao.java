package com.zhaoyan.webserver.apps;

import java.util.ArrayList;
import java.util.List;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.common.Logger;
import com.zhaoyan.webserver.db.AppInfoTable;

public class Update3rdAppDao implements Update3rdAppService {
	private JDBCUtils mJdbcUtils;

	public Update3rdAppDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean update3rdApp(List<AppInfo> appInfos) {
		boolean result = false;

		try {
			mJdbcUtils.getConnection();
			// Delete all.
			String deleteAllSQL = "delete from " + AppInfoTable.TABLE_NAME;
			mJdbcUtils.updateByPreparedStatement(
					deleteAllSQL, null);
			// Insert all.
			for (AppInfo appInfo : appInfos) {
				List<Object> params = new ArrayList<>();
				params.add(appInfo.getAppId());
				params.add(appInfo.getLabel());
				params.add(appInfo.getAuthorId());
				params.add(appInfo.getAuthor());
				params.add(appInfo.getDate());
				params.add(appInfo.getAppLanguage());
				params.add(appInfo.getAppVersion());
				params.add(appInfo.getIntroduce());
				params.add(appInfo.getTitle());
				params.add(appInfo.getNotes());
				params.add(appInfo.getSize());
				params.add(appInfo.getAppUrl());
				params.add(appInfo.getIconUrl());
				params.add(appInfo.getJiemianUrl1());
				params.add(appInfo.getJiemianUrl2());
				params.add(appInfo.getAppType());
				params.add(appInfo.getPackageName());

				String addAppInfoSQL = "insert into " + AppInfoTable.TABLE_NAME
						+ "(" + AppInfoTable.APP_ID + ","
						+ AppInfoTable.APP_LABEL + "," + AppInfoTable.AUTHOR_ID
						+ "," + AppInfoTable.AUTHOR + "," + AppInfoTable.DATE
						+ "," + AppInfoTable.APP_LANGUAGE + ","
						+ AppInfoTable.APP_VERSION + ","
						+ AppInfoTable.INTRODUCE + "," + AppInfoTable.TITLE
						+ "," + AppInfoTable.NOTES + "," + AppInfoTable.SIZE
						+ "," + AppInfoTable.APP_URL + ","
						+ AppInfoTable.ICON_URL + ","
						+ AppInfoTable.JIEMIAN_URL1 + ","
						+ AppInfoTable.JIEMIAN_URL2 + ","
						+ AppInfoTable.APP_TYPE + ","
						+ AppInfoTable.PACKAGE_NAME
						+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				boolean addResult = mJdbcUtils.updateByPreparedStatement(
						addAppInfoSQL, params);
				if (!addResult) {
					Logger.loge("insert fail. apptitle" + appInfo.getTitle());
					return result;
				}
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}

		return result;
	}
}
