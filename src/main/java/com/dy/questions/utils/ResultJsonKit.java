package com.dy.questions.utils;

import com.alibaba.fastjson.JSONObject;

public class ResultJsonKit {

    public static JSONObject error(String message, Object data) {
        JSONObject json = new JSONObject();
        json.put("type","error");
        json.put("code","500");
        json.put("message",message);
        json.put("data",data);
        return json;
    }

    public static JSONObject error() {
        return error("操作失败",null);
    }

    public static JSONObject error(String message) {
        return error(message,null);
    }

    public static JSONObject success(String message, Object data) {
        JSONObject json = new JSONObject();
        json.put("type","success");
        json.put("code","200");
        json.put("message",message);
        json.put("data",data);
        return json;
    }

    public static JSONObject success() {
        return success("操作成功",null);
    }

    public static JSONObject success(Object data) {
        return success("操作成功",data);
    }

    public static JSONObject result(boolean result) {
        return result ? success() : error();
    }
}