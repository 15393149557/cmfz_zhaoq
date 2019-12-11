<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>

    <script src="${path}/bootstrap/js/jquery.min.js"/>
    <!--引入echarts相关js-->
    <script src="${path}/echarts/js/echarts.min.js"/>
    <!--引入地图相关js-->
    <script src="${path}/echarts/js/china.js"/>

    <script type="text/javascript">

        $(function(){
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));


            //声明空数组
            var series=[]

            $.get("${path}/user/queryByUserChina",function(data){
                //遍历返回的数据
                for(var i=0;i<data.length;i++){

                    var e =data[i];

                    //将数据放入数组中
                    series.push(
                        {
                            name: e.sex,
                            type: 'map',
                            mapType: 'china',
                            label: {
                                normal: {
                                    show: false
                                },
                                emphasis: {
                                    show: true
                                }
                            },
                            data:e.citys
                        }
                    );
                }

                // 指定图表的配置项和数据
                var option = {
                    title : {
                        text: '用户分布',
                        subtext: '纯属虚构',
                        left: 'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data:['小男孩','小女孩']
                    },
                    visualMap: {
                        min: 0,
                        max: 2000,
                        left: 'left',
                        top: 'bottom',
                        text:['高','低'],           // 文本，默认为数值文本
                        calculable : true
                    },
                    toolbox: {
                        show: true,
                        orient : 'vertical',
                        left: 'right',
                        top: 'center',
                        feature : {
                            mark : {show: true},
                            dataView : {show: true, readOnly: false},
                            restore : {show: true},
                            saveAsImage : {show: true}
                        }
                    },
                    series : series
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);

            },"json");




        });

    </script>
</head>
<body>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>
</body>