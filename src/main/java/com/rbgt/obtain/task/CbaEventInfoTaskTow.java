package com.rbgt.obtain.task;

import com.rbgt.obtain.util.CbaEventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class CbaEventInfoTaskTow {
	
	@Autowired
	private CbaEventUtil cbaEventUtil;
	
    /**
	 * 每天中午2点获取推荐和比赛的结果判断红黑，并入库
	 */
    @Scheduled(cron = "0 30 23 * * ?")
    private void TaskThree() {
    	cbaEventUtil.getEventInfoSaveNbaTeventInfo("https://cba.hupu.com/gamespace/");
    }
}
