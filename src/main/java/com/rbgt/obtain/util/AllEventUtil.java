package com.rbgt.obtain.util;

import com.github.houbb.opencc4j.util.ZhConverterUtil;
import com.rbgt.obtain.entity.NbaTquizInfoCopy;
import com.rbgt.obtain.mapper.NbaTquizInfoCopyMapper;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

@Component
public class AllEventUtil {

	@Autowired
	private NbaTquizInfoCopyMapper nbaTquizInfoCopyMapper;

	/**
	 * 获取所有赛事推荐
	 */
	public void getCbaQuizInfoAll(String url) {

		String createTime = EventUtil.getPD(1);

		// 获取赛事信息
		Document document = HtmlUtil.getAllEventInfo(url);

		int len = document.getElementById("live").getElementsByTag("table")
				.size();

		Calendar now = Calendar.getInstance();
		// 获取当日天数+1,获取明天比赛信息
		String day = (now.get(Calendar.DAY_OF_MONTH) + 1) + "";
		// 判断是否是小于10
		int day_len = day.length();
		if (1 == day_len) {
			day = "0" + day;
		}

		String LX, ZD, time, dayBS, dayBSS, KD = "", format = "HH:mm", SSMC = "";
		String[] arr = new String[2];
		for (int i = 0; i < len; i++) {
			LX = document.getElementById("live").getElementsByTag("table")
					.get(i).getElementsByTag("tr").get(0).text();

			LX = ZhConverterUtil.convertToSimple(LX);

			if (LX.contains("N") || LX.contains("B") || LX.contains("E")
					|| LX.contains("篮") || LX.contains("甲") || LX.contains("甲")
					|| LX.contains("星") || LX.contains("乙") || LX.contains("女")
					|| LX.contains("友") || LX.contains("东") || LX.contains("西")
					|| LX.contains("联") || LX.contains("杯") || LX.contains("超")) {
				SSMC = LX;
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
						// 获取主客队名称
						ZD = document.getElementById("live")
								.getElementsByTag("table").get(i)
								.getElementsByTag("tr").get(1)
								.getElementsByTag("td").get(1).text();
						KD = document.getElementById("live")
								.getElementsByTag("table").get(i)
								.getElementsByTag("tr").get(2)
								.getElementsByTag("td").get(0).text();

						// 将主客队名称繁体改为简体
						ZD = ZhConverterUtil.convertToSimple(ZD);
						KD = ZhConverterUtil.convertToSimple(KD);

						// 插入数据
						NbaTquizInfoCopy copy = new NbaTquizInfoCopy();
						copy.setQuizCode(UUID.randomUUID().toString()
								.replace("-", ""));
						copy.setQuizName(SSMC + " " + time + " " + ZD + "VS"
								+ KD);
						copy.setQuizCreateTime(createTime);
						if (ZD.indexOf("[") != -1) {
							ZD = ZD.substring(0, ZD.indexOf("["));
						}

						if (KD.indexOf("[") != -1) {
							KD = KD.substring(0, KD.indexOf("["));
						}
						copy.setQuizUpdateTime(ZD + "VS" + KD);
						copy.setQuizEnvision(RandomNumberUtil.getRandomNumber());
						// System.out.println(time+" "+ZD+"VS"+KD);

						nbaTquizInfoCopyMapper.insert(copy);

					}
				}
				i = i + 1;
			} catch (Exception e) {
				break;
			}
		}
		System.out.println("=============================================");

		// 转换成简体

		// 转换成繁体
		// String traditional = ZhConverterUtil.convertToTraditional("欧阳");

		System.out
				.println("==============================================================================");
	}
}
