package com.rbgt.obtain.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-12-01
 */
public class NbaTquizInfo {
	/**
     * 唯一标示
     */
    private Integer id;

    /**
     * 竞猜码
     */
    private String quizCode;

    /**
     * 竞猜球队名
     */
    private String quizName;

    /**
     * 竞猜预想
     */
    private String quizEnvision;

    /**
     * 竞猜结果
     */
    private String quizResults;

    /**
     * 竞猜时间
     */
    private String quizCreateTime;

    /**
     * 竞猜修改时间
     */
    private String quizUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuizCode() {
        return quizCode;
    }

    public void setQuizCode(String quizCode) {
        this.quizCode = quizCode == null ? null : quizCode.trim();
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName == null ? null : quizName.trim();
    }

    public String getQuizEnvision() {
        return quizEnvision;
    }

    public void setQuizEnvision(String quizEnvision) {
        this.quizEnvision = quizEnvision == null ? null : quizEnvision.trim();
    }

    public String getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(String quizResults) {
        this.quizResults = quizResults == null ? null : quizResults.trim();
    }

    public String getQuizCreateTime() {
        return quizCreateTime;
    }

    public void setQuizCreateTime(String quizCreateTime) {
        this.quizCreateTime = quizCreateTime == null ? null : quizCreateTime.trim();
    }

    public String getQuizUpdateTime() {
        return quizUpdateTime;
    }

    public void setQuizUpdateTime(String quizUpdateTime) {
        this.quizUpdateTime = quizUpdateTime == null ? null : quizUpdateTime.trim();
    }
}