<%--
         This Jsp File Created by 李建成 
         @date 2016/12/2-15:41.on NJBD
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>行程详情</title>
    <jsp:include page="../include/compage.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="../../js/manager/pt.postmanager.js"></script>
</head>
<body>
<div id="toolbar">
    <span> 查询类型：</span>
    <select id="searchType">
        <option value="name">行程名称</option>
        <option value="url">行程地址</option>
    </select>
    <input id="searchName" type="text">
    </br>
</div>

<table id="devicetab" title="行程详情">
</table>

</body>
</html>
