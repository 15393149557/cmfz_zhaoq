<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function () {
        jQuery("#abTable").jqGrid(
            {
                //url:"/album/album.json",
                url :"${path}/album/queryAll",
                datatype : "json",
                rowNum : 2,
                rowList : [ 2,8, 10, 20, 30 ],
                pager : '#alPage',
                viewrecords : true,
                height : "auto",
                autowidth:true,
                editurl:"${path}/album/edit",//定义增删改URL
                styleUI:"Bootstrap",
                colNames : [ 'Id', '名称',"封面",'评分', '作者','播音', '集数', '内容','时间' ],
                colModel : [
                    {name : 'id',index : 'id',  width : 55},
                    {name : 'title',index : 'invdate',width : 90,editable:true},
                    {name : "coverImg",index : "item",  width : 130,editable:true,edittype:"file",
                        formatter:function(cellvalue, options, rowObject){
                            console.log(cellvalue+options+rowObject);
                            return "<img src='${path}/upload/photo/"+cellvalue+"' style='width:180px;height:80px' />";
                        }

                    },
                    {name : 'score',index : 'score',width : 100,editable:true},
                    {name : 'author',index : 'name',width : 100,editable:true},
                    {name : 'broadcast',index : 'broad',width : 100,editable:true},
                    {name : 'count',index : 'amount',width : 80,align : "right",editable:true},
                    {name : 'countent',index : 'tax',width : 80,align : "right",editable:true},
                    {name : 'pubDate',index : 'note',width : 150,sortable : false,editable:true,edittype: "date"}
                ],
                subGrid : true, //是否开启子表格
                //1.subgrid_id 点击一行会在父表各种创建一个div来容纳子表格  subgrid_id就是这个div的id
                //2.row_id  点击行的id
                subGridRowExpanded : function(subgrid_id, row_id) {
                    addSubGrid(subgrid_id, row_id);
                }
            });
        //父表格 工具栏
        $("#abTable").jqGrid('navGrid', '#alPage', {add : true,edit : true,del : true},
            {
                closeAfterEdit:true, //关闭添加框
                beforeShowForm:function(obj){
                    obj.find("#cover_img").attr("disabled",true);
                }
            },   //修改之后的额外操作
            {
                closeAfterAdd:true, //关闭添加框
                afterSubmit:function (data) {  //提交之后执行的方法
                    //文件的上传
                    $.ajaxFileUpload({
                        url:"${path}/album/uploadAlbum",
                        type:"post",
                        datatype:"json",
                        data:{id:data.responseText},  //获取id
                        fileElementId:"cover_img",  //需要上传的文件域的ID，即<input type="file">的ID
                        success:function(){
                            //刷新表单
                            $("#abTable").trigger("reloadGrid");
                        }
                    });
                    return "hehe";  //必须要有返回值  返回值随便写
                }
            },   //添加之后的额外操作
            {}    //删除之后的额外操作
        );
    });

    //子表格
    function addSubGrid(subgridId, rowId){

        var subgridTableId= subgridId + "Table";
        var pagerId= subgridId+"Page";

        $("#"+subgridId).html("<table id='"+subgridTableId+"' />" +
            "<div id='"+pagerId+"'/>"
        );


        //子表格
        $("#" + subgridTableId).jqGrid({
            //url : "/chapter/queryByPage?AlbumId=" + rowId,
            url:"${path}/chapter/queryAll?rowId="+rowId,
            datatype : "json",
            rowNum : 5,
            rowList : [ 5,8, 10, 20, 30 ],
            pager : "#"+pagerId,
            sortname : 'num',
            sortorder : "asc",
            styleUI:"Bootstrap",
            height : "auto",
            viewrecords : true,
            autowidth:true,
            editurl:"${path}/chapter/edit?albumId="+rowId,//定义增删改URL
            colNames : [ 'Id', '名字','src','大小', '时长','日期' ,"操作"],
            colModel : [
                {name : "id",  index : "num",width : 80,key : true},
                {name : "title",index : "item",  width : 130,editable:true,editable:true},
                {name : "src",index : "src" ,  width : 130,editable:true,editable:true,edittype: "file"},
                {name : "size",index : "qty",width : 70,align : "right"},
                {name : "duration",index : "unit",width : 70,align : "right"},
                {name : "uploadTime",index : "total",width : 70,align : "right",editable:true,edittype: "date"},
                {name : "src",align:"center",
                    formatter:function(cellvalue, options, rowObject){

                        return "<a href='#' onclick='downloadAudio(\""+cellvalue+"\")'><span class='glyphicon glyphicon-download' /></a>&nbsp;&emsp;&emsp;" +
                            "<a href='#' onclick='audioPlay(\""+cellvalue+"\")'><span class='glyphicon glyphicon-play-circle' /></a>";
                    }
                }
            ]
        });

        //子表格的正删改查操作
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId, {edit : true,add : true,del : true},
            {
                closeAfterEdit:true //关闭添加框
            },   //修改之后的额外操作
            {
                closeAfterAdd:true, //关闭添加框
                afterSubmit:function (data) {  //提交之后执行的方法
                    //文件的上传
                    $.ajaxFileUpload({
                        url:"${path}/chapter/uploadChapter",
                        type:"post",
                        datatype:"json",
                        data:{id:data.responseText},  //获取id
                        fileElementId:"src",  //需要上传的文件域的ID，即<input type="file">的ID
                        success:function(){
                            //刷新表单
                            $("#" + subgridTableId).trigger("reloadGrid");
                        }
                    });
                    return "hehe";  //必须要有返回值  返回值随便写
                }
            },   //添加之后的额外操作
            {}    //删除之后的额外操作
        );
    }

    //下载
    function downloadAudio(fileName) {
        location.href = "${path}/chapter/download?fileName="+fileName;
    }

    //下载
    function audioPlay(fileName) {
        //展示模态框
        $("#AudioModal").modal("show");

        //给音频标签设置值
        $("#myAudio").attr("src","${path}/upload/audio/"+fileName);
    }
</script>
<div class="panel panel-info">

    <%--面板标题--%>
    <div class="panel panel-heading">
        <h3>专辑管理</h3>
    </div>

    <%--标签页--%>
    <ul class="nav nav-tabs">
        <li class="active"><a >专辑管理</a></li>
    </ul>

    <%--初始化表单--%>
    <table id="abTable"/>

    <%--分页工具栏--%>
    <div id="alPage" />

    <%--播放的模态框--%>
    <div id="AudioModal" class="modal fade" tabindex="-1" role="dialog">
        <div class="modal-dialog" role="document">
            <audio id="myAudio" src="" controls/>
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->

</div>