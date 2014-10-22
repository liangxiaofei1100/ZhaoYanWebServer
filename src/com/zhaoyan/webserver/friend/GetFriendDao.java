package com.zhaoyan.webserver.friend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.db.FriendTable;

public class GetFriendDao implements GetFriendService {
	private JDBCUtils mJdbcUtils;

	public GetFriendDao() {
		mJdbcUtils = new JDBCUtils();
	}

	@Override
	public List<Friend> getFriend(int userId) {
		List<Friend> friends = new ArrayList<>();
		mJdbcUtils.getConnection();

		String sql = "select " + FriendTable.FRIEND_ID + " from "
				+ FriendTable.getTableName(userId);
		try {
			List<Map<String, Object>> results = mJdbcUtils.findMultiResult(sql,
					null);
			for (Map<String, Object> map : results) {
				int friendId = Integer.valueOf((String) map
						.get(FriendTable.FRIEND_ID));
				Friend friend = FriendUtil.getFriendInfos(friendId);
				if (friend != null) {
					friends.add(friend);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mJdbcUtils.releaseConnection();
		}
		return friends;
	}
}
