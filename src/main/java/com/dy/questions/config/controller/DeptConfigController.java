package com.dy.questions.config.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dy.questions.config.entity.Organizes;
import com.dy.questions.config.service.DeptService;
import com.dy.questions.utils.ResultJsonKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author : wanghl
 * @version : 1.0
 * @date : 2022/10/1 23:16
 * @description : 部门维护控制器
 */
@RestController
@RequestMapping("/deptConfig")
public class DeptConfigController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView("config/dept/deptList");

        return mv;
    }

    @RequestMapping("/form")
    public ModelAndView form(String id){
        ModelAndView mv = new ModelAndView("config/dept/form");

        Organizes org = new Organizes();
        if(!StringUtils.isEmpty(id)){
            Organizes dept = deptService.getOrganizesData(id);
            if(dept!=null){
                org = dept;
            }
        }
        mv.addObject("org",org);
        return mv;
    }

    /**
     * 获取部门数据
     * @return
     */
    @RequestMapping("/getOrganizesList")
    public JSONArray getOrganizesList(String type){
        return this.deptService.getOrganizesList(type);
    }


    /**
     * 保存
     * @return
     */
    @RequestMapping("/save")
    public JSONObject save(Organizes org){

        return this.deptService.save(org);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("/delete")
    public JSONObject delete(String id){

        if(StringUtils.isEmpty(id)){
            return ResultJsonKit.error("删除失败");
        }
        return this.deptService.deleteData(id);
    }
}