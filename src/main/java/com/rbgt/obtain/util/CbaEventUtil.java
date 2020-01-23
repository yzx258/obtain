package com.rbgt.obtain.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.rbgt.obtain.entity.NbaTeventInfo;
import com.rbgt.obtain.entity.NbaTquizInfo;
import com.rbgt.obtain.mapper.NbaTeventInfoMapper;
import com.rbgt.obtain.mapper.NbaTmoneyInfoMapper;
import com.rbgt.obtain.mapper.NbaTquizInfoMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CbaEventUtil {

	@Autowired
	private NbaTquizInfoMapper nbaTquizInfoMapper;

	@Autowired
	private NbaTeventInfoMapper nbaTeventInfoMapper;

	@Autowired
	private NbaTmoneyInfoMapper nbaTmoneyInfoMapper;

	/**
	 * 获取CBA赛事推荐
	 */
	public void getCbaQuizInfo(String url) {
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

		int lenTable = document.getElementsByTag("table").size() - 1;

		for (int j = 0; j < lenTable; j++) {

			Elements elementsByTag = document.getElementsByTag("table").get(j)
					.getElementsByTag("tr").get(1).getElementsByTag("td");

			Elements elementsByTag2 = document.getElementsByTag("table").get(j)
					.getElementsByTag("tr").get(2).getElementsByTag("td");

			String quizName = "<" + rounds + ">" + elementsByTag.get(0).text()
					+ "VS" + elementsByTag2.get(0).text();

			NbaTquizInfo select = new NbaTquizInfo();
			select.setQuizName(quizName);
			NbaTquizInfo selectByNameAndTime = nbaTquizInfoMapper
					.selectByName(select);
			if (null == selectByNameAndTime) {
				NbaTquizInfo q = new NbaTquizInfo();
				q.setQuizCode(UUID.randomUUID().toString().replace("-", ""));
				q.setQuizName(quizName);
				q.setQuizUpdateTime(EventUtil.getDate("yyyy-MM-dd hh:mm:ss"));
				q.setQuizEnvision(RandomNumberUtil.getRandomNumber());
				nbaTquizInfoMapper.insertSelective(q);
			}
		}
	}

	/**
	 * 保存推荐CBA赛事单双
	 */
	public void getEventInfoSaveNbaTeventInfo(String url)
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

		int lenTable = document.getElementsByTag("table").size() - 1;

		for (int j = 0; j < lenTable; j++) {

			Elements elementsByTag = document.getElementsByTag("table").get(j)
					.getElementsByTag("tr").get(1).getElementsByTag("td");

			Elements elementsByTag2 = document.getElementsByTag("table").get(j)
					.getElementsByTag("tr").get(2).getElementsByTag("td");

			String quizName = "<" + rounds + ">" + elementsByTag.get(0).text()
					+ "VS" + elementsByTag2.get(0).text();

			int len = elementsByTag.size()-3;
			
			//判断比赛是否开始
	        if(len <= 5)
	        {
	            continue;
	        }
	        
	        NbaTeventInfo record = new NbaTeventInfo();
			record.setEventName(quizName);
			NbaTeventInfo selectByNameAndTime = nbaTeventInfoMapper
					.selectByName(record);
	        if(null == selectByNameAndTime)
	        {
	        	NbaTeventInfo e = new NbaTeventInfo();
	        	e.setEventName(quizName);
				e.setEventCode(UUID.randomUUID().toString()
						.replace("-", ""));
				e.setCreateTime(EventUtil.getDate("yyyy-MM-dd"));
				e.setUpdateTime(EventUtil.getDate("yyyy-MM-dd hh:mm:ss"));
				e.setEventStartTime(EventUtil.getPD(0));
				e.setExtension1(rounds);
	        	
	        	int all = 0;
	        //循环遍历比赛信息
	        for(int i=1;i<len;i++)
	        {
	            if(i==5)
	            {
	            	e.setEventScoreAll(elementsByTag.get(i).text()+":"+elementsByTag2.get(i).text());
            		all = Integer.parseInt(elementsByTag.get(i).text())+Integer.parseInt(elementsByTag2.get(i).text());
            		if (all % 2 == 1) {
						e.setEventScoreAllType("单");
					} else {
						e.setEventScoreAllType("双");
					}
	            }else if(i==6)
	            {
	            	e.setEventScoreAll(elementsByTag.get(6).text()+":"+elementsByTag2.get(6).text());
            		all = Integer.parseInt(elementsByTag.get(6).text())+Integer.parseInt(elementsByTag2.get(6).text());
            		if (all % 2 == 1) {
						e.setEventScoreAllType("单");
					} else {
						e.setEventScoreAllType("双");
					}
	            }
	            else if(i==7)
	            {
	            	e.setEventScoreAll(elementsByTag.get(7).text()+":"+elementsByTag2.get(7).text());
            		all = Integer.parseInt(elementsByTag.get(7).text())+Integer.parseInt(elementsByTag2.get(7).text());
            		if (all % 2 == 1) {
						e.setEventScoreAllType("单");
					} else {
						e.setEventScoreAllType("双");
					}
	            }
	            else if(i==8)
	            {
	            	e.setEventScoreAll(elementsByTag.get(8).text()+":"+elementsByTag2.get(8).text());
            		all = Integer.parseInt(elementsByTag.get(8).text())+Integer.parseInt(elementsByTag2.get(8).text());
            		if (all % 2 == 1) {
						e.setEventScoreAllType("单");
					} else {
						e.setEventScoreAllType("双");
					}
	            }
	            else if(i==9)
	            {
	            	e.setEventScoreAll(elementsByTag.get(9).text()+":"+elementsByTag2.get(9).text());
            		all = Integer.parseInt(elementsByTag.get(9).text())+Integer.parseInt(elementsByTag2.get(9).text());
            		if (all % 2 == 1) {
						e.setEventScoreAllType("单");
					} else {
						e.setEventScoreAllType("双");
					}
	            }
	            else
	            {
	            	if(i == 1)
	            	{
	            		e.setEventScoreOne(elementsByTag.get(i).text()+":"+elementsByTag2.get(i).text());
	            		all = Integer.parseInt(elementsByTag.get(i).text())+Integer.parseInt(elementsByTag2.get(i).text());
	            		if (all % 2 == 1) {
							e.setEventScoreOneType("单");
						} else {
							e.setEventScoreOneType("双");
						}
	            	}else if(i ==2)
	            	{
	            		e.setEventScoreTow(elementsByTag.get(i).text()+":"+elementsByTag2.get(i).text());
	            		all = Integer.parseInt(elementsByTag.get(i).text())+Integer.parseInt(elementsByTag2.get(i).text());
	            		if (all % 2 == 1) {
							e.setEventScoreTowType("单");
						} else {
							e.setEventScoreTowType("双");
						}
	            	}else if(i ==3)
	            	{
	            		e.setEventScoreThree(elementsByTag.get(i).text()+":"+elementsByTag2.get(i).text());
	            		all = Integer.parseInt(elementsByTag.get(i).text())+Integer.parseInt(elementsByTag2.get(i).text());
	            		if (all % 2 == 1) {
							e.setEventScoreThreeType("单");
						} else {
							e.setEventScoreThreeType("双");
						}
	            	}else if(i ==4)
	            	{
	            		e.setEventScoreFour(elementsByTag.get(i).text()+":"+elementsByTag2.get(i).text());
	            		all = Integer.parseInt(elementsByTag.get(i).text())+Integer.parseInt(elementsByTag2.get(i).text());
	            		if (all % 2 == 1) {
							e.setEventScoreFourType("单");
						} else {
							e.setEventScoreFourType("双");
						}
	            	}
	            }
	        }
	        nbaTeventInfoMapper.insertSelective(e);
	        }
		}
	}

	
	/**
	 * 对比CBA赛事是否正确
	 */
	public void getEventInfoContrast()
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
		
		NbaTeventInfo e = new NbaTeventInfo();
		e.setExtension1(rounds);
		// 获取赛事信息
		List<NbaTeventInfo> selectByTime2 = nbaTeventInfoMapper.selectByExtension1(e);
		int len = selectByTime2.size();
		String[] arr = new String[4];
		int arrLen = 0;
		String jg = "";
		for(int i=0;i<len;i++)
		{
			NbaTquizInfo q = new NbaTquizInfo();
			q.setQuizName(selectByTime2.get(i).getEventName());
			// 获取推荐赛事的比赛
			NbaTquizInfo qw = nbaTquizInfoMapper.selectByName(q);
			
			if(null != qw)
			{
				arr = qw.getQuizEnvision().split(",");
				arrLen = arr.length;
				for(int k=0;k<arrLen;k++)
				{
					if (0 == k) {
						// 判断是否一样
						if (arr[k].equals(selectByTime2.get(i)
								.getEventScoreOneType())) {
							jg = "红";
						} else {
							jg = "黑";
						}
					} else if (1 == k) {
						// 判断是否一样
						if (arr[k].equals(selectByTime2.get(i)
								.getEventScoreTowType())) {
							jg = jg + ",红";
						} else {
							jg = jg + ",黑";
						}
					} else if (2 == k) {
						// 判断是否一样
						if (arr[k].equals(selectByTime2.get(i)
								.getEventScoreThreeType())) {
							jg = jg + ",红";
						} else {
							jg = jg + ",黑";
						}
					} else if (3 == k) {
						// 判断是否一样
						if (arr[k].equals(selectByTime2.get(i)
								.getEventScoreFourType())) {
							jg = jg + ",红";
						} else {
							jg = jg + ",黑";
						}
					}
				}
				qw.setQuizResults(jg);
				nbaTquizInfoMapper.updateByPrimaryKeySelective(qw);
			}
		}
	}
	
}
