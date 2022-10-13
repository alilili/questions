layui.use(['layer', 'form'], function () {

    layui.form.on('submit(submit)', function (data) {
        var orgName = data.field.orgName.trim();
        var showOrder = data.field.showOrder;
        if(!orgName){
            top.layer.msg("部门名称不能为空", {icon: 5});
            return false;
        }
        if(!showOrder){
            data.field.showOrder = 1;
        }

        var postData = $.extend(true,{},data.field);
        var pk = $("#pk").val();
        if(pk){
            postData.id = pk;
        }

        $.ajax({
            url: "/deptConfig/save",
            type: 'post',
            async: false,
            data: postData,
            success: function (data) {
                if (data && data.type == "success") {

                    layer.msg("保存成功", {icon: 1,time:1000}, function () {
                        // parent.location.reload();
                        top.layer.closeAll();
                    });
                } else {
                    layer.msg(data.message, {icon: 5});
                }
            }
        });
        return false;
    });
});




function modify(id) {
    var url = "/deptConfig/form?id="+id;
    layer.open({
        type: 2,
        area: [1300+'px', 600 +'px'],
        fixed: false, //不固定
        title: "",
        content: url,  //url 为子布局的url路径
        success:function (layero,index) {
            loadTable();
        }
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
