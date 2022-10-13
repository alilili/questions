<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>统计</title>
  <link rel="stylesheet" type="text/css" href="${context}/external/layui/css/layui.css">
</head>
<body class="content" style="height: 100%">
<button type='button' class='layui-btn layui-btn-normal' onclick='exportExcel()'>导出Excel</button>
<div style="padding: 30px;">
  <div class="layui-row layui-col-space15">
    <div class="layui-col-md6">
      <div class="layui-panel">
        <div style="text-align: center">回收答卷数</div>
        <div style="text-align: center"><label id="quesCount" style="font-weight: bold;font-size: 18px;">0</label></div>
      </div>
    </div>
    <div class="layui-col-md6">
      <div class="layui-panel">
        <div style="text-align: center">满意度总得分</div>
        <div style="text-align: center"><label id="cStore" style="font-weight: bold;font-size: 18px;">0</label></div>
      </div>
    </div>
  </div>
</div>
<div class="content-con">
  <table id="demo" lay-filter="test"></table>
  <div style="text-align: center;width: 100%">
    <div style="text-align: center;font-color:#6a8ef5;font-weight: bold" id="barTitle"></div>
    <div id="main" style="width: 600px;height:400px;left:30%"></div>
    <div class="layui-form" style="width: 600px;margin-left:30%">
      <table class="layui-table" id="etable">
        <colgroup>
          <col>
          <col width="150">
          <col width="150">
        </colgroup>
        <thead>
        <tr>
          <th>选项</th>
          <th>百分比</th>
          <th>响应数</th>
        </tr>
        </thead>
        <tbody>

        </tbody>
      </table>
    </div>
  </div>
</div>
</body>

<script src="${context}/external/jquery-1.12.4.min.js"></script>
<script src="${context}/external/layui/layui.all.js"></script>
<script src="${context}/external/echarts/echarts.min.js"></script>
<script src="${context}/census/js/totalCensus.js"></script>
<script type="text/html" id="question_Tpl">
  <a href='javascript:void(0);' onClick=checkReport('{{d["questionid"]}}','{{d["question"]}}')>
      {{d["question"]}}
  </a>
</script>
</html>
