package com.rbgt.obtain.util;

import com.rbgt.obtain.entity.MoneyData;
import com.rbgt.obtain.entity.NbaTeventInfo;
import com.rbgt.obtain.entity.NbaTmoneyInfo;
import com.rbgt.obtain.entity.NbaTquizInfo;
import com.rbgt.obtain.mapper.NbaTeventInfoMapper;
import com.rbgt.obtain.mapper.NbaTmoneyInfoMapper;
import com.rbgt.obtain.mapper.NbaTquizInfoMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class EventUtil {

	@Autowired
	private NbaTquizInfoMapper nbaTquizInfoMapper;

	@Autowired
	private NbaTeventInfoMapper nbaTeventInfoMapper;

	@Autowired
	private NbaTmoneyInfoMapper nbaTmoneyInfoMapper;
	
	private static final String QUIZ_RESULTS = "红";
	
	/**
	 * 获取赛事信息
	 */
	public void getEventInfoNbaTquizInfo(String url) {
		// String url = "https://nba.hupu.com/games/2019-12-2";
		try {
			// 先获得的是整个页面的html标签页面
			Document doc = Jsoup.connect(url).get();
			// 获取比赛场次
			Elements btEl = doc.select(".table_data");

			// 获取场次次数
			int lenCc = btEl.size();

			// 获取本场比赛是否比完
			int l = 0;
			for (int z = 0; z < lenCc; z++) {

				Element span = doc.getElementsByClass("team_vs_b").get(l);
				// 判断是否结束
				String sfjs = span.text();

				System.out.println(sfjs);
				// 获取比赛队伍名称
				Elements a = doc.getElementsByTag("table").get(z).select("a");

				int len = a.size();
				if (len > 0) {

					String quizName = "";
					for (int i = 0; i < len; i++) {
						if (i == 0) {
							quizName = a.get(i).ownText();
						} else {
							quizName = quizName + "VS" + a.get(i).ownText();
						}
					}
					String time = getDate("yyyy-MM-dd");
					NbaTquizInfo select = new NbaTquizInfo();
					select.setQuizName(quizName);
					select.setQuizCreateTime(time);
					NbaTquizInfo selectByNameAndTime = nbaTquizInfoMapper
							.selectByNameAndTime(select);
					if (null == selectByNameAndTime) {
						NbaTquizInfo q = new NbaTquizInfo();
						q.setQuizCode(UUID.randomUUID().toString()
								.replace("-", ""));
						q.setQuizName(quizName);
						q.setQuizCreateTime(getDate("yyyy-MM-dd"));
						q.setQuizUpdateTime(getDate("yyyy-MM-dd hh:mm:ss"));
						q.setQuizEnvision(RandomNumberUtil.getRandomNumber());
						nbaTquizInfoMapper.insertSelective(q);
					}
					z = z + 1;
					l = l + 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存推荐赛事单双
	 */
	public void getEventInfoSaveNbaTeventInfo(String url) {
		// String url = "https://nba.hupu.com/games/2019-12-2";
		try {
			// 先获得的是整个页面的html标签页面
			Document doc = Jsoup.connect(url).get();
			// 获取比赛场次
			Elements btEl = doc.select(".table_data");

			// 获取场次次数
			int lenCc = btEl.size();

			// 获取本场比赛是否比完
			int l = 0;
			for (int z = 0; z < lenCc; z++) {

				Element span = doc.getElementsByClass("team_vs_b").get(l);
				// 判断是否结束
				String sfjs = span.text();

				System.out.println(sfjs);

				// 获取比赛队伍名称
				Elements a = doc.getElementsByTag("table").get(z).select("a");

				int len = a.size();
				if (len > 0) {

					String quizName = "";
					for (int i = 0; i < len; i++) {
						if (i == 0) {
							quizName = a.get(i).ownText();
						} else {
							quizName = quizName + "VS" + a.get(i).ownText();
						}
					}
					// 获取比赛比分内容
					Elements td = doc.getElementsByTag("table").get(z)
							.select("tbody").select("tr");
					// 获取每节比分分数
					Elements tdFs1 = td.get(1).select("td");
					Elements tdFs2 = td.get(2).select("td");

					int lenFs = tdFs1.size();
					String[] arr = new String[lenFs];
					for (int j = 0; j < lenFs; j++) {
						if ("已结束".equals(sfjs) && j != (lenFs - 1)) {
							// System.out.println("第" + (j + 1) + "节比分："
							// + tdFs1.get(j).text() + ":"
							// + tdFs2.get(j).text());
							arr[j] = tdFs1.get(j).text() + ":"
									+ tdFs2.get(j).text();
						} else {
							// System.out.println("总比分：" + tdFs1.get(j).text()
							// + ":" + tdFs2.get(j).text());
							arr[j] = tdFs1.get(j).text() + ":"
									+ tdFs2.get(j).text();
						}

					}

					NbaTeventInfo record = new NbaTeventInfo();
					record.setEventName(quizName);
					record.setCreateTime(getDate("yyyy-MM-dd"));
					record.setEventStartTime(getPD(0));
					record.setEventEndTime(getPD(0));
					NbaTeventInfo selectByNameAndTime = nbaTeventInfoMapper
							.selectByNameAndTime(record);
					if (null == selectByNameAndTime) {
						NbaTeventInfo e = new NbaTeventInfo();
						e.setEventName(quizName);
						e.setEventCode(UUID.randomUUID().toString()
								.replace("-", ""));
						e.setCreateTime(getDate("yyyy-MM-dd"));
						e.setUpdateTime(getDate("yyyy-MM-dd hh:mm:ss"));
						e.setEventStartTime(getPD(0));
						int m = arr.length;
						String[] arrs = new String[2];
						String type = "";
						for (int h = 0; h < m; h++) {
							if (h == 0) {
								e.setEventScoreOne(arr[h]);
								arrs = arr[h].split(":");
								int all = Integer.parseInt(arrs[0])
										+ Integer.parseInt(arrs[1]);
								if (all % 2 == 1) {
									type = "单";
								} else {
									type = "双";
								}
								e.setEventScoreOneType(type);

							} else if (h == 1) {
								e.setEventScoreTow(arr[h]);
								arrs = arr[h].split(":");
								int all = Integer.parseInt(arrs[0])
										+ Integer.parseInt(arrs[1]);
								if (all % 2 == 1) {
									type = "单";
								} else {
									type = "双";
								}
								e.setEventScoreTowType(type);
							} else if (h == 2) {
								e.setEventScoreThree(arr[h]);
								arrs = arr[h].split(":");
								int all = Integer.parseInt(arrs[0])
										+ Integer.parseInt(arrs[1]);
								if (all % 2 == 1) {
									type = "单";
								} else {
									type = "双";
								}
								e.setEventScoreThreeType(type);
							} else if (h == 3) {
								e.setEventScoreFour(arr[h]);
								arrs = arr[h].split(":");
								int all = Integer.parseInt(arrs[0])
										+ Integer.parseInt(arrs[1]);
								if (all % 2 == 1) {
									type = "单";
								} else {
									type = "双";
								}
								e.setEventScoreFourType(type);
							}
						}
						if (5 == m) {
							e.setEventScoreAll(arr[4]);
							arrs = arr[4].split(":");
							int all = Integer.parseInt(arrs[0])
									+ Integer.parseInt(arrs[1]);
							if (all % 2 == 1) {
								type = "单";
							} else {
								type = "双";
							}
							e.setEventScoreAllType(type);
						}

						if (6 == m) {
							e.setEventScoreJsOne(arr[4]);
							e.setEventScoreAll(arr[5]);
							arrs = arr[5].split(":");
							int all = Integer.parseInt(arrs[0])
									+ Integer.parseInt(arrs[1]);
							if (all % 2 == 1) {
								type = "单";
							} else {
								type = "双";
							}
							e.setEventScoreAllType(type);
						}

						if (7 == m) {
							e.setEventScoreJsTow(arr[4]);
							e.setEventScoreJsOne(arr[5]);
							e.setEventScoreAll(arr[6]);
							arrs = arr[5].split(":");
							int all = Integer.parseInt(arrs[0])
									+ Integer.parseInt(arrs[1]);
							if (all % 2 == 1) {
								type = "单";
							} else {
								type = "双";
							}
							e.setEventScoreAllType(type);
						}

						if (8 == m) {
							e.setEventScoreJsThree(arr[4]);
							e.setEventScoreJsTow(arr[5]);
							e.setEventScoreJsOne(arr[6]);
							e.setEventScoreAll(arr[7]);
							arrs = arr[7].split(":");
							int all = Integer.parseInt(arrs[0])
									+ Integer.parseInt(arrs[1]);
							if (all % 2 == 1) {
								type = "单";
							} else {
								type = "双";
							}
							e.setEventScoreAllType(type);
						}

						if (9 == m) {
							e.setEventScoreJsFour(arr[4]);
							e.setEventScoreJsThree(arr[5]);
							e.setEventScoreJsTow(arr[6]);
							e.setEventScoreJsOne(arr[7]);
							e.setEventScoreAll(arr[8]);
							arrs = arr[8].split(":");
							int all = Integer.parseInt(arrs[0])
									+ Integer.parseInt(arrs[1]);
							if (all % 2 == 1) {
								type = "单";
							} else {
								type = "双";
							}
							e.setEventScoreAllType(type);
						}
						nbaTeventInfoMapper.insertSelective(e);
					}
					z = z + 1;
					l = l + 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 竞猜结果比对
	 */
	public void getEventInfoContrast() {

		NbaTeventInfo e = new NbaTeventInfo();
		e.setCreateTime(getPD(2));
		// 获取赛事信息
		List<NbaTeventInfo> selectByTime2 = nbaTeventInfoMapper.selectByTime(e);

		NbaTquizInfo q = new NbaTquizInfo();
		q.setQuizCreateTime(getPD(2));
		// 获取推荐赛事的比赛
		List<NbaTquizInfo> selectByTime = nbaTquizInfoMapper.selectByTime(q);
		int len = selectByTime.size();
		int len2 = selectByTime2.size();
		String[] arr = new String[4];
		String jg = "";
		if (len > 0 && len2 > 0) {
			for (int i = 0; i < len; i++) {
				arr = selectByTime.get(i).getQuizEnvision().split(",");
				for (int k = 0; k < 4; k++) {
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
				selectByTime.get(i).setQuizResults(jg);
				nbaTquizInfoMapper.updateByPrimaryKeySelective(selectByTime
						.get(i));
			}
		}
	}

	/**
	 * 对比当天竞猜结果，入金额表
	 */
	public void getMoneyInfo()
	{
		NbaTquizInfo q = new NbaTquizInfo();
		q.setQuizCreateTime(getPD(2));
		// 获取推荐赛事的比赛
		List<NbaTquizInfo> selectByTime = nbaTquizInfoMapper.selectByTime(q);
		int len = selectByTime.size();
		
		NbaTmoneyInfo record = new NbaTmoneyInfo();
		record.setCreateTime(getDate("yyyy-MM-dd"));
		
		List<NbaTmoneyInfo> selectByCreateTime = nbaTmoneyInfoMapper.selectByCreateTime(record);
		
		if(len > 0 && selectByCreateTime.size() != len)
		{
			for(int i = 0;i<len;i++)
			{
				if(selectByTime.get(i).getQuizResults().contains(QUIZ_RESULTS))
				{
					record.setMoney("40");
					record.setCreateTime(getDate("yyyy-MM-dd"));
					record.setState("1");
					record.setNote(selectByTime.get(i).getQuizName()+"<盈利>");
					nbaTmoneyInfoMapper.insert(record);
				}else
				{
					record.setMoney("600");
					record.setCreateTime(getDate("yyyy-MM-dd"));
					record.setState("0");
					record.setNote(selectByTime.get(i).getQuizName()+"<亏损>");
					nbaTmoneyInfoMapper.insert(record);
				}
			}
		}
	}
	
	/**
	 * 获取当天盈利金额
	 * @return
	 */
	public MoneyData<NbaTmoneyInfo> getAllMoney()
	{
		MoneyData<NbaTmoneyInfo> data = new MoneyData<NbaTmoneyInfo>();
		NbaTmoneyInfo record = new NbaTmoneyInfo();
		record.setCreateTime(getDate("yyyy-MM-dd"));
		
		List<NbaTmoneyInfo> selectByCreateTime = nbaTmoneyInfoMapper.selectByCreateTime(record);
		data.setData(selectByCreateTime);
		
		int len = selectByCreateTime.size();
		int money = 0;
		if(len > 0)
		{
			for(int i = 0;i<len;i++)
			{
				if("1".equals(selectByCreateTime.get(i).getState()))
				{
					money = money + Integer.parseInt(selectByCreateTime.get(i).getMoney());
				}else
				{
					money = money - Integer.parseInt(selectByCreateTime.get(i).getMoney());
				}
			}
			data.setMsg("恭喜当天获利：" + money+" 元");
		}else
		{
			data.setMsg("数据还未同步！");
		}
		data.setAllMoney(money+" 元");
		return data;
	}
	
	/**
	 * 获取时间 0:减一天 1：加一天 2：当天
	 * 
	 * @param i
	 * @return
	 */
	public static String getPD(int i) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		if (i == 1) {
			c.add(Calendar.DATE, 1);
		} else if (i == 0) {
			c.add(Calendar.DATE, -1);
		} else if(i == 3){
			c.add(Calendar.DATE, -2);
		}else
		{
			c.add(Calendar.DATE, 0);
		}
		Date d = c.getTime();
		String day = format.format(d);
		return day;
	}

	/**
	 * 获取当前时间
	 * 
	 * @param formats
	 * @return
	 */
	public static String getDate(String formats) {
		SimpleDateFormat format = new SimpleDateFormat(formats);
		String time = format.format(new Date());
		return time;
	}
	
	public static void main(String[] args){  
        Calendar now = Calendar.getInstance();  
        System.out.println("年: " + now.get(Calendar.YEAR));  
        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");  
        System.out.println("日: " + (now.get(Calendar.DAY_OF_MONTH) + 1) + "");  
        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));  
        System.out.println("分: " + now.get(Calendar.MINUTE));  
        System.out.println("秒: " + now.get(Calendar.SECOND));  
        System.out.println("当前时间毫秒数：" + now.getTimeInMillis());  
        System.out.println(now.getTime());  
  
        Date d = new Date();  
        System.out.println(d);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        String dateNowStr = sdf.format(d);  
        System.out.println("格式化后的日期：" + dateNowStr);  
          
        String str = "2012-1-13 17:26:33";  //要跟上面sdf定义的格式一样  
        Date today;
		try {
			today = sdf.parse(str);
			System.out.println("字符串转成日期：" + today); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    }  
}
