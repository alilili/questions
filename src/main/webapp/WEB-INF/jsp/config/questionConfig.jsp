<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>问卷调查配置</title>
    <link rel="stylesheet" type="text/css" href="${context}/external/layui/css/layui.css">
</head>
<body>
<center>
<form class="layui-form" action="" id="question_form" style="width:800px">
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>基本信息维护</legend>
    </fieldset>
    <div class="layui-form-item">
        <label class="layui-form-label">标题</label>
        <div class="layui-input-block" style="float:left;margin-left:10px;width:580px">
            <input type="text" id="bt" lay-verify="required" lay-reqtext="请输入标题" lay-verify="title"
                   value="${question.bt}"  autocomplete="off" placeholder="请输入标题" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">模板</label>
        <div class="layui-input-block" style="float:left;margin-left:10px;width:580px">
            <input type="text" id="mark" lay-verify="required" lay-reqtext="请输入模板" lay-verify="title"
                   value="${question.mark}"  autocomplete="off" placeholder="请输入模板" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">调查年份</label>
        <div class="layui-input-block" style="float:left;margin-left:10px;float:left">
            <select id="year" name="year" lay-filter="year">
                <c:forEach begin="${year-2}" end="${year+2}" var="i">
                    <option value="${i}"  <c:if test="${question.year==i}">selected=""</c:if>  >${i}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">生效日期</label>
        <div class="layui-input-inline" style="width: 100px;">
            <input type="text" name="date" id="startDate" value="${question.startDate}" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
        </div>
        <div class="layui-form-mid">-</div>
        <div class="layui-input-inline" style="width: 100px;">
            <input type="text" name="date" id="endDate" value="${question.endDate}" lay-verify="date" placeholder="yyyy-MM-dd" autocomplete="off" class="layui-input">
        </div>
    </div>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
        <legend>单位指标维护</legend>
    </fieldset>
    <div>
        <table style="width: 100%;border-collapse:unset;border-spacing:20px">
            <thead>
                <tr>
                    <td style="width:200px;text-align:center">维护单位</td>
                    <td style="text-align:center">维护指标</td>
                    <td style="width:80px;text-align:center">排序</td>
                    <td style="width:80px;text-align:center;">操作</td>
                </tr>
            </thead>
            <tbody id="question_content">
                <c:forEach items="${questionSub}" var="sub" varStatus="status" >
                    <tr class="question_tr">
                        <td>
                            <input type="hidden" class="subId" value="${sub.id}">
                            <div class='layui-input-inline'>
                                <select class='org' name='org' id='org' lay-filter='org'>
                                    <c:forEach items="${deptList}" var="dept" varStatus="st" >
                                        <option value='${dept.id}'  <c:if test="${sub.organize.id==dept.id}">selected=""</c:if>  >${dept.orgName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </td>
                        <td>
                            <div class='layui-input-inline' style='width: 100%;'>
                                <textarea placeholder='请输入内容' class='layui-textarea question'>${sub.question}</textarea>
                            </div>
                        </td>
                        <td>
                            <div class='layui-input-inline' style='width: 100%;'>
                                <input type="text" id="showOrder" value="${sub.showOrder}" class="layui-input showorder">
                            </div>
                        </td>
                        <td>
                            <button type='button' class='layui-btn layui-btn-normal' onclick='deleteQuestion(this,"${sub.id}")'>删除</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="button" class="layui-btn layui-btn-normal" id="save_question">保存</button>
            <button type="button" class="layui-btn layui-btn-normal" id="add_question">新增</button>
            <c:choose>
                <c:when test="${question.publish==1}">
                    <button type="button" class="layui-btn layui-btn-normal" id="unPublish_question">取消发布</button>
                </c:when>
                <c:otherwise>
                    <button type="button" class="layui-btn layui-btn-normal" id="publish_question">发布</button>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</form>
</center>
</body>
    <script src="${context}/external/jquery-1.12.4.min.js"></script>
    <script src="${context}/external/layui/layui.all.js"></script>
    <script src="${context}/config/questionConfig.js"></script>
</html>
