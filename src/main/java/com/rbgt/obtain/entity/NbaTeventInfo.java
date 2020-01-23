package com.rbgt.obtain.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-11-29
 */
public class NbaTeventInfo {
    /**
     * 唯一标识
     */
    private Integer id;

    /**
     * 赛事标识
     */
    private String eventCode;

    /**
     * 比赛球队名
     */
    private String eventName;

    /**
     * 第一节比分
     */
    private String eventScoreOne;

    /**
     * 第一节比分单双
     */
    private String eventScoreOneType;

    /**
     * 第二节比分
     */
    private String eventScoreTow;

    /**
     * 第二节比分单双
     */
    private String eventScoreTowType;

    /**
     * 第三节比分
     */
    private String eventScoreThree;

    /**
     * 第三节比分单双
     */
    private String eventScoreThreeType;

    /**
     * 第四节比分
     */
    private String eventScoreFour;

    /**
     * 第四节比分单双
     */
    private String eventScoreFourType;

    /**
     * 第一加时比分
     */
    private String eventScoreJsOne;

    /**
     * 第一加时单双
     */
    private String eventScoreJsOneType;

    /**
     * 第二加时比分
     */
    private String eventScoreJsTow;

    /**
     * 第二加时单双
     */
    private String eventScoreJsTowType;

    /**
     * 第三加时比分
     */
    private String eventScoreJsThree;

    /**
     * 第三加时单双
     */
    private String eventScoreJsThreeType;

    /**
     * 第四加时比分
     */
    private String eventScoreJsFour;

    /**
     * 第四加时单双
     */
    private String eventScoreJsFourType;

    /**
     * 总比分
     */
    private String eventScoreAll;

    /**
     * 总比分单双
     */
    private String eventScoreAllType;

    /**
     * 赛事开始时间
     */
    private String eventStartTime;

    /**
     * 赛事结束时间
     */
    private String eventEndTime;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 修改时间
     */
    private String updateTime;

    /**
     * 扩展字段
     */
    private String extension1;

    /**
     * 扩展字段
     */
    private String extension2;

    /**
     * 扩展字段
     */
    private String extension3;

    /**
     * 扩展字段
     */
    private String extension4;

    /**
     * 扩展字段
     */
    private String extension5;

    /**
     * 扩展字段
     */
    private String extension6;

    /**
     * 扩展字段
     */
    private String extension7;

    /**
     * 扩展字段
     */
    private String extension8;

    /**
     * 扩展字段
     */
    private String extension9;

    /**
     * 扩展字段
     */
    private String extension10;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode == null ? null : eventCode.trim();
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    public String getEventScoreOne() {
        return eventScoreOne;
    }

    public void setEventScoreOne(String eventScoreOne) {
        this.eventScoreOne = eventScoreOne == null ? null : eventScoreOne.trim();
    }

    public String getEventScoreOneType() {
        return eventScoreOneType;
    }

    public void setEventScoreOneType(String eventScoreOneType) {
        this.eventScoreOneType = eventScoreOneType == null ? null : eventScoreOneType.trim();
    }

    public String getEventScoreTow() {
        return eventScoreTow;
    }

    public void setEventScoreTow(String eventScoreTow) {
        this.eventScoreTow = eventScoreTow == null ? null : eventScoreTow.trim();
    }

    public String getEventScoreTowType() {
        return eventScoreTowType;
    }

    public void setEventScoreTowType(String eventScoreTowType) {
        this.eventScoreTowType = eventScoreTowType == null ? null : eventScoreTowType.trim();
    }

    public String getEventScoreThree() {
        return eventScoreThree;
    }

    public void setEventScoreThree(String eventScoreThree) {
        this.eventScoreThree = eventScoreThree == null ? null : eventScoreThree.trim();
    }

    public String getEventScoreThreeType() {
        return eventScoreThreeType;
    }

    public void setEventScoreThreeType(String eventScoreThreeType) {
        this.eventScoreThreeType = eventScoreThreeType == null ? null : eventScoreThreeType.trim();
    }

    public String getEventScoreFour() {
        return eventScoreFour;
    }

    public void setEventScoreFour(String eventScoreFour) {
        this.eventScoreFour = eventScoreFour == null ? null : eventScoreFour.trim();
    }

    public String getEventScoreFourType() {
        return eventScoreFourType;
    }

    public void setEventScoreFourType(String eventScoreFourType) {
        this.eventScoreFourType = eventScoreFourType == null ? null : eventScoreFourType.trim();
    }

    public String getEventScoreJsOne() {
        return eventScoreJsOne;
    }

    public void setEventScoreJsOne(String eventScoreJsOne) {
        this.eventScoreJsOne = eventScoreJsOne == null ? null : eventScoreJsOne.trim();
    }

    public String getEventScoreJsOneType() {
        return eventScoreJsOneType;
    }

    public void setEventScoreJsOneType(String eventScoreJsOneType) {
        this.eventScoreJsOneType = eventScoreJsOneType == null ? null : eventScoreJsOneType.trim();
    }

    public String getEventScoreJsTow() {
        return eventScoreJsTow;
    }

    public void setEventScoreJsTow(String eventScoreJsTow) {
        this.eventScoreJsTow = eventScoreJsTow == null ? null : eventScoreJsTow.trim();
    }

    public String getEventScoreJsTowType() {
        return eventScoreJsTowType;
    }

    public void setEventScoreJsTowType(String eventScoreJsTowType) {
        this.eventScoreJsTowType = eventScoreJsTowType == null ? null : eventScoreJsTowType.trim();
    }

    public String getEventScoreJsThree() {
        return eventScoreJsThree;
    }

    public void setEventScoreJsThree(String eventScoreJsThree) {
        this.eventScoreJsThree = eventScoreJsThree == null ? null : eventScoreJsThree.trim();
    }

    public String getEventScoreJsThreeType() {
        return eventScoreJsThreeType;
    }

    public void setEventScoreJsThreeType(String eventScoreJsThreeType) {
        this.eventScoreJsThreeType = eventScoreJsThreeType == null ? null : eventScoreJsThreeType.trim();
    }

    public String getEventScoreJsFour() {
        return eventScoreJsFour;
    }

    public void setEventScoreJsFour(String eventScoreJsFour) {
        this.eventScoreJsFour = eventScoreJsFour == null ? null : eventScoreJsFour.trim();
    }

    public String getEventScoreJsFourType() {
        return eventScoreJsFourType;
    }

    public void setEventScoreJsFourType(String eventScoreJsFourType) {
        this.eventScoreJsFourType = eventScoreJsFourType == null ? null : eventScoreJsFourType.trim();
    }

    public String getEventScoreAll() {
        return eventScoreAll;
    }

    public void setEventScoreAll(String eventScoreAll) {
        this.eventScoreAll = eventScoreAll == null ? null : eventScoreAll.trim();
    }

    public String getEventScoreAllType() {
        return eventScoreAllType;
    }

    public void setEventScoreAllType(String eventScoreAllType) {
        this.eventScoreAllType = eventScoreAllType == null ? null : eventScoreAllType.trim();
    }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime == null ? null : eventStartTime.trim();
    }

    public String getEventEndTime() {
        return eventEndTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime == null ? null : eventEndTime.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getExtension1() {
        return extension1;
    }

    public void setExtension1(String extension1) {
        this.extension1 = extension1 == null ? null : extension1.trim();
    }

    public String getExtension2() {
        return extension2;
    }

    public void setExtension2(String extension2) {
        this.extension2 = extension2 == null ? null : extension2.trim();
    }

    public String getExtension3() {
        return extension3;
    }

    public void setExtension3(String extension3) {
        this.extension3 = extension3 == null ? null : extension3.trim();
    }

    public String getExtension4() {
        return extension4;
    }

    public void setExtension4(String extension4) {
        this.extension4 = extension4 == null ? null : extension4.trim();
    }

    public String getExtension5() {
        return extension5;
    }

    public void setExtension5(String extension5) {
        this.extension5 = extension5 == null ? null : extension5.trim();
    }

    public String getExtension6() {
        return extension6;
    }

    public void setExtension6(String extension6) {
        this.extension6 = extension6 == null ? null : extension6.trim();
    }

    public String getExtension7() {
        return extension7;
    }

    public void setExtension7(String extension7) {
        this.extension7 = extension7 == null ? null : extension7.trim();
    }

    public String getExtension8() {
        return extension8;
    }

    public void setExtension8(String extension8) {
        this.extension8 = extension8 == null ? null : extension8.trim();
    }

    public String getExtension9() {
        return extension9;
    }

    public void setExtension9(String extension9) {
        this.extension9 = extension9 == null ? null : extension9.trim();
    }

    public String getExtension10() {
        return extension10;
    }

    public void setExtension10(String extension10) {
        this.extension10 = extension10 == null ? null : extension10.trim();
    }
}