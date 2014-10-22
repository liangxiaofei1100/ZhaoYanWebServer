package com.zhaoyan.webserver.friend;

import java.util.List;

public interface SearchForFriendService {

	Friend searchFriendById(int friendId);

	List<Friend> searchFriendByName(String name);
}
