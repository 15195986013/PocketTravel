<%--
         This Jsp File Created by 李建成 
         @date 2016/12/5-11:07.on NJBD
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../include/compage.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="../../js/manager/pt.drivermanager.js"></script>
    <title>司机审核</title>
</head>
<body>

<div id="toolbar">
    <span> 查询类型：</span>
    <select id="searchType">
        <option value="name">司机名称</option>
        <option value="info">司机详情</option>
    </select>
    <input id="searchName" type="text">
    </br>
</div>

<table id="devicetab" title="司机审核">
</table>


</body>
</html>
