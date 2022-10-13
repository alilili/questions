<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/external/layui/css/layui.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/config/dept/css/deptList.css?v=${version}">
    <title>部门维护</title>


</head>
<body style="padding:0 10px">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>部门列表</legend>
</fieldset>

<div class="layui-form">
    <div style="text-align: right;padding-right: 10px;"><button class="layui-btn" lay-filter="newFile" onclick="modify('')">新增</button></div>
    <table class="layui-table">
        <colgroup>
            <col>
            <col width="150" >
            <col width="200" >
            <col width="150" >
            <col width="150" >
        </colgroup>
        <thead >
        <tr>
            <th>部门名称</th>
            <th>排序</th>
            <th>创建时间</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="deptData">

        </tbody>
    </table>
</div>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/external/layui/layui.js?v=${version}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/external/layui/layui.all.js?v=${version}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/external/jquery-1.12.4.min.js?v=${version}"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/config/dept/js/deptList.js?v=${version}"></script>
</html>
