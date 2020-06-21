package com.rbgt.obtain.util;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.rbgt.obtain.entity.NbaTquizInfoCopy;
import com.rbgt.obtain.mapper.NbaTquizInfoCopyMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by xuyh at 2017/11/6 14:03.
 */
@Component
public class HtmlUtil {
    
	@Autowired
	private NbaTquizInfoCopyMapper nbaTquizInfoCopyMapper;
	
	public static void main(String[] args) {
		//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnScriptError(false);
		//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setActiveXNative(false);
		//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setCssEnabled(false);
		//很重要，启用JS
        webClient.getOptions().setJavaScriptEnabled(true);
		//很重要，设置支持AJAX
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        HtmlPage page = null;
        try {
			//尝试加载上面图片例子给出的网页
            page = webClient.getPage("http://score.nowscore.com/basketball.htm?date=2020-06-20");
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }
		//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束
        webClient.waitForBackgroundJavaScript(30000);
		//直接将加载完成的页面转换成xml格式的字符串
        String pageXml = page.asXml();
		//获取html文档
        Document document = Jsoup.parse(pageXml);
        
        
        int len = document.getElementById("live").getElementsByTag("table")
				.size();

		Calendar now = Calendar.getInstance();
		// 获取当日天数+1,获取明天比赛信息
		String day = (now.get(Calendar.DAY_OF_MONTH)) + "";
		int day_len = day.length();
		if(1 == day_len)
		{
			day = "0"+day;
		}
		String LX, ZD, time, dayBS, dayBSS, KD = "", format = "HH:mm",BSTYPE = "";
		String[] arr = new String[2];
		for (int i = 0; i < len; i++) {
			LX = document.getElementById("live").getElementsByTag("table")
					.get(i).getElementsByTag("tr").get(0).text();
			LX = ZhConverterUtil.convertToSimple(LX);
			if (	LX.contains("N") || LX.contains("B") || LX.contains("篮") 
					|| LX.contains("甲") || LX.contains("甲")
					|| LX.contains("乙") || LX.contains("女") || LX.contains("友")
					|| LX.contains("东") || LX.contains("联") || LX.contains("杯")
					|| LX.contains("超")
					)
			{
				BSTYPE = LX;
				System.out.println("联赛名称：" + LX);
				i = i + 1;
			}
			time = document.getElementById("live").getElementsByTag("table")
					.get(i).getElementsByTag("tr").get(0)
					.getElementsByTag("tr").get(0).getElementsByTag("td")
					.get(0).text();
			arr = time.split("日");
			dayBS = arr[0];
			try {
				// 判断是否为单日比赛
				if (day.equals(dayBS)) {
					// 获取比赛开始时间
					dayBSS = arr[1];
					// 判断比赛是否在规定区间内
					if (DateUtil.isEffectiveDate(
							new SimpleDateFormat(format).parse(dayBSS),
							new SimpleDateFormat(format).parse("12:00"),
							new SimpleDateFormat(format).parse("20:30"))) {
						//获取主客队名称
						ZD = document.getElementById("live")
								.getElementsByTag("table").get(i)
								.getElementsByTag("tr").get(1)
								.getElementsByTag("td").get(1).text();
						KD = document.getElementById("live")
								.getElementsByTag("table").get(i)
								.getElementsByTag("tr").get(2)
								.getElementsByTag("td").get(0).text();
						
						
						//将主客队名称繁体改为简体
						ZD = ZhConverterUtil.convertToSimple(ZD);
						KD = ZhConverterUtil.convertToSimple(KD);
						System.out.println(BSTYPE+" "+ZD+"VS"+KD);
					}
				}
				i = i + 1;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e);
				break;
			}
		}
		System.out.println("=============================================");

		// 转换成简体

		// 转换成繁体
		// String traditional = ZhConverterUtil.convertToTraditional("欧阳");

        
	}
	
    /**
     * 获取所有比赛数据
     * @param url
     * @return
     */
    public static Document getAllEventInfo(String url)
    {
    	final WebClient webClient = new WebClient(BrowserVersion.CHROME);//新建一个模拟谷歌Chrome浏览器的浏览器客户端对象

        webClient.getOptions().setThrowExceptionOnScriptError(false);//当JS执行出错的时候是否抛出异常, 这里选择不需要
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);//当HTTP的状态非200时是否抛出异常, 这里选择不需要
        webClient.getOptions().setActiveXNative(false);
        webClient.getOptions().setCssEnabled(false);//是否启用CSS, 因为不需要展现页面, 所以不需要启用
        webClient.getOptions().setJavaScriptEnabled(true); //很重要，启用JS
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());//很重要，设置支持AJAX

        HtmlPage page = null;
        try {
            //page = webClient.getPage("https://cba.hupu.com/gamespace/?lun=14");//尝试加载上面图片例子给出的网页
            page = webClient.getPage(url);//尝试加载上面图片例子给出的网页
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            webClient.close();
        }

        webClient.waitForBackgroundJavaScript(30000);//异步JS执行需要耗时,所以这里线程要阻塞30秒,等待异步JS执行结束

        String pageXml = page.asXml();//直接将加载完成的页面转换成xml格式的字符串

        Document document = Jsoup.parse(pageXml);//获取html文档
        return document;
    }
    
    //获取比赛结果并入库
    public void saveEventnfoBS(Document document,String times)
    {
    	int len = document.getElementById("live").getElementsByTag("table").size();
        System.out.println(len);
        
        Calendar now = Calendar.getInstance();
        String day = (now.get(Calendar.DAY_OF_MONTH) - 1) + "";
        String LX,ZD,time,dayBS,dayBSS,KD="",format = "HH:mm";
        String[] arr = new String[2];
        System.out.println("=============================================");
        for(int i = 0;i<len;i++)
        {
        		LX = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(0).text();
        		if("NBA".equals(LX) || "CBA".equals(LX) || "WCBA".equals(LX)
    					|| "NBL(A)".equals(LX) || "韩篮甲".equals(LX)
    					|| "德篮甲".equals(LX) || "西篮甲".equals(LX) || "德篮乙".equals(LX)
    					|| "意篮甲".equals(LX) || "土篮超".equals(LX) || "俄篮超".equals(LX)
    					|| "亚海联".equals(LX) || "法篮甲".equals(LX))
        		{
        			i = i + 1;
        		}
        		time = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(0).getElementsByTag("tr").get(0).getElementsByTag("td").get(0).text();
        		arr = time.split("日");
        		dayBS = arr[0];
        		try {
        			if(day.equals(dayBS))
                	{
        			dayBSS = arr[1];
        			if(DateUtil.isEffectiveDate(new SimpleDateFormat(format).parse(dayBSS), new SimpleDateFormat(format).parse("07:00"), new SimpleDateFormat(format).parse("23:30")))
        			{
        				
	        			ZD = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(1).getElementsByTag("td").get(1).text();
	                    
	                    KD = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(2).getElementsByTag("td").get(0).text();
	                    
	                    ZD = new String(ZD.getBytes("ISO8859-1"), "UTF-8");
	                    
	                    System.out.println(ZD);
	                    
//	                    ZD = ZhConverterUtil.convertToSimple(ZD);
//	                    KD = ZhConverterUtil.convertToSimple(KD);
	                    
	                    
	                    System.out.println("======================================");
	                    
	                    if(ZD.indexOf("[") != -1)
						{
							ZD = ZD.substring(0, ZD.indexOf("["));
						}
						
						if(KD.indexOf("[") != -1)
						{
							KD = KD.substring(0, KD.indexOf("["));
						}
	                    
						String bs = ZD+"VS"+KD;
						NbaTquizInfoCopy record = new NbaTquizInfoCopy();
						record.setQuizCreateTime(times);
						record.setQuizUpdateTime(bs);
						NbaTquizInfoCopy selectByCreateTimeAndUpdate = nbaTquizInfoCopyMapper.selectByCreateTimeAndUpdate(record );
						
						if(null != selectByCreateTimeAndUpdate)
						{
							String value = ""; 
							String zd1 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(1).getElementsByTag("td").get(2).text();
		                    String zd2 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(1).getElementsByTag("td").get(3).text();
		                    String zd3 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(1).getElementsByTag("td").get(4).text();
		                    String zd4 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(1).getElementsByTag("td").get(5).text();
		                    
		                    String kd1 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(2).getElementsByTag("td").get(1).text();
		                    String kd2 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(2).getElementsByTag("td").get(2).text();
		                    String kd3 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(2).getElementsByTag("td").get(3).text();
		                    String kd4 = document.getElementById("live").getElementsByTag("table").get(i).getElementsByTag("tr").get(2).getElementsByTag("td").get(4).text();
		                    
		                    int all = Integer.parseInt(zd1) + Integer.parseInt(kd1);
		                    
		                    if(all % 2 == 1)
		                    {
		                    	value = "单";
		                    }else
		                    {
		                    	value = "双";
		                    }
		                    
		                    all = Integer.parseInt(zd2) + Integer.parseInt(kd2);
		                    
		                    if(all % 2 == 1)
		                    {
		                    	value = value + ",单";
		                    }else
		                    {
		                    	value = value + ",双";
		                    }
		                    
		                    all = Integer.parseInt(zd3) + Integer.parseInt(kd3);
		                    
		                    if(all % 2 == 1)
		                    {
		                    	value = value + ",单";
		                    }else
		                    {
		                    	value = value + ",双";
		                    }
		                    
		                    all = Integer.parseInt(zd4) + Integer.parseInt(kd4);
		                    
		                    if(all % 2 == 1)
		                    {
		                    	value = value + ",单";
		                    }else
		                    {
		                    	value = value + ",双";
		                    }
		                    selectByCreateTimeAndUpdate.setQuizEnvision(value);
		                    nbaTquizInfoCopyMapper.updateByPrimaryKeySelective(selectByCreateTimeAndUpdate);
//		                    System.out.println("第一节：" + text+":"+text32);
//		                    System.out.println("第二节：" + text2+":"+text33);
//		                    System.out.println("第三节：" + text3+":"+text34);
//		                    System.out.println("第四节：" + text4+":"+text35);
						}
	                    System.out.println("======================================");
	                	}
                	}
        			i = i + 1;
				} catch (Exception e) {
					break;
				}
        }
    }
    
    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     * 
     * @param nowTime 当前时间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return
     * @author jqlin
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
}
