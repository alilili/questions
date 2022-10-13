<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/external/layui/css/layui.css">
    <title>部门维护</title>

</head>
<body style="padding:0 10px">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>部门维护</legend>
</fieldset>

<input type="hidden" id="pk" value="${org.id}">
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label">部门名称</label>
        <div class="layui-input-block">
            <input type="text" name="orgName" lay-verify="orgName" value="${org.orgName}" autocomplete="off" placeholder="请输入部门名称" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">顺序</label>
        <div class="layui-input-block">
            <input type="number" name="showOrder" lay-verify="showOrder"  value="${org.showOrder}" autocomplete="off" placeholder="请输入顺序号" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="radio" name="status" value="1" title="启用" class="layui-radio" checked="checked">
            <input type="radio" name="status" value="-1" title="停用" class="layui-radio">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button type="submit" class="layui-btn" lay-submit="" lay-filter="submit">保存</button>
        </div>
    </div>

</form>



</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/external/layui/layui.js?v=${version}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/external/jquery-1.12.4.min.js?v=${version}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/config/dept/js/form.js?v=${version}"></script>
</html>
