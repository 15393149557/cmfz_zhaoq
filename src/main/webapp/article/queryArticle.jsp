<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<script>
    $(function(){

        //点击搜索按钮执行
        $("#searchBtn").click(function(){

            //清空表单
            $("#queryTable").empty();
            $("#queryTable").append("<tr>" +
                "<td>ID</td>" +
                "<td>标题</td>" +
                "<td>内容</td>" +
                "</tr>");

            //获取输入的内容
            var content =$("#searchInput").val();
            $.ajax({
                url:"${path}/article/elasticSearch",
                type:"post",
                dataType:"json",
                data:{"keyword":content},
                success:function(data){

                    //console.log(data);

                    //遍历集合数据
                    $.each(data,function(index,article){
                        $("#queryTable").append("<tr>" +
                            "<td>"+article.id+"</td>" +
                            "<td>"+article.title+"</td>" +
                            "<td>"+article.content+"</td>" +
                            "</tr>")
                    });
                }
            });
        });
    });

</script>


<div align="center">
    <div class="input-group" style="width: 30%" >
        <input id="searchInput" name="content" type="text" class="form-control" placeholder="请输入搜索内容" aria-describedby="basic-addon2">
        <span class="input-group-btn" id="basic-addon2">
            <button class="btn btn-info" id="searchBtn" >百知一下</button>
        </span>
    </div>
</div>