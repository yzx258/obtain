package com.rbgt.obtain.task;

import com.rbgt.obtain.entity.NbaTquizInfoCopy;
import com.rbgt.obtain.mapper.NbaTquizInfoCopyMapper;
import com.rbgt.obtain.util.AllEventUtil;
import com.rbgt.obtain.util.EventUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class AllEventInfoTaskTomorrow {
	
	@Autowired
	private AllEventUtil allEventUtil;
	
	@Autowired
	private EventUtil e;
	
	@Autowired
	private NbaTquizInfoCopyMapper nbaTquizInfoCopyMapper;
	
	/**
	 * 每天凌晨14点20分获取赛事信息，并推荐
	 */
	//*/5 * * * * ? 0 0 23 * * ?
    @Scheduled(cron = "0 0 23 * * ?")
    private void TaskAllEventInfo() {
    	
    	String createTime = EventUtil.getPD(1);
    	
		List<NbaTquizInfoCopy> list = nbaTquizInfoCopyMapper.selectByCreateTime(createTime);
		
		if(list.size() > 0)
		{
			System.out.println("查询条数：" + list.size());
			return;
		}
		
    	String url1 = "http://score.nowscore.com/basketball.htm?date=";
    	String time = EventUtil.getPD(1);
    	url1 = url1 +time;
    	allEventUtil.getCbaQuizInfoAll(url1);
    	
    }
}
