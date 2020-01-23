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
public class EventInfoTaskFive {
	
	@Autowired
	private EventUtil e;
	
    /**
	 * 每天中午2点20分获取是否亏损信息，并入库
	 */
    @Scheduled(cron = "0 10 15 * * ?")
    private void TaskThree() {
		e.getMoneyInfo();
    }
}
