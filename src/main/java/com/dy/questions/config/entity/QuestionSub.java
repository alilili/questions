package com.dy.questions.config.entity;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/2 22:21
 * @description : 调查问卷子表
 */
public class QuestionSub {

    private String id;
    private String orgId;
    private String fid;
    private String question;
    private int rowstate;
    private int showOrder;

    private Organizes organize;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getRowstate() {
        return rowstate;
    }

    public void setRowstate(int rowstate) {
        this.rowstate = rowstate;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }

    public Organizes getOrganize() {
        return organize;
    }

    public void setOrganize(Organizes organize) {
        this.organize = organize;
    }
}