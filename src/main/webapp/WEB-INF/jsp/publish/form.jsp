<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>

    <title>问卷调查</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/external/layui/css/layui.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/publish/css/index.css?v=${version}">
</head>
<body>

<form class="layui-form" action="" id="question_form">
    <fieldset class="layui-elem-field layui-field-title" >
        <legend>${question.bt}</legend>
    </fieldset>

    <input type="hidden" id="configId" name="configId" value="${question.id}"/>
    <input type="hidden" id="year" name="year" value="${question.year}"/>
    <input type="hidden" id="bt" name="bt" value="${question.bt}"/>

    <div class="questions">
    <c:forEach items="${questionSub}" var="sub" varStatus="status" >

        <div class="layui-form-item">
            <div class="layui-input-block"><span class="req">*</span>${status.count}. ${sub.question}</div>
            <div class="layui-input-block score" questionId="${sub.id}" questionContent="${sub.question}" question_num="${status.count}">
                <input type="radio" autocomplete="off" name="question_${status.count}" value="10" title="优秀" class="layui-radio">
                <input type="radio" autocomplete="off" name="question_${status.count}" value="8" title="良好" class="layui-radio"> <br/>
                <input type="radio" autocomplete="off" name="question_${status.count}" value="6" title="较好" class="layui-radio">
                <input type="radio" autocomplete="off" name="question_${status.count}" value="4" title="一般" class="layui-radio">
                <input type="radio" autocomplete="off" name="question_${status.count}" value="0" title="较差" class="layui-radio">
            </div>
        </div>

    </c:forEach>
    </div>

    <div class="layui-form-item" style="margin-top: 30px;">
        <label class="layui-form-label"><span class="req">*</span>您的姓名</label>
        <div class="layui-input-block">
            <input type="text" name="userName" lay-verify="userName" placeholder="请输入您的姓名" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">您的年龄</label>
        <div class="layui-input-block">
            <input type="number" name="age" lay-verify="age" autocomplete="off" placeholder="请输入您的年龄" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">联系方式</label>
        <div class="layui-input-block">
            <input type="text" name="mobile" lay-verify="mobile" autocomplete="off" placeholder="请输入您的联系方式" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <div style="text-align: center;">
            <button type="button" class="layui-btn layui-btn-normal" id="submit_question">提交</button>
        </div>
    </div>

</form>

<script type="text/javascript">
    function initFont() {
        var s_w = document.body.clientWidth;
        console.log(s_w)
        var font = (100 * s_w) / 1920
        document.getElementsByTagName("body")[0].setAttribute("style", "font-size:" + font + "px;");
    }

    // initFont()
    // window.addEventListener("resize", initFont)
</script>

</body>
    <script src="${pageContext.request.contextPath}/external/jquery-1.12.4.min.js?v=${version}"></script>
    <script src="${pageContext.request.contextPath}/external/layui/layui.all.js?v=${version}"></script>
    <script src="${pageContext.request.contextPath}/publish/js/index.js?v=${version}"></script>
</html>
