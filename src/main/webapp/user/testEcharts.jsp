<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- 引入 ECharts 文件 -->
    <script type="application/javascript" src="${path}/echarts/js/echarts.min.js"></script>
</head>
    <body>
        <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
        <div align="center">
            <div id="main" style="width: 600px;height:400px;" />
        </div>
        <script type="text/javascript">
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '用户月注册统计',  //标题
                    link:"${path}/main/main.jsp", //主标题超链接
                    subtext:"用户信息" //副标题
                },
                tooltip: {},  //鼠标提示
                legend: {
                    data:['小男孩',"小女孩"]  //选项卡
                },
                xAxis: {  //横轴数据
                    data: ["1月","2月","3月","4月","5月","6月"]
                },
                yAxis: {},  //纵轴数据   自适应
                series: [{
                    name: '小男孩',
                    type: 'bar',  //图标的类型
                    data: [100, 200, 360, 100, 900, 200]  //数据
                },{
                    name: '小女孩',
                    type: 'line',  //图标的类型
                    data: [50, 200, 500, 100, 600, 200]  //数据
                }]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        </script>
    </body>
</html>