package com.dy.questions.publish.entity;

/**
 * zhaoyh
 * 回复子表数据实体类
 */
public class ReplySub {

    private String id;
    private String fid;

    private String configId;
    private String questionId;

    private String answer;
    private String answerText;
    private String question;
    private String showOrder;
    private String rowstate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(String showOrder) {
        this.showOrder = showOrder;
    }

    public String getRowstate() {
        return rowstate;
    }

    public void setRowstate(String rowstate) {
        this.rowstate = rowstate;
    }
}