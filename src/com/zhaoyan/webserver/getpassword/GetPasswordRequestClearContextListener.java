package com.zhaoyan.webserver.getpassword;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.zhaoyan.webserver.common.JDBCUtils;
import com.zhaoyan.webserver.common.Logger;
import com.zhaoyan.webserver.db.GetPasswordTable;

public class GetPasswordRequestClearContextListener implements
		ServletContextListener {
	private Timer mTimer;
	private static final long TIMER_PERIOD = 24 * 60 * 60 * 1000;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		mTimer.cancel();
		Logger.log("GetPasswordRequestClear timer canceled.");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		mTimer = new Timer(true);
		Logger.log("GetPasswordRequestClear timer started.");

		// 00：00：00 execute task every day.
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.add(Calendar.DAY_OF_YEAR, 1);

		mTimer.schedule(new ClearTask(), calendar.getTime(), TIMER_PERIOD);
		Logger.log("GetPasswordRequestClear timer scheduled.");
	}

	static class ClearTask extends TimerTask {

		@Override
		public void run() {
			Logger.log("GetPasswordRequestClear execute clear.");
			JDBCUtils jdbcUtils = new JDBCUtils();
			String sql = "delete from " + GetPasswordTable.TABLE_NAME
					+ " where " + GetPasswordTable.VALID_TIME + "<now()";

			try {
				jdbcUtils.getConnection();
				jdbcUtils.updateByPreparedStatement(sql, null);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				jdbcUtils.releaseConnection();
			}
		}

	}

}
