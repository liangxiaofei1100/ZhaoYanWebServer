package com.zhaoyan.webserver.friend;

import java.util.ArrayList;
import java.util.List;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.FriendTable;

public class AddFriendDao implements AddFriendService {
	private JDBCUtils mJdbcUtils;

	public AddFriendDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public boolean addFriend(int userId, int friendId) {
		boolean result = false;
		mJdbcUtils.getConnection();

		String sql = "insert into " + FriendTable.getTableName(userId) + "("
				+ FriendTable.FRIEND_ID + ") values (?)";
		List<Object> params = new ArrayList<>();
		params.add(friendId);

		try {
			// Create table if table is not exist.
			mJdbcUtils.executeSQL(FriendTable.createTable(userId));
			result = mJdbcUtils.updateByPreparedStatement(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return result;
	}

}
