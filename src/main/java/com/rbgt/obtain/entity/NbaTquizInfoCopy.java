package com.rbgt.obtain.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-12-21
 */
public class NbaTquizInfoCopy {
    private Integer id;

    private String quizCode;

    private String quizName;

    private String quizEnvision;

    private String quizResults;

    private String quizCreateTime;

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