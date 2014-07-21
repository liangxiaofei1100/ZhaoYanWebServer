package com.zhaoyan.webserver.db;

public class AppInfoTable {
	public static final String TABLE_NAME = "appinfo";
	/**
	 * 该表的主键，自增长 type:int
	 */
	public static final String ID = "id";

	/**
	 * 标记该应用的唯一标识符 type:int
	 */
	public static final String APP_ID = "appid";
	/**
	 * 应用名称 type:Text
	 */
	public static final String APP_LABEL = "label";
	/**
	 * 作者id,备用 type:int
	 */
	public static final String AUTHOR_ID = "author_id";
	/**
	 * 应用作者，个人称呼或者公司名称等等 type:Text
	 */
	public static final String AUTHOR = "author";
	/**
	 * 应用上传或者更新日期，如：2014-07-02 type:Text
	 */
	public static final String DATE = "date";
	/**
	 * 应用语言,如：简体中文/英文等等 type:Text
	 */
	public static final String APP_LANGUAGE = "language";
	/**
	 * 应用版本号，如：2.13.2 type:Text
	 */
	public static final String APP_VERSION = "version";
	/**
	 * 应用详细介绍 type:Text
	 */
	public static final String INTRODUCE = "introduce";
	/**
	 * 应用简介 type:Text
	 */
	public static final String TITLE = "title";
	/**
	 * 额外备注，比如：下载或者打开该应用获取积分介绍 type:Text
	 */
	public static final String NOTES = "notes";
	/**
	 * 应用大小，如：13928878 type:Double
	 */
	public static final String SIZE = "size";

	/**
	 * app文件下载地址 type:Text
	 */
	public static final String APP_URL = "app_url";
	/**
	 * app icon 地址 type:Text
	 */
	public static final String ICON_URL = "icon_url";
	// 暂定显示两张app界面截图介绍
	/**
	 * app 界面截图介绍地址1 type:Text
	 */
	public static final String JIEMIAN_URL1 = "jiemian_url1";
	/**
	 * app 界面截图介绍地址2 type:Text
	 */
	public static final String JIEMIAN_URL2 = "jiemian_url2";
	/**
	 * 应用分类：如社交，游戏（卡牌，回合等等），购物等等 type:Text
	 */
	public static final String APP_TYPE = "type";
	/**
	 * 应用包名 type:Text
	 */
	public static final String PACKAGE_NAME = "package";

	public String createTable() {
		String sql = "create table appinfo(id int auto_increment primary key,appid int,label text,author_id int,author text,date text,language text,version text,introduce text,title text,notes longtext,size double,app_url text,icon_url text,jiemian_url1 text,jiemian_url2 text,type text,package text);";
		return sql;
	}
}
