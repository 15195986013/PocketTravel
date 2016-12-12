<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>角色管理</title>
    <jsp:include page="../include/compage.jsp" flush="true"></jsp:include>
    <script type="text/javascript" src="../../js/manager/pt.rolemanager.js"></script>
</head>
<body>


<div id="toolbar">
    查询类型：
    <select id="searchType">
        <option value="name">角色名称</option>
        <option value="description">角色描述</option>
    </select>
    <input id="searchName" type="text" name="searchName">
    </br>
</div>


<table id="devicetab" title="角色管理列表">

    <%--json 树 弹出框  约定  id mdlg  form id addfm   --%>
    <div id="treedlg" class="easyui-dialog" style="width: 240px; height: 420px; top: 20px; padding: 10px 20px;"
         closed="true"
         buttons="#dlg-buttons">
        <ul id="treefunction" class="easyui-tree" checkbox="true">
        </ul>
        <input id="roleIds" type="hidden" value="">
    </div>

    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" id="savefunButn" iconcls="icon-save">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#treedlg').dialog('close');"
           iconcls="icon-cancel">取消</a>
    </div>

</table>


<%--添加编辑 弹出框  约定  id mdlg  form id addfm   --%>

<div id="mdlg" class="easyui-dialog" style="width: 350px; height: 250px; top: 110px; padding: 10px 20px;" closed="true"
     buttons="#mdlg-buttons">
    <form id="addfm" method="post">
        <table>
            <tr>
                <td class="rightTd">角色名称：</td>
                <td>
                    <input type="text" name="name" id="name" class="easyui-validatebox" validType="length[2,16]"
                           required="required" missingMessage="不能为空">
                    <input type="hidden" name="roleId" id="roleId" value="">
                </td>
            </tr>
            <tr>
                <td class="rightTd">角色描述：</td>
                <td>
                    <textarea name="description" id="description"
                              class="easyui-validatebox"
                              validType="length[2,100]"
                              required="required"
                              missingMessage="不能为空"
                              style="overflow:auto;width:100%;height:50px;"></textarea>
                </td>
            </tr>
            <tr>
                <td class="rightTd">是否开启：</td>
                <td>
                    <select id="isEnabled" name="isEnabled">
                        <option value="0" selected>开启</option>
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


</body>
</html>
