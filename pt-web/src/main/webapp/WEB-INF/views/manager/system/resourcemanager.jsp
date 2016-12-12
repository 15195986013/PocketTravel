<%--
  Created by IntelliJ IDEA.
  User: LJC
  Date: 2016/10/21
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>权限资源管理</title>
    <jsp:include page="../include/compage.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="../../js/manager/pt.resourcemanager.js"></script>
</head>
<body>


<div id="toolbar">
    <span> 查询类型：</span>
    <select id="searchType">
        <option value="name">权限名称</option>
        <option value="url">权限地址</option>
    </select>
    <input id="searchName" type="text">
    </br>
</div>

<table id="devicetab" title="权限管理列表">
</table>


<div id="mdlg" class="easyui-dialog" style="width: 380px;height: 250px;top: 102px;padding: 40px;" closed="true"
     buttons="#mdlg-buttons">
    <form id="addfm" method="post">
        <table>
            <tr>
                <th class="rightTd">选择导航：</th>
                <td>
                    <select id="parentId" name="parentId">
                    </select>
                </td>
            </tr>
            <tr>
                <th class="rightTd">资源名称：</th>
                <td>
                    <input type="text" name="name" id="name" class="easyui-validatebox"
                           validType="length[1,12]" required="required" missingMessage="不能为空">
                    <input type="hidden" name="resourceId" id="resourceId" value="">
                </td>
            </tr>
            <tr>
                <th class="rightTd">资源地址：</th>
                <td>
                    <input type="text" name="url" id="url" class="easyui-validatebox"
                           validType="length[1,30]"
                           required="required" missingMessage="不能为空">
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="mdlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" id="saveButn" iconcls="icon-save">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#mdlg').dialog('close');"
       iconcls="icon-cancel">取消</a>
</div>


</body>
</html>
