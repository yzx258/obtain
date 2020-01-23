package com.rbgt.obtain.task;

import com.rbgt.obtain.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class EventInfoTaskOne {
	
	@Autowired
	private EventUtil e;
	
	/**
	 * 每天凌晨12点20分推荐比赛单双
	 */
	//*/5 * * * * ?
    @Scheduled(cron = "0 20 0 * * ?")
    private void TaskOne() {
    	String url = "https://nba.hupu.com/games";
		e.getEventInfoNbaTquizInfo(url);
    }
}
