package com.rbgt.obtain.task;

import com.rbgt.obtain.util.AllEventUtil;
import com.rbgt.obtain.util.EventUtil;
import com.rbgt.obtain.util.HtmlUtil;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class AllEventInfoTaskYesterday {
	
	@Autowired
	private AllEventUtil allEventUtil;
	
	@Autowired
	private EventUtil e;
	
	@Autowired
	private HtmlUtil htmlUtil;
	
	/**
	 * 每天凌晨15点20分获取昨天的比赛并入库
	 */
	//*/5 * * * * ?
    //@Scheduled(cron = "0 0 3 * * ?")
    private void TaskAllEventInfo() {
    	
    	String url = "http://score.nowscore.com/basketball.htm?date=";
    	String time = EventUtil.getPD(0);
    	url = url +time;
    	//昨天比赛结果
    	Document allEventInfo = HtmlUtil.getAllEventInfo(url);
    	htmlUtil.saveEventnfoBS(allEventInfo,time);
    	
    	String url2 = "http://score.nowscore.com/basketball.htm?date=";
    	String time2 = EventUtil.getPD(3);
    	url2 = url2 +time2;
    	
    	//前天比赛结果
    	Document EventInfo = HtmlUtil.getAllEventInfo(url2);
    	htmlUtil.saveEventnfoBS(EventInfo,time2);
    	
    }
}
