package com.zhaoyan.webserver.apps;


public class AppInfo{
	private static final String TAG = AppInfo.class.getSimpleName();
	
    private int app_id;//标记该应用的唯一标识符
    private String app_label;//应用名称
    private int author_id;//作者id，暂时不知道怎么用
    private String author;//应用作者
    private String update_time;//应用上传或者更新时间
    private String app_language;//应用语言
    private String app_version;//应用版本号
    private String introduce ;//详细介绍
    private String title;//简介
    private String goldInfos;//获取金币相关信息
    private long size;
    
    private String app_url; //app下载地址
    private String icon_url;// app icon 地址
    //暂定显示两张app界面截图介绍
    private String jiemian_url1;//app 界面截图介绍地址1
    private String jiemian_url2;//app 界面截图介绍地址2
    
    private String app_type;//应用分类：如社交，游戏（卡牌，回合等等），购物等等
    
    private String packageName;//应用包名
    
    public AppInfo(){
    	
    }
    
    public AppInfo(int app_id,String app_label,
    		int author_id,String update_time,
    		String app_language,String app_version,
    		String introduce,String title, String goldInfos,
    		long size,String author,
    		String app_url, String icon_url,
    		String jiemian_url1, String jiemian_url2,
    		String app_type, String packageName
    		){
    	this.app_id = app_id ;
    	this.app_label = app_label;
    	this.author_id = author_id;
    	this.author = author;
    	this.introduce = introduce;
    	this.title = title;
    	this.goldInfos = goldInfos;
    	this.size = size;
    	this.app_version = app_version;
    	this.app_language = app_language;
    	this.update_time = update_time;
    	
    	this.app_url = app_url;
    	this.icon_url = icon_url;
    	this.jiemian_url1 = jiemian_url1;
    	this.jiemian_url2 = jiemian_url2;
    	
    	this.app_type = app_type;
    	this.packageName = packageName;
    }
    
    public void setAppId(int appId){
    	this.app_id = appId;
    }
    
    public int getAppId(){
    	return app_id;
    }
    
    public void setSize(long size){
    	this.size = size;
    }
    
    public long getSize(){
    	return size;
    }
    
    public void setLabel(String label){
    	this.app_label = label;
    }
    
    public String getLabel(){
    	return app_label;
    }
    
    public void setAuthorId(int authorId){
    	this.author_id = authorId;
    }
    
    public int getAuthorId(){
    	return author_id;
    }
    
    public void setDate(String updateTime){
    	this.update_time = updateTime;
    }
    
    public String getDate(){
    	return update_time;
    }
    
    public void setAppLanguage(String language){
    	this.app_language = language;
    }
    
    public String getAppLanguage(){
    	return app_language;
    }
    
    public void setAppVersion(String version){
    	this.app_version = version;
    }
    
    public String getAppVersion(){
    	return app_version;
    }
    
    public void setIntroduce(String introduce){
    	this.introduce = introduce;
    }
    
    public String getIntroduce(){
    	return introduce;
    }
    
    public void setAuthor(String author){
    	this.author = author;
    }
    
    public String getAuthor(){
    	return author;
    }
    
    public void setTitle(String title){
    	this.title = title;
    }
    
	public String getTitle() {
		return title;
	}
	
	public void setNotes(String goldInfos){
    	this.goldInfos = goldInfos;
    }
    
	public String getNotes() {
		return goldInfos;
	}
	
	public void setAppUrl(String appUrl){
		this.app_url = appUrl;
	}
	
	public String getAppUrl() {
		return app_url;
	}
	
	public void setIconUrl(String iconUrl){
		this.icon_url = iconUrl;
	}
	
	public String getIconUrl() {
		return icon_url;
	}
	
	public void setJiemianUrl1(String jiemianUrl1){
		this.jiemian_url1 = jiemianUrl1;
	}
	
	public String getJiemianUrl1() {
		return jiemian_url1;
	}
	
	public void setJiemianUrl2(String jiemianUrl2){
		this.jiemian_url2 = jiemianUrl2;
	}
	
	public String getJiemianUrl2() {
		return jiemian_url2;
	}
	
	public void setAppType(String appType){
		this.app_type = appType;
	}
	
	public String getAppType() {
		return app_type;
	}
	
	public void setPackageName(String packageName){
		this.packageName = packageName;
	}
	
	public String getPackageName(){
		return packageName;
	}
    
	public String toString(){
		String info = "AppId:" + app_id + "\n"
					+"App_Label:" + app_label + "\n"
					+ "Author_id:" + author_id + "\n"
					+ "Author:" + author + "\n"
					+ "Update_time:" + update_time + "\n"
					+ "App_language:" + app_language + "\n"
					+ "App_version:" + app_version + "\n"
					+ "Introduce:" + introduce + "\n"
					+ "Title:" + title + "\n"
					+ "GoldInfos:" + goldInfos + "\n"
					+ "Size:" + size + "\n"
					+ "App_url:" + app_url + "\n"
					+ "Icon_url:" + icon_url + "\n"
					+ "Jiemian_url1:" + jiemian_url1 + "\n"
					+ "Jiemian_url2:" + jiemian_url2 + "\n"
					+ "App_type:" + app_type + "\n"
					+ "PackageName:" + packageName;
		return info;
	}
}
