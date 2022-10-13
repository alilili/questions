$(function () {
    document.body.unselectstart = document.oncontextmenu = function (ev) { return false; }
});

layui.use(['form'], function(){
    form = layui.form
        , layer = layui.layer;

    layui.$('#submit_question').on('click', function(){

        //校验问题
        if(!checkQuestions()){
            return;
        }

        var configId = $("#configId").val();

        var questions = [];
        $(".questions .score").each(function(index, item){

            var $radio = $("input[name='question_"+(index+1)+"']:checked");

            var quesObj = {
                "questionId":$(item).attr("questionId"),
                "configId":configId ,
                "answer":$radio.val(),
                "answerText":$radio.attr("title"),
                "question":$(item).attr("questionContent"),
                "showOrder":(index+1)
            };
            questions.push(quesObj);
        });

        var userName = $("input[name='userName']").val();
        var age = $("input[name='age']").val();
        var mobile = $("input[name='mobile']").val();

        if(!userName){
            layer.alert("请填写您的姓名");
            return false;
        }

        if(age && age.length>3){
            layer.alert("请输入正确的年龄");
            return false;
        }

        if(mobile && mobile.length>15){
            layer.alert("联系方式不能超过15个字");
            return false;
        }

        var data = {
            "configId":configId,
            "year":$("#year").val(),
            "bt":$("#bt").val(),

            "userName":userName,
            "age":age,
            "mobile":mobile,
            "questions":JSON.stringify(questions)
        };

        layer.confirm('请确认是否提交，提交后不可修改？', function(index){
            top.layer.close(index);

            $.ajax({
                url: '/publish/save',
                type: 'post',
                data: data,
                success: function (result) {

                    if (result && result.type == "success") {

                        layer.alert("提交成功",function () {
                            //do something
                            //返回、关闭？
                            window.location.href ="/resources/success.html"
                        });
                    } else {
                        layer.alert(result.message||"提交失败！");
                    }
                }
            });
        });
    });
    
    function checkQuestions() {

        var flag = true;
        $(".questions .layui-form-item .score").each(function(index, item){

            var $obj = $(item);
            var value = $("input[name='question_"+(index+1)+"']:checked").val();
            if(value==""||value==null||value==undefined){
                layer.alert("请填写第"+(index+1)+"题");
                flag = false;
                return false;
            }
        });

        return flag;
    }
});

