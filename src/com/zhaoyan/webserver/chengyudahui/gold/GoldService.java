package com.zhaoyan.webserver.chengyudahui.gold;

public interface GoldService {

	boolean addGold(String username, String gold);

	boolean subGold(String username, String gold);

	boolean setGold(String username, String gold);

}
