package com.rbgt.obtain.controller;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rbgt.obtain.entity.Event;
import com.rbgt.obtain.entity.EventData;
import com.rbgt.obtain.entity.NbaTquizInfo;
import com.rbgt.obtain.entity.NbaTquizInfoCopy;
import com.rbgt.obtain.mapper.NbaTeventInfoMapper;
import com.rbgt.obtain.mapper.NbaTmoneyInfoMapper;
import com.rbgt.obtain.mapper.NbaTquizInfoCopyMapper;
import com.rbgt.obtain.mapper.NbaTquizInfoMapper;
import com.rbgt.obtain.util.CbaEventUtil;
import com.rbgt.obtain.util.EventUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Administrator
 */
@Controller
@RequestMapping("rbgt/servlet")
public class ServletController {

	@Autowired
	private EventUtil e;
	
	@Autowired
	private NbaTquizInfoMapper nbaTquizInfoMapper;
	
	@Autowired
	private NbaTquizInfoCopyMapper nbaTquizInfoCopyMapper;
	
	@Autowired
	private NbaTmoneyInfoMapper nbaTmoneyInfoMapper;
	
	@Autowired
	private NbaTeventInfoMapper nbaTeventInfoMapper;
	
	@Autowired
	private CbaEventUtil cbaEventUtil;


	@RequestMapping("/getAllInfo")
	public ModelAndView getAllInfo()
	{
		ModelAndView mav = new ModelAndView();
		
		List<NbaTquizInfoCopy> selectByCreateTime = nbaTquizInfoCopyMapper.selectByCreateTime(EventUtil.getPD(2));
		
		EventData<Event> map = new EventData<Event>();
		List<Event> list = new ArrayList<Event>();
		
		int len = selectByCreateTime.size();
		if(len > 0)
		{
			for(int i=0;i<len;i++ )
			{
				Event e = new Event();
				e.setEventName(selectByCreateTime.get(i).getQuizName());
				e.setEventQuiz(selectByCreateTime.get(i).getQuizEnvision());
				e.setEventResults(selectByCreateTime.get(i).getQuizResults());
				list.add(e);
			}
			map.setData(list);
			map.setMsg(EventUtil.getPD(2)+"，其他赛事推荐信息");
			map.setNote("如有合作，请加QQ：414840501");
		}else
		{
			map.setMsg(EventUtil.getPD(2)+"，其他赛事暂未推荐信息，敬请期待！");
			map.setNote("如有合作，请加QQ：414840501");
		}
		
		mav.setViewName("enevtInfo");
		mav.addObject("list", map);
		return mav;
	}
	
	@RequestMapping("/getNbaInfo")
	public ModelAndView getInfo()
	{
		ModelAndView mav = new ModelAndView();
		
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
		}else
		{
			map.setMsg(EventUtil.getPD(2)+"，NBA赛事暂未推荐信息，敬请期待！");
			map.setNote("如有合作，请加QQ：414840501");
		}
		
		mav.setViewName("enevtInfo");
		mav.addObject("list", map);
		return mav;
	}
	
	@RequestMapping("/getCbaInfo")
	public ModelAndView getInfo1()
	{
		ModelAndView mav = new ModelAndView();

		// 新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);

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
		}else
		{
			map.setMsg(rounds+",CBA赛事暂未推荐信息，敬请期待！");
			map.setNote("如有合作，请加QQ：414840501");
		}
		
		mav.setViewName("enevtInfo");
		mav.addObject("list", map);
		return mav;
	}
	
	@GetMapping("/getCbaInfoDay")
	public ModelAndView getInfo2(HttpServletRequest request)
	{
		
		String lun = request.getParameter("lun");
		
		ModelAndView mav = new ModelAndView();
		
		NbaTquizInfo q = new NbaTquizInfo();
		q.setQuizName("<第"+lun+"轮>%");
		
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
			map.setMsg("第"+lun+"轮,CBA赛事推荐信息");
			map.setNote("如有合作，请加QQ：414840501");
		}else
		{
			String url = "https://cba.hupu.com/gamespace/?lun="+lun;
			cbaEventUtil.getCbaQuizInfo(url);
			selectByNameLink = nbaTquizInfoMapper.selectByNameLink(q);
			len = selectByNameLink.size();
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
				map.setMsg("第"+lun+"轮,CBA赛事推荐信息");
				map.setNote("如有合作，请加QQ：414840501");
			}
		}
		
		mav.setViewName("enevtInfo");
		mav.addObject("list", map);
		return mav;
	}
	
}
