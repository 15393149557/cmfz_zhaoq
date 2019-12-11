<%@ page pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
<script>
    KindEditor.ready(function(K) {
        window.editor = K.create('#editor_id',{
            //width:"1000px",
            //height:"100px",
            //minWidth:10,
            //minheight:20,
            /*items:[
                'source', '|', 'undo', 'redo', '|', 'preview', 'print', 'template', 'code', 'cut', 'copy', 'paste',
                'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
                'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
                'superscript', 'clearhtml', 'quickformat', 'selectall', '|', 'fullscreen', '/',
                'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'multiimage',
                'flash', 'media', 'insertfile', 'table', 'hr', 'emoticons', 'baidumap', 'pagebreak',
                'anchor', 'link', 'unlink', '|', 'about'
            ],*/
            uploadJson:"${path}/editor/uploadPhoto",  //指定文件上传的路径
            filePostName:"img",  //指定上传文件的名  默认是imgFile
            allowFileManager:true,//显示浏览远程图片的按钮
            fileManagerJson:"${path}/editor/photos"  //指定浏览远程图片的路径
        });
    });
</script>

<div align="center">
    <%--引入输入框--%>
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
            &lt;strong&gt;HTML内容&lt;/strong&gt;
    </textarea>
</div>
