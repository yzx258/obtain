package com.rbgt.obtain.entity;

/**
 * 
 * 
 * @author wcyong
 * 
 * @date 2019-12-03
 */
public class NbaTmoneyInfo {
	/**
     * 唯一标识
     */
    private Integer id;

    /**
     * 账户金额
     */
    private String money;

    /**
     * 金额正负
     */
    private String state;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 备注
     */
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money == null ? null : money.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}