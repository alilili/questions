var table;

layui.use('table', function(){
    table = layui.table;

    //第一个实例
    table.render({
        elem: '#demo'
        ,url: '/questionConfig/data' //数据接口
        ,height : 580
        ,page: false //开启分页
        ,cols: [[ //表头
            {field: 'id', title: '主键',hide: true}
            ,{field: 'bt', title: '标题'}
            ,{field: 'publish', title: '状态',width: 80,templet :'#status'}
            ,{field: 'year', title: '调查年份', width:120}
            ,{field: 'createDate', title: '创建时间', width:180, sort: true}
            ,{field: 'operate', title: '操作', width: 200,toolbar:'#toolbar'}
        ]]
    });

    //监听工具条
    table.on('tool(test)', function(obj){
        var data = obj.data;
        if(obj.event === 'del'){
            top.layer.confirm('确定删除该记录吗？', function(index){
                delRecord(data.id);
                top.layer.closeAll();
            });
        } else if(obj.event === 'edit'){
            editRecord(data.id);
        } else if(obj.event === 'publish'){
            publish(data.id);
        }else if(obj.event === 'unPublish'){
            unPublish(data.id)
        }
    });
});

function publish(id){
    $.ajax({
        url: '/questionConfig/publish',
        type: 'post',
        async: false,
        data: {"id":id},
        success: function (data) {
            if(data.result){
                layer.alert("发布成功！")
                refreshData();
            }else{
                layer.alert("发布失败！")
            }
        }
    });
}

function unPublish(id){
    $.ajax({
        url: '/questionConfig/unPublish',
        type: 'post',
        async: false,
        data: {"id":id},
        success: function (data) {
            if(data.result){
                layer.alert("取消发布成功！")
                refreshData();
            }else{
                layer.alert("取消发布失败！")
            }
        }
    });
}

/**
 * 新增记录
 */
function addNewData(){
    top.layer.open({
        type: 2,
        area:['900px','650px'],
        shade:0.3,
        title:'问卷调查',
        content: '/questionConfig/index'
    });
    top.win = window;
}

/**
 * 刷新列表
 */
function refreshData() {
    table.reload("demo");
}

/**
 * 删除记录
 * @param id 要删除记录的ID
 */
function delRecord(id) {
    $.ajax({
        url:"/questionConfig/delRecord",
        type:'POST',
        dataType:'json',
        data:{id:id},
        success:function (value) {
            if(value.result) {
                top.layer.msg("操作成功");
                refreshData();
            }
        }
    })
}

function editRecord(id) {
    top.layer.open({
        type: 2,
        area:['900px','650px'],
        shade:0.3,
        title:'问卷调查',
        content: '/questionConfig/index?id='+id
    });
    top.win = window;
}