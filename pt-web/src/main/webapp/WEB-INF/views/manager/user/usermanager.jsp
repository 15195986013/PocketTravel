<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户管理</title>
    <jsp:include page="../include/compage.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="../../js/manager/pt.usermanager.js"></script>

</head>
<body>
<div id="toolbar">
    <span> 查询类型：</span>
    <select id="searchType">
        <option value="account">用户名</option>
    </select>
    <input id="searchName" type="text" name="searchName">
    </br>
</div>
<table id="devicetab" title="用户管理列表">
    <div id="treedlg" class="easyui-dialog" style="width: 240px; height: 420px; padding: 10px 20px;" closed="true"
         buttons="#dlg-buttons">
        <ul id="treefunction" class="easyui-tree" checkbox="true">
        </ul>
        <input id="adminIds" type="hidden" value="">
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" id="savefunButn" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#treedlg').dialog('close');"
           iconcls="icon-cancel">取消</a>
    </div>

</table>
<%-- 添加编辑 --%>
<div id="mdlg" class="easyui-dialog" style="width: 350px; top: 110px; height: 250px; padding: 10px 20px;" closed="true"
     buttons="#mdlg-buttons">
    <form id="addfm" method="post">
        <table>
            <tr>
                <td class="rightTd">账号:</td>
                <td>
                    <input type="text" class="easyui-validatebox" name="account" id="account" required="true"
                           validType="length[1,12]" missingMessage="不能为空">
                    <input type="hidden" name="adminId" id="adminId" value="">
                </td>
            </tr>
            <tr>
                <td class="rightTd">密码:</td>
                <td>
                    <input type="password" class="easyui-validatebox" name="password" id="password" required="true"
                           validType="length[1,12]" missingMessage="不能为空">
                </td>
            </tr>
            <tr>
                <td class="rightTd">是否开启:</td>
                <td>
                    <select id="isEnabled" name="isEnabled">
                        <option value="0" selected="selected">开启</option>
                        <option value="1">关闭</option>
                    </select>
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
</div>

</body>
</html>
