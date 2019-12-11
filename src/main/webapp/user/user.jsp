<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<head>
    <script>
        $(function(){
            //创建表单
            $("#usTable").jqGrid({
                url : "${path}/user/queryAllUsers",   //page  当前页    rows 每页展示条数
                datatype : "json",
                rowNum : 2,
                rowList : [ 2,5,10, 20, 30 ],
                pager : '#aPage',
                viewrecords : true,  //是否展示总条数
                styleUI:"Bootstrap",
                height:"auto",
                autowidth:true,
                editurl:"${path}/user/edit",//定义增删改URL
                colNames : [ 'Id','图片','名字','法名','密码','salt','状态','性别','城市','签名',  '注册时间', '上师id'],
                colModel : [
                    {name : 'id',width : 55},
                    {name : 'picImg',width : 90,align:"center",editable:true,edittype:"file",
                        formatter:function(cellvalue, options, rowObject){
                            return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:180px;height:80px'/>";
                        }
                    },
                    {name : 'name',width : 150,sortable : false,editable:true},
                    {name : 'legalName',width : 150,sortable : false,editable:true},
                    {name : 'password',width : 150,sortable : false,editable:true},
                    {name : 'salt',width : 150,sortable : false},
                    {name : 'status',width : 150,sortable : false,
                        formatter:function(cellvalue, options, rowObject){
                            if(cellvalue=="1"){
                                return "<button class='btn btn-success' onclick='changeStatus(\""+rowObject.id+"\",\""+cellvalue+"\")'>冻结</button>";
                            }else{
                                return "<button class='btn btn-danger' onclick='changeStatus(\""+rowObject.id+"\",\""+cellvalue+"\")'>解冻</button>";
                            }
                        }
                    },
                    {name : 'sex',width : 150,sortable : false,editable:true},
                    {name : 'city', width : 100,editable:true,editable:true},
                    {name : 'sign',width : 80,align : "right",align:"center",editable:true},
                    {name : 'regTime',width : 150,sortable : false},
                    {name : 'guruId',width : 150,sortable : false,editable:true},
                ]
            });

            //增删改查操作
            $("#usTable").jqGrid('navGrid', '#usPage', {edit : true,add : true,del : true,addtext:"添加",edittext:"编辑"});
        });

        function changeStatus(id,status) {
            if(status=="0"){
                $.ajax({
                    url:"${path}/user/edit",
                    type:"post",
                    datatype:"json",
                    data:{"id":id,"status":"1","oper":"edit"},
                    success:function(){
                        //刷新表单
                        $("#usTable").trigger("reloadGrid");
                    }
                });
            }else{
                $.ajax({
                    url:"${path}/user/edit",
                    type:"post",
                    datatype:"json",
                    data:{"id":id,"status":"0","oper":"edit"},
                    success:function(){
                        //刷新表单
                        $("#usTable").trigger("reloadGrid");
                    }
                });
            }
        }
    </script>

    <script>
        $("#btns").click(function () {
            location.href = "${path}/user/export";
        });
    </script>
</head>
<body>
<div class="panel panel-info">

    <%--面板标题--%>
    <div class="panel panel-heading">
        <h3>用户管理</h3>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs">
        <li class="active"><a >用户管理</a></li>
    </ul>

    <div class="panel panel-body">
        <button id="btns" class="btn btn-info">导出数据</button>
    </div>

    <%--初始化表单--%>
    <table id="usTable"/>

    <%--分页工具栏--%>
    <div id="usPage" />

</div>
</body>