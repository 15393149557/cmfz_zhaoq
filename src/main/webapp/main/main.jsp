<%@page pageEncoding="UTF-8" isELIgnored="false"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>持明法州后台管理系统</title>
    <link rel="icon" href="${path}/bootstrap/img/arrow-up.png" type="image/x-icon">
    <link rel="stylesheet" href="${path}/bootstrap/css/bootstrap.css">

    <%--引入jqgrid中主题css--%>
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/css/css/hot-sneaks/jquery-ui-1.8.16.custom.css">
    <link rel="stylesheet" href="${path}/bootstrap/jqgrid/boot/css/trirand/ui.jqgrid-bootstrap.css">
    <%--引入js文件--%>
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <script src="${path}/bootstrap/js/bootstrap.js"></script>
    <script src="${path}/bootstrap/jqgrid/js/i18n/grid.locale-cn.js"></script>
    <script src="${path}/bootstrap/jqgrid/boot/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${path}/bootstrap/js/ajaxfileupload.js"></script>

</head>
<body>
<!--顶部导航-->
<nav class="navbar navbar-default">
    <!--流式布局容器-->
    <div class="container-fluid">
        <!-- 导航条头 -->
        <div class="navbar-header ">
            <!--标题-->
            <h3>持明法洲管理系统</h3>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">

            <ul class="nav navbar-nav navbar-right">
                <li  class="dropdown">
                    <!--登录用户显示-->
                    <a class="dropdown-toggle" type="button"  >
                        用户：<b class="text text-danger">${sessionScope.flag}</b>
                    </a>
                </li>
                <li><a href="${path}/admin/exit"> 退出登录<span class="glyphicon glyphicon-log-out"></span></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>





<div class="container-fluid"><!--栅格系统-->
    <div class="row">
        <div class="col-lg-2">
            <!--添加手风琴的样式  面板组  这个id可用于让下面的面板对象只显示一个-->
            <div class="panel-group" id="accordion">

                <!--添加默认的面板样式-->
                <div class="panel panel-info">
                    <!--定义面板的头信息-->
                    <div class="panel-heading">

                        <h4 class="panel-title">
                            <!--添加展示或者折叠面板的触发器-->
                            <a  data-toggle="collapse" data-parent="#accordion" href="#collapseOne1">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <!--添加折叠面板的样式-->
                    <div id="collapseOne1" class="panel-collapse collapse" >
                        <!---->
                        <div class="panel-body">
                            <div align="center">
                                <a type="button-group" class="btn btn-warning" href="javascript:$('#rightPage').load('${path}/user/user.jsp')">用户信息</a><br/>
                                <br/>
                                <a type="button-group" class="btn btn-warning" href="javascript:$('#rightPage').load('${path}/user/statistic.jsp')">用户统计</a> <br/>
                                <br/>
                                <a type="button-group" class="btn btn-warning" href="javascript:$('#rightPage').load('${path}/user/userMap.jsp')">用户分布</a><br/>
                            </div>
                        </div>
                    </div>
                </div>

                <hr/>

                <!--添加默认的面板样式-->
                <div class="panel panel-success">
                    <!--定义面板的头信息-->
                    <div class="panel-heading">

                        <h4 class="panel-title">
                            <!--添加展示或者折叠面板的触发器-->
                            <a  data-toggle="collapse" data-parent="#accordion" href="#collapseOne2">
                                轮播图管理
                            </a>
                        </h4>
                    </div>
                    <!--添加折叠面板的样式-->
                    <div id="collapseOne2" class="panel-collapse collapse" >
                        <!---->
                        <div class="panel-body">
                            <div class="btn btn-warning"><a href="javascript:$('#rightPage').load('${path}/banner/banner.jsp')">轮播图展示</a></div>
                        </div>
                    </div>
                </div>

                <hr/>

                <!--添加默认的面板样式-->
                <div class="panel panel-warning">
                    <!--定义面板的头信息-->
                    <div class="panel-heading">

                        <h4 class="panel-title">
                            <!--添加展示或者折叠面板的触发器-->
                            <a  data-toggle="collapse" data-parent="#accordion" href="#collapseOne3">
                                专辑管理
                            </a>
                        </h4>
                    </div>
                    <!--添加折叠面板的样式-->
                    <div id="collapseOne3" class="panel-collapse collapse" >
                        <!---->
                        <div class="panel-body">
                            <div class="btn btn-warning"><a href="javascript:$('#rightPage').load('${path}/album/album.jsp')"> 专辑展示</a></div>
                        </div>
                    </div>
                </div>

                <hr/>

                <!--添加默认的面板样式-->
                <div class="panel panel-danger">
                    <!--定义面板的头信息-->
                    <div class="panel-heading">

                        <h4 class="panel-title">
                            <!--添加展示或者折叠面板的触发器-->
                            <a  data-toggle="collapse" data-parent="#accordion" href="#collapseOne4">
                                文章管理
                            </a>
                        </h4>
                    </div>
                    <!--添加折叠面板的样式-->
                    <div id="collapseOne4" class="panel-collapse collapse" >
                        <!---->
                        <div class="panel-body">
                            <div class="panel-body">
                                <div class="btn btn-warning"><a href="javascript:$('#rightPage').load('${path}/article/article.jsp')"> 文章展示</a></div>
                            </div><br/>
                            <div class="panel-body">
                                <div class="btn btn-warning"><a href="javascript:$('#rightPage').load('${path}/article/queryArticle.jsp')"> 文章查询</a></div>
                            </div>
                        </div>
                    </div>
                </div>

                <hr/>

                <!--添加默认的面板样式-->
                <div class="panel panel-primary">
                    <!--定义面板的头信息-->
                    <div class="panel-heading">

                        <h4 class="panel-title">
                            <!--添加展示或者折叠面板的触发器-->
                            <a  data-toggle="collapse" data-parent="#accordion" href="#collapseOne5">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <!--添加折叠面板的样式-->
                    <div id="collapseOne5" class="panel-collapse collapse" >
                        <!---->
                        <div class="panel-body">
                            <a href="#">用户列表</a> <br/>
                            <a href="#">添加用户</a>
                        </div>
                    </div>
                </div>

                <hr/>

                <!--添加默认的面板样式-->
                <div class="panel panel-info ">
                    <!--定义面板的头信息-->
                    <div class="panel-heading">

                        <h4 class="panel-title">
                            <!--添加展示或者折叠面板的触发器-->
                            <a  data-toggle="collapse" data-parent="#accordion" href="#collapseOne6">
                                用户管理
                            </a>
                        </h4>
                    </div>
                    <!--添加折叠面板的样式-->
                    <div id="collapseOne6" class="panel-collapse collapse" >
                        <!---->
                        <div class="panel-body">
                            <a href="#">用户列表</a> <br/>
                            <a href="#">添加用户</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <div class="col-lg-10" id="rightPage">

            <!--巨幕开始-->
            <div class="row">
                <div class="jumbotron">
                    <h2>欢迎来到持明法洲后台管理系统</h2>
                </div>
            </div>

            <!--右边轮播图部分-->
            <div class="row">

                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <!-- Indicators -->
                    <ol class="carousel-indicators">
                        <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="2"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="3"></li>
                        <li data-target="#carousel-example-generic" data-slide-to="4"></li>
                    </ol>

                    <!-- Wrapper for slides -->
                    <div class="carousel-inner" role="listbox">
                        <div class="item active" align="center">
                            <img src="${path}/bootstrap/img/1.png" alt="..." >
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item" align="center">
                            <img src="${path}/bootstrap/img/2.png" alt="...">
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item" align="center">
                            <img src="${path}/bootstrap/img/3.png" alt="..."  />
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item" align="center">
                            <img src="${path}/bootstrap/img/4.png" alt="..."  />
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                        <div class="item" align="center">
                            <img src="${path}/bootstrap/img/shouye.jpg" alt="..."  />
                            <div class="carousel-caption">
                                ...
                            </div>
                        </div>
                    </div>

                    <!-- Controls -->
                    <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>

                </div>
                <!--页脚-->

            </div>
            <div class="row" >
                <nav class="navbar navbar-default navbar-static-bottom" align="center">
                    @百知教育 zhaoq@zparkhr.com
                </nav>
            </div>
        </div>
    </div>
</div><!--栅格系统-->

</body>
</html>
