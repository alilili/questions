var table;
var form;
var barData=[];

layui.use(['table','form'], function(){
    table = layui.table;
    form = layui.form;

    //第一个实例
    table.render({
        elem: '#demo'
        ,url: '/census/totalData' //数据接口
        ,height : 300
        ,page: false //开启分页
        ,cols: [[ //表头
            {field: 'question',align:'center', title: '维度',templet :'#question_Tpl'}
            ,{field: 'excellent',align:'center', title: '优秀率',width: 120}
            ,{field: 'good',align:'center', title: '良好率',width: 120}
            ,{field: 'quiteGood',align:'center', title: '较好率',width: 120}
            ,{field: 'commonly',align:'center', title: '一般率',width: 120}
            ,{field: 'poor',align:'center', title: '较差率',width: 120}
        ]]
    });


});

$(document).ready(function(){
    document.body.unselectstart = document.oncontextmenu = function (ev) { return false; }

    $.ajax({
        url: '/census/totalTopData',
        type: 'post',
        success: function (data) {
            $("#quesCount").html(data.total);
            $("#cStore").html(data.cstore);
            layui.form.render();
        }
    });

    bindEcharts();
})

function checkReport(questionId,questionName){
    $.ajax({
        url: '/census/getTotalBarData',
        data:{questionId:questionId},
        type: 'post',
        success: function (data) {
            barData=[];
            let d = data.data;
            barData.push(d.excellent);
            barData.push(d.good);
            barData.push(d.quiteGood);
            barData.push(d.commonly);
            barData.push(d.poor);
            bindEcharts();
            $("#barTitle").html(questionName);
            bindEhartTable(d);

        }
    });
}

function bindEhartTable(data){
    let table = $("#etable tbody");
    table.empty();
    console.log(data);
    let html = "<tr><td>选项1：优秀</td><td>" + data.excellent + "</td><td>" + data.excellentPer + "</td></tr>";
    html += "<tr><td>选项2：良好</td><td>" + data.good + "</td><td>" + data.goodPer + "</td></tr>";
    html += "<tr><td>选项3：较好</td><td>" + data.quiteGood + "</td><td>" + data.quiteGoodPer + "</td></tr>";
    html += "<tr><td>选项4：一般</td><td>" + data.commonly + "</td><td>" + data.commonlyPer + "</td></tr>";
    html += "<tr><td>选项5：较差</td><td>" + data.poor + "</td><td>" + data.poorPer + "</td></tr>";

    table.append(html);
    form.render();
}

function exportExcel(){
    var url = "/census/exportExcel?year=2022&quesCount="+$("#quesCount").html()+"&cStore="+$("#cStore").html();
    var aTag = document.getElementById("SEARCH_DOWNLOAD_A");
    if (aTag == null) {
        aTag = document.createElement("A");
        aTag.id = "SEARCH_DOWNLOAD_A";
        document.body.appendChild(aTag);
    }
    aTag.href = url;
    aTag.download = "";
    aTag.click();
}

function bindEcharts(){
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {},
        xAxis: {
            data: ['优秀', '良好', '较好', '一般', '较差']
        },
        yAxis: {
            // max:100,
            // min:0
        },
        series: [
            {
                name: '次数',
                type: 'bar',
                data: barData
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
}

/**
 * 刷新列表
 */
function refreshData() {
    table.reload("demo");
}
