<%--
         This Jsp File Created by 李建成 
         @date 2016/12/1-16:29.on NJBD
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../include/compage.jsp"></jsp:include>
    <script src="../../js/manager/pt.feedbackmanager.js"></script>
    <title>客户端意见反馈</title>
</head>
<body>

<div id="toolbar">
    查询类型：
    <select id="searchType">
        <option value="content">描述</option>
    </select>
    <input id="searchName" type="text" name="searchName">
    </br>
</div>

<table id="devicetab" title="意见反馈列表" style="margin:0px 0px 0px 0px;">
</table>
</body>
</html>
