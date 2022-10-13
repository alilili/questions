<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>问卷调查</title>
    <link rel="stylesheet" type="text/css" href="${context}/external/layui/css/layui.css">
</head>
<body class="content" style="height: 100%">
<div class="content-con">
    <div class="layui-btn-group demoTable">
        <button class="layui-btn" onclick="addNewData()">新增</button>
        <button class="layui-btn" onclick="refreshData()">刷新</button>
    </div>
    <table id="demo" lay-filter="test"></table>
</div>
</body>
<script type="text/html" id="toolbar">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    {{# if(d.publish == 1 ){ }}
        <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="unPublish">取消发布</a>
    {{# } else { }}
        <a class="layui-btn layui-btn-warm layui-btn-xs" lay-event="publish">发布</a>
    {{# }}}
</script>

<script type="text/html" id="status">
    {{# if(d.publish == 1 ){ }}
        已发布
    {{# } else { }}
        未发布
    {{# }}}
</script>

<script src="${context}/external/jquery-1.12.4.min.js"></script>
<script src="${context}/external/layui/layui.all.js"></script>
<script src="${context}/config/questionList.js"></script>
</html>
