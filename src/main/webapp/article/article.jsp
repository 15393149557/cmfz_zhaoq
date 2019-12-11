<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!--引入kindeditor相关css-->
<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>

<head>

    <!--渲染页面数据的代码-->
    <script>
        $(function(){
            //创建表单
            $("#aTable").jqGrid({
                url : "${path}/article/queryAllArticles",   //page  当前页    rows 每页展示条数
                datatype : "json",
                rowNum : 2,
                rowList : [ 2,5,10, 20, 30 ],
                pager : '#aPage',
                viewrecords : true,  //是否展示总条数
                styleUI:"Bootstrap",
                height:"auto",
                autowidth:true,
                editurl:"${path}/article/edit",//定义增删改URL
                colNames : [ 'Id','标题','上传时间','上师名','内容', '上师id'],
                colModel : [
                    {name : 'id',width : 55},
                    {name : 'title',width : 150,sortable : false,editable:true},
                    {name : 'uploadTime',width : 150,sortable : false,editable:true,edittype:"date"},
                    {name : 'guruName',width : 150,sortable : false,editable:true},
                    {name : 'content',width : 150,sortable : false},
                    {name : 'guruId',width : 150,sortable : false}
                ]
            });

            //增删改查操作
            $("#aTable").jqGrid('navGrid', '#aPage', {edit : false,add : false,del : true,addtext:"添加",edittext:"编辑"});
        });


    </script>

    <!--对数据做操作的相关函数-->
    <script>
        //点击展示按钮后触发的事件
        $("#btn1").click(function () {
            //利用jqGrid中的方法getGridParam获取选中行的数据
            var rowId=$("#aTable").jqGrid("getGridParam","selrow");

            //判断选中行的id
            if(rowId!=null){

                //根据行id返回指定行的数据
                var row =$("#aTable").jqGrid("getRowData",rowId);

                //给标题的input设置内容
                $("#titles").val(row.title);
                //给作者的input设置内容
                $("#guruNames").val(row.guruName);
                //给kindeditor设置值
                KindEditor.html("#editor_id",row.content);

                //给模态框设置两个按钮
                $("#modalFooter").html("<button onclick='updateArticle(\""+rowId+"\")' class='btn btn-default'>保存</button >" +
                    "<button class='btn btn-primary' data-dismiss='modal'>关闭</button>"
                );

                //展示模态框
                $("#articleModal").modal("show");

            }else{
                alert("请选中一行");
            }
        });

        //点击添加文章
        $("#btn2").click(function(){

            //重置表单
            $("#articleForm")[0].reset();

            //给kindeditor设置值
            KindEditor.html("#editor_id","");

            //给模态框设置两个按钮
            $("#modalFooter").html("<button onclick='addArticle()' class='btn btn-default'>保存</button >" +
                "<button class='btn btn-primary' data-dismiss='modal'>关闭</button>"
            );

            //展示模态框
            $("#articleModal").modal("show");
        });

        //点击删除按钮出发的事件
        $("#btn3").click(function () {
            //获取选中行的id
           var idens=$("#aTable").jqGrid("getGridParam","selrow");

           if(idens!=null){
               $.ajax({
                   url:"${path}/article/edit",
                   type:"post",
                   data:{"id":idens,"oper":"del"},
                   dataType: "text",
                   success:function () {
                       $("#aTable").trigger("reloadGrid");
                   }
               })
           }else{
               alert("请选中一行！")
           }


        });

        //点击添加文章按钮
        function addArticle(){
            $.ajax({
                url:"${path}/article/add",
                type:"post",
                dataType:"text",
                data:$("#articleForm").serialize(),
                success:function(){
                    //关闭模态框
                    $("#articleModal").modal("hide");
                    //刷新表单
                    $("#aTable").trigger("reloadGrid");
                }
            });

        }

        //点击修改文章按钮
        function updateArticle(id){
            $.ajax({
                url:"${path}/article/update?id="+id,
                type:"post",
                datatype:"json",
                data:$("#articleForm").serialize(),
                success:function(){
                    //关闭模态框
                    $("#articleModal").modal("hide");
                    //刷新表单
                    $("#aTable").trigger("reloadGrid");
                }
            });
        }
    </script>

    <!--kindeditor生效的代码-->
    <script>
        KindEditor.create('#editor_id',{
            uploadJson:"${path}/editor/uploadPhoto",  //指定文件上传的路径
            filePostName:"img",  //指定上传文件的名  默认imgFile
            allowFileManager:true,//显示浏览远程图片的按钮
            fileManagerJson:"${path}/editor/photos",  //指定浏览远程图片的路径
            afterBlur:function(){  //失去焦点之后执行的方法
                this.sync();   //将kindeditor中的内容同步到表单中
            }
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
        <button id="btn1" class="btn btn-info">查看文章</button>&emsp;
        <button id="btn2" class="btn btn-success" >添加文章</button>&emsp;
        <button id="btn3" class="btn btn-warning">删除文章</button>
    </div>

    <%--初始化表单--%>
    <table id="aTable"/>

    <%--分页工具栏--%>
    <div id="aPage" />


</div>

<%--模态框--%>
<div id="articleModal" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content" style="width:730px">

            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="gridSystemModalLabel">文章信息展示</h4>
            </div>

            <%--模态框的内容--%>
            <div class="modal-body">
                <%--放一个表单--%>
                <form class="form-horizontal" id="articleForm">
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">标题</span>
                        <input id="titles" name="title" type="text" class="form-control"  aria-describedby="basic-addon1"/>
                    </div><br>


                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon3">作者</span>
                        <input id="guruNames" name="guruName" type="text" class="form-control" aria-describedby="basic-addon1"/>
                    </div><br>

                    <div class="input-group">
                        <%--引入输入框--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;"/>
                    </div>

                </form>
            </div>

            <%--模态框按钮--%>
            <div class="modal-footer" id="modalFooter">
                <%--<button type="button" class="btn btn-default">保存</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>