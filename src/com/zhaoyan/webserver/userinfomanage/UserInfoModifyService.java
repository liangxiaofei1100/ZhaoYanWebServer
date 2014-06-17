package com.zhaoyan.webserver.userinfomanage;

import java.util.List;

public interface UserInfoModifyService {

	boolean modifyPassword(List<Object> params);

	boolean modifyBasicInfo(List<Object> params);
}
