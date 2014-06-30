package com.zhaoyan.webserver.common;

import java.util.Random;

public class GetPasswordConfig {
	public static final String SEND_MAIL_HOST = "smtp.163.com";
	public static final String SEND_MAIL_USERNAME = "zhaoyantech@163.com";
	public static final String SEND_MAIL_PASSWORD = "zhaoyantech123";

	private static final int TOKEN_LENGTH = 20;

	/**
	 * Get password valid time from email sent. (unit: day).
	 */
	public static final int VALID_TIME = 1;

	public static String getEmailContent(String userName, String url,
			String validTime) {
		StringBuffer sb = new StringBuffer();

		sb.append("亲爱的用户 " + userName + "：您好！<br><br>");
		sb.append("        您收到这封这封电子邮件是因为您 (也可能是某人冒充您的名义) 申请了一个新的密码。假如这不是您本人所申请, 请不用理会<br>这封电子邮件, 但是如果您持续收到这类的信件骚扰, 请您尽快联络管理员。<br><br>");
		sb.append("        要使用新的密码, 请在 " + validTime + " 之前使用以下链接启用密码。<br><br>");
		sb.append("        <a href='" + url + "'>" + url + "</a>");
		sb.append("<br><br>我们将一如既往、热忱的为您服务！");

		return sb.toString();
	}

	public static String getEmailSubject() {

		return "密码找回";
	}

	public static String generateToken() {
		return getRandomString(TOKEN_LENGTH);
	}

	private static String getRandomString(int length) {
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
}
