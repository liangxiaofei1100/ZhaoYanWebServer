package com.zhaoyan.webserver.friend;

import java.util.List;

public class SearchForFriendDao implements SearchForFriendService {

	@Override
	public Friend searchFriendById(int friendId) {
		Friend friend = FriendUtil.getFriendInfos(friendId);
		return friend;
	}

	@Override
	public List<Friend> searchFriendByName(String name) {
		List<Friend> friends = FriendUtil.getFriendInfos(name);
		return friends;
	}
}
