package com.rbgt.obtain.controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rbgt.obtain.entity.*;
import com.rbgt.obtain.mapper.NbaTeventInfoMapper;
import com.rbgt.obtain.mapper.NbaTmoneyInfoMapper;
import com.rbgt.obtain.mapper.NbaTquizInfoCopyMapper;
import com.rbgt.obtain.mapper.NbaTquizInfoMapper;
import com.rbgt.obtain.util.AllEventUtil;
import com.rbgt.obtain.util.EventUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("nba")
public class TestController {

	@Autowired
	private EventUtil e;
	
	@Autowired
	private NbaTquizInfoMapper nbaTquizInfoMapper;
	
	@Autowired
	private NbaTmoneyInfoMapper nbaTmoneyInfoMapper;
	
	@Autowired
	private NbaTeventInfoMapper nbaTeventInfoMapper;
	
	@Autowired
	private AllEventUtil allEventUtil;
	
	@Autowired
	private NbaTquizInfoCopyMapper nbaTquizInfoCopyMapper;
	
	/**
	 * 手动获取明天赛事结果
	 * @return
	 */
	@GetMapping("/getAllInfo")
	public String AllEventInfoTomorrow()
	{
		String createTime = EventUtil.getPD(1);
    	
		List<NbaTquizInfoCopy> list = nbaTquizInfoCopyMapper.selectByCreateTime(createTime);
		
		if(list.size() > 0)
		{
			System.out.println("查询条数：" + list.size());
			return "手动获取成功";
		}
		
    	String url1 = "http://score.nowscore.com/basketball.htm?date=";
    	String time = EventUtil.getPD(1);
    	url1 = url1 +time;
    	allEventUtil.getCbaQuizInfoAll(url1);
    	
    	String url = "http://score.nowscore.com/basketball.htm";
    	allEventUtil.getCbaQuizInfoAll(url);
		return "手动获取成功";
	}
	/**
	 * 获取昨天推荐结果
	 * @return
	 */
	@GetMapping("/getCba2")
	public List<NbaTquizInfo> getCba1()
	{
		String rounds = getLunci("https://cba.hupu.com/gamespace/");
		
		String str = rounds;
		str=str.trim();
		String str2="";
		if(str != null && !"".equals(str)){
		for(int i=0;i<str.length();i++){
		if(str.charAt(i)>=48 && str.charAt(i)<=57){
			str2+=str.charAt(i);
			}
		 
		}
		}
		
		int lun = Integer.parseInt(str2)-1;
		
		String lunci = "lun="+lun;
		
		String rounds1 = getLunci("https://cba.hupu.com/gamespace/?"+lunci);
		
		NbaTquizInfo q = new NbaTquizInfo();
		q.setQuizName("<"+rounds1+">%");
		return nbaTquizInfoMapper.selectByNameLink(q);
	}
	
	/**
	 * 获取推荐结果
	 * @return
	 */
	@GetMapping("/getCba1")
	public EventData<Event> getCba2()
	{
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);// 新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

		webClient.getOptions().setThrowExceptionOnScriptError(false);// 当JS执行出错的时候是否抛出异常,
																		// 这里选择不需要
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);// 当HTTP的状态非200时是否抛出异常,
																			// 这里选择不需要
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(false);// 是否启用CSS, 因为不需要展现页面,
													// 所以不需要启用
		webClient.getOptions().setJavaScriptEnabled(true); // 很重要，启用JS
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 很重要，设置支持AJAX

		HtmlPage page = null;
		try {
			// page =
			// webClient.getPage("https://cba.hupu.com/gamespace/?lun=14");//尝试加载上面图片例子给出的网页
			page = webClient.getPage("https://cba.hupu.com/gamespace/");// 尝试加载上面图片例子给出的网页
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webClient.close();
		}

		webClient.waitForBackgroundJavaScript(5000);// 异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

		String pageXml = page.asXml();// 直接将加载完成的页面转换成xml格式的字符串

		Document document = Jsoup.parse(pageXml);// 获取html文档

		String rounds = document.getElementsByClass("days_c").text();
		
		NbaTquizInfo q = new NbaTquizInfo();
		q.setQuizName("<"+rounds+">%");
		
		List<NbaTquizInfo> selectByNameLink = nbaTquizInfoMapper.selectByNameLink(q);
		
		EventData<Event> map = new EventData<Event>();
		List<Event> list = new ArrayList<Event>();
		
		int len = selectByNameLink.size();
		if(len > 0)
		{
			for(int i=0;i<len;i++ )
			{
				Event e = new Event();
				e.setEventName(selectByNameLink.get(i).getQuizName());
				e.setEventQuiz(selectByNameLink.get(i).getQuizEnvision());
				e.setEventResults(selectByNameLink.get(i).getQuizResults());
				list.add(e);
			}
			map.setData(list);
			map.setMsg(rounds+",CBA赛事推荐信息");
			map.setNote("如有合作，请加QQ：414840501");
		}
		
		
		return map;
	}
	
	/**
	 * 获取今天比赛结果及推荐情况
	 * @return
	 */
	@GetMapping("/get1")
	public EventData<Event> get1()
	{
		NbaTquizInfo q = new NbaTquizInfo();
		q.setQuizCreateTime(EventUtil.getPD(2));
		List<NbaTquizInfo> selectByTime = nbaTquizInfoMapper.selectByTime(q);
		
		EventData<Event> map = new EventData<Event>();
		List<Event> list = new ArrayList<Event>();
		
		int len = selectByTime.size();
		if(len > 0)
		{
			for(int i=0;i<len;i++ )
			{
				Event e = new Event();
				e.setEventName(selectByTime.get(i).getQuizName());
				e.setEventQuiz(selectByTime.get(i).getQuizEnvision());
				e.setEventResults(selectByTime.get(i).getQuizResults());
				list.add(e);
			}
			map.setData(list);
			map.setMsg(EventUtil.getPD(2)+"，NBA赛事推荐信息");
			map.setNote("如有合作，请加QQ：414840501");
		}
		
		
		return map;
	}
	
	/**
	 * 获取昨天比赛结果及推荐情况
	 * @return
	 */
	@GetMapping("/get2")
	public List<NbaTquizInfo> get2()
	{
		NbaTquizInfo q = new NbaTquizInfo();
		q.setQuizCreateTime(EventUtil.getPD(0));
		List<NbaTquizInfo> selectByTime = nbaTquizInfoMapper.selectByTime(q);
		return selectByTime;
	}
	
	/**
	 * 手动获取今天比赛结果及推荐情况
	 * @return
	 */
	@GetMapping("/rbgt/get1")
	public String getRbgt1()
	{
		e.getEventInfoContrast();
		return "刷新对比推荐结果成功，请查询：http://47.106.143.218:8082/nba/get1";
	}
	
	/**
	 * 手动获取今天比赛比分结果
	 * @return
	 */
	@GetMapping("/rbgt/get2")
	public String getRbgt2()
	{
		String url = "https://nba.hupu.com/games";
    	String time = EventUtil.getPD(2);
    	url = url +"/"+time;
		e.getEventInfoSaveNbaTeventInfo(url);
		return "刷新当天比分结果成功！";
	}
	
	/**
	 * 手动获取今天比赛比分结果
	 * @return
	 */
	@GetMapping("/rbgt/get3")
	public String getRbgt3()
	{
		e.getMoneyInfo();
		return "手动获取当天金额成功";
	}
	
	private String getLunci(String url)
	{
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);// 新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

		webClient.getOptions().setThrowExceptionOnScriptError(false);// 当JS执行出错的时候是否抛出异常,
																		// 这里选择不需要
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);// 当HTTP的状态非200时是否抛出异常,
																			// 这里选择不需要
		webClient.getOptions().setActiveXNative(false);
		webClient.getOptions().setCssEnabled(false);// 是否启用CSS, 因为不需要展现页面,
													// 所以不需要启用
		webClient.getOptions().setJavaScriptEnabled(true); // 很重要，启用JS
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());// 很重要，设置支持AJAX

		HtmlPage page = null;
		try {
			// page =
			// webClient.getPage("https://cba.hupu.com/gamespace/?lun=14");//尝试加载上面图片例子给出的网页
			page = webClient.getPage(url);// 尝试加载上面图片例子给出的网页
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webClient.close();
		}

		webClient.waitForBackgroundJavaScript(5000);// 异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

		String pageXml = page.asXml();// 直接将加载完成的页面转换成xml格式的字符串

		Document document = Jsoup.parse(pageXml);// 获取html文档

		String rounds = document.getElementsByClass("days_c").text();
		return rounds;
	}
}
