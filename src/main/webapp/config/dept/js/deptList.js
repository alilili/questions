$(function(){

    loadTable();
});

function loadTable() {
    $.ajax({
        url: '/deptConfig/getOrganizesList',
        data:{type:"all"},
        type: 'post',
        success: function (data) {
            if (data ) {
                var rows = data;
                if(rows&&rows!=""){

                    var sb = new StringBuffer();

                    for(var i=0;i<rows.length;i++){

                        var row = rows[i];

                        sb.append("<tr>");
                        sb.append("<td>"+row["orgName"]+"</td>");
                        sb.append("<td>"+row["showOrder"]+"</td>");
                        sb.append("<td>"+row["createTime"]+"</td>");
                        sb.append("<td>"+(row["status"]=="1"?"启用":"<span style='color: red'>停用</span>")+"</td>");
                        sb.append("<td><a class=\"layui-btn layui-btn-xs\" href='javascript:void(0);' onclick=\"modify('"+row["id"]+"')\">修改</a>" +
                            "&nbsp;&nbsp;<a class=\"layui-btn layui-btn-danger layui-btn-xs\" href='javascript:void(0);' onclick=\"deleteOrg('"+row["id"]+"')\">删除</a></td>");
                        sb.append("</tr>");
                    }
                    $("#deptData").empty().append(sb.toString());
                }
            } else {
                layer.msg("部门数据加载失败", {icon: 5});
            }
        }
    });
}

function modify(id) {
    var url = "/deptConfig/form?id="+id;
    top.layer.open({
        type: 2,
        area: [600+'px', 400 +'px'],
        fixed: true, //不固定
        title: "",
        content: url,  //url 为子布局的url路径
        success:function (layero,index) {
            // loadTable();
        },end:function (layero,index) {
            loadTable();
        }
    });
}


function deleteOrg(id) {

    layer.confirm('请确认是否删除?', {
        icon: 3,
        title: '提示',
        btn: ['确认', '取消']
    }, function (dindex) {

        top.layer.close(dindex);
        $.ajax({
            url: "/deptConfig/delete",
            type: 'post',
            data: {"id": id},
            success: function (data) {
                if (data && data.type == "success") {

                    layer.msg("删除成功", {icon: 1,time:1000},function () {
                        loadTable();
                    });
                } else {
                    layer.msg(data.message, {icon: 5});
                }
            }
        });

    }, function () {
        //do nothing
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
