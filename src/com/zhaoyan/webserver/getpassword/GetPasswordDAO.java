package com.zhaoyan.webserver.getpassword;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.search.StringTerm;

import com.zhaoyan.webserver.common.GetPasswordConfig;
import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.DBData.UserInfoTable;
import com.zhaoyan.webserver.db.GetPasswordTable;
import com.zhaoyan.webserver.userinfomanage.UserManager;

public class GetPasswordDAO implements GetPasswordService {
	private JDBCUtils mJdbcUtils;
	private UserManager mUserManager;

	public GetPasswordDAO() {
		mJdbcUtils = new JDBCUtils();
		mUserManager = new UserManager();
	}

	@Override
	public String getUserName(List<Object> params) {
		String userNameOrEmail = (String) params.get(0);
		return mUserManager.getUserName(userNameOrEmail);
	}

	@Override
	public int getUserId(List<Object> params) {
		String userName = (String) params.get(0);
		System.out.println("getUserId params: " + userName);
		Map<String, Object> map = mUserManager.getUserInfo(userName);
		System.out.println("getUserId userinfo id = "
				+ map.get(UserInfoTable.ID) + ", "
				+ map.get(UserInfoTable.USER_NAME) + ","
				+ map.get(UserInfoTable.EMAIL));

		int id = (int) map.get(UserInfoTable.ID);
		return id;
	}

	@Override
	public String getEmail(List<Object> params) {
		String userNameOrEmail = (String) params.get(0);
		Map<String, Object> userInfoMap = mUserManager
				.getUserInfo(userNameOrEmail);

		String email = "";
		if (!userInfoMap.isEmpty()) {
			email = (String) userInfoMap.get("email");
		}
		return email;
	}

	@Override
	public boolean isEmailAlreadySent(List<Object> params) {

		boolean result = false;
		String sql = "select * from " + GetPasswordTable.TABLE_NAME + " where "
				+ GetPasswordTable.USER_NAME + "=?";

		try {
			mJdbcUtils.getConnection();
			Map<String, Object> map = mJdbcUtils.findSingleResult(sql, params);
			result = !map.isEmpty();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;

	}

	@Override
	public boolean addSendEmailRecordIntoDB(List<Object> params) {
		boolean result = false;
		String validTime = "date_add(now(),interval "
				+ GetPasswordConfig.VALID_TIME + " day)";
		String sql = "insert into " + GetPasswordTable.TABLE_NAME + "("
				+ GetPasswordTable.USER_NAME + "," + GetPasswordTable.EMAIL
				+ "," + GetPasswordTable.TOKEN + ","
				+ GetPasswordTable.VALID_TIME + ") values (?,?,?," + validTime
				+ ")";
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
	public boolean isResetPasswordRequestLeagal(List<Object> params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteSendEmailRecordIntoDB(List<Object> params) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateSendEmailRecordDB(String username, String email) {
		boolean result = false;
		String validTime = "date_add(now(),interval "
				+ GetPasswordConfig.VALID_TIME + " day)";

		String sql = "update " + GetPasswordTable.TABLE_NAME + " set "
				+ GetPasswordTable.VALID_TIME + "=" + validTime + " where "
				+ GetPasswordTable.USER_NAME + "=? and "
				+ GetPasswordTable.EMAIL + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(username);
		params.add(email);

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
	public String getToken(String userName, String email) {
		String token = "";

		String sql = "select * from " + GetPasswordTable.TABLE_NAME + " where "
				+ GetPasswordTable.USER_NAME + "=? and "
				+ GetPasswordTable.EMAIL + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(userName);
		params.add(email);

		try {
			mJdbcUtils.getConnection();
			Map<String, Object> map = mJdbcUtils.findSingleResult(sql, params);
			if (!map.isEmpty()) {
				token = (String) map.get(GetPasswordTable.TOKEN);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return token;
	}

	@Override
	public String getValidTime(String userName, String email, String token) {
		String validTime = "";
		
		String sql = "select * from " + GetPasswordTable.TABLE_NAME + " where "
				+ GetPasswordTable.USER_NAME + "=? and "
				+ GetPasswordTable.EMAIL + "=? and " + GetPasswordTable.TOKEN + "=?";
		ArrayList<Object> params = new ArrayList<>();
		params.add(userName);
		params.add(email);
		params.add(token);

		try {
			mJdbcUtils.getConnection();
			Map<String, Object> map = mJdbcUtils.findSingleResult(sql, params);
			if (!map.isEmpty()) {
				Timestamp timestamp = (Timestamp) map.get(GetPasswordTable.VALID_TIME);
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				validTime = dateFormat.format(timestamp);
			} else {
				System.err.println("Can not find valid time! usernam = " + userName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		
		return validTime;
	}
}
