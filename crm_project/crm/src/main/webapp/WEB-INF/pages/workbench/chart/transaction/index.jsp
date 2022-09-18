<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <title>Title</title>
    <%--jquery--%>
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <%--echarts.min.js--%>
    <script type="text/javascript" src="jquery/echars/echarts.min.js"></script>

    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));


            /**
             * 完整显示漏斗图需要先向后台发送请求，获取数据
             * 以组查询数据
             */
                // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '交易信息'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c}%'
                },
                toolbox: {
                    feature: {
                        dataView: { readOnly: false },
                        restore: {},
                        saveAsImage: {}
                    }
                },
                legend: {
                    data: ['Show', 'Click', 'Visit', 'Inquiry', 'Order']
                },
                series:
                    {
                        name: 'Expected',
                        type: 'funnel',
                        left: '10%',
                        width: '80%',
                        label: {
                            formatter: '{b}Expected'
                        },
                        labelLine: {
                            show: false
                        },
                        itemStyle: {
                            opacity: 0.7
                        },
                        emphasis: {
                            label: {
                                position: 'inside',
                                formatter: '{b}Expected: {c}%'
                            }
                        },
                        data: [
                            { value: 60, name: 'Visit' },
                            { value: 40, name: 'Inquiry' },
                            { value: 20, name: 'Order' },
                            { value: 80, name: 'Click' },
                            { value: 100, name: 'Show' }
                        ]
                    },
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);


            //结尾标签
        })
    </script>
</head>
<body>

    <!-- 为 ECharts 准备一个定义了宽高的 DOM -->
    <div id="main" style="width: 600px;height:400px;"></div>

</body>
</html>
