package com.dy.questions.publish.service;

import com.alibaba.fastjson.JSONObject;
import com.dy.questions.publish.entity.Reply;

import javax.servlet.http.HttpServletRequest;

public interface PublishService {

    JSONObject saveReply(HttpServletRequest request, Reply reply);
}
