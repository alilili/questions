let depts,form,layer;

$(function(){
    $.ajax({
        url: '/deptConfig/getOrganizesList',
        type: 'post',
        success: function (data) {
            depts = data;
        }
    });
});


layui.use(['form', 'laydate'], function(){
    form = layui.form
        ,laydate = layui.laydate
        , layer = layui.layer;

    laydate.render({
        elem: '#startDate'
    });
    laydate.render({
        elem: '#endDate'
    });


    //表单取值
    layui.$('#add_question').on('click', function(){
        if(!depts||depts.length===0){
            layer.alert("请先维护部门！");
            return false;
        }

        var sb = new StringBuffer();

        sb.append("<tr class='question_tr'>");
        sb.append("<td>");
        sb.append("<div class='layui-input-inline'>");
        sb.append("<select class='org' name='org' id='org' lay-filter='org'>");
        for(var i = 0;i<depts.length;i++){
            var dept = depts[i];
            sb.append("<option value='"+dept.id+"'>"+dept.orgName+"</option>")
        }
        sb.append("</select>")
        sb.append("</div>");
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<div class='layui-input-inline' style='width: 100%;'>");
        sb.append("<textarea placeholder='请输入内容' class='layui-textarea question'></textarea>")
        sb.append("</div>");
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<input type='text' class='layui-input showorder'>")
        sb.append("</td>");
        sb.append("<td>");
        sb.append("<button type='button' class='layui-btn layui-btn-normal' onclick='deleteQuestion(this)'>删除</button>");
        sb.append("</td>");
        sb.append("</tr>");

        $("#question_content").append(sb.toString());

        form.render("select");

    });

    layui.$('#save_question').on('click', function(){

        let flag = true;
        let id = getQueryVariable("id");

        if(id==null||id===""){
            $.ajax({
                url: '/questionConfig/check',
                type: 'post',
                async: false,
                data: {"year":$("#year").val()},
                success: function (data) {
                    if(data.result!=null) {
                        layer.alert("当前年份已经存在调查问卷维护！");
                        flag = false;
                    }
                }
            });
        }

        if(!flag){
            return;
        }

        let questionTr = $(".question_tr");

        if(questionTr.length < 1){
            layer.alert("请维护考核指标！");
            return;
        }

        var question = new Array;
        for(var i = 0;i<questionTr.length;i++){

            var obj = questionTr[i];

            var quesObj = {
                "subId":$(obj).find(".subId").val(),
                "org":$(obj).find(".org").val(),
                "showOrder":$(obj).find(".showorder").val(),
                "question":$(obj).find(".question").val()
            }
            question.push(quesObj);
        }

        var data = {
            "id":getQueryVariable("id"),
            "year":$("#year").val(),
            "bt":$("#bt").val(),
            "mark":$("#mark").val(),
            "startDate":$("#startDate").val(),
            "endDate":$("#endDate").val(),
            "questions":JSON.stringify(question)
        };
        $.ajax({
            url: '/questionConfig/save',
            type: 'post',
            data: data,
            success: function (result) {
                if(result.result){
                    top.layer.alert("操作成功",function () {
                        top.win.refreshData();
                        top.layer.closeAll();
                    })
                }else{
                    top.layer.alert("操作失败！");
                }
            }
        });
    });

    layui.$('#publish_question').on('click', function(){
        $.ajax({
            url: '/questionConfig/publish',
            type: 'post',
            async: false,
            data: {"id":getQueryVariable("id")},
            success: function (data) {
                if(data.result){
                    layer.alert("发布成功！",function () {
                        top.win.refreshData();
                        top.layer.closeAll();
                    })
                }else{
                    layer.alert("发布失败！")
                }
            }
        });
    });

    layui.$('#unPublish_question').on('click', function(){
        $.ajax({
            url: '/questionConfig/unPublish',
            type: 'post',
            async: false,
            data: {"id":getQueryVariable("id")},
            success: function (data) {
                if(data.result){
                    layer.alert("取消发布成功！",function () {
                        top.win.refreshData();
                        top.layer.closeAll();
                    })
                }else{
                    layer.alert("取消发布失败！")
                }
            }
        });
    });

});

function getQueryVariable(variable) {

    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return "";
}

function deleteQuestion(obj,subId) {

    layer.confirm('确定删除该记录吗？', function(index){
        layer.close(index);//关闭confirm
        if(subId==null){
            $(obj).parents("tr").remove();
            return;
        }

        $.ajax({
            url: '/questionConfig/deleteSub',
            type: 'post',
            data: {subId:subId},
            success: function (result) {
                if(result.result){
                    layer.alert("操作成功");
                    $(obj).parents("tr").remove();
                }else{
                    alert("操作失败！");
                }
            }
        });
    });
}

function StringBuffer(){
    this.content = new Array;
}

StringBuffer.prototype.append = function(str){
    this.content.push(str);
    return this ;
};

StringBuffer.prototype.toString = function(){
    return this.content.join("");
};