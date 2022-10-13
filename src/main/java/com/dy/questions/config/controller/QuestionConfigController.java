package com.dy.questions.config.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.SaveBean;
import com.dy.questions.config.service.DeptService;
import com.dy.questions.config.service.QuestionConfigService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/1 23:39
 * @description : 问题配置控制器
 */
@RestController
@RequestMapping("/questionConfig")
public class QuestionConfigController {

    private final QuestionConfigService configService;
    private final DeptService deptService;

    public QuestionConfigController(QuestionConfigService configService, DeptService deptService) {
        this.configService = configService;
        this.deptService = deptService;
    }

    @RequestMapping("/list")
    public ModelAndView list(HttpServletRequest request){
        String context = request.getContextPath();

        ModelAndView mv = new ModelAndView("config/questionList");
        mv.addObject("context",context);

        return mv;
    }

    @RequestMapping("/data")
    public JSONObject getListData() {
        return configService.data();
    }

    @RequestMapping("/index")
    public ModelAndView index(HttpServletRequest request,String id){

        String context = request.getContextPath();
        Calendar cal = Calendar.getInstance();

        ModelAndView mv = new ModelAndView("config/questionConfig");

        mv.addObject("context",context);
        mv.addObject("year",cal.get(Calendar.YEAR));

        if(!StringUtils.isEmpty(id)){
            mv.addObject("question",configService.loadQuestion(id));
            mv.addObject("questionSub",configService.loadQuestionSub(id,""));
            mv.addObject("deptList",deptService.getOrganizesList());
        }

        return mv;
    }

    @PostMapping("/check")
    public JSONObject check(String year){
        return configService.check(year);
    }

    @PostMapping("/publish")
    public JSONObject publish(String id){
        return configService.publish(id);
    }

    @PostMapping("/unPublish")
    public JSONObject unPublish(String id){
        return configService.unPublish(id);
    }

    @PostMapping("/save")
    public JSONObject save(SaveBean bean){
        return configService.save(bean);
    }

    @PostMapping("/delRecord")
    public JSONObject delRecord(String id){
        return configService.delRecord(id);
    }

    @PostMapping("/deleteSub")
    public JSONObject deleteSub(String subId){
        return configService.deleteSub(subId);
    }
}